<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>OTP</title>

</head>
<body onload="enableButtonAfterTimeout()">

<form id="registration_Form" method="POST" action="">

OTP :<input type="text"/><br>
	<input id="otpButton" type="button" value="Get Verification code" onclick="callOTPGenerator()"/>
	<p id="msgLimitReached"></p>
	 <input type="submit"  value="Verify"/><br>
	  
</form>
<script>
var resendOTPCounter=0;
function countDown() {
	var counter = [];
	for (var j = 10; j >= 0; j--) {
		counter[10 - j] = j;
	}
	counter[11] = "Resend OTP";
	for (var i = 0; i <= 11; i++) {
		let k = i;
		setTimeout(function() {
			document.getElementById("otpButton").value = counter[k];
		}, 1000 * k);
	}
}
function enableButtonAfterTimeout() {
	document.getElementById("otpButton").disabled=true;
	countDown();
	setTimeout(function(){document.getElementById("otpButton").disabled=false;},11*1000);

}
function callOTPGenerator(){
	resendOTPCounter=resendOTPCounter+1;
	if(resendOTPCounter <3){
	window.location.href="/Vote/generateOTP";
	}else{
		document.getElementById("msgLimitReached").innerHTML="Maximum attempt reached";
	}
}
</script>
</body>

</html>