<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>メインページ</title>
<!-- css読み込み -->
<link rel="stylesheet" type="text/css" href="./css/Maincss.css"
	media="all">
<link rel="stylesheet" type="text/css" href="./css/mainPage.css"
	media="all">
<!-- JS読み込み	-->
<script src="./js/mainPage.js"></script>
<!-- js無効時エラーページに飛ぶ -->
<noscript>
<meta http-equiv="Refresh" content="0;URL=/chat/errorPage">
</noscript>
</head>
<body id="mainPage">
	<!-- 以下、ヘッダー部分になります。各自実装お願いします -->
	<div id="header">
		${ session.getUserName() }さん <br>
		<form name="logout" action="/chat/logout" method="POST">
			<a href="javascript:if(confirm ('本当にログアウトしますか？')){logout.submit();}">logout</a>
		</form>
		<hr>
	</div>
	<!-- ここまでです -->
	<!-- グループメッセージから来たか判別する為のパラメータ -->
	<input type="hidden" name="from" id="from" value="${fromGM}">
	<table class="rogoTable">
		<tr>
			<td><img src="./img/title_mainPage.png" alt="Ch@" width="50%"></td>
			<td>
			<a class="imgBtn"><img src="./img/editProfile.png" usemap="#EditProfile"
				alt="editProfile" height="100" align="right" class="imgBtn"> </a>
				<map name="EditProfile">
					<area shape="circle" coords="50,50,50" href="/chat/myPage" alt="editProfile">
				</map>
			</td>
		</tr>
	</table>
	<table class="titleTable">
		<tr>
			<td id="directMessageBtn" onclick="DMBtnclick()">DirectMessage</td>
			<td id="groupMessageBtn" onclick="GMBtnclick()">GroupMessage</td>
		</tr>
	</table>

	<div class="page" id="directMessage">
		<br>
		<form name="DM">
			<!-- jstlで作成したform"DM"がひとつだけの場合、インデックスが機能しないのを避ける為に作成 -->
		</form>
		<!-- 綺麗じゃないから余裕があれば直す -->
		<img src="./img/DM.png" alt="dm" width="200">
		<table class="mTable">
			<c:forEach var="obj" items="${userbean}" varStatus="status">
				<tr>
					<td>
						<form name="DM" method="get" action="/chat/directMessage">
							<input type=hidden name="userNo"
								value="${userbean[status.index].userNo}">○ <a
								href="javascript:DM[${status.index + 1}].submit()">${userbean[status.index].userName}</a>
						</form>
					</td>
					<td>${userbean[status.index].directMessage}</td>
				</tr>
			</c:forEach>
		</table>
	</div>

	<div class="page" id="groupMessage">
		<br>
		<form name="GM">
			<!-- jstlで作成したform"GM"がひとつだけの場合、インデックスが機能しないのを避ける為に作成 -->
		</form>
		<!-- 綺麗じゃないから余裕があれば直す -->
		<c:choose>
			<c:when test="${!empty groupbean[0].getGroupNullMes()}">
				<img src="./img/zeroGroup.png" alt="dm" width="200"
					class="inlineBlock">
			</c:when>
			<c:otherwise>
				<table class="mTable">
					<c:forEach var="obj" items="${groupbean}" varStatus="status">
						<tr>
							<td>
								<form name="GM" method="get" action="/chat/groupMessage">
									<input type=hidden name="groupNo"
										value="${groupbean[status.index].groupNo}">○ <a
										href="javascript:GM[${status.index + 1}].submit()">${groupbean[status.index].groupName}</a>
								</form>
							</td>
							<td>${groupbean[status.index].groupMessage}</td>
						</tr>
					</c:forEach>
				</table>
			</c:otherwise>
		</c:choose>
		<p class="inlineBlock">
			<a class="imgBtn">
			<img src="./img/makeGroup.png" usemap="#Map" alt="makeGroup"
				height="100" class="block">
			</a>
			<map name="Map">
				<area shape="circle" coords="50,50,50" href="/chat/makeGroup"
					alt="makeGroup" onmouseover="${makeGroup}=''"
					onmouseout="this.src='img/banner.jpg'">
			</map>
			<img src="./img/GM.png" alt="dm" width="200" class="block"> <br>
		</p>
	</div>
</body>
</html>