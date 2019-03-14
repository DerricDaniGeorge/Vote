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
	var electionName=document.getElementById("electionName");
	var startDate=document.getElementById("startDate");
	var endDate=document.getElementById("endDate")
	electionName.value=selectedValue;
	hideById("save_button");
	disableById("electionName");
	disableById("startDate");
	disableById("endDate");
	var elections=JSON.parse(JSON.stringify(${jsonElections}));
	for(i=0;i<elections.length;i++){
		if((elections[i].electionName)===selectedValue){
			startDate.value=elections[i].electionDetails.START_DATE;
			endDate.value=elections[i].electionDetails.END_DATE;
			break;
		}
	}
}

function add(){
	disableById("modify_button");
	disableById("delete_button");
	enableById("electionName");
	setValue("electionName","");
	enableById("startDate");
	setValue("startDate","");
	enableById("endDate");
	setValue("endDate","");
	unHideById("save_button");
}
function modify(){
	if(getValue("electionName")==""){
		alert("No election selected");
	}else{
		enableById("electionName");
		enableById("startDate");
		enableById("endDate");
		unHideById("save_button");
	}
}
