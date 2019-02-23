<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Manage election</title>
</head>
<body>
	Existing elections:
	<br>
	<select id="electionBox" size="10" multiple="multiple" onclick="updateFieldsOnSelection()">
	<c:forEach var="election" items="${elections}">
		<option value="${election.electionName}">${election.electionName} - ${election.electionDetails['YEAR']}</option>
	</c:forEach>
	
	</select> Election Name:
	<input id="election_name" name="election_name" type="text" value="${elections[0].electionName}" disabled size="30"/>
	<br> Start Date:
	<input id="start_date"type="date" name="start_date" value="${elections[0].electionDetails['START_DATE']}" disabled /> End Date:
	<input id="end_date" type="date" name="end_date" value="${elections[0].electionDetails['END_DATE']}" disabled />
	<br>
	<input type="button" value="ADD"  onclick="add()"/>
	<input type="button" value="MODIFY" onclick="modify()" />
	<input type="button" value="DELETE" />
	<p></p>
	<input type="button" id="save_button" value="SAVE" hidden/>
<script>
function disableById(id){
	document.getElementById(id).setAttribute("disabled","disabled");
}
function enableById(id){
	document.getElementById(id).removeAttribute("disabled");
}
function hideById(id){
	document.getElementById(id).setAttribute("hidden","hidden");
}
function unHideById(id){
	document.getElementById(id).removeAttribute("hidden");
}
function setValue(id,val){
	document.getElementById(id).value=val;
}
function getValue(id){
	return document.getElementById(id).value;
}
function updateFieldsOnSelection(){
	var selectBox=document.getElementById("electionBox");
	var selectedValue=selectBox.options[selectBox.selectedIndex].value;
	var election_name=document.getElementById("election_name");
	var start_date=document.getElementById("start_date");
	var end_date=document.getElementById("end_date")
	election_name.value=selectedValue;
	hideById("save_button");
	disableById("election_name");
	disableById("start_date");
	disableById("end_date");
	var elections=JSON.parse(JSON.stringify(${jsonElections}));
	for(i=0;i<elections.length;i++){
		if((elections[i].electionName)===selectedValue){
			start_date.value=elections[i].electionDetails.START_DATE;
			end_date.value=elections[i].electionDetails.END_DATE;
			break;
		}
	}
}

function add(){
	enableById("election_name");
	setValue("election_name","");
	enableById("start_date");
	setValue("start_date","");
	enableById("end_date");
	setValue("end_date","");
	unHideById("save_button");
}
function modify(){
	if(getValue("election_name")==""){
		alert("No election selected");
	}else{
		enableById("election_name");
		enableById("start_date");
		enableById("end_date");
		unHideById("save_button");
	}
}
</script>
</body>
</html>