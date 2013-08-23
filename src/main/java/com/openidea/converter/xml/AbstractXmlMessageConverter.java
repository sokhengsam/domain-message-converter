/**
 * 
 */
package com.openidea.converter.xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import com.openidea.converter.AbstractMessageConverter;
import com.openidea.converter.MediaType;

/**
 * 
 * @author sokheng
 *
 */
public abstract class AbstractXmlMessageConverter extends AbstractMessageConverter{

	public AbstractXmlMessageConverter(MediaType mediaType) {
		super(mediaType);
		// TODO Auto-generated constructor stub
	}

	@Override
	public <T> T read(Class<? extends T> clazz, InputStream inputStream)
			throws IOException {
		// TODO Auto-generated method stub
		return readStream(clazz, inputStream);
	}

	@Override
	public <T> void write(T body, OutputStream outputStream, Charset charset)
			throws IOException {
		writeStream(body, outputStream, charset);
		
	}

	@Override
	public <T> boolean isSupported(Class<? extends T> clazz) {
		// TODO Auto-generated method stub
		return false;
	}
	/**
	 * 
	 * @param clazz
	 * @param inputStream
	 * @return
	 */
	public abstract <T> T readStream(Class<? extends T> clazz,InputStream inputStream);
	
	/**
	 * 
	 * @param body
	 * @param outputStream
	 * @param charset
	 * @return
	 */
	public abstract <T> T writeStream(T body, OutputStream outputStream, Charset charset);

}
