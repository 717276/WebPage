package util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public final class DB {
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	private ArrayList<Connection> free;
	private ArrayList<Connection> used;
	private String url;
	private String id;
	private String pwd;
	private static DB db;
	private int initialCons = 10;
	private int maxCons = 20;
	private int numCons = 0;
	
	public static DB getInstance() {
		if(db == null) {
			synchronized(DB.class) {
				try {
					db = new DB();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return db;
	}
	private DB() throws SQLException {
		String filepath = "D:/웹이클립스_파일/myproject/jsp/Game/src/main/java/util/db.properties";
		Properties properties = new Properties();
		try {
			properties.load(new FileReader(filepath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		url = properties.getProperty("url");
		id = properties.getProperty("id");
		pwd = properties.getProperty("pwd");
		
		free = new ArrayList<Connection>(initialCons);
		used = new ArrayList<Connection>(initialCons);
		while(numCons < maxCons) {
			addConnection();
		}
	}
	private void addConnection() throws SQLException {
		free.add(getNewConnection());
	}
	private Connection getNewConnection() throws SQLException {
		Connection con = null;
		con = DriverManager.getConnection(url,id,pwd);
		++numCons;
		return con;
	}
	
	public synchronized Connection getConntion() throws SQLException {
		if (free.isEmpty()) {
			while(numCons < maxCons) {
				addConnection();
			}
		}
		Connection _con;
		_con = free.get(free.size()-1);
		free.remove(_con);
		used.add(_con);
		return _con;
	}
	
	public synchronized void releaseConnection(Connection _con) throws SQLException {
		boolean flag = false;
		if (used.contains(_con)) {
			used.remove(_con);
			numCons--;
			flag = true;
		} else {
			throw new SQLException("ConnectionPool 없음");
		}
		try {
			if (flag) {
				free.add(_con);
				numCons++;
			} else {
				_con.close();
			}
		} catch (SQLException e) {
			_con.close();
		}
	}
	public void closeAll() {
		for (int i = used.size() - 1; i >= 0; --i) {
			Connection _con = (Connection) used.get(i);
			used.remove(i);
			try {
				_con.close();
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
		for (int i = free.size() - 1; i >= 0; --i) {
			Connection _con = (Connection) free.get(i);
			free.remove(i);
			try {
				_con.close();
			} catch (SQLException sqle) {
				sqle.printStackTrace();
				;
			}
		}
	}
}

























