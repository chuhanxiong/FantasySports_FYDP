import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.json.CDL;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {
    public static void main (String [] args) throws Exception {

        //String token = PrimaryQueries.getUID();
        /*String query = "Select * from NBAPlayerStats as a, NBAPlayer as b where playerName = 'Stephen Curry' and `3P` >= 10 and a.playerID = b.playerID";
        String output = QueryDAO.getCustomQuery(query, "3a2993df-e8a2-11e5-9f31-0228a770e05b").toString();
        System.out.println(output);

        String output1 = QueryDAO.getPlayerDailyStats("Stephen Curry", League.Leagues.NBA, "2015-10-27", "3a2993df-e8a2-11e5-9f31-0228a770e05b").toString();
        System.out.println(output1);*/
    }

    private static void query1() throws Exception{
        String baseURL = "http://52.25.54.103:8080/FantasySports/query/getPlayerDailyStats/";
        String playerName = java.net.URLEncoder.encode("Stephen Curry", "UTF-8").replace("+", "%20");
        String league = "NBA";
        String date = "2015-10-27";
        String token = "3a2993df-e8a2-11e5-9f31-0228a770e05b";
        URL url = new URL(baseURL + playerName + "/" + league + "/" + date + "/" + token);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));
        String output = br.readLine();
        JSONArray ja = new JSONArray(output);
        String csv = CDL.toString(ja);
        System.out.println(csv);
        conn.disconnect();
    }

    private static void query2() throws Exception{
        Client client = Client.create();

        WebResource webResource = client
                .resource("http://localhost:8080/FantasySports/query/getCustomQueryNew");

        String input = "{\"query\":\"Select * from NBAPlayerStats as a, NBAPlayer as b where playerName = 'Stephen Curry' and `3P` >= 10 and a.playerID = b.playerID\",\"token\":\"3a2993df-e8a2-11e5-9f31-0228a770e05b\"}";

        String response = webResource.type("application/json")
                .post(String.class, input);

        System.out.println("Output from Server .... \n");
        System.out.println(response);
    }
}
