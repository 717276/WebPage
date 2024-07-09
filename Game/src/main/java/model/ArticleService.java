package model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import model.vo.Article;
import model.vo.Comment;
import util.DB;

public class ArticleService {
	DB db = DB.getInstance();

	public Article getArticleByNo(int no) {
		Connection con = null;
		ResultSet arti = null;
		ResultSet comt = null;
		CallableStatement cstmt = null;
		String sql = "{call getArticleByNo_proc(?,?,?)}";
		Article article = null;
		Comment comment = null;
		ArrayList<Comment> comments = new ArrayList<>();
		try {
			con = db.getConntion();
			cstmt = con.prepareCall(sql);
			cstmt.setInt(1, no);
			cstmt.registerOutParameter(2, Types.REF_CURSOR);
			cstmt.registerOutParameter(3, Types.REF_CURSOR);
			cstmt.executeQuery();

			arti = (ResultSet) cstmt.getObject(2);
			if (arti == null) System.out.println("arti cant get object");
			comt = (ResultSet) cstmt.getObject(3);
			
			while (arti.next()) {
				article = new Article();
				article.setNo(arti.getInt("no"));
				article.setWriter(arti.getString("writer"));
				article.setWriter_no(arti.getInt("writer_no"));
				article.setTitle(arti.getString("title"));
				article.setContent(arti.getString("content"));
				article.setCreatedDate(arti.getDate("createdDate"));

				while (comt.next()) {
					comment = new Comment();
					comment.setNo(comt.getInt("no"));
					comment.setWriter(comt.getString("writer"));
					comment.setWriter_no(comt.getInt("writer_no"));
					comment.setContent(comt.getString("content"));
					comment.setLike_count(comt.getInt("like_count"));
					comment.setDislike_count(comt.getInt("dislike_count"));
					comment.setCreatedDate(comt.getDate("createdDate"));

					comments.add(comment);
				}
				article.setComments(comments);
				article.setView_count(arti.getInt("view_count"));
				article.setLike_count(arti.getInt("like_count"));
				article.setDislike_count(arti.getInt("dislike_count"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				arti.close();
				comt.close();
				cstmt.close();
				db.releaseConnection(con);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		if (article == null) {
			System.out.println("article is null");
		}
		if (comments.size() == 0) {
			System.out.println("comments is zero");
		}
		return article;
	}

	public ArrayList<Article> getAllArticles() {
		Connection con = null;
		ResultSet rs = null;
		CallableStatement cstmt = null;
		String sql = "{call getAllArticles_proc(?)}";
		ArrayList<Article> articles = new ArrayList<Article>();
		try {
			con = db.getConntion();
			cstmt = con.prepareCall(sql);
			cstmt.registerOutParameter(1, Types.REF_CURSOR);
			cstmt.executeQuery();
			rs = (ResultSet) cstmt.getObject(1);
			while (rs.next()) {
				Article a = new Article();
				a = new Article();
				a.setNo(rs.getInt("no"));
				a.setWriter(rs.getString("writer"));
				a.setTitle(rs.getString("title"));
				a.setContent(rs.getString("content"));
				a.setCreatedDate(rs.getDate("createdDate"));
				a.setLike_count(rs.getInt("like_count"));
				a.setDislike_count(rs.getInt("dislike_count"));
				a.setView_count(rs.getInt("view_count"));
				articles.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				cstmt.close();
				rs.close();
				db.releaseConnection(con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (articles.size() == 0) {
			System.out.println("article list is empty");
		}
		return articles;
	}

	public ArrayList<Article> getArticlesBySort(int no) {
		Connection con = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		ArrayList<Article> articles = new ArrayList<Article>();
		String sql = "{call getArticlesBySort_proc(?,?)}";
		try {
			con = db.getConntion();
			cstmt = con.prepareCall(sql);
			cstmt.setInt(1, no);
			cstmt.registerOutParameter(2, Types.REF_CURSOR);
			cstmt.executeQuery();
			rs = (ResultSet) cstmt.getObject(2);

			while (rs.next()) {
				Article a = new Article();
				a = new Article();
				a.setNo(rs.getInt("no"));
				a.setWriter(rs.getString("writer"));
				a.setTitle(rs.getString("title"));
				a.setContent(rs.getString("content"));
				a.setCreatedDate(rs.getDate("createdDate"));
				a.setLike_count(rs.getInt("like_count"));
				a.setDislike_count(rs.getInt("dislike_count"));
				a.setView_count(rs.getInt("view_count"));
				articles.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				cstmt.close();
				rs.close();
				db.releaseConnection(con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (articles.size() == 0) {
			System.out.println("sorted article list is empty");
		}
		return articles;
	}

	public ArrayList<Article> searchArticles(String word) {
		Connection con = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		ArrayList<Article> articles = new ArrayList<Article>();
		String sql = "{call getSearchedArticles_proc(?,?)}";

		try {
			con = db.getConntion();
			cstmt = con.prepareCall(sql);
			cstmt.setString(1, word);
			cstmt.registerOutParameter(2, Types.REF_CURSOR);
			cstmt.executeQuery();
			rs = (ResultSet) cstmt.getObject(2);
			while (rs.next()) {
				Article a = new Article();
				a = new Article();
				a.setNo(rs.getInt("no"));
				a.setWriter(rs.getString("writer"));
				a.setTitle(rs.getString("title"));
				a.setContent(rs.getString("content"));
				a.setCreatedDate(rs.getDate("createdDate"));
				a.setLike_count(rs.getInt("like_count"));
				a.setDislike_count(rs.getInt("dislike_count"));
				a.setView_count(rs.getInt("view_count"));
				articles.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				cstmt.close();
				rs.close();
				db.releaseConnection(con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (articles.size() == 0) {
			System.out.println("searched list is empty");			
		}
		return articles;
	}

	public boolean deleteArticleByNo(int no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql="delete from article where no = ?";
		int rowsAffected = 0;
		
		try {
			con = db.getConntion();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, no);
			rowsAffected = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				db.releaseConnection(con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (rowsAffected > 0) {
			return true;
		}
		return false;
	}

	public boolean updateArticle(int no, String title, String content) {
		// update Article
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "update article set title = ? , content = ? where no = ?";
		int rowsAffected = 0;
		try {
			con = db.getConntion();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setInt(3, no);
			rowsAffected = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				db.releaseConnection(con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (rowsAffected > 0) {
			return true;
		}
		return false;
	}
}
