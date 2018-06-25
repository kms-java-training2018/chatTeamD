<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>メンバー脱退</h1>
	<div class="listDiv">
		<form action="/chat/withdrawal" method="post">
			<c:forEach items="${ list }" var="obj" varStatus="status">
				<c:if test="${ !session.getUserName().equals(obj.userName) }">
					<a class="listCell"><input type="checkbox" name="userNo"
						value="${ obj.userNo }"> <c:out value="${ obj.userName }">
						</c:out></a>
				</c:if>
			</c:forEach>
			<center>
				<br> <input type="hidden" name="groupNo"
					value="${ bean.getGroupNo() }">
				<c:if
					test="${ !message.equals('メンバーを脱退させました') && !errorMsg.equals('エラーです。グループページに戻ってください')}">


					<input type="submit" value="強制脱退" class="btn2">
				</c:if>
			</center>
		</form>
	</div>
	${ message } ${ errorMsg }
	<!--  -->
	<center>
		<input type="submit" value="戻る" onclick="window.close()" class="btn2">
	</center>
</body>
</html>