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
<link rel="stylesheet" type="text/css" href="./css/Maincss.css"
	media="all">
<script src="./js/doubleSubmit.js"></script>
<script src="./js/login.js"></script>


<!-- js無効時エラーページに飛ぶ -->
<noscript>
<meta http-equiv="Refresh" content="0;URL=/chat/errorPage">
</noscript>


</head>
<body id="page">
	<!-- 以下、ヘッダー部分になります。各自実装お願いします -->
	<div id="header">
		<c:out value="${ session.getUserName() }さん"></c:out> <br>
		<form name="logout" action="/chat/logout" method="POST">
			<a href="javascript:if(confirm ('本当にログアウトしますか？')){logout.submit();}">logout</a>
		</form>
		<hr>
	</div>
	<div id="toBottom">
	<a href="#footer">∨ ページ最下部へ</a> <br>
	</div>
	<!-- ここまでです -->
	<font color="red" size="5"><Strong><c:out value="${bean.getErrorMsg()}"></c:out></Strong></font>
	<h1></h1>
	<br>
	<center>
		<form name="topShowProfile" action="/chat/showProfile" method="GET"
			target="newtab">
			<!-- 相手の名前リンク(ページトップ) -->
			<input type=hidden name="otherUserNo" value="${bean.getUserNo()}">
			<h3>
				<a href="javascript:topShowProfile.submit()"><c:out value="${bean.getUsername()}"></c:out></a>
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
						<td class="msoutside" rowspan="2"></td>
						<td class="msnone" rowspan="2"></td>
						<td class="msinside"></td>
						<td class="me" rowspan="2"><c:out value="${obj}"></c:out></td>
						<td class="myname">
							<form name="showProfile" action="/chat/showProfile" method="GET">
								<c:out value="${bean.getListUserName()[status.index]}"></c:out></form>
						</td>
					</tr>
					<tr>
						<td></td>
						<td align="right"><form name="" action="/chat/directMessage"
								method="POST">
								<input type=hidden name="check" value="1"> <input
									type=hidden name="userNo" value="${bean.getUserNo()}">
								<input type=hidden name="deleteMessageNo"
									value="${bean.getListMsgNo()[status.index]}">
									<input class="deletebtn"
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
						<td class="yourname" rowspan="2"><form
								name="showProfile" action="/chat/showProfile" method="GET"
								target="newtab">
								<input type=hidden name="otherUserNo"
									value="${bean.getListUserNo()[status.index]}"> <a
									href="javascript:showProfile[${status.index+1}].submit()"><c:out value="${bean.getListUserName()[status.index]}"></c:out></a>
							</form></td>
						<td rowspan="2" class="you">${obj}</td>
						<td class="msinside"></td>
						<td class="msnone" rowspan="2"></td>
						<td class="msoutside"></td>
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
		<form action="/chat/directMessage" method="POST"onSubmit="return send()">
			<input type=hidden name="check" value="2">
			<input type="text" name="sendMessage" id="textarea" title="${ bean.getUsername() }へのメッセージ                    " class="placeholder"><input type=hidden
				name="userNo" value="${bean.getUserNo()}"> <input
				type="submit" value="送信" name="sendMessage" class="btn">
		</form>

		<form action="/chat/directMessage" method="GET">
		<input type="hidden" name="userNo" value="${bean.getUserNo()}">
		<input type="submit" value="更新" class="updbtn">
		</form>
<!-- 画像ボタン追加につきコメントアウト、不要なら削除
		<form action="/chat/main" method="POST">
			<input type="submit" value="メインメニューに戻る" class="btn2">
		</form>
 -->
	</center>
	<div class="backBtn">
		<br> <br> <br>
		<form action="/chat/main" method="POST">
			<a class="imgBtn">
			<input type="image" src="./img/backMainPage.png" name="button"
				alt="makeGroup" height="40">
			</a>
		</form>
	</div>
	<!-- 以下、フッター部分になります。各自実装お願いします -->
	<br>
	<div id="toTop">
	<a href="#header">∧  ページトップへ</a>
	</div>
	<div id="footer">
		<hr>
		Ch@<br>
		kms2018 team D chat tool
		<br>
	</div>
	<!-- ここまでです -->
</body>
</html>