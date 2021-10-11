package com.todo.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class DbConnect {
	
	private static Connection conn= null ;

	public static void closeConnection() {
		// TODO Auto-generated method stub
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Connection getConnection() {
		if(conn == null ) {
			try {
				Class.forName("org.sqlite.JDBC");
				conn = DriverManager.getConnection("jdbc:sqlite:"+"mytodolist.db");
				
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return conn;
	}

}
