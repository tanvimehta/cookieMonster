package utils;

import javax.servlet.http.Cookie;

public class CookieUtils {
	
	private static final String DELIM = "$";
	private static final String REGEX = "\\$";

	public static Cookie createCookie(String sessionId, int version, String location) {
		Cookie c = new Cookie ("CS5300PROJ1SESSION", sessionId + DELIM + version + DELIM + location);;
		c.setMaxAge(120);
		return c;
	}
	
	public static Cookie incrementVersion(Cookie c) {
		String[] params = c.getValue().split(REGEX);

		int newVersion = Integer.parseInt(params[1]);
		newVersion++;
		return createCookie(params[0], newVersion, params[2]);
	}
	
	public static String getSessionId(Cookie c) {
		String[] params = c.getValue().split(REGEX);
		return params[0];
	}
	
	public static String getVersion(Cookie c) {
		String[] params = c.getValue().split(REGEX);
		return params[1];
	}

}
