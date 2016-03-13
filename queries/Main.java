import com.fantasysports.DAO.QueryDAO;
import com.fantasysports.Model.NBAQueryObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.json.CDL;
import org.json.JSONArray;

import java.net.URL;

public class Main {
    public static void main (String [] args) throws Exception {
        //query1();
        //query2();
        NBAQueryObject ob = new NBAQueryObject();
        ob.setPlayerName("Stephen Curry");
        ob.setPTS(30);
        ob.setAST(5);
        System.out.println(QueryDAO.getNBAAdvancedStats(ob, "3a2993df-e8a2-11e5-9f31-0228a770e05b").toString());
    }

    private static void query1() throws Exception{
        String baseURL = "http://localhost:8080/FantasySports/query/getPlayerDailyStats/";
        String playerName = java.net.URLEncoder.encode("Stephen Curry", "UTF-8").replace("+", "%20");
        String league = "NBA";
        String date = "2015-10-27";
        String token = "3a2993df-e8a2-11e5-9f31-0228a770e05b";
        URL url = new URL(baseURL + playerName + "/" + league + "/" + date + "/" + token);

        Client client = Client.create();
        WebResource webResource = client
                .resource(url.toString());
        String response = webResource.accept("application/json")
                .get(String.class);
        JSONArray ja = new JSONArray(response);
        String csv = CDL.toString(ja);
        System.out.println(csv);
    }

    private static void query2() throws Exception{
        Client client = Client.create();

        WebResource webResource = client
                .resource("http://localhost:8080/FantasySports/query/getCustomQueryNew");

        String input = "{\"query\":\"Select * from NBAPlayerStats as a, NBAPlayer as b where playerName = 'Stephen Curry' and `3P` >= 10 and a.playerID = b.playerID\",\"token\":\"3a2993df-e8a2-11e5-9f31-0228a770e05b\"}";

        String response = webResource.type("application/json")
                .post(String.class, input);
        JSONArray ja = new JSONArray(response);
        String csv = CDL.toString(ja);
        System.out.println(csv);
    }


}
