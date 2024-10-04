/**
 * @author Rakhi Sharma
 */
var count = 5;
var redirectTo = "/";
var elem = document.getElementById("timer");
function countDown(){
	if(elem != null){
		if(count >= 0){
			document.getElementById("timer").innerHTML = count--;
			setTimeout("countDown()", 1000);
		}else{
			window.location.href = redirectTo;
		}
	}
}
countDown();