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
	<!-- 以下、ヘッダー部分になります。各自実装お願いします -->
	${ userName }さん
	<br>
	<form action="/chat/myPage" method="POST">
		<input type="hidden" name="logout" value="logout"><input
			type="submit" value="ログアウト">
	</form>
	<hr>
	<!-- ここまでです -->
	<br>
	<form action="/chat/myPage" method="POST">
		<center>
			<table width=>
				<tr>
					<td width="20%">名前</td>
					<td width="20%"><textarea cols="30" name="dispName">${ myName }</textarea>
				</tr>
				<tr>
					<td width="20%">自己紹介</td>
					<td width="20%"><textarea cols="30" rows="20"
							name="myPageText">${ myPageText }</textarea>
				</tr>

			</table>
		</center>
		<input type="submit" value="プロフィールを更新">
	</form>
	<form action="/chat/main" method="POST">
		<input type="submit" value="メインメニューに戻る"> <br> ${ errorMsg }<br>
	</form>

	${ errorMsg }
	<br>
</body>
</html>