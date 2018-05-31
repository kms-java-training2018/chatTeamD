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
	<h2>グループ作成</h2>
	<p>誰にもチェックを入れなかった場合、あなたひとりのグループはできません</p>
	<form action="/chat/makeGroup" method="POST">
		グループ名<input type="text" name="groupName" value="" size="30"> <br>
		<c:forEach var="obj" items="${bean.getUserName()}" varStatus="status">
			<input type="checkbox" name="userNo"
				value="${bean.getUserNo()[status.index]}">${obj}
		</c:forEach>
		<br>
		<input type="submit" value="グループを作成する">
	</form>

	<form action="/chat/main" method="POST">
		<input type="submit" value="メインメニューに戻る">
	</form>
</body>
</html>