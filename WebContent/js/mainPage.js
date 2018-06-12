/**
 * MainPage
 */
window.onload = function() {
	// 最初GM隠してDM選択状態色にする
	document.getElementById("groupMessage").style.display = "none";
	var dmBtn = document.getElementById("directMessageBtn");
	dmBtn.style.backgroundColor = '#FFC085';
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
