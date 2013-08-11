/**
 * 
 */
package com.openidea.converter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author sokheng
 * 
 */
public abstract class AbstractMessageConverter implements
		MessageConverter {

	private List<MediaType> supportedMediaTypes;

	public AbstractMessageConverter(MediaType mediaType) {
		setSupportedMediaTypes(mediaType);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.openidea.converter.HttpMessageConverter#canRead(java.lang.Class,
	 * com.openidea.converter.MediaType)
	 */
	@Override
	public <T> boolean canRead(Class<? extends T> clazz, MediaType mediaType) {
		return isSupported(clazz) && canRead(mediaType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.openidea.converter.HttpMessageConverter#canWrite(java.lang.Class,
	 * com.openidea.converter.MediaType)
	 */
	@Override
	public <T> boolean canWrite(Class<? extends T> clazz, MediaType mediaType) {
		return isSupported(clazz) && canWrite(mediaType);
	}

	/**
	 * @return the supportedMediaType
	 */
	public List<MediaType> getSupportedMediaType() {
		return supportedMediaTypes;
	}

	/**
	 * @param supportedMediaType
	 *            the supportedMediaType to set
	 */
	public void setSupportedMediaTypes(MediaType... supportedMediaType) {
		this.supportedMediaTypes = Collections.unmodifiableList(Arrays
				.asList(supportedMediaType));
	}

	/**
	 * Verify supported media file before writing body to outputstream
	 * 
	 * @param other
	 * @return
	 */
	protected boolean canWrite(MediaType other) {
		if (null == other) {
			return true;
		}

		for (MediaType mediaType : getSupportedMediaType()) {
			if (mediaType.isCompatibleWith(other)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Verify media type before reading response body
	 * 
	 * @param other
	 *            W * @return
	 */
	protected boolean canRead(MediaType other) {
		if (null == other) {
			return true;
		}

		for (MediaType mediaType : getSupportedMediaType()) {
			if (mediaType.isIncludes(other)) {
				return true;
			}
		}

		return false;
	}

	public abstract <T> T read(Class<? extends T> clazz, InputStream inputStream)
			throws IOException;

	public abstract <T> void write(T body, OutputStream outputStream,
			Charset charset) throws IOException;

	public abstract <T> boolean isSupported(Class<? extends T> clazz);

}
