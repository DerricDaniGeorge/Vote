<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add States</title>
<style>
.errorText{color:#ff0000;font-weight:bold}
</style>
<script src="<c:url value="/js/vote.js"/>"></script>
</head>
<body onload="showSaveButton()">
<form:form method="POST" modelAttribute="addStateForm" onsubmit="doRequiredThingsState()">

	State:<form:input path="state" name="state" id="state"/><br><form:errors path="state" cssClass="errorText"></form:errors>
	States:
	<select id="stateBox" size="10" onclick="updateFieldsOnSelection()">
	<c:forEach var="state" items="${states}">
		<option value="${state}">${state}</option>
	</c:forEach>
	</select> 
	<br>
	<input id="add_button" type="button" value="ADD"  onclick="addState()" onmouseover="cancelStateAdding()"/>
	<input id="delete_button" type="button" value="DELETE" onclick="deleteState()" onmouseover="cancelStateDeleting()"/>
	<p></p>
	<input type="submit" id="save_button" value="SAVE" hidden/>
	<input type="hidden" id="actionToDo" name="action" value=""/>
	</form:form>	


</body>
<script>
function updateFieldsOnSelection(){
	var selectBox=document.getElementById("stateBox");
	var selectedValue=selectBox.options[selectBox.selectedIndex].value;
	var stateName=document.getElementById("state");
	stateName.value=selectedValue;
	disableById("state");
	hideById("save_button");
	
} 

function showSaveButton(){
	var	hasErrors=${hasErrors}
		console.log(hasErrors);
		if(typeof hasErrors!=="undefined" && hasErrors!==null && hasErrors=="true"){
		unHideById("save_button");
		hideById("delete_button");
		}
		showAlert();
	}
function showAlert(){
	var status="${stateStatus}"
	if(typeof status!=="undefined" && status!==null && status!==''){
		alert(status);
	}
}
</script>
</html>