
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
/*
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
*/
function addElection(){
/*	setValue("actionToDo","ADD");
	disableById("modify_button");
	disableById("delete_button");
	enableById("electionName");
	setValue("electionName","");
	enableById("startDate");
	setValue("startDate","");
	enableById("endDate");
	setValue("endDate","");
	unHideById("save_button");  */
	allInOne({actionToDo:"ADD",electionName:"",startDate:"",endDate:""},["electionName","startDate","endDate"],["modify_button","delete_button"],[],["save_button"]);
}

function allInOne(valuesToSet,enableElements,disableElements,hideElements,unhideElements){
	if(typeof valuesToSet=="object" && valuesToSet!==null){
		for (key in valuesToSet){
			setValue(key,valuesToSet[key]);
		}
	}
	if(typeof enableElements!=="undefined" && Array.isArray(enableElements)){
		for(var i=0;i<enableElements.length;i++){
			enableById(enableElements[i]);
		}
	}
	if(typeof disableElements!=="undefined" && Array.isArray(disableElements)){
		for(var j=0;j<disableElements.length;j++){
			disableById(disableElements[j]);
		}
	}
	if(typeof hideElements!=="undefined" && Array.isArray(hideElements)){
		for(var k=0;k<hideElements.length;k++){
			hideById(hideElements[k]);
		}
	}
	if(typeof unhideElements!=="undefined" && Array.isArray(unhideElements)){
		for(var m=0;m<unhideElements.length;m++){
			unHideById(unhideElements[m]);
		}
	}
}
function modifyElection(){
	if(getValue("electionName")==""){
		alert("No election selected");
	}else{
		allInOne({actionToDo:"MODIFY"},["startDate","endDate"],["electionName"],[],["save_button"]);
/*		setValue("actionToDo","MODIFY");
		disableById("electionName");
		enableById("startDate");
		enableById("endDate");
		unHideById("save_button");  */
	}
}
function deleteElection(){
	if(getValue("electionName")==""){
		alert("No election selected");
	}else{
		allInOne({actionToDo:"DELETE"},[],["modify_button","add_button"],[],["save_button"]);
	//	setValue("actionToDo","DELETE");
		alert("You are going to delete the election. Click on Save button to proceed deleting");
	//	disableById("modify_button");
	//	disableById("add_button");
	//	unHideById("save_button");
	}
}
function cancelDeteting(){
//	setValue("actionToDo","");
	clearErrors();
//	hideById("save_button");
//	unHideById("modify_button");
//	unHideById("add_button");
//	enableById("modify_button");
//	enableById("add_button");
	allInOne({actionToDo:""},["modify_button","add_button"],[],["save_button"],["modify_button","add_button"]);
}
/*
function showSaveButton(){
var	hasErrors=${hasErrors};
	console.log(hasErrors);
	if(typeof hasErrors!=="undefined" && hasErrors!==null && hasErrors==true){
	unHideById("save_button");
	hideById("modify_button");
	hideById("delete_button");
	}
	showAlert();
} */
function clearErrors(){
	var errorTexts=document.getElementsByClassName("errorText");
	if(errorTexts!=null){
	for(i=0;i<errorTexts.length;i++){
		errorTexts[i].innerHTML="";
	}
	}
}
function cancelAdding(){
//	setValue("actionToDo","");
	clearErrors();
//	hideById("save_button");
//	unHideById("modify_button");
//	unHideById("delete_button");
//	enableById("modify_button");
//	enableById("delete_button");
	allInOne({actionToDo:""},["modify_button","delete_button"],[],["save_button"],["modify_button","delete_button"]);
}

function cancelModifying(){
//	setValue("actionToDo","");
	clearErrors();
//	hideById("save_button");
//	unHideById("modify_button");
//	unHideById("delete_button");
//	enableById("modify_button");
//	enableById("delete_button");
//	unHideById("add_button");
//	enableById("add_button");
	allInOne({actionToDo:""},["modify_button","delete_button","add_button"],["startDate","endDate"],["save_button"],["modify_button","delete_button","add_button"]);
}

