<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>個人チャット</title>
<link rel="stylesheet" type="text/css" href="./css/directMessage.css"
	media="all">
<link rel="stylesheet" type="text/css" href="./css/Maincss.css" media="all">
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
	<font color="red" size="5"><Strong>${bean.getErrorMsg()}</Strong></font>
	<h1></h1>
	<br>
	<center>
	<form name="topShowProfile" action="/chat/showProfile" method="GET"
		target="newtab">
		<!-- 名前を押すとユーザープロフィールに飛ぶ処理 -->
		<input type=hidden name="otherUserNo" value="${bean.getUserNo()}">
		<h3>
			<a href="javascript:topShowProfile.submit()">${bean.getUsername()}</a>
		</h3>
	</form>
	</center>

	<br>
	<form name="showProfile">
	<!-- リストに入ったログインユーザー名、相手ユーザー名などの順番(インデックス)調整用の空formタグ -->
	</form>
	<table>
		<c:forEach var="obj" items="${bean.listMessage}" varStatus="status">
			<c:if test="${bean.getListUserNo()[status.index]==bean.getMyNo()}">
			</c:if>
			<c:choose>
				<c:when test="${bean.getListUserNo()[status.index]==bean.getMyNo()}">
					<tr>
						<td width="10%" align="left" rowspan="2"></td>
						<td width="38%" align="left" rowspan="2"></td>
						<td width="4%"></td>
						<td width="38%" align="right" rowspan="2" class="me">${obj}</td>
						<td width="10%" align="right">
						<form name="showProfile" action="/chat/showProfile" method="GET">
						${bean.getListUserName()[status.index]}
						</form></td>
					</tr>
					<tr>
						<td></td>
						<td align="right"><form name="" action="/chat/directMessage"
								method="POST">
								<input type=hidden name="check" value="1"> <input
									type=hidden name="userNo" value="${bean.getUserNo()}">
								<input type=hidden name="deleteMessageNo"
									value="${bean.getListMsgNo()[status.index]}"> <input
									type="button" value="削除"
									onClick="if(confirm ('本当に削除しますか？')){submit();}">
							</form></td>
					</tr>
					<tr>
						<td colspan="5" height="10px"></td>
					</tr>
				</c:when>
				<c:otherwise>
					<tr>
						<td width="10%" align="left" rowspan="2"><form
								name="showProfile" action="/chat/showProfile" method="GET"
								target="newtab">
								<input type=hidden name="otherUserNo"
									value="${bean.getListUserNo()[status.index]}"> <a
									href="javascript:showProfile[${status.index+1}].submit()">${bean.getListUserName()[status.index]}</a>
							</form></td>
						<td width="38%" align="left" rowspan="2" class="you">${obj}</td>
						<td width="4%"></td>
						<td width="38%" align="left" rowspan="2"></td>
						<td width="10%" align="left"></td>
					</tr>
					<tr>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td colspan="5" height="10px"></td>
					</tr>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</table>
	<br>
	<br>
	<br>
	<br>
	<center>
	<form action="/chat/directMessage" method="POST">
		<input type=hidden name="check" value="2"> <input type="text"
			name="sendMessage" size="50"><input type=hidden name="userNo"
			value="${bean.getUserNo()}"> <input type="submit"
			value="送信" name="sendMessage">
	</form>
	<form action="/chat/main" method="POST">
		<input type="submit" value="メインメニューに戻る">
	</form>
	</center>
</body>
</html>