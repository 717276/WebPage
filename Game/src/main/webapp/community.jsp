<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="./js/community.js"></script>
<title>Article list</title>
<link rel="stylesheet" href="/Game/css/header.css" />
<link rel="stylesheet" href="/Game/css/footer.css" />
<link rel="stylesheet" href="/Game/css/community.css" />
<%
	Integer sessionId = (Integer)session.getAttribute("login_session");
	if (sessionId == null) { 
		sessionId = -1;
	}
%>
</head>
<body>
	<c:import url="./header.jsp" />
	<div class="content">
		<div class="list_box">
			<div class="nav">
				<div class="serach_box">
					<input id="search_input" name="search_input" type="text"
						placeholder="검색어 입력" /> <input id="search_btn" type="button"
						value="검색">
				</div>
				<select class="sort_box">
					<option value="1">최신</option>
					<option value="2">인기</option>
					<option value="3">조회</option>
				</select>
			</div>
			 <%
			 	if (sessionId != -1) {
			 %>
                <div class="write_box">
                    <input id="write_btn" name="write_btn" type="button" value="글 작성" onclick="writeArticle()" />
                </div>
            <%
			 	}
            %> 
			<!--시간, 제목, 작성자, 조회수, 좋아요, 실어요 -->
			<div class="list"></div>
		</div>
	</div>
	<c:import url="./footer.jsp" />
</body>
</html>