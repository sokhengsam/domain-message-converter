/**
 * 
 */
package com.openidea.converter;


/**
 * @author sokheng
 * @param <T>
 *
 */
public interface MessageConverter {

	<T> boolean canRead(Class<? extends T> clazz,MediaType mediaType);
	
	<T> boolean canWrite(Class<? extends T> clazz,MediaType mediaType);
	
}
