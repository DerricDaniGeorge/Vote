<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Loksabha Constituency</title>
<style>
.errorText{color:#ff0000;font-weight:bold}
</style>
<script src="<c:url value="/js/vote.js"/>"></script>
</head>
<body onload="showSaveButton()">
<form:form method="POST" modelAttribute="addLokSabhaConstituencyForm" onsubmit="doRequiredThingsConstituency()">

	Constituency:<form:input path="constituency" name="constituency" id="constituency"/><br><form:errors path="constituency" cssClass="errorText"></form:errors>
	Constituencies:
	<select id="constituencyBox" size="10" onclick="updateFieldsOnSelection()">
	<c:forEach var="constituency" items="${constituencies}">
		<option value="${constituency}">${constituency}</option>
	</c:forEach>
	</select> 
	<br>
	<input id="add_button" type="button" value="ADD"  onclick="addConstituency()" onmouseover="cancelConstituencyAdding()"/>
	<input id="delete_button" type="button" value="DELETE" onclick="deleteConstituency()" onmouseover="cancelConstituencyDeleting()"/>
	<p></p>
	<input type="submit" id="save_button" value="SAVE" hidden/>
	<input type="hidden" id="actionToDo" name="action" value=""/>
	</form:form>	


</body>
<script>
function updateFieldsOnSelection(){
	var selectBox=document.getElementById("constituencyBox");
	var selectedValue=selectBox.options[selectBox.selectedIndex].value;
	var stateName=document.getElementById("constituency");
	stateName.value=selectedValue;
	disableById("constituency");
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
	var status="${constituencyStatus}"
	if(typeof status!=="undefined" && status!==null && status!==''){
		alert(status);
	}
}
</script>
</html>