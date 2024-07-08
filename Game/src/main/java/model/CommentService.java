package model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import util.DB;

public class CommentService {
	DB db = DB.getInstance();

	public void addComment(int articleNo, int writer_no, String writer, String content) {
		Connection con = null;
		String sql = "insert into comments(no, content, writer, article_no, writer_no) values(com_seq.nextVal,?,?,?,?)";
		CallableStatement cstmt = null;

		try {
			con = db.getConntion();
			cstmt = con.prepareCall(sql);
			cstmt.setString(1, content);
			cstmt.setString(2, writer);
			cstmt.setInt(3, articleNo);
			cstmt.setInt(4, writer_no);
			cstmt.execute();
			int rowsAffected = cstmt.getUpdateCount();
			if (rowsAffected > 0) {
				System.out.println(rowsAffected + " row(s) inserted successfully.");
			} else {
				System.out.println("No rows inserted.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				cstmt.close();
				db.releaseConnection(con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
