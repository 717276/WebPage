package model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import model.vo.Article;
import model.vo.Comment;
import model.vo.Member;
import util.DB;

public class HomeService {
	DB db = DB.getInstance();
	public HomeService() {	
	}
	public Member login(String email, String password) {
		Connection con = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		Member m = null;
		String sql ="{call login_proc(?,?,?)}";
		try {
			con = db.getConntion();
			cstmt = con.prepareCall(sql);
			cstmt.registerOutParameter(1, Types.REF_CURSOR);
			cstmt.setString(2, email);
			cstmt.setString(3, password);
			cstmt.executeQuery();
			rs = (ResultSet) cstmt.getObject(1);
			while(rs.next()) {
				m = new Member();
				m.setNo(rs.getInt("no"));
				m.setEmail(rs.getString("email"));
				m.setNickname(rs.getString("nickname"));
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
		if (m == null) {
			System.out.println("no data in users");
		}
		return m;		
	}

}
