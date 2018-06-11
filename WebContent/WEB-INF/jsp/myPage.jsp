<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>マイページ</title>
</head>
<body>
	<!-- 以下、ヘッダー部分になります。各自実装お願いします -->
	${ session.getUserName() }さん
	<br>
	<form name="log_out" action="/chat/logout" method="POST">
		<input type="button" value="logout"
			onClick="if(confirm ('本当にログアウトしますか？')){submit();}">
	</form>
	<hr>
	<!-- ここまでです -->
	<br>
	<center>
		<form action="/chat/myPage" method="POST">

			<table>
				<tr>
					<td width="100">名前</td>
					<td><textarea cols="30" name="dispName">${ myName }</textarea>
				</tr>
				<tr>
					<td width="100">自己紹介</td>
					<td><textarea cols="30" rows="20" name="myPageText">${ myPageText }</textarea>
				</tr>
			</table>

			<input type="hidden" name="dispName" value="${ bean.getUserName() }">
			<input type="hidden" name="myPageText" value="${ bean.getMyPageText }"> <input
				type="submit" value="プロフィールを更新">
		</form>
		<form action="/chat/main" method="POST">
			<input type="submit" value="メインメニューに戻る">
		</form>
		<br> <font color="red"><Strong>${ errormessage }</Strong></font>
		<br>
	</center>
</body>
</html>