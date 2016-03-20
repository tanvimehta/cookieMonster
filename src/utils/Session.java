package utils;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Session {

	private String sessionId;
	private int version;
	private String message;
	private Date expirationTimestamp;
	
	public Session() {
		sessionId = UUID.randomUUID().toString().replaceAll("[^\\d.]", "");
		message = "Hello, User!";
		version = 1;
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, 120);
		expirationTimestamp = cal.getTime();
	}
	
	public void updateTimeStamp() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, 120);
		expirationTimestamp = cal.getTime();
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
	
	public void setVersion(int version) {
		this.version = version;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public Date getExpirationTimestamp() {
		return expirationTimestamp;
	}
	
	public void setExpirationTimestamp(Date expirationTimestamp) {
		this.expirationTimestamp = expirationTimestamp;
	}	
}
