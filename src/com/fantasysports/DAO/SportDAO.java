package com.fantasysports.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.fantasysports.Model.Sport;
import com.fantasysports.Model.Team;

public class SportDAO {
	private final static Logger logger = Logger.getLogger(SportDAO.class);

	public static Sport createSport(Sport sport) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		String query = "insert into Sport (name,imgURL) values (?,?)";
		int index = 1;

		try {
			conn = BasicDAO.getConnection();
			stmt = conn
					.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			stmt.setString(index++, sport.getName());
			stmt.setString(index++, sport.getImgURL());

			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();
			rs.next();
			sport.setId(rs.getInt(1));
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return sport;
	}

	public static ArrayList<Team> getTeams(int sportId) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		ArrayList<Team> teams = new ArrayList<Team>();
		String query = "select * from Team JOIN Sport ON (Team.sportId = ? AND Sport.id = Team.sportId)";

		try {
			conn = BasicDAO.getConnection();
			stmt = conn.prepareStatement(query);

			stmt.setInt(1, sportId);
			rs = stmt.executeQuery();

			while (rs.next()) {
				teams.add(TeamDAO.createTeamByResultSet(rs));
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return teams;
	}

	public static Sport getSport(int sportId) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		Sport sport = null;
		String query = "select * from Sport where Sport.id = ?";

		try {
			conn = BasicDAO.getConnection();
			stmt = conn.prepareStatement(query);

			stmt.setInt(1, sportId);
			rs = stmt.executeQuery();

			if (rs.next()) {
				sport = createSportByResultSet(rs);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return sport;
	}

	private static Sport createSportByResultSet(ResultSet rs)
			throws SQLException {
		return new Sport(rs.getInt("id"), rs.getString("name"),
				rs.getString("imgURL"));
	}
}
