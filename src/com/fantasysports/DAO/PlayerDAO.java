package com.fantasysports.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.fantasysports.Model.Player;

public class PlayerDAO {
	private final static Logger logger = Logger.getLogger(PlayerDAO.class);

	public static Player createPlayer(Player player) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String query = "insert into Player (userId,teamId,score,name,imgURL,timestamp) values (?,?,?,?,?,?)";
		int index = 1;

		try {
			conn = BasicDAO.getConnection();
			stmt = conn
					.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			stmt.setInt(index++, player.getUserId());
			stmt.setInt(index++, player.getTeamId());
			stmt.setInt(index++, player.getScore());
			stmt.setString(index++, player.getName());
			stmt.setString(index++, player.getImgURL());
			stmt.setLong(index++, player.getTimestamp());

			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();
			rs.next();
			player.setId(rs.getInt(1));
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return player;
	}

	public static void updatePlayer(Player player) {
		PreparedStatement stmt = null;
		Connection conn = null;
		String query = "update Player set userId=?,score=?,imgURL=?,timestamp=? where id = ?";
		int index = 1;

		try {
			conn = BasicDAO.getConnection();
			stmt = conn.prepareStatement(query);

			stmt.setInt(index++, player.getUserId());
			stmt.setInt(index++, player.getScore());
			stmt.setString(index++, player.getImgURL());
			stmt.setLong(index++, player.getTimestamp());
			stmt.setInt(index++, player.getId());

			int recordsAffected = stmt.executeUpdate();
			if (recordsAffected == 0) {
				throw new Exception("No such Player");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	public static void updatePlayerScore(int playerId, int score) {
		PreparedStatement stmt = null;
		Connection conn = null;
		String query = "update Player set score = ? where id = ?";

		try {
			conn = BasicDAO.getConnection();
			stmt = conn.prepareStatement(query);

			stmt.setInt(1, score);
			stmt.setInt(2, playerId);

			int recordsAffected = stmt.executeUpdate();
			if (recordsAffected == 0) {
				throw new Exception("No such Player");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	public static Player getPlayer(int playerId) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		Player player = null;
		String query = "select * from Player where id = ?";

		try {
			conn = BasicDAO.getConnection();
			stmt = conn.prepareStatement(query);

			stmt.setInt(1, playerId);
			rs = stmt.executeQuery();

			if (rs.next()) {
				player = createPlayerByResultSet(rs);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return player;
	}

	public static Player createPlayerByResultSet(ResultSet rs)
			throws SQLException {
		return new Player(rs.getInt("id"), rs.getInt("userId"),
				rs.getInt("teamId"), rs.getInt("score"), rs.getString("name"),
				rs.getString("imgURL"), rs.getLong("timestamp"));
	}
}
