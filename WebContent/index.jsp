<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>Time on server: <%= new Date() %></h2>
<p><b>NetID: tmm259  Session: ${session} Version:${version}</b></p>
<%@ page import="java.util.*" %>
<FORM ACTION="session">
<INPUT TYPE="SUBMIT" VALUE="Replace" NAME="Replace">
<INPUT TYPE="TEXT" NAME="replace"><BR><BR>
<INPUT TYPE="SUBMIT" VALUE="Refresh" NAME="Refresh"><BR><BR>
<INPUT TYPE="SUBMIT" VALUE="Logout" NAME="Logout"><BR><BR>
</FORM>
</body>
</html>