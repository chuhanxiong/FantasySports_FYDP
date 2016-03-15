package com.fantasysports.DAO;

import com.fantasysports.Model.League.Leagues;
import com.fantasysports.Model.NBAQueryObject;
import com.fantasysports.Model.NHLQueryObject;
import com.fantasysports.util.ResultSetConverter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;
import java.util.LinkedHashMap;

public class QueryDAO {

    private static String URL = "jdbc:mysql://fantasysports.cvut0y8wktov.us-west-2.rds.amazonaws.com:3306/fantasysports?connectTimeout=2000";
    private static String USER = "root";
    private static String PASS = "hamyharrymic";


    /**
     * @param token for authentication
     * @return boolean
     * @throws SQLException
     */
    public static boolean isValidToken(String token)
            throws Exception {
        Connection conn  = DriverManager.getConnection(URL, USER, PASS);

        String query = "Select token_id From Auth Where token_id = " + token;
        PreparedStatement pstmt = conn.prepareStatement(query);

        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            return true;
        }
        else
            return false;
    }

    public static JSONArray getPlayerCurrentOverallStats(String playerName, Leagues league, String token)
            throws Exception{
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection conn  = DriverManager.getConnection(URL, USER, PASS);

        String authQuery = "Select Token From AuthToken Where Token = ?";
        PreparedStatement authStmt = conn.prepareStatement(authQuery);
        authStmt.setString(1, token);
        ResultSet authRS = authStmt.executeQuery();

        if (!authRS.next()) {
            return null;
        }
        authStmt.close();

        String dataQuery = "";
        switch(league) {
            case NBA:
            case WNBA:
                String getPlayerIDsQuery = "Select playerID from NBAPlayer Where playerName = ?";
                PreparedStatement getPlayerIDstmt = conn.prepareStatement(getPlayerIDsQuery);
                getPlayerIDstmt.setString(1, playerName);
                ResultSet playerIDrs = getPlayerIDstmt.executeQuery();

                int playerID = 0;
                if(playerIDrs.next()) {
                    playerID = playerIDrs.getInt(1);
                }
                getPlayerIDstmt.close();
                dataQuery = "Select "
                        + "playerID, "
                        + "sum(playerID) as G, "
                        + "sum(MP) as MP, "
                        + "sum(FG) as FG, "
                        + "sum(FGA) as FGA, "
                        + "sum(FG)/sum(FGA) as `FG%`, "
                        + "sum(3P) as 3P, "
                        + "sum(3PA) as 3PA, "
                        + "sum(3P)/sum(3PA) as `3P%`, "
                        + "sum(FT) as FT, "
                        + "sum(FTA) as FTA, "
                        + "sum(FT)/sum(FTA) as `FT%`, "
                        + "sum(ORB) as ORB, "
                        + "sum(DRB) as DRB, "
                        + "sum(TRB) as TRB, "
                        + "sum(AST) as AST, "
                        + "sum(STL) as STL, "
                        + "sum(BLK) as BLK, "
                        + "sum(TOV) as TOV, "
                        + "sum(PF) as PF, "
                        + "sum(PTS) as PTS "
                        + "From NBAPlayerStats "
                        + "Where playerID = ? "
                        + "Group By playerID";
                PreparedStatement dataStmt = conn.prepareStatement(dataQuery);
                dataStmt.setInt(1, playerID);
                ResultSet dataRS = dataStmt.executeQuery();

                return ResultSetConverter.convert(dataRS);
            case NHL:
                dataQuery = "Select "
                        + "playerName, "
                        + "teamName, "
                        + "sum(playerID) as GP, "
                        + "sum(G) as G, "
                        + "sum(A) as A, "
                        + "sum(PTS) as PTS, "
                        + "sum(PlusMinus) as PlusMinus, "
                        + "sum(PIM) as PIM, "
                        + "sum(PP) as PP, "
                        + "sum(S), 3P%, "
                        + "sum(`S%`) as `S%`, "
                        + "sum(TSA) as TSA, "
                        + "avg(TOI), TOI, "
                        + "sum(FOwin) as FOwin, "
                        + "sum(FOloss) as FOloss, "
                        + "avg(`FO%`), `FO%`, "
                        + "sum(HIT) as HIT, "
                        + "sum(BLK) as BLK, "
                        + "sum(TK) as TK, "
                        + "sum(AST) as AST, "
                        + "sum(GV) as GV "
                        + "From NHLPlayer "
                        + "Where playerName = " + playerName
                        + " Group By playerID";
            case MLB:
            	break;
        }

        PreparedStatement dataStmt = conn.prepareStatement(dataQuery);
        ResultSet dataRS = dataStmt.executeQuery();

        return ResultSetConverter.convert(dataRS);
    }

    public static JSONArray getPlayerDailyStats(String playerName, Leagues league, String date, String token)
            throws Exception{
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection conn  = DriverManager.getConnection(URL, USER, PASS);

        String authQuery = "Select Token From AuthToken Where Token = ?";
        PreparedStatement authStmt = conn.prepareStatement(authQuery);
        authStmt.setString(1, token);
        ResultSet authRS = authStmt.executeQuery();

        if (!authRS.next()) {
            return null;
        }
        authStmt.close();

        String dataQuery = "";
        switch(league) {
            case NBA:
            case WNBA:
                String getPlayerIDsQuery = "Select playerID from NBAPlayer Where playerName = ?";
                PreparedStatement getPlayerIDstmt = conn.prepareStatement(getPlayerIDsQuery);
                getPlayerIDstmt.setString(1, playerName);
                ResultSet playerIDrs = getPlayerIDstmt.executeQuery();

                int playerID = 0;
                if(playerIDrs.next()) {
                    playerID = playerIDrs.getInt(1);
                }
                getPlayerIDstmt.close();
                dataQuery = "select * from NBAGame as a, NBAPlayerStats as b where `date` = ? and playerID = ? and a.gameID = b.gameID";
                PreparedStatement dataStmt = conn.prepareStatement(dataQuery);
                dataStmt.setDate(1, java.sql.Date.valueOf(date));
                dataStmt.setInt(2, playerID);
                ResultSet dataRS = dataStmt.executeQuery();
                return ResultSetConverter.convert(dataRS);
            case NHL:
                dataQuery = "Select * from NHLPlayer Where playerName = " + playerName + " and date = " + date;
                break;
            case MLB:
                dataQuery = "Select * from MLBPlayer Where playerName = " + playerName + " and date = " + date;
        }

        PreparedStatement dataStmt = conn.prepareStatement(dataQuery);
        ResultSet dataRS = dataStmt.executeQuery();

        return ResultSetConverter.convert(dataRS);
    }

    public static JSONArray getPlayerPeriodicStats(String playerName, Leagues league, String startDate, String endDate, String token)
            throws Exception {
        Connection conn = DriverManager.getConnection(URL, USER, PASS);

        String authQuery = "Select Token From AuthToken Where Token = ?";
        PreparedStatement authStmt = conn.prepareStatement(authQuery);
        authStmt.setString(1, token);
        ResultSet authRS = authStmt.executeQuery();

        if (!authRS.next()) {
            return null;
        }
        authStmt.close();
        String dataQuery = "";
        String getPlayerIDsQuery = "";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int playerID = 0;
        switch (league) {
            case NBA:
                getPlayerIDsQuery = "Select playerID from NBAPlayer Where playerName = ?";
                stmt = conn.prepareStatement(getPlayerIDsQuery);
                stmt.setString(1, playerName);
                rs = stmt.executeQuery();

                playerID = 0;
                if(rs.next()) {
                    playerID = rs.getInt(1);
                }
                stmt.close();
                dataQuery = "Select "
                        + "playerID, "
                        + "sum(MP) as MP, "
                        + "sum(FG) as FG, "
                        + "sum(FGA) as FGA, "
                        + "sum(FG)/sum(FGA) as `FG%`, "
                        + "sum(3P) as 3P, "
                        + "sum(3PA) as 3PA, "
                        + "sum(3P)/sum(3PA) as `3P%`, "
                        + "sum(FT) as FT, "
                        + "sum(FTA) as FTA, "
                        + "sum(FT)/sum(FTA) as `FT%`, "
                        + "sum(ORB) as ORB, "
                        + "sum(DRB) as DRB, "
                        + "sum(TRB) as TRB, "
                        + "sum(AST) as AST, "
                        + "sum(STL) as STL, "
                        + "sum(BLK) as BLK, "
                        + "sum(TOV) as TOV, "
                        + "sum(PF) as PF, "
                        + "sum(PTS) as PTS "
                        + "From NBAPlayerStats as a, NBAGame as b "
                        + "Where playerID = ?"
                        + " and date >= ?"
                        + " and date <= ?"
                        + " and a.gameID = b.gameID"
                        + " Group By playerID";
                stmt = conn.prepareStatement(dataQuery);
                stmt.setInt(1, playerID);
                stmt.setDate(2, java.sql.Date.valueOf(startDate));
                stmt.setDate(3, java.sql.Date.valueOf(endDate));
                rs = stmt.executeQuery();
                return ResultSetConverter.convert(rs);
            case NHL:
                getPlayerIDsQuery = "Select playerID from NHLPlayer Where playerName = ?";
                stmt = conn.prepareStatement(getPlayerIDsQuery);
                stmt.setString(1, playerName);
                rs = stmt.executeQuery();

                playerID = 0;
                if(rs.next()) {
                    playerID = rs.getInt(1);
                }
                stmt.close();
                dataQuery = "Select "
                        + "playerID, "
                        + "sum(G) as G, "
                        + "sum(A) as A, "
                        + "sum(PTS) as PTS, "
                        + "sum(PlusMinus) as PlusMinus, "
                        + "sum(PIM) as PIM, "
                        + "sum(EVG) as EVG, "
                        + "sum(PPG), PPG, "
                        + "sum(SHG) as SHG, "
                        + "sum(GW) as GW, "
                        + "sum(EVA), EVA, "
                        + "sum(PPA) as PPA, "
                        + "sum(SHA) as SHA, "
                        + "sum(S), S, "
                        + "avg(S%) as `S%`, "
                        + "sum(TOI) as TOI, "
                        + "From NHLPlayerStats as a, NHLGame as b "
                        + "Where playerID = ?"
                        + " and date >= ?"
                        + " and date <= ?"
                        + " and a.gameID = b.gameID"
                        + " Group By playerID";
                stmt = conn.prepareStatement(dataQuery);
                stmt.setInt(1, playerID);
                stmt.setDate(2, java.sql.Date.valueOf(startDate));
                stmt.setDate(3, java.sql.Date.valueOf(endDate));
                rs = stmt.executeQuery();
                return ResultSetConverter.convert(rs);
            case MLB:
        }
        return null;
    }

    public static JSONArray getTeamCurrentOverallStats(String teamName, Leagues league, String token)
            throws Exception {
        Connection conn = DriverManager.getConnection(URL, USER, PASS);

        String authQuery = "Select Token From AuthToken Where Token = ?";
        PreparedStatement authStmt = conn.prepareStatement(authQuery);
        authStmt.setString(1, token);
        ResultSet authRS = authStmt.executeQuery();

        if (!authRS.next()) {
            return null;
        }
        authStmt.close();
        String dataQuery = "";

        switch (league) {
            case NBA:
            case WNBA:
                String getTeamIDsQuery = "SELECT teamID FROM NBATeam WHERE teamName = ?";
                PreparedStatement getTeamIDstmt = conn.prepareStatement(getTeamIDsQuery);
                getTeamIDstmt.setString(1, teamName);
                ResultSet teamIDrs = getTeamIDstmt.executeQuery();

                int teamID = 0;
                if (teamIDrs.next()) {
                    teamID = teamIDrs.getInt(1);
                }
                getTeamIDstmt.close();
                dataQuery = "Select "
                        + "teamID, "
                        + "sum(FG) as FG, "
                        + "sum(FGA) as FGA, "
                        + "sum(FG)/sum(FGA) as `FG%`, "
                        + "sum(3P) as 3P, "
                        + "sum(3PA) as 3PA, "
                        + "sum(3P)/sum(3PA) as `3P%`, "
                        + "sum(FT) as FT, "
                        + "sum(FTA) as FTA, "
                        + "sum(FT)/sum(FTA) as `FT%`, "
                        + "sum(ORB) as ORB, "
                        + "sum(DRB) as DRB, "
                        + "sum(TRB) as TRB, "
                        + "sum(AST) as AST, "
                        + "sum(STL) as STL, "
                        + "sum(BLK) as BLK, "
                        + "sum(TOV) as TOV, "
                        + "sum(PF) as PF, "
                        + "sum(PTS) as PTS "
                        + "From NBAPlayerStats "
                        + "Where teamID = ?"
                        + " Group By teamID";
                PreparedStatement dataStmt = conn.prepareStatement(dataQuery);
                dataStmt.setInt(1, teamID);
                ResultSet dataRS = dataStmt.executeQuery();
                return ResultSetConverter.convert(dataRS);
            case NHL:
        }

        return null;
    }

    public static JSONArray getTeamDailyStats(String teamName, Leagues league, String date, String token)
            throws Exception {
        Connection conn = DriverManager.getConnection(URL, USER, PASS);

        String authQuery = "Select Token From AuthToken Where Token = ?";
        PreparedStatement authStmt = conn.prepareStatement(authQuery);
        authStmt.setString(1, token);
        ResultSet authRS = authStmt.executeQuery();

        if (!authRS.next()) {
            return null;
        }
        authStmt.close();
        String dataQuery = "";

        switch (league) {
            case NBA:
            case WNBA:
                String getTeamIDsQuery = "SELECT teamID FROM NBATeam WHERE teamName = ?";
                PreparedStatement getTeamIDstmt = conn.prepareStatement(getTeamIDsQuery);
                getTeamIDstmt.setString(1, teamName);
                ResultSet teamIDrs = getTeamIDstmt.executeQuery();

                int teamID = 0;
                if (teamIDrs.next()) {
                    teamID = teamIDrs.getInt(1);
                }
                getTeamIDstmt.close();
                dataQuery = "Select "
                        + "teamID, "
                        + "sum(FG) as FG, "
                        + "sum(FGA) as FGA, "
                        + "sum(FG)/sum(FGA) as `FG%`, "
                        + "sum(3P) as 3P, "
                        + "sum(3PA) as 3PA, "
                        + "sum(3P)/sum(3PA) as `3P%`, "
                        + "sum(FT) as FT, "
                        + "sum(FTA) as FTA, "
                        + "sum(FT)/sum(FTA) as `FT%`, "
                        + "sum(ORB) as ORB, "
                        + "sum(DRB) as DRB, "
                        + "sum(TRB) as TRB, "
                        + "sum(AST) as AST, "
                        + "sum(STL) as STL, "
                        + "sum(BLK) as BLK, "
                        + "sum(TOV) as TOV, "
                        + "sum(PF) as PF, "
                        + "sum(PTS) as PTS "
                        + "From NBAPlayerStats as a, NBAGame as b "
                        + "Where teamID = ? "
                        + "and `date` = ? "
                        + "and a.gameID = b.gameID "
                        + "Group By teamID";
                PreparedStatement dataStmt = conn.prepareStatement(dataQuery);
                dataStmt.setInt(1, teamID);
                dataStmt.setDate(2, java.sql.Date.valueOf(date));
                ResultSet dataRS = dataStmt.executeQuery();
                return ResultSetConverter.convert(dataRS);
            case NHL:
        }

        return null;
    }

    public static JSONArray getTeamPeriodicStats(String teamName, Leagues league, String startDate, String endDate, String token)
            throws SQLException{
        Connection conn = DriverManager.getConnection(URL, USER, PASS);

        String authQuery = "Select Token From AuthToken Where Token = ?";
        PreparedStatement authStmt = conn.prepareStatement(authQuery);
        authStmt.setString(1, token);
        ResultSet authRS = authStmt.executeQuery();

        if (!authRS.next()) {
            return null;
        }
        authStmt.close();
        String dataQuery = "";

        switch (league) {
            case NBA:
            case WNBA:
                String getTeamIDsQuery = "SELECT teamID FROM NBATeam WHERE teamName = ?";
                PreparedStatement getTeamIDstmt = conn.prepareStatement(getTeamIDsQuery);
                getTeamIDstmt.setString(1, teamName);
                ResultSet teamIDrs = getTeamIDstmt.executeQuery();

                int teamID = 0;
                if (teamIDrs.next()) {
                    teamID = teamIDrs.getInt(1);
                }
                getTeamIDstmt.close();
                dataQuery = "Select "
                        + "teamID, "
                        + "sum(FG) as FG, "
                        + "sum(FGA) as FGA, "
                        + "sum(FG)/sum(FGA) as `FG%`, "
                        + "sum(3P) as 3P, "
                        + "sum(3PA) as 3PA, "
                        + "sum(3P)/sum(3PA) as `3P%`, "
                        + "sum(FT) as FT, "
                        + "sum(FTA) as FTA, "
                        + "sum(FT)/sum(FTA) as `FT%`, "
                        + "sum(ORB) as ORB, "
                        + "sum(DRB) as DRB, "
                        + "sum(TRB) as TRB, "
                        + "sum(AST) as AST, "
                        + "sum(STL) as STL, "
                        + "sum(BLK) as BLK, "
                        + "sum(TOV) as TOV, "
                        + "sum(PF) as PF, "
                        + "sum(PTS) as PTS "
                        + "From NBAPlayerStats as a, NBAGame as b "
                        + "Where teamID = ? "
                        + "and `date` >= ? "
                        + "and `date` <= ? "
                        + "and a.gameID = b.gameID "
                        + "Group By teamID";
                PreparedStatement dataStmt = conn.prepareStatement(dataQuery);
                dataStmt.setInt(1, teamID);
                dataStmt.setDate(2, java.sql.Date.valueOf(startDate));
                dataStmt.setDate(3, java.sql.Date.valueOf(endDate));
                ResultSet dataRS = dataStmt.executeQuery();
                return ResultSetConverter.convert(dataRS);
            case NHL:
        }

        return null;
    }

    public static JSONArray getNBAAdvancedStats(NBAQueryObject qb, String token)
            throws SQLException {

        Connection conn = DriverManager.getConnection(URL, USER, PASS);

        String authQuery = "Select Token From AuthToken Where Token = ?";
        PreparedStatement authStmt = conn.prepareStatement(authQuery);
        authStmt.setString(1, token);
        ResultSet authRS = authStmt.executeQuery();

        if (!authRS.next()) {
            return null;
        }
        authStmt.close();

        String dataQuery = "Select * from NBAPlayerStats where ";

        String playerName = qb.getPlayerName();
        int playerID = 0;
        if (!playerName.equals("")) {
            String getPlayerIDsQuery = "Select playerID from NBAPlayer Where playerName = ?";
            PreparedStatement getPlayerIDstmt = conn.prepareStatement(getPlayerIDsQuery);
            getPlayerIDstmt.setString(1, playerName);
            ResultSet playerIDrs = getPlayerIDstmt.executeQuery();

            if (playerIDrs.next()) {
                playerID = playerIDrs.getInt(1);
            }
            getPlayerIDstmt.close();
            dataQuery += "playerID = ?";
        }
        String teamName = qb.getTeamName();
        int teamID = 0;
        if (!teamName.equals("")) {
            String getTeamIDsQuery = "SELECT teamID FROM NBATeam WHERE teamName = ?";
            PreparedStatement getTeamIDstmt = conn.prepareStatement(getTeamIDsQuery);
            getTeamIDstmt.setString(1, teamName);
            ResultSet teamIDrs = getTeamIDstmt.executeQuery();

            if (teamIDrs.next()) {
                teamID = teamIDrs.getInt(1);
            }
            getTeamIDstmt.close();
            dataQuery += "teamID = ?";
        }

        LinkedHashMap<String, String> map = qb.getMap();
        for (String key : map.keySet()) {
            double value = Double.valueOf(map.get(key));
            if (value > 0) {
                dataQuery += " and " + key + " >= ?";
            }
            else {
                dataQuery += " and " + key + " < ?";
            }
        }

        PreparedStatement dataStmt = conn.prepareStatement(dataQuery);
        int count = 1;
        if (playerID != 0) {
            dataStmt.setInt(count, playerID);
            count++;
        }
        if (teamID != 0) {
            dataStmt.setInt(count, teamID);
            count++;
        }
        for (String key: map.keySet()) {
            dataStmt.setDouble(count, Math.abs(Double.valueOf(map.get(key))));
            count++;
        }
        ResultSet dataRS = dataStmt.executeQuery();
        return ResultSetConverter.convert(dataRS);
    }

    public static JSONArray getNHLAdvancedStats(NHLQueryObject qb, String token)
            throws SQLException {

        Connection conn = DriverManager.getConnection(URL, USER, PASS);

        String authQuery = "Select Token From AuthToken Where Token = ?";
        PreparedStatement authStmt = conn.prepareStatement(authQuery);
        authStmt.setString(1, token);
        ResultSet authRS = authStmt.executeQuery();

        if (!authRS.next()) {
            return null;
        }
        authStmt.close();

        String dataQuery = "Select * from NHLPlayerStats where ";

        String playerName = qb.getPlayerName();
        int playerID = 0;
        if (!playerName.equals("")) {
            String getPlayerIDsQuery = "Select playerID from NHLPlayer Where playerName = ?";
            PreparedStatement getPlayerIDstmt = conn.prepareStatement(getPlayerIDsQuery);
            getPlayerIDstmt.setString(1, playerName);
            ResultSet playerIDrs = getPlayerIDstmt.executeQuery();

            if (playerIDrs.next()) {
                playerID = playerIDrs.getInt(1);
            }
            getPlayerIDstmt.close();
            dataQuery += "playerID = ?";
        }
        String teamName = qb.getTeamName();
        int teamID = 0;
        if (!teamName.equals("")) {
            String getTeamIDsQuery = "SELECT teamID FROM NHLTeam WHERE teamName = ?";
            PreparedStatement getTeamIDstmt = conn.prepareStatement(getTeamIDsQuery);
            getTeamIDstmt.setString(1, teamName);
            ResultSet teamIDrs = getTeamIDstmt.executeQuery();

            if (teamIDrs.next()) {
                teamID = teamIDrs.getInt(1);
            }
            getTeamIDstmt.close();
            dataQuery += "teamID = ?";
        }
        if (!qb.getStartDate().equals("")) {
            dataQuery += " and `date` >= ?";
        }
        if (!qb.getEndDate().equals("")) {
            dataQuery += " and `date` <= ?";
        }
        LinkedHashMap<String, String> map = qb.getMap();
        for (String key : map.keySet()) {
            double value = Double.valueOf(map.get(key));
            if (value > 0) {
                dataQuery += " and " + key + " >= ?";
            }
            else {
                dataQuery += " and " + key + " < ?";
            }
        }

        PreparedStatement dataStmt = conn.prepareStatement(dataQuery);
        int count = 1;
        if (playerID != 0) {
            dataStmt.setInt(count, playerID);
            count++;
        }
        if (teamID != 0) {
            dataStmt.setInt(count, teamID);
            count++;
        }
        if (!qb.getStartDate().equals("")) {
            dataStmt.setDate(count, java.sql.Date.valueOf(qb.getStartDate()));
            count++;
        }
        if (!qb.getEndDate().equals("")) {
            dataStmt.setDate(count, java.sql.Date.valueOf(qb.getEndDate()));
            count++;
        }
        for (String key: map.keySet()) {
            dataStmt.setDouble(count, Math.abs(Double.valueOf(map.get(key))));
            count++;
        }
        ResultSet dataRS = dataStmt.executeQuery();
        return ResultSetConverter.convert(dataRS);
    }

    public static JSONArray getCustomQuery(String customQuery, String token)
            throws SQLException {
        String url = "jdbc:mysql://fantasysports.cvut0y8wktov.us-west-2.rds.amazonaws.com:3306/fantasysports?connectTimeout=2000";
        String readOnlyUser = "readonly";
        String readOnlyPass = "readonly";
        Connection conn = DriverManager.getConnection(url, readOnlyUser, readOnlyPass);

        String authQuery = "Select Token From AuthToken Where Token = ?";
        PreparedStatement authStmt = conn.prepareStatement(authQuery);
        authStmt.setString(1, token);
        ResultSet authRS = authStmt.executeQuery();

        if (!authRS.next()) {
            return null;
        }
        authStmt.close();

        PreparedStatement dataStmt = conn.prepareStatement(customQuery);
        ResultSet dataRS = dataStmt.executeQuery();
        return ResultSetConverter.convert(dataRS);
    }

    public static JSONObject getUID() throws SQLException{
        Connection conn = DriverManager.getConnection(URL, USER, PASS);
        String call = "{call genUID(?)}";
        CallableStatement stmt = conn.prepareCall(call);
        stmt.registerOutParameter(1, Types.VARCHAR);
        stmt.executeUpdate();
        String uid = stmt.getString(1);

        JSONObject jo = new JSONObject();
        jo.put("token", uid);
        return jo;
    }
}

