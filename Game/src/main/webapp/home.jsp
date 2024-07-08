<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Home</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="./js/home.js"></script>
<link rel="stylesheet" href="/Game/css/home.css" />
<link rel="stylesheet" href="/Game/css/home_login.css" />
<link rel="stylesheet" href="/Game/css/home_slide.css" />
<link rel="stylesheet" href="/Game/css/home_other.css" />
<link rel="stylesheet" href="/Game/css/header.css" />
<link rel="stylesheet" href="/Game/css/footer.css" />
<%
Integer sessionVal = (Integer) session.getAttribute("login_session");
int sint;
String email = "";
String nick = "";
if (sessionVal == null) {
	sint = -1;
} else {
	sint = sessionVal;
	email = (String) session.getAttribute("email");
	nick = (String) session.getAttribute("nickname");
}
%>
</head>
<body>
	<c:import url="./header.jsp" />
	<div class="content">
		<div class="content_top">
			<div class="slide">
				<div class="wrapper">
					<div class="img_box">
						<a href=""><img
							src="https://via.placeholder.com/800x400.png?text=Image+1" /></a>
					</div>
					<div class="img_box">
						<a href=""><img
							src="https://via.placeholder.com/800x400.png?text=Image+2" /></a>
					</div>
					<div class="img_box">
						<a href=""><img
							src="https://via.placeholder.com/800x400.png?text=Image+3" /></a>
					</div>
					<div class="img_box">
						<a href=""><img
							src="https://via.placeholder.com/800x400.png?text=Image+4" /></a>
					</div>
					<div class="img_box">
						<a href=""><img
							src="https://via.placeholder.com/800x400.png?text=Image+5" /></a>
					</div>
				</div>
				<div class="moveButton">
					<div class="left"><</div>
					<div class="right">></div>
				</div>
			</div>
			<div class="login">
				<%
				if (sint == -1) {
				%>
				<div class="login_box">
					<h4>로그인</h4>
					<input type="hidden" id="user_no" name="user_no" value="<%=sint%>">
					<table class="login_table">
						<tr>
							<td><label for="email">Email:</label></td>
							<td><input id="email" name="email" type="email"
								placeholder="Email" maxlength="30" size="30"></td>
						</tr>
						<tr>
							<td><label for="password">Password:</label></td>
							<td><input id="password" name="password" type="password"
								placeholder="Password" maxlength="30" size="30"></td>
						</tr>
						<tr>
							<td colspan="2"><span id="loginError" style="display: none;">로그인
									실패 아이디 패스워드 확인</span></td>
						</tr>
						<tr>
							<td colspan="2"><button id="login_btn" onclick="login()">로그인</button></td>
						</tr>
					</table>
				</div>
				<%
				} else {
				%>
				<div class="logout_box">
					<h4>환영합니다.</h4>
					<input type="hidden" id="user_no" name="user_no" value="<%=sint%>">
					<table class="login_table">
						<tr>
							<td>Email:</td>
							<td><%=email%></td>
						</tr>
						<tr>
							<td>NickName:</td>
							<td><%=nick%></td>
						</tr>
						<tr>
							<td colspan="2"><button id="logout_btn" onclick="logout()">로그아웃</button></td>
						</tr>
					</table>
				</div>
				<%
				}
				%>
			<div class="login_api">
				<button>
					<img id="google_api" alt="" src="">Google
				</button>
				<button>
					<img id="naver_api" alt="" src="">Naver
				</button>
			</div>
		</div>
	</div>

	<div class="content_bottom">
		<div class="notice_new">
			<div class="new_info">주요 정보</div>
		</div>
		<!-- articles, article.no , article.title -->
		<div class="others">
			<div class="best_articles">Top 10</div>
		</div>
	</div>
	</div>
	<c:import url="./footer.jsp" />
</body>
</html>