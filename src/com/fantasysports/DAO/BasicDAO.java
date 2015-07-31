package com.fantasysports.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

public class BasicDAO implements ServletContextListener {
	private static Connection conn = null;
	private final static String RDSUrl = "jdbc:mysql://localhost:3306/fantasysports?connectTimeout=2000";
	private final static String RDSUser = "root";
	private final static String RDSPassword = "harryhamymicheal";
	private final static Logger logger = Logger.getLogger(BasicDAO.class);

	public void init() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(RDSUrl, RDSUser, "");
			logger.info("Successfully connected to DB");
		} catch (SQLException | InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		return conn;
	}

	public static void closeConnection() {
		logger.info("Close connection");
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		closeConnection();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		init();
	}
}
