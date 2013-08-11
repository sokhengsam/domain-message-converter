/**
 * 
 */
package com.openidea.converter;


/**
 * @author sokheng
 * 
 */
public class MediaType {

	public static final String ALL = "*/*";

	public static final String APPLICATION_ATOM = "application/atom+xml";

	public static final String APPLICATION_FORM_URLENCODED = "application/x-www-form-urlencoded";

	public static final String APPLICATION_JSON = "application/json";

	public static final String APPLICATION_OCTET_STREAM = "application/octet-stream";

	public static final String APPLICATION_XHTML_XML = "application/xhtml+xml";

	public static final String APPLICATION_XML = "application/xml";

	public static final String IMAGE_GIF = "image/gif";

	public static final String IMAGE_JPEG = "image/jpeg";

	public static final String IMAGE_PNG = "image/png";

	public static final String MULTIPART_FORM_DATA = "multipart/form-data";

	public static final String TEXT_HTML = "text/html";

	public static final String TEXT_PLAIN = "text/plain";

	public static final String TEXT_XML = "text/xml";

	public static final String WILDCARD_TYPE = "*";

	private final String type;

	private final String subType;

	public MediaType(String type, String subtype) {
		this.type = type;
		this.subType = subtype;
		
	}
	
	/**
	 * @param other
	 * @return
	 */
	public boolean isIncludes(MediaType other) {
		boolean returnValue = false;
		if (null == other) {
			throw new IllegalArgumentException(
					"Illegal passing media type object");
		}
		if (this.isWildcardType()) { // */* media type
			returnValue = true;
		} else if (other.getType().equals(this.getType())) {
			if (other.getSubType().equals(this.getSubType())
					|| this.isWidcardSubType()) {
				returnValue = true;
			}

			// application/*+xml includes application/soap+xml
			String[] thisSubType = this.getSubType().split("+");
			String[] otherSubType = this.getSubType().split("+");

			if (otherSubType.length >= 2 && thisSubType.length >= 2) {
				if (thisSubType[1].equals(otherSubType[1])
						&& WILDCARD_TYPE.equalsIgnoreCase(thisSubType[0])) {
					returnValue = true;
				}
			}
		}

		return returnValue;
	}
	
	/**
	 * 
	 * @param other
	 * @return
	 */
	public boolean isCompatibleWith(MediaType other) {
		boolean returnValue = false;

		if (null == other) {
			return returnValue;
		}

		if (isWildcardType()) {
			returnValue = true;
		} 
		else if (this.getType().equals(other.getType())) {
			if (this.getSubType().equals(other.getSubType())
					|| this.isWidcardSubType() || other.isWidcardSubType()) {
				returnValue = true;
			}
			
			// application/*+xml is compatible with application/soap+xml, and vice-versa
			String[] thisSubType = this.getSubType().split("+");
			String[] otherSubType = this.getSubType().split("+");
			if (thisSubType.length >= 2 && otherSubType.length >= 2 && thisSubType[1].equals(otherSubType[1]) && (WILDCARD_TYPE.equals(thisSubType[0]) || WILDCARD_TYPE.equals(otherSubType[0]))) {
				returnValue = true;
			}
		}
		
		return returnValue;
	}

	public boolean isWidcardSubType() {
		return true;
	}

	public boolean isWildcardType() {
		return this.equals(WILDCARD_TYPE);
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return the subType
	 */
	public String getSubType() {
		return subType;
	}

}
