<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ログイン</title>

<link rel="stylesheet" type="text/css" href="./css/login.css"
	media="all">
<script src="./js/login.js"></script>
</head>
<body class="main">
	<form action="/chat/login" method="POST">
		<center>
			<br> <br>
			<img src="./img/title.png" alt="Ch@" width="25%">
			<br> <br> <input type="text" name="userId"
				value="${been.userId}" title="USER ID" class="placeholder"
				class="id"> <input type="password" name="password"
				value="${been.password}" title="1234567890" class="placeholder"
				class="password"> <br>
			<div class="error"><c:out value="${ errorMessage }"></c:out></div>
			<br> <input type="submit" value="ログイン">



		</center>
	</form>
	<center>
		<form action="/chat/signup" method="get">
		<input type="submit" value="新規登録">
		</form>
	</center>
</body>
</html>