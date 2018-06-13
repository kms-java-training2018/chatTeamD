/**
 * MainPage
 */
window.onload = function() {
	var fromCheck = document.getElementByID("from").value;
	if (fromCheck == "GMから来た") {
		// 最初GM隠してDM選択状態色にする
		document.getElementById("groupMessage").style.display = "none";
		var dmBtn = document.getElementById("directMessageBtn");
		dmBtn.style.backgroundColor = '#FFC085';
	} else {
		// 最初DM隠してGM選択状態色にする
		document.getElementById("directMessage").style.display = "none";
		var gmBtn = document.getElementById("groupMessageBtn");
		gmBtn.style.backgroundColor = '#FFC085';
	}
	document.getElementById("directMessageBtn").onclick = function DMBtnclick() {
		// GM隠す
		var gm = document.getElementById("groupMessage");
		var dm = document.getElementById("directMessage");
		var gmBtn = document.getElementById("groupMessageBtn");
		var dmBtn = document.getElementById("directMessageBtn");
		dm.style.display = "block";
		gm.style.display = "none";
		dmBtn.style.backgroundColor = '#FFC085';
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
		gmBtn.style.backgroundColor = '#FFC085';
	};
};
