<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body>
<form action="/Vote/login" method="post">
Voter's ID:<input type="text" name="votersId"/>
Password:<input type="password" name="password"/>
<input type="submit" value="LOGIN"/>
<p>${invalidCredentials}</p>
</body>
</form>
</html>