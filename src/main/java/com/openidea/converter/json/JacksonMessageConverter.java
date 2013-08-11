/**
 * 
 */
package com.openidea.converter.json;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.JavaType;

import com.openidea.converter.AbstractMessageConverter;
import com.openidea.converter.MediaType;

/**
 * @author sokheng
 * 
 */
public class JacksonMessageConverter extends AbstractMessageConverter {

	private final ObjectMapper mapper;

	public JacksonMessageConverter() {
		super(new MediaType("application", "json"));
		mapper = new ObjectMapper();
		
	}

	@Override
	public <T> T read(Class<? extends T> clazz, InputStream inputStream)
			throws IOException,JsonProcessingException {
		try{
			T returnValue = mapper.readValue(inputStream, clazz);
			return returnValue;			
		}
		catch(JsonProcessingException jsonProcessingException){
			throw jsonProcessingException;
		}
	}

	@Override
	public <T> void write(T body, OutputStream outputStream, Charset charset)
			throws IOException,JsonProcessingException {
		final JsonEncoding jsonEncoding = getJsonEncoding(charset);
		final JsonGenerator  jsonGenerator = this.mapper.getJsonFactory().createJsonGenerator(outputStream, jsonEncoding);
		try{
			this.mapper.writeValue(jsonGenerator, body);
		}
		catch(JsonProcessingException jsonProcessingException){
			throw jsonProcessingException;
		}
	}

	@Override
	public <T> boolean isSupported(Class<? extends T> clazz) {
		return this.mapper.canDeserialize(getJavaType(clazz)) || this.mapper.canSerialize(clazz);
	}
	
	protected <T> JavaType getJavaType(Class<? extends T> clazz){
		return TypeFactory.type(clazz);
	}
	
	/**
	 * Get JsonEncoding based on passing charset
	 * 
	 * @param charset
	 * 
	 * @return
	 */
	protected JsonEncoding getJsonEncoding(Charset charset){
		if(null != charset){
			for (JsonEncoding jsonEncoding: JsonEncoding.values()) {
				if(charset.name().equals(jsonEncoding.name())){
					return jsonEncoding;
				}
			}
		}
		return JsonEncoding.UTF8;
		
	}

}
