public class Main {
    public static void main (String [] args) throws Exception {

        String token = PrimaryQueries.getUID();
        String query = "Select * from NBAPlayerStats as a, NBAPlayer as b where playerName = 'Stephen Curry' and `3P` >= 10 and a.playerID = b.playerID";
        String output = PrimaryQueries.getCustomQuery(query, token).toString();
        System.out.println(output);
    }
}
