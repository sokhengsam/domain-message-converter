/**
 * 
 */
package com.openidea.converter.xml;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.openidea.converter.MediaType;
import com.openidea.converter.exception.DomainMessageConverterException;

/**
 * @author sokheng
 *
 */
public class Jaxb2XmlMessageConverter extends AbstractXmlMessageConverter {

	private final ConcurrentMap<Class<?>, JAXBContext> jaxbContexts = new ConcurrentHashMap<Class<?>, JAXBContext>(); 
	
	public Jaxb2XmlMessageConverter() {
		super(new MediaType("application", "xml"));
	}

	/* (non-Javadoc)
	 * @see com.openidea.converter.xml.AbstractXmlMessageConverter#readStream(java.lang.Class, java.io.InputStream)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> T readStream(Class<? extends T> clazz, InputStream inputStream) throws DomainMessageConverterException{
		final Unmarshaller unmarshaller = createUnmarshaller(clazz);
		try {
			T domain = (T) unmarshaller.unmarshal(inputStream);
			return domain;
		} catch (JAXBException e) {
			throw new DomainMessageConverterException(String.format("Unable to parse domain from inputstream, cause by %s", e.getMessage())); 
		}
	}

	/* (non-Javadoc)
	 * @see com.openidea.converter.xml.AbstractXmlMessageConverter#writeStream(java.lang.Object, java.io.OutputStream, java.nio.charset.Charset)
	 */
	@Override
	public <T> void writeStream(T body, OutputStream outputStream, Charset charset) {
		final Marshaller marshaller = createMarshaller(body.getClass());
		try {
			marshaller.marshal(body, outputStream);
		} catch (JAXBException e) {
			throw new DomainMessageConverterException(String.format("Unable to write domain to output stream, cause by %s", e.getMessage()));
		}
	}
	
	/**
	 * 
	 * @param clazz
	 * @return
	 */
	public final JAXBContext getJAXBContext(Class<?> clazz) throws DomainMessageConverterException{
		JAXBContext jaxbContext = jaxbContexts.get(clazz);
		if(null == jaxbContext){
			try {
				jaxbContext = JAXBContext.newInstance(clazz);
				jaxbContexts.putIfAbsent(clazz, jaxbContext);
			} catch (JAXBException e) {
				throw new DomainMessageConverterException(String.format("Initiate jaxb context instance, cause by %s", e.getMessage()));
			}
		}
		return jaxbContext;
	}
	
	/**
	 * 
	 * @param clazz
	 * @return
	 */
	public final Marshaller createMarshaller(Class<?> clazz) throws DomainMessageConverterException{
		try {
			
			return getJAXBContext(clazz).createMarshaller();
		} catch (JAXBException e) {
			throw new DomainMessageConverterException(String.format("Issue with initiate marshaller instance, cause by %s", e.getMessage()));
		}
	}
	
	/**
	 * 
	 * @param clazz
	 * @return
	 */
	public final Unmarshaller createUnmarshaller(Class<?> clazz) throws DomainMessageConverterException{
		try {
			return getJAXBContext(clazz).createUnmarshaller();
		} catch (JAXBException e) {
			throw new DomainMessageConverterException(String.format("Issue with initiate ummarshaller instance, cause by %s", e.getMessage()));
		}
	}
}

