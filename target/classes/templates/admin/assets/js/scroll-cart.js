
window.onscroll = function () { myFunction() };

var bill = document.getElementById("bill");
var sticky = bill.offsetHeight;


function myFunction() {

	if (window.pageYOffset >= sticky) {
		bill.classList.add("sticky1");
	} else {
		bill.classList.remove("sticky1");
	}
}