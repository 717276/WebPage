package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import model.HomeService;
import model.vo.Article;
import model.vo.Member;

@WebServlet("/home/*")
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HomeService homeService = new HomeService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String path = request.getPathInfo();
		if ("/logout".equals(path)) {
			session.invalidate(); // 세션 초기화

			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write("SUCCESS");
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String path = request.getPathInfo();

		if ("/login".equals(path)) {
			Member login_session = homeService.login(request.getParameter("email"), request.getParameter("password"));
			session.setAttribute("login_session", (Integer) login_session.getNo());
			session.setAttribute("nickname", login_session.getNickname());
			session.setAttribute("email", login_session.getEmail());

			if (login_session != null) {
				response.setContentType("text/plain");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write("SUCCESS");
			}
		} else if ("/logout".equals(path)) {
			session.invalidate(); // 세션 초기화

			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write("SUCCESS");
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}
}
