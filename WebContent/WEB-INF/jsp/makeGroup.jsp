<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!--	JS読み込み	-->
<script type="text/javascript" charset="UTF-8" language="javascript" src="./dialog.js"></script>
</head>
<body>
	<!-- 以下、ヘッダー部分になります。各自実装お願いします -->
	${ session.getUserName() }さん
	<br>
	<form name="log_out" action="/chat/logout" method="POST">
	<input type="button" value="logout"onClick="if(confirm ('本当にログアウトしますか？')){submit();}">
	</form>
	<hr>
	<!-- ここまでです -->
	<h1>チャット研修プログラム</h1>
	<h2>グループ作成</h2>
	<p>誰にもチェックを入れなかった場合、あなたひとりのグループができます</p>
	<form action="/chat/makeGroup" method="POST">
		グループ名<input type="text" name="groupName" value="" size="30"> <br>
		<table>
			<tr>
			<c:forEach var="obj" items="${bean.getUserName()}"
					varStatus="status">
				<c:if test="${status.index % 3 == 0}">
					</tr>
					<tr>
				</c:if>
				<td>
			<input type="checkbox" name="userNo" value="${bean.getUserNo()[status.index]}">${bean.getUserName()[status.index]}
				</td>
			</c:forEach>
			</tr>
		</table>
		<br> <input type="submit" value="グループを作成する">

	</form>

	<form action="/chat/main" method="POST">
		<input type="submit" value="メインメニューに戻る">
	</form>
</body>
</html>