<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="model.vo.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="/Game/js/article.js"></script>
<title>Article</title>
<link rel="stylesheet" href="/Game/css/article.css" />
<link rel="stylesheet" href="/Game/css/header.css" />
<link rel="stylesheet" href="/Game/css/footer.css" />
<%
Integer sessionId = (Integer) session.getAttribute("login_session");
int sId;
if (sessionId == null) {
	System.out.println("sessionId is null");
	sId = -1;
} else {
	sId = (int) sessionId;
}
String nickname = (String) session.getAttribute("nickname");
Article article = (Article) request.getAttribute("article");
%>
</head>
<body>
	<c:import url="./header.jsp" />

	<div class="content">
		<div class="article_box">
			<div class="article_wrapper">
				<div class="info">
					<input class="article_no" type="hidden" value="${article.no}" />
					<div class="article_writer">${article.writer}</div>
					<div class="count">
						<div class="view">${article.view_count}</div>
						<div class="like">${article.like_count}</div>
						<div class="dislike">${article.dislike_count}</div>
					</div>
				</div>
				<div>
					<label for="title">Title:</label><br>
					<input id="title" class="title" type="text" value="${article.title}" readonly />
				</div>
				<div>
					<label for="article">Content:</label><br>
					<textarea id="article" class="article" readonly >${article.content}</textarea>
				</div>	
				<%
				if (sId == article.getWriter_no()) {
				%>
				<div class="article_btn">
					<button id="update_btn" onclick="updateArticle()">수정</button>
					<button id="delete_btn" onclick="deleteArticle(${article.no})">삭제</button>
					<button id="commit_btn" style="display: none;"
						onclick="commit(${article.no})">완료</button>
					<button id="cancel_btn" style="display: none;" onclick="cancel()">취소</button>
				</div>
				<%
				}
				%>
			</div>
		</div>

		<div class="comment_box">
			<input type="hidden" id="nickname" value="<%=nickname%>" /> <input
				type="hidden" id="writer_no" value="<%=sessionId%>" />
			<div class="comment_input">
				<input id="comment_content" type="text" placeholder="댓글 입력"
					maxlength="1000" />
				<button id="comment_btn" onclick="writeComment()">댓글</button>
			</div>
			<table class="comment_table">
				<tbody>
					<%
					for (Comment comment : article.getComments()) {
					%>
					<tr>
						<td>
							<input type="hidden" name="writer_no" value="${comment.writer_no}" /></td>
						<td><%=comment.getWriter()%></td>
						<td style="width: 70%;">
							<input id="content_td<%= comment.getNo()%>" type="text" readOnly value="<%=comment.getContent()%>" />
						</td>
						<td><%=comment.getLike_count()%></td>
						<td><%=comment.getDislike_count()%></td>
						<td><%=comment.getCreatedDate()%></td>

						<%
						if (sId == comment.getWriter_no()) {
						%>
						<td class="comment_btns">
							<span id="editComment_btn<%= comment.getNo()%>" onclick="editComment(<%=comment.getNo()%>)" style="cursor: pointer;">수정</span> 
							<span id="deleteComment_btn<%= comment.getNo()%>" onclick="deleteComment(<%=comment.getNo()%>)"	style="cursor: pointer;">삭제</span>
							<span id="commitComment_btn<%= comment.getNo()%>" onclick="commitComment(<%=comment.getNo()%>)" style="display:none; cursor: pointer;">완료</span> 
							<span id="cancelComment_btn<%= comment.getNo()%>" onclick="cancelComment(<%=comment.getNo()%>)"	style="display:none; cursor: pointer;">취소</span>
						</td>
						<%
						}
						%>

						<%
						}
						%>
					
				</tbody>
			</table>
		</div>
	</div>
	<c:import url="./footer.jsp" />
</body>
</html>