package com.fantasysports.DAO;

import org.json.JSONArray;

import com.fantasysports.Model.League.Leagues;
import com.fantasysports.Model.NBAQueryObject;
import com.fantasysports.util.ResultSetConverter;

import java.sql.*;

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

        /*String authQuery = "Select token_id From Auth Where token_id = " + token;
        PreparedStatement authStmt = conn.prepareStatement(authQuery);

        ResultSet authRS = authStmt.executeQuery();

        if (!authRS.next()) {
            return null;
        }
        authStmt.close();*/

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

        /*String authQuery = "Select token_id From Auth Where token_id = " + token;
        PreparedStatement authStmt = conn.prepareStatement(authQuery);
        ResultSet authRS = authStmt.executeQuery();

        if (!authRS.next()) {
            return null;
        }
        authStmt.close();*/

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

        /*String authQuery = "Select token_id From Auth Where token_id = " + token;
        PreparedStatement authStmt = conn.prepareStatement(authQuery);

        ResultSet authRS = authStmt.executeQuery();

        if (!authRS.next()) {
            return null;
        }
        authStmt.close();*/
        String dataQuery = "";

        switch (league) {
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
                PreparedStatement dataStmt = conn.prepareStatement(dataQuery);
                dataStmt.setInt(1, playerID);
                dataStmt.setDate(2, java.sql.Date.valueOf(startDate));
                dataStmt.setDate(3, java.sql.Date.valueOf(endDate));
                ResultSet dataRS = dataStmt.executeQuery();
                return ResultSetConverter.convert(dataRS);
            case NHL:
                dataQuery = "Select "
                        + "playerName, "
                        + "teamName, "
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
                        + " and date >= " + startDate
                        + " and date <= " + endDate
                        + " and a.gameID = b.gameID"
                        + " Group By playerID";
            case MLB:
        }

        PreparedStatement dataStmt = conn.prepareStatement(dataQuery);
        ResultSet dataRS = dataStmt.executeQuery();

        return ResultSetConverter.convert(dataRS);
    }

    public static JSONArray getTeamCurrentOverallStats(String teamName, Leagues league, String token)
            throws Exception {
        Connection conn = DriverManager.getConnection(URL, USER, PASS);

        /*String authQuery = "Select token_id From Auth Where token_id = " + token;
        PreparedStatement authStmt = conn.prepareStatement(authQuery);

        ResultSet authRS = authStmt.executeQuery();

        if (!authRS.next()) {
            return null;
        }
        authStmt.close();*/
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

        /*String authQuery = "Select token_id From Auth Where token_id = " + token;
        PreparedStatement authStmt = conn.prepareStatement(authQuery);

        ResultSet authRS = authStmt.executeQuery();

        if (!authRS.next()) {
            return null;
        }
        authStmt.close();*/
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

    public static JSONArray getTeamPeriodStats(String teamName, Leagues league, String startDate, String endDate, String token)
            throws SQLException{
        Connection conn = DriverManager.getConnection(URL, USER, PASS);

        /*String authQuery = "Select token_id From Auth Where token_id = " + token;
        PreparedStatement authStmt = conn.prepareStatement(authQuery);

        ResultSet authRS = authStmt.executeQuery();

        if (!authRS.next()) {
            return null;
        }
        authStmt.close();*/
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

        /*String authQuery = "Select token_id From Auth Where token_id = " + token;
        PreparedStatement authStmt = conn.prepareStatement(authQuery);

        ResultSet authRS = authStmt.executeQuery();

        if (!authRS.next()) {
            return null;
        }
        authStmt.close();*/

        String dataQuery = "";

        dataQuery = "Select * from NBAPlayerStats where ";

        String[] listOfNames = qb.getPlayerName();
        int[] listOfIDs = new int[listOfNames.length];
        for(int i = 0; i < listOfNames.length; i++) {
            String getPlayerIDsQuery = "Select playerID from NBAPlayer Where playerName = ?";
            PreparedStatement getPlayerIDstmt = conn.prepareStatement(getPlayerIDsQuery);
            getPlayerIDstmt.setString(1, listOfNames[i]);
            ResultSet playerIDrs = getPlayerIDstmt.executeQuery();

            int playerID = 0;
            if(playerIDrs.next()) {
                playerID = playerIDrs.getInt(1);
            }
            getPlayerIDstmt.close();
            listOfIDs[i] = playerID;
        }
        dataQuery += " ( ";
        for (int i = 0; i < listOfIDs.length; i++) {
            if(i != 0) dataQuery += " or ";
            dataQuery += "playerID = ?";
        }
        dataQuery += " ) ";
        if (qb.getPTS() != 0) {
            if (qb.getPTS() > 0){
                dataQuery += " and PTS >= ?";
            }else {
                dataQuery += " and PTS < ?";
            }
        }
        if (qb.getAST() != 0) {
            if (qb.getAST() > 0){
                dataQuery += " and PTS >= ?";
            }else {
                dataQuery += " and PTS < ?";
            }
        }

        PreparedStatement dataStmt = conn.prepareStatement(dataQuery);
        for(int i = 0; i < listOfIDs.length; i++) {
            dataStmt.setInt(i+1, listOfIDs[i]);
        }
        dataStmt.setInt(listOfIDs.length+1, 30);
        dataStmt.setInt(listOfIDs.length+2, 5);
        System.out.println(dataQuery);
        ResultSet dataRS = dataStmt.executeQuery();
        return ResultSetConverter.convert(dataRS);
    }
}

