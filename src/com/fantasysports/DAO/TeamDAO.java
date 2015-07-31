package com.fantasysports.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.fantasysports.Model.Player;
import com.fantasysports.Model.Team;

public class TeamDAO {
	private final static Logger logger = Logger.getLogger(TeamDAO.class);

	public static Team createTeam(Team team) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String query = "insert into Team (sportId,name,imgURL) values (?,?,?)";
		int index = 1;

		try {
			conn = BasicDAO.getConnection();
			stmt = conn
					.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			stmt.setInt(index++, team.getSportId());
			stmt.setString(index++, team.getName());
			stmt.setString(index++, team.getImgURL());

			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();
			rs.next();
			team.setId(rs.getInt(1));
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return team;
	}

	public static Team getTeam(int teamId) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		Team team = null;
		String query = "select * from Team where id = ?";

		try {
			conn = BasicDAO.getConnection();
			stmt = conn.prepareStatement(query);

			stmt.setInt(1, teamId);
			rs = stmt.executeQuery();

			if (rs.next()) {
				team = createTeamByResultSet(rs);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return team;
	}

	public static void addTeamToSport(int teamId, int sportId) {
		PreparedStatement stmt = null;
		Connection conn = null;
		String query = "update Team set sportId = ? where id = ?";

		try {
			conn = BasicDAO.getConnection();
			stmt = conn.prepareStatement(query);

			stmt.setInt(1, sportId);
			stmt.setInt(1, teamId);
			int affected = stmt.executeUpdate();

			if (affected == 0) {
				throw new Exception("No such Team");
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	public static ArrayList<Player> getTeamPlayers(int teamId) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		ArrayList<Player> players = new ArrayList<Player>();
		String query = "select * from Player JOIN Team ON (Player.teamId = ? AND Player.TeamId = team.id AND Player.userId = -1)";

		try {
			conn = BasicDAO.getConnection();
			stmt = conn.prepareStatement(query);

			stmt.setInt(1, teamId);
			rs = stmt.executeQuery();

			while (rs.next()) {
				players.add(PlayerDAO.createPlayerByResultSet(rs));
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return players;
	}

	// public static void deleteTeam(int teamId) {
	// PreparedStatement stmt = null;
	// Connection conn = null;
	// String query = "delete from Team where id = ?";
	//
	// try {
	// conn = BasicDAO.getConnection();
	// stmt = conn.prepareStatement(query);
	//
	// stmt.setInt(1, teamId);
	//
	// int recordsAffected = stmt.executeUpdate();
	// if (recordsAffected == 0) {
	// throw new Exception("delete Team failed");
	// }
	// } catch (Exception e) {
	// logger.error(e.getMessage());
	// e.printStackTrace();
	// } finally {
	// BasicDAO.closeConnection(conn);
	// }
	// }

	public static Team createTeamByResultSet(ResultSet rs) throws SQLException {
		return new Team(rs.getInt("id"), rs.getInt("sportId"),
				rs.getString("name"), rs.getString("imgURL"));
	}

}
