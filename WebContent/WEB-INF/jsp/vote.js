/**
 * 
 */
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
	setTimeout(function(){document.getElementById("otpButton").disabled=false;},12*1000);

}