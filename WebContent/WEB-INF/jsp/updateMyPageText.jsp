<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="/chat/updateMyPage" method="POST">
		<table>
			<tr>
				<td>表示名</td>
				<td><input type="text" name="dispName"></td>
			</tr>
			<tr>
				<td>自己紹介</td>
				<td><input type="text" name="myPageText"></td>
			</tr>
		</table>


		<input type="submit" value="更新">
	</form>
	<font color="red"><strong>${ msg }</strong></font>
	<form action="/chat/myPage" method="GET">
		<input type="submit" value="戻る">
	</form>

</body>
</html>