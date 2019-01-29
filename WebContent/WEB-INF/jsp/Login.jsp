<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
<style>
.errorText{color:#ff0000;font-weight:bold}
</style>
</head>
<body>
<form:form  method="POST" modelAttribute="loginForm">
Voter's ID:<form:input path="votersID"/><form:errors path="votersID" cssClass="errorText"/>
Password:<form:input type="password" path="password"/><form:errors path="password" cssClass="errorText"/>
<input type="submit" value="LOGIN"/>
</form:form>
<p>${invalidCredentials}</p>
</body>

</html>