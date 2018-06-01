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
	<c:forEach var="obj" items="${bean.getListUserName()}"
		varStatus="status">
		<form name="showProfile" method="get" action="/chat/showProfile">
			<input type=hidden name="otherUserNo" value="${userNo}"> <a
				href="javascript:showProfile.submit()">${obj}</a>
			<p>「 ${bean.getListMessage()[status.index]} 」</p>
		</form>
		<br>
	</c:forEach>
	<br>
	<p></p>
	<br>
	<br>
	<form action="/chat/groupMessage" method="POST">
		<input type="text" name="message"> <input type="submit"
			value="メッセージの送信">
	</form>
	<form action="/chat/main" method="POST">
		<input type="submit" value="メインメニューに戻る">
	</form>
</body>
</html>