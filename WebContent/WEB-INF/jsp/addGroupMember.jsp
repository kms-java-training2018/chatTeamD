<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- css読み込み -->
<link rel="stylesheet" type="text/css" href="./css/Maincss.css"
	media="all">
<link rel="stylesheet" type="text/css" href="./css/addgroupmember.css"
	media="all">
	<!-- js無効時エラーページに飛ぶ -->
<noscript>
<meta http-equiv="Refresh" content="0;URL=/chat/errorPage">
</noscript>
</head>
<body id="page">
	<h1>メンバー追加</h1>
	<div class="listDiv">
		<form action="/chat/addGroupMember" method="post">
			<c:forEach items="${ list }" var="obj" varStatus="status">
				<a class="listCell"><input type="checkbox" name="userNo"
					value="${ obj.userNo }"> <c:out value="${ obj.userName }">
					</c:out></a>
			</c:forEach>
<center>
<br>
			<input type="hidden" name="groupNo" value="${ bean.getGroupNo() }">
			<c:if
					test="${ !message.equals('メンバーを追加しました') && !errorMsg.equals('エラーです。グループページに戻ってください')}">


					<input type="submit" value="追加" class="btn2"></c:if></center>
		</form>
	</div>
	<c:out value="${ message }"></c:out>
	<c:out value="${ errorMsg }"></c:out>
<!--  -->
		<center><input type="submit" value="戻る" onclick="window.close()" class="btn2"></center>
</body>
</html>