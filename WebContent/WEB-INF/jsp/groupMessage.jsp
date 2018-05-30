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
	<h1>チャット研修プログラム</h1>
	<h2>グループメッセージ</h2>
	<a href="/chat/showProfile">たかつか</a>：グループメッセージのサンプルだよー４（´・ω・｀）
		<br><a href="/chat/showProfile">いしかわ</a>：グループメッセージのサンプルだよー３（´・ω・｀）
		<br><a href="/chat/showProfile">ますだ</a>：グループメッセージのサンプルだよー２（´・ω・｀）
		<br><a href="/chat/showProfile">たかつか</a>：グループメッセージのサンプルだよー１（´・ω・｀）
		<br>あなた：わーい！たーのしー！
	<br>
	<br>
	<form action="/chat/groupMessage" method="POST">
		<input type="submit" value="メッセージの送信">
	</form>
	<form action="/chat/main" method="POST">
		<input type="submit" value="メインメニューに戻る">
	</form>
</body>
</html>