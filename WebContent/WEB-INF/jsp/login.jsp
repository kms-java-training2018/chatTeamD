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
			<br>
			<br>
			<p class="title">Ch@</p>
			<p class="sub">kms2018 chat tool</p>
			<br>
			<br> <input type="text" name="userId" value="${been.userId}"
				title="会員ID/半角20字まで" class="placeholder" class="id"> <input
				type="password" name="password" value="${been.password}"
				title="1234567890" class="placeholder" class="password"> <br>
			<div class="error">${ errorMessage }</div><br>
			<input type="submit" value="ログイン">
		</center>
	</form>
</body>
</html>