<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新規グループ作成</title>
<!-- css読み込み -->
<link rel="stylesheet" type="text/css" href="./css/Maincss.css"
	media="all">
<!--	JS読み込み	-->
</head>
<body id="page">
	<!-- 以下、ヘッダー部分になります。各自実装お願いします -->
	<div id="header">
		${ session.getUserName() }さん <br>
		<form name="log_out" action="/chat/logout" method="POST">
			<input type="button" value="logout"
				onClick="if(confirm ('本当にログアウトしますか？')){submit();}">
		</form>
		<hr>
	</div>
	<!-- ここまでです -->
	<div class="page">
		<h2>Make Group</h2>
		<p>誰にもチェックを入れなかった場合、あなたひとりのグループができます</p>
		<p>
			<font color="#FF0000">${errorMsg}</font>
		</p>
		<form action="/chat/makeGroup" method="POST">
			グループ名(全角10文字まで)<input type="text" name="groupName" value="" size="30">
			<br>
			<table class="centerizedTable">
				<tr>
					<c:forEach var="obj" items="${bean}" varStatus="status">
						<c:if test="${status.index % 3 == 0}">
				</tr>
				<tr>
						</c:if>
					<td>
					<input type="checkbox" name="userNo"
					value="${bean[status.index].userNo}">${bean[status.index].userName}
					</td>
					</c:forEach>
				</tr>
			</table>
			<br>
			<input type="image" src="./img/makeGroupBtn.png" name="button" alt="makeGroup" height="80">
		</form>

		<form action="/chat/main" method="POST">
			<input type="submit" value="メインメニューに戻る">
		</form>
	</div>
</body>
</html>