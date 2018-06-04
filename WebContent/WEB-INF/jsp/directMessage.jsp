<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- 以下、ヘッダー部分-->
	${ session.getUserName() }さん
	<br>
	<form action="/chat/logout" method="POST">
		<input type="hidden" name="logout" value="logout"><input
			type="submit" value="ログアウト">
	</form>
	<hr>
	<!-- ここまで -->
	<h1>チャット研修プログラム</h1>
	<h2>メッセージ</h2>
	${ session.getUserName() }：${sendMessage}
	<br>
	<form name="showProfile" action="/chat/showProfile" method="GET">
	<input type=hidden name="otherUserNo" value="${userNo}">
	<a href="javascript:showProfile.submit()">${username}</a>：${message}
	</form>
	<br>
	<br><br>
	<br>


	<form action="/chat/directMessage" method="POST">
	<input type="text" name="sendMessage" size="30">
		<input type="submit" value="メッセージの送信" name= "sendMessage">
	</form>
	<form action="/chat/directMessage" method="POST">
		<input type="submit" value="メッセージの削除" onClick= "confirm('本当に削除しますか？')">
		<input type=hidden name="deleteMessage" value="deleteMessage" onClick="if(confirm ('本当に削除しますか？')){submit();}">
	</form>
	<form action="/chat/main" method="POST">
		<input type="submit" value="メインメニューへ戻る">
	</form>
</body>
</html>