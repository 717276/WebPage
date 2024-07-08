package controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.CommentService;

@WebServlet("/comment/*")
public class CommentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    CommentService commentService = new CommentService();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if("/write".equals(request.getPathInfo())) {
			String writer = request.getParameter("writer");
		    String content = request.getParameter("content");
		    int articleNo = Integer.parseInt(request.getParameter("articleNo"));
		    int writerNo = Integer.parseInt(request.getParameter("writerNo"));		    
		    
		    commentService.addComment(articleNo,writerNo, writer, content);
		    
		    LocalDateTime now = LocalDateTime.now();
		    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");	
	        String formattedNow = now.format(formatter);

	        response.setContentType("text/plain");
	        response.setCharacterEncoding("UTF-8");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
