<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!--	JS読み込み	-->
<script type="text/javascript" charset="UTF-8" language="javascript" src="./dialog.js"></script>
<script>
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
</script>
</head>
<body>
	<!-- 以下、ヘッダー部分になります。各自実装お願いします -->
	${ session.getUserName() }さん
	<br>
	<form name="logout">
		<input type="button" value="ログアウト"
			onclick="return dialog('logout', '/chat/logout', 'POST','ログアウトしてよろしいですか')">
	</form>
	<form name="log_out" action="/chat/logout" method="POST">
	<input type="submit" value="logout"onClick="confirm('本当にログアウトしますか？')">
	<input type=hidden name="log_out" value="${messageNo[status.index]}" onClick="if(confirm ('本当に削除しますか？')){submit();}" >
	</form>
	<hr>
	<!-- ここまでです -->
	<h1>チャット研修プログラム</h1>
	<h2>メインメニュー</h2>
	<br>■会員一覧
	<br>
	<c:forEach var="obj" items="${userbean.getUserNo()}" varStatus="status">
		<form name="DM" method="get" action="/chat/directMessage">
			<input type=hidden name="userNo"
				value="${userbean.getUserNo()[status.index]}"> <a
				href="javascript:DM[${status.index}].submit()">${userbean.getUserName()[status.index]}</a>
		</form>
		<p>> ${userbean.getDirectMessage()[status.index]}</p>
		<br>
	</c:forEach>

	<br>■グループ一覧
	<br> ${groupbean.getGroupNullMes()}
	<c:forEach var="obj" items="${groupbean.getGroupNo()}"
		varStatus="status">
		<form name="GM" method="get" action="/chat/groupMessage">
			<input type=hidden name="groupNo"
				value="${groupbean.getGroupNo()[status.index]}"> <a
				href="javascript:GM[${status.index}].submit()">${groupbean.getGroupName()[status.index]}</a>
		</form>
		<p>> ${groupbean.getGroupMessage()[status.index]}</p>
		<br>
	</c:forEach>

	<br>
	<br>
	<form action="/chat/makeGroup" method="GET">
		<input type="submit" value="グループの作成">
	</form>
	<form action="/chat/myPage" method="GET">
		<input type="submit" value="プロフィール画面へ">
	</form>


</body>
</html>