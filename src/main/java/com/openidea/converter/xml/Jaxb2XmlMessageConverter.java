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

/**
 * @author sokheng
 *
 */
public class Jaxb2XmlMessageConverter extends AbstractXmlMessageConverter {

	private final ConcurrentMap<Class<?>, JAXBContext> jaxbContexts = new ConcurrentHashMap<Class<?>, JAXBContext>(); 
	
	public Jaxb2XmlMessageConverter(MediaType mediaType) {
		super(mediaType);
	}

	/* (non-Javadoc)
	 * @see com.openidea.converter.xml.AbstractXmlMessageConverter#readStream(java.lang.Class, java.io.InputStream)
	 */
	@Override
	public <T> T readStream(Class<? extends T> clazz, InputStream inputStream) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.openidea.converter.xml.AbstractXmlMessageConverter#writeStream(java.lang.Object, java.io.OutputStream, java.nio.charset.Charset)
	 */
	@Override
	public <T> T writeStream(T body, OutputStream outputStream, Charset charset) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 
	 * @param clazz
	 * @return
	 */
	public final JAXBContext getJAXBContext(Class<?> clazz){
		JAXBContext jaxbContext = jaxbContexts.get(clazz);
		if(null == jaxbContext){
			try {
				jaxbContext = JAXBContext.newInstance(clazz);
				jaxbContexts.putIfAbsent(clazz, jaxbContext);
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return jaxbContext;
	}
	
	/**
	 * 
	 * @param clazz
	 * @return
	 */
	public final Marshaller createMarshaller(Class<?> clazz){
		Marshaller marshaller = null;
		try {
			
			marshaller = getJAXBContext(clazz).createMarshaller();
			
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return marshaller;
	}
	
	/**
	 * 
	 * @param clazz
	 * @return
	 */
	public final Unmarshaller createUnmarshaller(Class<?> clazz){
		Unmarshaller unmarshaller = null;
		try {
			unmarshaller = getJAXBContext(clazz).createUnmarshaller();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return unmarshaller;
	}
}
