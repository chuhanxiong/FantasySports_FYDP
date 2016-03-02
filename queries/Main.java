import org.arms.sportsAPI.Leagues;
import org.arms.sportsAPI.NBAQueryObject;
import org.arms.sportsAPI.PrimaryQueries;

public class Main {
    public static void main (String [] args) throws Exception {
        String output = PrimaryQueries.getPlayerCurrentOverallStats("Stephen Curry", Leagues.NBA, "blah").toString();
        System.out.println(output);

        String output2 = PrimaryQueries.getPlayerDailyStats("Stephen Curry", Leagues.NBA, "2015-10-27", "blah").toString();
        System.out.println(output2);

        String output3 = PrimaryQueries.getPlayerPeriodicStats("Stephen Curry", Leagues.NBA, "2015-10-27", "2016-02-27", "blah").toString();
        System.out.println(output3);

        String output4 = PrimaryQueries.getTeamCurrentOverallStats("Toronto Raptors", Leagues.NBA, "blah").toString();
        System.out.println(output4);

        String output5 = PrimaryQueries.getTeamDailyStats("Toronto Raptors", Leagues.NBA, "2015-10-28", "blah").toString();
        System.out.println(output5);

        String output6 = PrimaryQueries.getTeamPeriodStats("Toronto Raptors", Leagues.NBA, "2015-10-27", "2016-02-27", "blah").toString();
        System.out.println(output6);

        NBAQueryObject qb = new NBAQueryObject();
        qb.setPlayerName(new String[] {"Stephen Curry", "Kyle Lowry"});
        qb.setPTS(30);
        qb.setAST(5);
        String output7 = PrimaryQueries.getNBAAdvancedStats(qb, "blah").toString();
        System.out.println(output7);
    }
}
