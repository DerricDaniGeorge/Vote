<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Manage election</title>
<style>
.errorText{color:#ff0000;font-weight:bold}
</style>
<script src="<c:url value="/js/vote.js"/>"></script>
</head>
<body onload="showSaveButton()">
<form:form method="POST" modelAttribute="electionForm" onsubmit="doRequiredThings()" >
	Existing elections:
	<br>
	<select id="electionBox" size="10"  onclick="updateFieldsOnSelection()" onkeypress="updateFieldsOnSelection()">
	<c:forEach var="election" items="${elections}">
		<option value="${election.electionName}">${election.electionName} - ${election.electionDetails['YEAR']}</option>
	</c:forEach>
	
	</select> Election Name:
	<form:input id="electionName"  size="40" path="electionName" value=""/><form:errors path="electionName" cssClass="errorText"/>
	<br> Start Date:
	<form:input id="startDate" type="date"  path="startDate" /><form:errors path="startDate" cssClass="errorText"/> End Date:
	<form:input id="endDate" type="date"    path="endDate" /><form:errors path="endDate" cssClass="errorText"/>
	<br>
	<input id="add_button" type="button" value="ADD"  onclick="addElection()" onmouseover="cancelAdding()"/>
	<input id="modify_button" type="button" value="MODIFY" onclick="modifyElection()" onmouseover="cancelModifying()"/>
	<input id="delete_button" type="button" value="DELETE" onclick="deleteElection()" onmouseover="cancelDeteting()"/>
	<p></p>
	<input type="submit" id="save_button" value="SAVE" hidden/>
	<input type="hidden" id="actionToDo" name="action" value=""/>
	<input type="hidden" id="election_id" name="election_id" value=""/>
	<input type="hidden" id="election_startDate" name="election_startDate" value=""/>
	<input type="hidden" id="election_endDate" name="election_endDate" value=""/>
</form:form>	
<script>
function updateFieldsOnSelection(){
	var selectBox=document.getElementById("electionBox");
	var selectedValue=selectBox.options[selectBox.selectedIndex].value;
	var electionName=document.getElementById("electionName");
	var startDate=document.getElementById("startDate");
	var endDate=document.getElementById("endDate");
	var electionId=document.getElementById("election_id");
	var electionStartDate=document.getElementById("election_startDate");
	var electionEndDate=document.getElementById("election_endDate");
	hideById("save_button");
	
	disableById("electionName");
	disableById("startDate");
	disableById("endDate");
	var elections=JSON.parse(JSON.stringify(${jsonElections}));
	for(i=0;i<elections.length;i++){
		if((elections[i].electionName)===selectedValue){
			electionName.value=elections[i].electionName;
			startDate.value=elections[i].electionDetails.START_DATE;
			endDate.value=elections[i].electionDetails.END_DATE;
			electionId.value=elections[i].electionDetails.ELECTION_ID;
			electionStartDate.value=elections[i].electionDetails.START_DATE;
			electionEndDate.value=elections[i].electionDetails.END_DATE;
			break;
		}
	}
} 
function showSaveButton(){
	var	hasErrors=${hasErrors}
		console.log(hasErrors);
		if(typeof hasErrors!=="undefined" && hasErrors!==null && hasErrors=="true"){
		unHideById("save_button");
		hideById("modify_button");
		hideById("delete_button");
		}
		showAlert();
	}
function showAlert(){
	var status="${electionStatus}"
	if(typeof status!=="undefined" && status!==null && status!==''){
		alert(status);
	}
}
</script>
</body>
</html>