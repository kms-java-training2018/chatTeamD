<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>プロフィール</title>
	<link rel="stylesheet" type="text/css" href="./css/Maincss.css"
	media="all">
	<!-- js無効時エラーページに飛ぶ -->
<noscript>
<meta http-equiv="Refresh" content="0;URL=/chat/errorPage">
</noscript>
</head>
<body id="page">
<br><br><br><br>
	<form action="/chat/showProfile" method="POST">
		<center>
			<table>
				<tr>
					<td>名前</td>
					<td><c:out value="${ showName }"></c:out></td>
				</tr>
				<tr>
					<td>自己紹介</td>
					<td><c:out value="${ showMyPageText }"></c:out></td>
				</tr>
			</table>
			<input type="button" value="戻る" onClick="window.close();">
		</center>
	</form>
</body>
</html>