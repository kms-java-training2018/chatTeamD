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
	<center>
		<h1>${ bean.getGroupName() }|作成者:${ bean.getAuthorName() }</h1>
	</center>
	<center>
		<table cellspacing="0">
			<!-- 自分 -->
			<c:forEach items="${ list }" var="obj" varStatus="status">
				<c:if test="${obj.userNo.equals(session.getUserNo()) }">
					<tr>
						<!-- name -->
						<td colspan="3" align="right" width="100%"><c:out
								value="${ obj.userName }" /></td>
					</tr>
					<tr>
						<!-- message -->
						<td colspan="2" align="right" width="95%">「 <c:out
								value="${ obj.message }" />」
						</td>
						<!-- delete -->
						<td width="5%" valign="bottom">
							<form action="/chat/groupMessage" method="post">
								<input type="hidden" name="delete"> <input type="hidden"
									name="groupNo" value="${ bean.groupNo }"><input
									type="hidden" name="deleteNo" value="${ obj.messageNo }">
								<input type="button" value="削除"
									onClick="if(confirm ('本当に削除しますか？')){submit();}">
							</form>
					</tr>
					<tr>
						<td colspan="3" height="10px"></td>
					</tr>
				</c:if>
				<!-- グループ脱退者 -->
				<c:if
					test="${ obj.userName.equals(bean.getOutFlagMessage()) && !obj.userNo.equals(session.getUserNo()) }">
					<tr>
						<!-- name -->
						<td colspan="3" width="100%"><c:out value="${ obj.name }" />
						</td>
					</tr>
					<tr>
						<!-- message -->
						<td colspan="3" width="100%">「<c:out value="${ obj.message }" />
							」
						</td>
					</tr>
					<tr>
						<td colspan="3" height="10px"></td>
					</tr>
				</c:if>
				<c:if
					test="${!obj.userName.equals(bean.getOutFlagMessage()) && !obj.userNo.equals(session.getUserNo())}">
					<tr>
						<td width="100%" align="left" colspan="3"><a
							href="/chat/showProfile?otherUserNo=${ obj.userNo }"
							target="blank"><c:out value="${ obj.userName}" /></a></td>
					</tr>
					<tr>
						<td width="100%" align="left" colspan="3">「<c:out
								value="${ obj.message}" />」
						</td>
					</tr>
					<tr>
						<td colspan="3" height="10px"></td>
					</tr>
				</c:if>
			</c:forEach>
		</table>
		<form action="/chat/groupMessage" method="POST">
			<input type="text" name="message"> <input type="hidden"
				name="groupNo" value="${ bean.getGroupNo()}"><input
				type="submit" value="送信">
		</form>


		<form action="/chat/main" method="POST">
			<input type="submit" value="メインメニューに戻る">
		</form>
	</center>
</body>
</html>