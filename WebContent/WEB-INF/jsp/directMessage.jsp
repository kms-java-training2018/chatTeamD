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
	<!-- 以下、ヘッダー部分-->
	${ session.getUserName() }さん
	<br>
	<form action="/chat/logout" method="POST">
		<input type="hidden" name="logout" value="logout"><input
			type="submit" value="ログアウト">
	</form>
	<hr>
	<!-- ここまで -->

<h1></h1>
    <br>
    <form name="showProfile" action="/chat/showProfile" method="GET">

		<input type=hidden name="otherUserNo" value="${bean.getUserNo()}">
		<a href="javascript:showProfile.submit()" >${bean.getUsername()}</a>

	</form>


	<br>
	<c:forEach var="obj" items="${bean.getListMessage()}" varStatus="status">
		<c:if test="${bean.getListUserNo()[status.index]==bean.getMyNo()}">
			<form name="" action="/chat/directMessage" method="POST">
				<input type=hidden name="check" value="1"><input type=hidden
					name="userNo" value="${bean.getUserNo()}"> <input type=hidden
					name="deleteMessageNo" value="${bean.getListMsgNo()[status.index]}">
				<input type="button" value="メッセージの削除"
					onClick="if(confirm ('本当に削除しますか？')){submit();}">
			</form>
		</c:if>



		<form name="showProfile" action="/chat/showProfile" method="GET">
			<input type=hidden name="otherUserNo" value="${bean.getListUserNo()[status.index]}">
			<a href="javascript:showProfile[${status.index}].submit()" >${bean.getListUserName()[status.index]}</a>：${obj}
		</form>
	</c:forEach>



	<br>
	<br>
	<br>
	<br>


	<form action="/chat/directMessage" method="POST">
		<input type=hidden name="check" value="2"> <input type="text"
			name="sendMessage" size="30"><input type=hidden name="userNo"
			value="${bean.getUserNo()}"> <input type="submit"
			value="メッセージの送信" name="sendMessage">
	</form>
	<form action="/chat/main" method="POST">
		<input type="submit" value="メインメニューへ戻る">
	</form>
</body>
</html>