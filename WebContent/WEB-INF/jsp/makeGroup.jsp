<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新規グループ作成</title>
<!-- css読み込み -->
<link rel="stylesheet" type="text/css" href="./css/Maincss.css"
	media="all">
<link rel="stylesheet" type="text/css" href="./css/makeGroup.css"
	media="all">
<!--	JS読み込み	-->
<script src="./js/login.js"></script>
<script src="./js/doubleSubmit.js"></script>
<noscript>
<meta http-equiv="Refresh" content="0;URL=/chat/errorPage">
</noscript>
</head>
<body id="page">
	<!-- 以下、ヘッダー部分になります。各自実装お願いします -->
	<div id="header">
		${ session.getUserName() }さん <br>
		<form name="logout" action="/chat/logout" method="POST">
			<a href="javascript:if(confirm ('本当にログアウトしますか？')){logout.submit();}">logout</a>
		</form>
		<hr>
	</div>
	<div id="toBottom">
	<a href="#footer">∨ ページ最下部へ</a> <br>
	</div>
	<!-- ここまでです -->
	<div class="page">
		<h2>Make Group</h2>
		<p>誰にもチェックを入れなかった場合、あなたひとりのグループができます</p>
		<p>
			<font color="#FF0000">${errorMsg}</font>
		</p>
		<form action="/chat/makeGroup" method="POST" onSubmit="return send()">
			<input type="text" title="グループ名(全角10文字まで)" class="placeholder"
				name="groupName" value="" size="30"> <br>
		<div class="listDiv">
		<c:forEach var="obj" items="${bean}" varStatus="status">
			<a class="listCell"> <input type="checkbox" name="userNo"
				value="${bean[status.index].userNo}">${bean[status.index].userName}
			</a>
		</c:forEach>
		</div>
			<br>
			<a class="imgBtn">
			<input type="image" src="./img/makeGroupBtn.png" name="button" alt="makeGroup" height="80">
			</a>
		</form>
	</div>

	<div class="backBtn">
		<br> <br> <br>
		<form action="/chat/main" method="POST">
			<a class="imgBtn">
			<input type="image" src="./img/backMainPage.png" name="button"
				alt="makeGroup" height="40">
			</a>
		</form>
	</div>
	<!-- 以下、フッター部分になります。各自実装お願いします -->
	<br>
	<div id="footer">
		<hr>
		<a href="#header">＾ページトップへ戻る</a> <br>
		<br>
	</div>
	<!-- ここまでです -->
</body>
</html>