package com.toDoApp.support;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class CreateConnection {

	private static Logger logger=Logger.getLogger(CreateConnection.class);
	private static Connection conn;
	public static Connection getConnection() {
		String url="./src/main/resources/data/database.db";
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			logger.error("Could not read driver. Message: "+e.getMessage());
			
		}
		try {
			conn=DriverManager.getConnection("jdbc:sqlite:"+url);
		} catch (SQLException e) {
			logger.error("Could not connect to database. Message: "+e.getMessage());
		}
		return conn;
	}
	public static void closeConnection() {
		if(conn!=null) {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.error("Could not close the connection. Message: "+e.getMessage());
				
			}
		}
	}
}
