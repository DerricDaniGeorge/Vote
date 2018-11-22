<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Vote-Registration Page</title>
<style>
.errorText{color:#ff0000;font-weight:bold}
</style>
</head>
<body>
<form:form id="registration_Form" method="POST" modelAttribute="registerUserForm">
	Voter's ID:<form:input path="votersID"/><form:errors path="votersID" cssClass="errorText"/><br>
	First Name:<form:input path="firstName"/><form:errors path="firstName" cssClass="errorText"/><br>
	Middle Name:<form:input path="middleName"/><form:errors path="middleName" cssClass="errorText"/><br>
	Last Name:<form:input path="lastName"/><form:errors path="lastName" cssClass="errorText"/><br>
	Gender:<select>
			<option value="Male">M</option>
			<option value="Female">F</option>
			<option value="TransGender">TransGender</option>
		</select><br>
	Date of Birth:<form:input type="date" path="dateOfBirth" /><form:errors path="dateOfBirth" cssClass="errorText"/><br>
	Email:<form:input type="email" path="email"/><form:errors path="email" cssClass="errorText"/><br>
	Password:<form:input type="password" path="password"/><form:errors path="password" cssClass="errorText"/><br>
	Re-type Password:<form:input type="password" path="retypePassword"/><form:errors path="retypePassword" cssClass="errorText"/><br>
	<input id="submit_button" type="submit" value="Register"/>
	
</form:form>
</body>
</html>