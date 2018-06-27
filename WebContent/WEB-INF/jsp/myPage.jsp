<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>マイページ</title>
<link rel="stylesheet" type="text/css" href="./css/Maincss.css"
	media="all">
<!-- js無効時エラーページに飛ぶ -->
<noscript>
	<meta http-equiv="Refresh" content="0;URL=/chat/errorPage">
</noscript>
</head>
<body id="page">
	<!-- 以下、ヘッダー部分になります。各自実装お願いします -->
	<div id="header">
		<c:out value="${ session.getUserName() }さん"></c:out>
		<br>
		<form name="logout" action="/chat/logout" method="POST">
			<a href="javascript:if(confirm ('本当にログアウトしますか？')){logout.submit();}">logout</a>
		</form>
		<hr>
	</div>
	<div id="toBottom">
		<a href="#footer">∨ ページ最下部へ</a> <br>
	</div>
	<!-- ここまでです -->
	<br>
	<center>
		<form action="/chat/myPage" method="POST">

			<table>
				<tr>
					<td width="100">名前</td>
					<td><textarea cols="30" name="dispName"><c:out
								value="${ myName }"></c:out></textarea>
				</tr>
				<tr>
					<td width="100">自己紹介</td>
					<td><textarea cols="30" rows="20" name="myPageText"><c:out
								value="${ myPageText }"></c:out></textarea>
				</tr>
			</table>

			<input type="hidden" name="dispName" value="${ bean.getUserName() }">
			<input type="hidden" name="myPageText"
				value="${ bean.getMyPageText }"> <input type="submit"
				value="プロフィールを更新">
		</form>
		<!-- 画像ボタン追加につきコメントアウト、不要なら削除
		<form action="/chat/main" method="POST">
			<input type="submit" value="メインメニューに戻る">
		</form>
 -->
		<br> <font color="red"><Strong><c:out
					value="${ errormessage }"></c:out></Strong></font> <br>
		<div class="backBtn">
			<br> <br> <br>
			<form action="/chat/main" method="POST">
				<a class="imgBtn"> <input type="image"
					src="./img/backMainPage.png" name="button" alt="makeGroup"
					height="40">
				</a>
			</form>
		</div>
	</center>

	<!-- 以下、フッター部分になります。各自実装お願いします -->
	<br>
	<div id="toTop">
		<a href="#header">∧ ページトップへ</a>
	</div>
	<div id="footer">
		<hr>
		Ch@<br> kms2018 team D chat tool <br>
	</div>
	<!-- ここまでです -->
</body>
</html>