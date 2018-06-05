<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>グループチャット</title>
</head>
<body>
	<!-- 以下、ヘッダー部分になります。各自実装お願いします -->
	${ session.getUserName() }さん
	<br>
	<form name="log_out" action="/chat/logout" method="POST">
		<input type="button" value="logout"
			onClick="if(confirm ('本当にログアウトしますか？')){submit();}">
	</form>
	<hr>
	<!-- ここまでです -->
	<center>
		<font color="red" size="5"><Strong>${ errorMsg }</Strong></font>
	</center>
	<c:forEach var="obj" items="${bean.getListUserNo()}" varStatus="status">
		<!-- 自分のメッセージ -->
		<c:if
			test="${bean.getListUserNo()[status.index].equals(session.getUserNo()) }">
			<div align="right">
				<form name="SP" method="get" action="/chat/showProfile">
					<input type=hidden name="otherUserNo"
						value="${bean.getListUserNo()[status.index]}">
					${bean.getListUserName()[status.index]}
				</form>
				<p>「 ${bean.getListMessage()[status.index]} 」</p>
				<form name="DLT" method="post" action="/chat/groupMessage">
					<input type="hidden" name="groupNo"
						value="${ groupBean.getGroupNo()}"> <input type="hidden"
						name="delete" value="${bean.getListMsgNo()[status.index]}">
					<input type="hidden" name="delete"> <input type="button"
						value="メッセージ削除" onClick="if(confirm ('本当に削除しますか？')){submit();}">
				</form>
			</div>
		</c:if>

		<!-- グループ脱退者のメッセージ -->
		<c:if
			test="${bean.getListUserName()[status.index].equals(bean.getOutFlag1()) && !bean.getListUserNo()[status.index].equals(session.getUserNo())  }">
			<div align="left">
				<form name="SP" method="get" action="/chat/showProfile">
					<input type=hidden name="otherUserNo"
						value="${bean.getListUserNo()[status.index]}">
					${bean.getListUserName()[status.index]}
				</form>
				<p>「 ${bean.getListMessage()[status.index]} 」</p>
			</div>
		</c:if>

		<!-- 他者のメッセージ -->
		<c:if
			test="${!bean.getListUserNo()[status.index].equals(session.getUserNo())  && !bean.getListUserName()[status.index].equals(bean.getOutFlag1())}">
			<div align="left">
				<form target="newtab" name="SP" method="get"
					action="/chat/showProfile">
					<input type=hidden name="otherUserNo"
						value="${bean.getListUserNo()[status.index]}"> <a
						href="javascript:SP[${status.index}].submit()">${bean.getListUserName()[status.index]}</a>
				</form>
				<p>「 ${bean.getListMessage()[status.index]} 」</p>
			</div>
		</c:if>
		<br>
		<hr width="100%" size="1" color="orange" style="border-style: dotted">
	</c:forEach>
	<br>
	<p></p>
	<br>
	<br>
	<center>
		<form action="/chat/groupMessage" method="POST">
			<input type="text" name="message"> <input type="hidden"
				name="groupNo" value="${ groupBean.getGroupNo()}"><input
				type="submit" value="メッセージの送信">
		</form>
	</center>
	<form action="/chat/groupMessage" method="POST">
		<input type="hidden" name="exit" value="${ groupBean.getGroupNo()}">
		<input type="button" value="グループ脱退"
			onClick="if(confirm ('本当に脱退しますか？')){submit();}">
	</form>
	<form action="/chat/main" method="POST">
		<input type="submit" value="メインメニューに戻る">
	</form>
</body>
</html>