<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Vote-Registration Page</title>
<style>
.errorText{color:#ff0000;font-weight:bold}
</style>
<script src="<c:url value="/js/vote.js"/>"></script>
</head>
<body onload="clickFirstOption()">
<form:form id="registration_Form" method="POST" modelAttribute="registerUserForm">
	Voter's ID:<form:input path="votersID"/><form:errors path="votersID" cssClass="errorText"/><br>
	First Name:<form:input path="firstName"/><form:errors path="firstName" cssClass="errorText"/><br>
	Middle Name:<form:input path="middleName"/><form:errors path="middleName" cssClass="errorText"/><br>
	Last Name:<form:input path="lastName"/><form:errors path="lastName" cssClass="errorText"/><br>
	Gender:<form:select path="gender">
			<form:option value="Male">M</form:option>
			<form:option value="Female">F</form:option>
			<form:option value="TransGender">TransGender</form:option>
		</form:select><br>
	Date of Birth:<form:input type="date" path="dateOfBirth" /><form:errors path="dateOfBirth" cssClass="errorText"/><br>
	Email:<form:input type="email" path="email"/><form:errors path="email" cssClass="errorText"/><br>
	States:
	<form:select path="state" id="stateBox"  name="state" onchange="getMappedConstituencies()">
	<c:forEach var="state" items="${states}">
		<form:option value="${state}">${state}</form:option>
	</c:forEach>
	</form:select>
	<br>
	LokSabha Constituency:<form:select id="constituencyBox" name="constituency" path="constituency">
	</form:select>
	Password:<form:input type="password" path="password"/><form:errors path="password" cssClass="errorText"/><br>
	Re-type Password:<form:input type="password" path="retypePassword"/><form:errors path="retypePassword" cssClass="errorText"/><br>
	<input id="submit_button" type="submit" value="Register"/>
</form:form>
<p id="msgUserExists">${msgUserExists}</p>
<script type="text/javascript">
function getMappedConstituencies(){
	var stateBox=document.getElementById("stateBox");
	var selectedValue=stateBox.options[stateBox.selectedIndex].value;
	var xhttp=new XMLHttpRequest();
	xhttp.onreadystatechange=function(){
		if(this.readyState==4 && this.status==200){
			loadConstituenciesToDropdownList(this.responseText);
		}
	};
	xhttp.open("GET","${pageContext.request.contextPath}/getMappedLokConstituency?state="+selectedValue,true);
	xhttp.send();
}
function loadConstituenciesToDropdownList(jsonText){
	clearOptionsInDropdown("constituencyBox");
	var obj=JSON.parse(jsonText);
	var constituencyBox=document.getElementById("constituencyBox");
	for(i in obj){
		var option=document.createElement("option");
		option.text=obj[i]["constituencyName"];
		option.value=obj[i]["constituencyName"];
		constituencyBox.add(option);
	}
}
function clearOptionsInDropdown(id){
	document.getElementById(id).options.length = 0;
}
function clickFirstOption(){
	document.getElementById("stateBox").selectedIndex="0";
	getMappedConstituencies();
}
</script>
</body>
</html>