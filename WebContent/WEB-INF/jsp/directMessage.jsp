<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>個人チャット</title>
</head>
<body>
	<!-- 以下、ヘッダー部分になります。各自実装お願いします -->
	<!-- 修正箇所　bean で値を取得できないか確認　　　　　　　--------------------------------- -->
	${ session.getUserName }さん
	<br>
	<form name="log_out" action="/chat/logout" method="POST">
		<input type="button" value="logout"
			onClick="if(confirm ('本当にログアウトしますか？')){submit();}">
	</form>
	<hr>
	<!-- ここまでです -->
	<font color="red" size="5"><Strong>${bean.getErrorMsg()}</Strong></font>
	<h1></h1>
	<br>
	<form name="topShowProfile" action="/chat/showProfile" method="GET"
		target="newtab">
<!-- 名前を押すとユーザープロフィールに飛ぶ処理 -->
		<input type=hidden name="otherUserNo" value="${bean.getUserNo()}">
		<a href="javascript:topShowProfile.submit()">${bean.getUsername()}</a>

	</form>
	<!-- メッセージを表示し、自分のメッセージであれば削除ボタンを付与する処理 -->
	<br>
	<c:forEach var="obj" items="${bean.getListMessage()}"
		varStatus="status">
		<c:if test="${bean.getListUserNo()[status.index]==bean.getMyNo()}">

			<form name="" action="/chat/directMessage" method="POST">
				<input type=hidden name="check" value="1"> <input
					type=hidden name="userNo" value="${bean.getUserNo()}"> <input
					type=hidden name="deleteMessageNo"
					value="${bean.getListMsgNo()[status.index]}"> <input
					type="button" value="メッセージの削除"
					onClick="if(confirm ('本当に削除しますか？')){submit();}">
			</form>

		</c:if>

		<!-- 相手のユーザー名を押すとプロフィールに飛ぶ処理 -->
		<c:choose>
			<c:when test="${bean.getListUserNo()[status.index]==bean.getMyNo()}">

	    <form name="showProfile" action="/chat/showProfile" method="GET"
					target="newtab">
		${bean.getListUserName()[status.index]}：${obj}
		</form>

		</c:when>
			<c:otherwise>
				<form name="showProfile" action="/chat/showProfile" method="GET"
					target="newtab">
					<input type=hidden name="otherUserNo"
						value="${bean.getListUserNo()[status.index]}"> <a
						href="javascript:showProfile[${status.index}].submit()">${bean.getListUserName()[status.index]}</a>：${obj}
				</form>
			</c:otherwise>
		</c:choose>
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