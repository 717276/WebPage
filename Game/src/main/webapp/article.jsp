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
	Integer sessionId = (Integer)session.getAttribute("login_session");
	int sId;
	if(sessionId != null) {
		sId = (int)sessionId;
	}
	String nickname = (String)session.getAttribute("nickname");
	Article article = (Article)request.getAttribute("article");
%>
</head>
<body>
	<c:import url="./header.jsp" />
	<div class="content">
		<div class="article_box">
			<div class="article_wrapper">
				<div class="title">${article.title}</div>
				<div class="info">
					<input class="article_no" type="hidden" value="${article.no}" />
					<div class="article_writer">${article.writer}</div>
					<div class="count">
						<div class="view">${article.view_count}</div>
						<div class="like">${article.like_count}</div>
						<div class="dislike">${article.dislike_count}</div>
					</div>
				</div>
				<div class="article">${article.content}</div>
				<%
					if (sessionId == article.getWriter_no()){
				%>
					<div class="article_btn">
						<button onclick="update()">수정</button>
						<button onclick="delete()">삭제</button>
					</div>
				<%
					}
				%>
			</div>
		</div>
		<div class="comment_box">
			<input type="hidden" id="nickname" value="<%= nickname %>" />
			<input type="hidden" id="writer_no" value="<%= sessionId %>" />
			<div class="comment_input">
				<input id="comment_content" type="text" placeholder="댓글 입력" maxlength="1000" />
				<button id="comment_btn" onclick="writeComment()">댓글</button>
			</div>
			<table class="comment_table">
				<tbody>
				<c:forEach var="comment" items="${article.comments}">					
					<tr>
	               		<td>
	               			<input type="hidden" name="writer_no" value="${comment.writer_no}"/>
	               		</td>
						<td>${comment.writer}</td>
		                <td>${comment.content}</td>
		                <td>${comment.like_count}</td>
		                <td>${comment.dislike_count}</td>
		                <td>${comment.createdDate}</td>		  
		                
           				<c:if test="${sId == comment.writer_no}">
				            <td>		       		                
				                <button type="button" onclick="editComment(${comment.no})">Edit</button>
				                <button type="button" onclick="deleteComment(${comment.no})">Delete</button>
				            </td>
				        </c:if>
				</c:forEach>				 
				</tbody>
			</table>
		</div>
	</div>
	<c:import url="./footer.jsp" />
</body>
</html>