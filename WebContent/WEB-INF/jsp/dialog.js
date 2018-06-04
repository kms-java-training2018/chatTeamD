/**
 * はい/いいえのダイアログ表示
 */
// windows.onloadとほぼ同義
function dialog(formname, url, method, text) {
	var result = confirm(text);
	if (result) {
		// true
		// urlへ
		var f = document.forms[formname];
		f.method = method;
		f.action = url;
		f.submit();
		// window.location.href = '/chat/logout';

	} else {
		// false
	}
}