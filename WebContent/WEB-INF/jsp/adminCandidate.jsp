<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Candidate</title>
<style>
.errorText{color:#ff0000;font-weight:bold}
</style>
</head>
<body onload="showSaveButton()">
<form:form method="POST" modelAttribute="candidateForm">
	First Name:
	<form:input id="firstName"  size="40" path="firstName" value=""/><form:errors path="firstName" cssClass="errorText"/>
	<br> Last Name:<form:input id="lastName"  path="lastName" /><form:errors path="lastName" cssClass="errorText"/> Party:
	<form:input id="party"     path="party" /><form:errors path="party" cssClass="errorText"/>
	<br>
	Age:	<form:input id="age"  type="number"   path="age" /><form:errors path="age" cssClass="errorText"/><br>
	Upload Profile Image:<form:input id="profile_img" path="" type="file"/><br>
	
	Upload Symbol:<form:input id="symbol" path="" type="file"/><br>
	<input type="submit" id="save_button" value="SAVE" hidden/>
</form:form>	
<script>
</script>
</body>
</html>