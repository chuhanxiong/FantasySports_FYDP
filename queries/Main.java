import com.fantasysports.DAO.QueryDAO;
import com.fantasysports.Model.League;

public class Main {
    public static void main (String [] args) throws Exception {

        //String token = PrimaryQueries.getUID();
        String query = "Select * from NBAPlayerStats as a, NBAPlayer as b where playerName = 'Stephen Curry' and `3P` >= 10 and a.playerID = b.playerID";
        String output = QueryDAO.getCustomQuery(query, "3a2993df-e8a2-11e5-9f31-0228a770e05b").toString();
        System.out.println(output);

        String output1 = QueryDAO.getPlayerDailyStats("Stephen Curry", League.Leagues.NBA, "2015-10-27", "3a2993df-e8a2-11e5-9f31-0228a770e05b").toString();
        System.out.println(output1);

    }
}
