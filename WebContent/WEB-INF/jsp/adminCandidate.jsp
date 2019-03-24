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
img{width:150px;height:100px;}
table{ border : 1px solid black;border-collapse: collapse;}
th,td{border: 2px solid;width:300px;text-align:center;padding: 30px;}
</style>
</head>
<body>
<form:form method="POST" modelAttribute="candidateForm" enctype="multipart/form-data"> <!-- 	enctype is important -->
	Voter's ID:<form:input id="votersId"  size="40" path="votersId" value=""/><form:errors path="votersId" cssClass="errorText"/>
	First Name:
	<form:input id="firstName"  size="40" path="firstName" value=""/><form:errors path="firstName" cssClass="errorText"/>
	<br> Last Name:<form:input id="lastName"  path="lastName" /><form:errors path="lastName" cssClass="errorText"/> Party:
	<form:input id="party"     path="party" /><form:errors path="party" cssClass="errorText"/>
	<br>
	Age:	<form:input id="age"  type="number"   path="age" min="18" max="150" value="18" onkeydown="return false" /><form:errors path="age" cssClass="errorText"/><br>
	
	Upload Profile Image:<form:input id="profile_img" name="profilePhoto" type="file" accept="image/jpeg,image/png,image/gif" path="profilePhoto"/><form:errors path="profilePhoto" cssClass="errorText"/><br>
	<input id="upload_profile_img" type="button" value="Upload" /><br>
	Upload Symbol:<form:input id="symbol"  type="file" name="symbol" accept="image/jpeg,image/png,image/gif" path="symbol"/><form:errors path="symbol" cssClass="errorText"/><br>
	<input id="upload_symbol_img" type="button" value="Upload"/><br>
	<form:select path=""></form:select>
	<input type="submit" id="save_button" value="SAVE"/>
</form:form>
<table>
<tr><th>Profile Picture</th><th>First Name</th><th>Last Name</th><th>Party</th><th>Age</th><th>Symbol</th></tr>
<c:forEach var="candidate" items="${candidates}">
	<tr>
		<td><img id="" src="data:image/jpg;base64,${candidate.candidateDetails['PROFILE_PHOTO']}"/></td>	
		<td>${candidate.candidateDetails['FIRST_NAME']}</td>
		<td>${candidate.candidateDetails['LAST_NAME']}</td>
		<td>${candidate.candidateDetails['PARTY']}</td>
		<td>${candidate.candidateDetails['AGE']}</td>
		<td><img id="" src="data:image/jpg;base64,${candidate.candidateDetails['SYMBOL']}"/></td>
	</tr>
</c:forEach>
</table>
<script>
</script>
</body>
</html>