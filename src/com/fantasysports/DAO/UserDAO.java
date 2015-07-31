package com.fantasysports.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.fantasysports.Model.User;
import com.fantasysports.util.StringParser;

public class UserDAO {
	private final static Logger logger = Logger.getLogger(UserDAO.class);

	public static User createUser(User user) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String query = "insert into User (score,playerList,name,password,facebook,imgURL) values (?,?,?,?,?,?)";
		int index = 1;

		try {
			conn = BasicDAO.getConnection();
			stmt = conn
					.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			stmt.setInt(index++, user.getScore());
			stmt.setString(index++,
					StringParser.listToString(user.getPlayerList()));
			stmt.setString(index++, user.getName());
			stmt.setString(index++, user.getPassword());
			stmt.setString(index++, user.getFacebook());
			stmt.setString(index++, user.getImgURL());

			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();
			rs.next();
			user.setId(rs.getInt(1));
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return user;
	}

	public static User getUser(int userId) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		User user = null;
		String query = "select * from User where id = ?";

		try {
			conn = BasicDAO.getConnection();
			stmt = conn.prepareStatement(query);

			stmt.setInt(1, userId);
			rs = stmt.executeQuery();

			if (rs.next()) {
				user = createUserByResultSet(rs);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}

		return user;
	}

	public static User getUser(String name, String password) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		User user = null;
		String query = "select * from User where name = ? AND password = ?";

		try {
			conn = BasicDAO.getConnection();
			stmt = conn.prepareStatement(query);

			stmt.setString(1, name);
			stmt.setString(2, password);
			rs = stmt.executeQuery();

			if (rs.next()) {
				user = createUserByResultSet(rs);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}

		return user;
	}

	public static void updateUser(User user) {
		PreparedStatement stmt = null;
		Connection conn = null;
		String query = "update User set score=?,playerList=? where id = ?";
		int index = 1;

		try {
			conn = BasicDAO.getConnection();
			stmt = conn.prepareStatement(query);

			stmt.setInt(index++, user.getScore());
			stmt.setString(index++,
					StringParser.listToString(user.getPlayerList()));			
			stmt.setInt(index++, user.getId());

			int recordsAffected = stmt.executeUpdate();
			if (recordsAffected == 0) {
				throw new Exception("No such User");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	// public static void deleteUser(int userId) {
	// PreparedStatement stmt = null;
	// Connection conn = null;
	// String query = "delete from User where id = ?";
	//
	// try {
	// conn = BasicDAO.getConnection();
	// stmt = conn.prepareStatement(query);
	//
	// stmt.setInt(1, userId);
	//
	// int recordsAffected = stmt.executeUpdate();
	// if (recordsAffected == 0) {
	// throw new Exception("delete User failed");
	// }
	// } catch (Exception e) {
	// logger.error(e.getMessage());
	// e.printStackTrace();
	// } finally {
	// BasicDAO.closeConnection(conn);
	// }
	// }

	private static User createUserByResultSet(ResultSet rs) throws SQLException {
		return new User(rs.getInt("id"), rs.getInt("score"),
				StringParser.stringToList(rs.getString("playerList")),
				rs.getString("name"), rs.getString("password"),
				rs.getString("facebook"), rs.getString("imgURL"));
	}
}
