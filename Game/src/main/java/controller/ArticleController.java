package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.ArticleService;
import model.vo.Article;
import model.vo.Comment;

@WebServlet("/article/*")
public class ArticleController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ArticleService articleService = new ArticleService();
    public ArticleController() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<Article> articles = null;
		Article article = null;

		if ("/get".equals(request.getPathInfo())) {
			String param = (String) request.getParameter("param");			
			int no = Integer.parseInt(param);
			if (no == 0) {
				articles = articleService.getAllArticles();				
			} else {				
				article = articleService.getArticleByNo(no);
			    request.setAttribute("article", article);
			    // forward to article.jsp
			    RequestDispatcher dispatcher = request.getRequestDispatcher("/article.jsp");
			    dispatcher.forward(request, response);
			    return;
			}			
		} else if ("/sort".equals(request.getPathInfo())){
			// sort
			String param = (String) request.getParameter("param");			
			int no = Integer.parseInt(param);
			articles = articleService.getArticlesBySort(no);
		}
		parseToGson(article, articles, response);		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if ("search".equals(request.getPathInfo())) {			
			String word = request.getParameter("search");
			ArrayList<Article> articles = articleService.searchArticles(word);
			parseToGson(null, articles, response);
		}else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "article 검색 오류");
		}
	}
	private void parseToGson(Article article, ArrayList<Article>articles, HttpServletResponse response) throws IOException {
		if (article == null && articles != null) {			
			Gson gson = new Gson();
			String jsonArticles = gson.toJson(articles);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jsonArticles);
		} else if(article != null && articles == null){
			Gson gson = new Gson();
			String jsonArticles = gson.toJson(article);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jsonArticles);
		}
	}
}
