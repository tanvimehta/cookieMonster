package utils;

import javax.servlet.http.Cookie;

public class CustomCookie extends Cookie{

	private static final long serialVersionUID = 1L;
	private static final String DELIM = ";";
	private String sessionId;
	private Integer version;
	private String locationMetadata;
	private String value;
	
	public CustomCookie(String name, String sessionId, Integer version, String location) {
		super(name, sessionId + DELIM + version + DELIM + location);
		value = sessionId + DELIM + version + DELIM + location;
		this.sessionId = sessionId;
		this.version = version;
		this.locationMetadata = location;
	}
	
	@Override
	public String toString() {
		return sessionId + DELIM + version + DELIM + locationMetadata;
	}
	
	public String getSessionId() {
		return sessionId;
	}
	
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	public int getVersion() {
		return version;
	}
	
	public void setVersionExpiry(Integer version) {
		this.version = version;
	}
	
	public String getLocationMetadata() {
		return locationMetadata;
	}
	
	public void setLocationMetadata(String locationMetadata) {
		this.locationMetadata = locationMetadata;
	}
	
	public String getValue() {
		return value;
	}
	
	public void incrementVersion() {
		version+=1;
	}

}
