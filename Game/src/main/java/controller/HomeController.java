package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getPathInfo();
		if ("/login".equals(path)) {			
			Member login_session = homeService.login(request.getParameter("email"), request.getParameter("password"));
			JsonObject jsonResponse = new JsonObject();
			if (login_session == null) {
				jsonResponse.addProperty("email", "");
				jsonResponse.addProperty("nickname", "");
				jsonResponse.addProperty("login_session", -1);
			}else {				
				jsonResponse.addProperty("email", login_session.getEmail());
				jsonResponse.addProperty("nickname", login_session.getNickname());
				jsonResponse.addProperty("login_session", login_session.getNo());
			}
			
			Gson gson = new Gson();
			String jsonData = gson.toJson(jsonResponse);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jsonData);
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}
}
