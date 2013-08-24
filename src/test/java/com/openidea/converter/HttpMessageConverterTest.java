/**
 * 
 */
package com.openidea.converter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.openidea.converter.xml.Jaxb2XmlMessageConverter;

/**
 * @author sokheng
 *
 */
public class HttpMessageConverterTest {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException {
		AbstractMessageConverter messageConverter = new Jaxb2XmlMessageConverter();
		Company company = messageConverter.read(Company.class, new FileInputStream(new File("/home/sokheng/book.xml")));
		for (Employee  employee: company.getEmployees().getEmployee()) {
			System.out.println(String.format("Name : %s, Sex: %s, Adress: %s",employee.getName(),employee.getSex(),employee.getAddress()));
		}
	}

}
