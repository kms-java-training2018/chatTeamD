<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>グループチャット</title>
<link rel="stylesheet" type="text/css" href="./css/groupMessage.css"
	media="all">
<link rel="stylesheet" type="text/css" href="./css/Maincss.css"
	media="all">
<script src="./js/doubleSubmit.js"></script>
<script src="./js/login.js"></script>

</head>
<body id="page">
	<!-- 以下、ヘッダー部分になります。各自実装お願いします -->
	<div id="header">
		${ session.getUserName() }さん <br>
		<form name="logout" action="/chat/logout" method="POST">
			<a href="javascript:if(confirm ('本当にログアウトしますか？')){logout.submit();}">logout</a>
		</form>
		<hr>
	</div>
	<!-- ここまでです -->

	<center>
		<font color="red" size="5"><Strong>${ errorMsg }</Strong></font>
	</center>
	<center>
		<table>
			<tr>
				<td class="topoutside"></td>
				<td class="groupname">${ bean.getGroupName() }</td>
				<td class="topoutside"></td>
			</tr>
			<tr>
				<td class="topoutside"></td>
				<td class="author">作成者: ${ bean.getAuthorName() }</td>
				<td class="topoutside"></td>
			</tr>
		</table>

				<div style="display: inline-flex">

			<form action="/chat/addGroupMember" method="GET" target="newtab">
				<input type="hidden" name="groupNo" value="${ bean.getGroupNo() }">
				<input type="submit" value="メンバーを追加する" class="btn2">
			</form>
			<form action="/chat/exitGroupMember" method="GET" target="newtab">
				<input type="hidden" name="groupNo" value="${ bean.getGroupNo() }">
				　<input type="submit" value="メンバーを脱退させる" class="btn2">
			</form>
			<c:if test="${ bean.getAuthorName().equals(session.getUserName()) }">
				<form action="/chat/breakup" method="post">
					<input type="hidden" name="groupNo" value="${ bean.getGroupNo() }">
					　<input class="btn2" type="button" value="解散！"
						onclick="if(confirm ('本当に解散しますか')){submit();}">
				</form>
			</c:if>
						<c:if test="${!bean.getAuthorName().equals(session.getUserName()) }">
				<form action="/chat/groupMessage" method="POST">
					<input type="hidden" name="exit" value="${ groupBean.getGroupNo()}">
					　<input class="btn2" type="button" value="グループ脱退"
						onClick="if(confirm ('本当に脱退しますか')){submit();}">
				</form>
			</c:if>
		</div>

	</center>
	<center>
		<table cellspacing="0">
			<!-- 自分 -->
			<c:forEach items="${ list }" var="obj" varStatus="status">
				<c:if test="${obj.userNo.equals(session.getUserNo()) }">
					<tr>
						<td class="msoutside"></td>
						<td class="msnone"></td>
						<td class="msinside"></td>
						<td rowspan="2" id="me${ obj.colorNo }"><c:out
								value="${ obj.message }" /></td>
						<td id="myName"><c:out value="${ obj.userName }" /></td>
					</tr>
					<tr>
						<td class="msoutside"></td>
						<td></td>
						<td></td>
						<td class="msoutside">
							<form action="/chat/groupMessage" method="post">
								<input type="hidden" name="delete"> <input type="hidden"
									name="groupNo" value="${ bean.groupNo }"><input
									type="hidden" name="deleteNo" value="${ obj.messageNo }">
								<input class="deletebtn" type="button" value="削除"
									onClick="if(confirm ('本当に削除しますか？')){submit();}">
							</form>
						</td>
					</tr>

					<tr>
						<td colspan="5" height="10px"></td>
					</tr>
				</c:if>
				<!-- グループ脱退者 -->
				<c:if
					test="${ obj.userName.equals(bean.getOutFlagMessage()) && !obj.userNo.equals(session.getUserNo()) }">
					<tr>
						<td class="msoutside" rowspan="2" id="otherName"><c:out
								value="${ obj.userName }" /></td>
						<td rowspan="2" id="other${ obj.colorNo }"><c:out
								value="${ obj.message }" /></td>
						<td class="msinside"></td>
						<td class="msnone"></td>
						<td class="msoutside"></td>
					</tr>
					<tr>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td colspan="5" height="10px"></td>
					</tr>
				</c:if>
				<!-- それ以外の方々 -->
				<c:if
					test="${!obj.userName.equals(bean.getOutFlagMessage()) && !obj.userNo.equals(session.getUserNo())}">
					<tr>
						<td id="otherName"><a
							href="/chat/showProfile?otherUserNo=${ obj.userNo }"
							target="blank"><c:out value="${ obj.userName}" /></a></td>
						<td width="38%" rowspan="2" id="other${ obj.colorNo }"><c:out
								value="${ obj.message }" /></td>
						<td class="msinside"></td>
						<td class="msnone"></td>
						<td class="msoutside"></td>
					</tr>
					<tr>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td colspan="5" height="10px"></td>
					</tr>

				</c:if>
			</c:forEach>
		</table>
		<form action="/chat/groupMessage" method="POST"
			onSubmit="return send()">
			<input type="text" name="message" id="textarea"
				title="${ bean.getGroupName() }へのメッセージ" class="placeholder"><input
				type="hidden" name="groupNo" value="${ bean.getGroupNo()}">
			<input type="submit" value="送信" class="btn">
		</form>


		<form action="/chat/main" method="POST">
			<input type="submit" value="メインメニューに戻る" class="btn2">
		</form>
	</center>
</body>
</html>