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
		String path = request.getPathInfo();
		if ("/get".equals(path)) {
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
		} else if ("/sort".equals(path)){
			// sort
			String param = (String) request.getParameter("param");			
			int no = Integer.parseInt(param);
			articles = articleService.getArticlesBySort(no);
		} else if ("/delete".equals(path)) {
			String param = (String) request.getParameter("param");			
			int no = Integer.parseInt(param);
			boolean result = articleService.deleteArticleByNo(no);
			
			if (result) {				
				response.setContentType("text/plain");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write("SUCCESS");
			}
			return;
		} else {
			
		}
		parseToGson(article, articles, response);		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo();

		if ("search".equals(path)) {			
			String word = request.getParameter("search");
			ArrayList<Article> articles = articleService.searchArticles(word);
			parseToGson(null, articles, response);
		}else if ("/update".equals(path)) {
			String title = request.getParameter("title");
			String content = request.getParameter("article");
			String noStr = request.getParameter("no");			
			int no = Integer.parseInt(noStr);
			boolean result = articleService.updateArticle(no, title, content);
			if (result) {				
				response.setContentType("text/plain");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write("SUCCESS");
			}
		}else{
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
