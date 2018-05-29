<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>show profile</title>
</head>
<body>
	<h1>チャット研修プログラム</h1>
	<h2>プロフィール確認</h2>
	<form action="/chat/showProfile" method="POST">
	<center>
		<table>
			<tr>
				<td>名前</td>
				<td>${ showName }</td>
			</tr>
			<tr>
				<td>自己紹介</td>
				<td>${ showMyPageText }</td>
			</tr>
		</table>
		<input type="submit" value="閉じる">
	</center>
	</form>
</body>
</html>