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
	<h2>メインメニュー</h2>
	<br>■会員一覧
	<br>
	<c:forEach var="obj" items="${bean.getUserName()}" varStatus="status">
			<form name="${obj}" method="post" action="/chat/directMessage">
				<input type=hidden name="userNo" value="${bean.getUserNo()[status.index]}"> <a
					href="javascript:${obj}.submit()">${obj}</a>
			</form>
		<p>> ${bean.getDirectMessage()[status.index]}</p>
		<br>
	</c:forEach>

	<br>■グループ一覧
	<br> ${bean.getGroupNullMes()[status.index]}
	<c:forEach var="obj" items="${bean.getGroupName()}" varStatus="status">
		<form name="${obj}" method="post" action="/chat/groupMessage">
				<input type=hidden name="groupNo" value="${bean.getGroupNo()[status.index]}"> <a
					href="javascript:${obj}.submit()">${obj}</a>
			</form>
		<p>> ${bean.getGroupMessage()[status.index]}</p>
		<br>
	</c:forEach>

	<br>
	<br>
	<form action="/chat/makeGroup" method="GET">
		<input type="submit" value="グループの作成">
	</form>
	<form action="/chat/myPage" method="GET">
		<input type="submit" value="プロフィール画面へ">
	</form>


</body>
</html>