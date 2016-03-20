package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.CookieUtils;
import utils.Session;

/**
 * Servlet implementation class SessionServlet
 */
@WebServlet("/session")
public class SessionServlet extends HttpServlet {
	
//	private static final String page = "<!DOCTYPE html>" 
//    		+"<html><head><title>Sample JSP Page</title></head><body>"
//    		+ "<h2>Time on server: " + new Date() + "</h2>"
//    		+ "<p><b>NetID: tmm259  Session:"
//    		+ "  Version:"
//    		+ "</b></p>"   
//    		+ "</p><h1>" 
//    		+ "Hello User"
//    		+ "</h1>"
//    		+ "<FORM ACTION=\"session\">"	    		
//    		+ "<INPUT TYPE=\"SUBMIT\" VALUE=\"Replace\" NAME=\"Replace\">"
//    		+ "<INPUT TYPE=\"TEXT\" NAME=\"replace\"><BR><BR>"
//    		+ "<INPUT TYPE=\"SUBMIT\" VALUE=\"Refresh\" NAME=\"Refresh\"><BR><BR>"
//    		+ "<INPUT TYPE=\"SUBMIT\" VALUE=\"Logout\" NAME=\"Logout\"><BR><BR>"
//    		+ "</FORM>" 
//    		+ "</BODY></HTML></body></html>";
			
	private static final long serialVersionUID = 1L;
	private static final String REPLACE = "Replace";
	private static final String REFRESH = "Refresh";
	private static final String LOGOUT = "Logout";
	private ConcurrentHashMap<String, Session> sessionDataTable = new ConcurrentHashMap<String, Session>();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		
		sessionTableCleanUp();
		
		// Cookie work		
		// Get the cookie that was received in the request
		Cookie[] allCookies =  request.getCookies();
		Cookie currentCookie = null;
		if (allCookies != null) {
			for (Cookie c: allCookies) {
				if (c.getName().compareTo("CS5300PROJ1SESSION") == 0) {
					currentCookie = c;
					break;
				}
			}
		}
		
		String sessionId; 
		Session currSession = null;
		
		// There is no cookie yet
		if (currentCookie == null) {
			Session session = new Session();
			sessionDataTable.put(session.getSessionId(), session);
			currentCookie = CookieUtils.createCookie(session.getSessionId(), session.getVersion(), "metadata");
			response.addCookie(currentCookie);
			String page = getPage(session.getSessionId(), session.getVersion(), 
					session.getMessage(), currentCookie.getValue(), 
					session.getExpirationTimestamp());
			out.println(page);
			out.println("New cookie and session needs to be created");
			out.println(currentCookie.getValue());
			return;
			
		} 
		// There exists a cookie
		else {
			
			sessionId = CookieUtils.getSessionId(currentCookie);
			
			// The cookie is valid
			if (sessionDataTable.containsKey(sessionId)) {
				currentCookie = CookieUtils.incrementVersion(currentCookie);
				response.addCookie(currentCookie);
				currSession = sessionDataTable.get(sessionId);
				currSession.setVersion(Integer.parseInt(CookieUtils.getVersion(currentCookie)));
			} 
			// Need a new cookie since it is invalid 
			// Has no session associated with it
			else {
				currSession = new Session();
				sessionDataTable.put(currSession.getSessionId(), currSession);
				currentCookie = CookieUtils.createCookie(currSession.getSessionId(), currSession.getVersion(), "metadata");
				response.addCookie(currentCookie);
			}
		}
		
		// No button pressed = Reload Page
		if (request.getParameter(REPLACE) == null && request.getParameter(REFRESH) == null 
				&& request.getParameter(LOGOUT) == null) {
			currSession.updateTimeStamp();
			currentCookie.setMaxAge(120);
			String page = getPage(currSession.getSessionId(), 
					currSession.getVersion(),
					currSession.getMessage(),
					currentCookie.getValue(), 
					currSession.getExpirationTimestamp());
			out.println(page);
			return;
		} else 	
		// Refresh
		if (request.getParameter(REPLACE) == null && request.getParameter(LOGOUT) == null) {
			currSession.updateTimeStamp();
			currentCookie.setMaxAge(120);
			String page = getPage(currSession.getSessionId(), 
					currSession.getVersion(),
					currSession.getMessage(),
					currentCookie.getValue(), 
					currSession.getExpirationTimestamp());
			out.println(page);
			out.println(request.getParameter("Refresh"));
		} else 
		// Replace
		if (request.getParameter(LOGOUT) == null && request.getParameter(REFRESH) == null) {
			currSession.setMessage(request.getParameter("message"));
			currSession.updateTimeStamp();
			currentCookie.setMaxAge(120);
			String page = getPage(currSession.getSessionId(), 
					currSession.getVersion(),
					request.getParameter("message"),
					currentCookie.getValue(), 
					currSession.getExpirationTimestamp());
			out.println(page);
			out.println(request.getParameter("Replace"));
		} else 
			
		// Logout
		if(request.getParameter(REPLACE) == null && request.getParameter(REFRESH) == null) {
			sessionDataTable.remove(sessionId);
			out.println(request.getParameter("Logout"));
		}
	}

	private void sessionTableCleanUp() {
		Enumeration<String> keys = sessionDataTable.keys();
		while (keys.hasMoreElements()) {
			String k = keys.nextElement();
			Session s = sessionDataTable.get(k);
			if (s.getExpirationTimestamp().before(new Date())) {
				sessionDataTable.remove(k);
			}
		}
	}

	private String getPage(String sessionId, int version, String message, String value, Date expirationTimestamp) {
		return "<!DOCTYPE html>" 
	    		+"<html><head><title>Session Management</title></head><body>"
	    		+ "<h2>Time on server: " + new Date() + "</h2>"
	    		+ "<p><b>NetID: tmm259  Session: "
	    		+ sessionId
	    		+ "  Version: "
	    		+ version
	    		+ "</b></p>"   
	    		+ "</p><h1>" 
	    		+ message
	    		+ "</h1>"
	    		+ "<FORM ACTION=\"session\">"	    		
	    		+ "<INPUT TYPE=\"SUBMIT\" VALUE=\"Replace\" NAME=\"Replace\">"
	    		+ "<INPUT TYPE=\"TEXT\" NAME=\"message\"><BR><BR>"
	    		+ "<INPUT TYPE=\"SUBMIT\" VALUE=\"Refresh\" NAME=\"Refresh\"><BR><BR>"
	    		+ "<INPUT TYPE=\"SUBMIT\" VALUE=\"Logout\" NAME=\"Logout\"><BR><BR>"
	    		+ "</FORM><BR>" 
	    		+ "<p><b>Cookie: "
	    		+ value
	    		+ "<br>Expires: "
	    		+ expirationTimestamp
	    		+ "<br></b><br>Debug: </BODY></HTML></body></html>";
	}	
}
