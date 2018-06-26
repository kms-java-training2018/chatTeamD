/**
 * doubleSubmit防止用です
 */
flag = false;
function send()
{
	if (flag) { alert("送信済みです"); return false; }
	flag = true;
	return true;
}