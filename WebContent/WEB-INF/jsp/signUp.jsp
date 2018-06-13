<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新規登録</title>
<link rel="stylesheet" type="text/css" href="./css/signup.css"
	media="all">
<script src="./js/login.js"></script>
</head>
<body class="main">
	<center>
		<br> <br>

		<p class="p1">Ch@ 新規会員登録ページ</p>
		<p class="p3">すべて必須入力となっています。</p>

		 <br> <br>
		<form action="/chat/signup" method="post">
			<table class="table">
				<tr>
					<td class="td">会員ID</td>
					<td class="td">パスワード</td>
					<td class="td">ユーザ名</td>
				</tr>
				<tr>
					<td class="td"><input type="text" name="userId"
						title="半角20字まで" class="placeholder"></td>
					<td class="td"><input type="password" name="password"
						title="半角20字まで" class="placeholder"></td>
					<td class="td"><input type="text" name="userName" title="表示名"
						class="placeholder"></td>
				</tr>
				<tr>
					<td class="td"></td>
					<td class="td"><input type="submit" value="登録"></td>
					<td class="td"></td>
				</tr>


			</table>
		</form>






	</center>

	${ errormsg }

	<br>

		<a href="/chat/login"> <input type="submit" value="戻る">
		</a>
</body>
</html>