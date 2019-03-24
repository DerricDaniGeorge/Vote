<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<script src="<c:url value="/js/vote.js"/>"></script>
</head>
<body onload="mappingShowMessage()">
<form method="POST" onsubmit="return getAllValuesFromList()">
States:
	<select id="stateBox"  name="state" onchange="getMappedConstituencies()">
	<c:forEach var="state" items="${states}">
		<option value="${state}">${state}</option>
	</c:forEach>
	</select>
	<br>
	All Constituencies:
	<select id="allConstituencyBox" size="30" onclick="">
	<c:forEach var="constituency" items="${constituencies}">
		<option value="${constituency}">${constituency}</option>
	</c:forEach>
	</select> 
	<input id="add_button" type="button" value= "&gt;&gt;" onclick="return addToList()"/>
	<input id="remove_button" type="button" value= "&lt;&lt;" onclick="return removeFromList()"/>
	<select id="constituencyBox" size="30" name="constituency" id="constituency">
	</select>
	<br>
	<input type="submit" value="SAVE"/>
	<input type="hidden" name="selected_constituencies" value="" id="selected_constituencies"/>
	</form>
</body>
<script>
function mappingShowMessage(){
var msg="${status}"
if(typeof msg!=="undefined" && msg!==null && msg!==''){
	alert(msg);
}
clickFirstOption();
}
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
</html>