function doRequiredThings(){
	if(getValue("actionToDo")=="MODIFY"){
		enableById("electionName");
	}
	if(getValue("actionToDo")=="DELETE"){
		var confirmation= confirm("This action will permanantly delete this election and cannot be undone. Do you want to continue ?");
		if(confirmation==true){
			enableById("electionName");
			enableById("startDate");
			enableById("endDate");
			return true;
		}
	}
}
function addState(){
	allInOne({actionToDo:"ADD"},["state"],["delete_button"],[],["save_button"]);
}
function cancelStateAdding(){
		clearErrors();
		allInOne({actionToDo:"",state:""},["delete_button"],["state"],["save_button"],["delete_button"]);
}
function deleteState(){
	if(getValue("state")==""){
		alert("No state selected");
	}else{
		allInOne({actionToDo:"DELETE"},[],["add_button"],[],["save_button"]);
		alert("You are going to delete the state. Click on Save button to proceed deleting");
	
	}
}
function cancelStateDeleting(){
	clearErrors();
	allInOne({actionToDo:""},["add_button"],[],["save_button"],["add_button"]);
}
function doRequiredThingsState(){
	if(getValue("actionToDo")=="DELETE"){
		var confirmation= confirm("This action will permanantly delete this state and cannot be undone. Do you want to continue ?");
		if(confirmation==true){
			enableById("state");
			return true;
		}
	}
}

function addConstituency(){
	allInOne({actionToDo:"ADD"},["constituency"],["delete_button"],[],["save_button"]);
}
function cancelConstituencyAdding(){
		clearErrors();
		allInOne({actionToDo:"",constituency:""},["delete_button"],["constituency"],["save_button"],["delete_button"]);
}
function deleteConstituency(){
	if(getValue("constituency")==""){
		alert("No constituency selected");
	}else{
		allInOne({actionToDo:"DELETE"},[],["add_button"],[],["save_button"]);
		alert("You are going to delete the constituency. Click on Save button to proceed deleting");
	
	}
}
function cancelConstituencyDeleting(){
	clearErrors();
	allInOne({actionToDo:""},["add_button"],[],["save_button"],["add_button"]);
}
function doRequiredThingsConstituency(){
	if(getValue("actionToDo")=="DELETE"){
		var confirmation= confirm("This action will permanantly delete this constituency and cannot be undone. Do you want to continue ?");
		if(confirmation==true){
			enableById("constituency");
			return true;
		}
	}
}
function addToList(){
	var selectBox=document.getElementById("allConstituencyBox");
	if(selectBox.selectedIndex==-1){
		alert("Select constituency from list on the left");
		return false;
	}
	var selectedValue=selectBox.options[selectBox.selectedIndex].value;
	var selectBox2=document.getElementById("constituencyBox");
	var option=document.createElement("option");
	option.text=selectedValue;
	selectBox2.add(option);
	selectBox.remove(selectBox.selectedIndex);
}
function removeFromList(){
	var selectBox=document.getElementById("constituencyBox");
	if(selectBox.selectedIndex==-1){
		alert("Select constituency from list on the right");
		return false;
	}
	var selectedValue=selectBox.options[selectBox.selectedIndex].value;
	var selectBox2=document.getElementById("allConstituencyBox");
	var option=document.createElement("option");
	option.text=selectedValue;
	selectBox2.add(option);
	selectBox.remove(selectBox.selectedIndex);
}
function getAllValuesFromList(){
	var selectBox=document.getElementById("constituencyBox");
	for(i=0;i<selectBox.options.length;i++){
		document.getElementById("selected_constituencies").value+=selectBox.options[i].value+"_";
	}
	console.log("value: "+document.getElementById("selected_constituencies").value);
	if(document.getElementById("selected_constituencies").value==""){
		alert("Nothing in right list. Please add some to save");
		return false;
	}
}
function addParty(){
	allInOne({actionToDo:"ADD"},["party"],["delete_button"],[],["save_button"]);
}
function cancelPartyAdding(){
		clearErrors();
		allInOne({actionToDo:"",party:""},["delete_button"],["party"],["save_button"],["delete_button"]);
}
function deleteParty(){
	if(getValue("party")==""){
		alert("No party selected");
	}else{
		allInOne({actionToDo:"DELETE"},[],["add_button"],[],["save_button"]);
		alert("You are going to delete the party. Click on Save button to proceed deleting");
	
	}
}
function cancelPartyDeleting(){
	clearErrors();
	allInOne({actionToDo:""},["add_button"],[],["save_button"],["add_button"]);
}
function doRequiredThingsParty(){
	if(getValue("actionToDo")=="DELETE"){
		var confirmation= confirm("This action will permanantly delete this party and cannot be undone. Do you want to continue ?");
		if(confirmation==true){
			enableById("party");
			return true;
		}
	}
}


