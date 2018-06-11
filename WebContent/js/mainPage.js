/**
 *
 */
window.onload = function() {
	document.getElementById("groupMessage").style.display = "none";
	var dmBtn = document.getElementById("directMessageBtn");
	dmBtn.style.backgroundColor = '#ff0000';
	document.getElementById("directMessageBtn").onclick = function DMBtnclick() {
		// GM隠す
		var gm = document.getElementById("groupMessage");
		var dm = document.getElementById("directMessage");
		var gmBtn = document.getElementById("groupMessageBtn");
		var dmBtn = document.getElementById("directMessageBtn");
		dm.style.display = "block";
		gm.style.display = "none";
		dmBtn.style.backgroundColor = '#ff0000';
		gmBtn.style.backgroundColor = '#FF7A00';
	};
	document.getElementById("groupMessageBtn").onclick = function GMBtnclick() {
		// DM隠す
		var gm = document.getElementById("groupMessage");
		var dm = document.getElementById("directMessage");
		var gmBtn = document.getElementById("groupMessageBtn");
		var dmBtn = document.getElementById("directMessageBtn");
		dm.style.display = "none";
		gm.style.display = "block";
		dmBtn.style.backgroundColor = '#FF7A00';
		gmBtn.style.backgroundColor = '#ff0000';
	};
};
