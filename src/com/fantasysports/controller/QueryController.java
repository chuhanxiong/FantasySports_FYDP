package com.fantasysports.controller;

import com.fantasysports.DAO.QueryDAO;
import com.fantasysports.Model.League.Leagues;
import com.fantasysports.Model.NBAQueryObject;
import com.fantasysports.Model.NHLQueryObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/query")
public class QueryController {

	@ResponseBody
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/vaildToken/{token}", method = RequestMethod.GET)
	public boolean validToken(@PathVariable String token) {
		try {
			return QueryDAO.isValidToken(token);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@ResponseBody
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/getPlayerCurrentOverallStats/{playerName}/{league}/{token}", method = RequestMethod.GET)
	public String getPlayerCurrentOverallStats(
			@PathVariable String playerName,
			@PathVariable Leagues league,
			@PathVariable String token
	) {
		try {
			return QueryDAO
					.getPlayerCurrentOverallStats(playerName, league, token)
					.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JSONArray(e.getMessage()).toString();
		}
	}

	@ResponseBody
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/getPlayerDailyStats/{playerName}/{league}/{date}/{token}", method = RequestMethod.GET)
	public String getPlayerDailyStats(
			@PathVariable String playerName,
			@PathVariable Leagues league,
			@PathVariable String date,
			@PathVariable String token
	) {
		try {
			return QueryDAO.getPlayerDailyStats(playerName, league, date, token)
					.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JSONArray(e.getMessage()).toString();
		}
	}

	@ResponseBody
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/getPlayerPeriodicStats/{playerName}/{league}/{startDate}/{endDate}/{token}", method = RequestMethod.GET)
	public String getPlayerPeriodicStats(
			@PathVariable String playerName,
			@PathVariable Leagues league,
			@PathVariable String startDate,
			@PathVariable String endDate,
			@PathVariable String token
	) {
		try {
			return QueryDAO.getPlayerPeriodicStats(playerName, league,
					startDate, endDate, token).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JSONArray(e.getMessage()).toString();
		}
	}

	@ResponseBody
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/getTeamCurrentOverallStats/{teamName}/{league}/{token}", method = RequestMethod.GET)
	public String getTeamCurrentOverallStats(
			@PathVariable String teamName,
			@PathVariable Leagues league,
			@PathVariable String token
	) {
		try {
			return QueryDAO.getTeamCurrentOverallStats(teamName, league, token)
					.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JSONArray(e.getMessage()).toString();
		}
	}

	@ResponseBody
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/getTeamDailyStats/{teamName}/{league}/{date}/{token}", method = RequestMethod.GET)
	public String getTeamDailyStats(
			@PathVariable String teamName,
			@PathVariable Leagues league,
			@PathVariable String date,
			@PathVariable String token
	) {
		try {
			return QueryDAO.getTeamDailyStats(teamName, league, date, token)
					.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JSONArray(e.getMessage()).toString();
		}
	}

	@ResponseBody
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/getTeamPeriodicStats/{teamName}/{league}/{startDate}/{endDate}/{token}", method = RequestMethod.GET)
	public String getTeamPeriodicStats(
			@PathVariable String teamName,
			@PathVariable Leagues league,
			@PathVariable String startDate,
			@PathVariable String endDate,
			@PathVariable String token
			) {
		try {
			return QueryDAO.getTeamPeriodicStats(teamName, league, startDate,
					endDate, token).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JSONArray(e.getMessage()).toString();
		}
	}

	@ResponseBody
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			value = "/getNBAAdvancedStats",
			method = RequestMethod.POST)
	public String getNBAAdvancedStats(
			@RequestBody String input
	) {
		try {
			JSONObject jo = new JSONObject(input);
			NBAQueryObject ob = new NBAQueryObject();
			String token = jo.getString("token");
			if(jo.has("playerName")) {
				ob.setPlayerName(jo.getString("playerName"));
			}
			if(jo.has("teamName")) {
				ob.setTeamName(jo.getString("teamName"));
			}
			if(jo.has("PTS")) {
				ob.setPTS(Integer.valueOf(jo.getString("PTS")));
			}
			if(jo.has("AST")) {
				ob.setAST(Integer.valueOf(jo.getString("AST")));
			}
			if(jo.has("BLK")) {
				ob.setBLK(Integer.valueOf(jo.getString("BLK")));
			}
			if(jo.has("STL")) {
				ob.setSTL(Integer.valueOf(jo.getString("STL")));
			}
			if(jo.has("TRB")) {
				ob.setTRB(Integer.valueOf(jo.getString("TRB")));
			}
			if(jo.has("FG%")) {
				ob.setFGPercent(Double.valueOf(jo.getString("FG%")));
			}
			if(jo.has("3P")) {
				ob.setThreeP(Integer.valueOf(jo.getString("3P")));
			}
			if(jo.has("3P%")) {
				ob.setThreePPercent(Double.valueOf(jo.getString("3P%")));
			}
			return QueryDAO.getNBAAdvancedStats(ob, token).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JSONArray(e.getMessage()).toString();
		}
	}

	@ResponseBody
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			value = "/getNHLAdvancedStats",
			method = RequestMethod.POST)
	public String getNHLAdvancedStats(
			@RequestBody String input
	) {
		try {
			JSONObject jo = new JSONObject(input);
			NHLQueryObject ob = new NHLQueryObject();
			String token = jo.getString("token");
			if(jo.has("playerName")) {
				ob.setPlayerName(jo.getString("playerName"));
			}
			if(jo.has("teamName")) {
				ob.setTeamName(jo.getString("teamName"));
			}
			if(jo.has("PTS")) {
				ob.setPTS(Integer.valueOf(jo.getString("PTS")));
			}
			if(jo.has("G")) {
				ob.setG(Integer.valueOf(jo.getString("G")));
			}
			if(jo.has("A")) {
				ob.setA(Integer.valueOf(jo.getString("A")));
			}
			if(jo.has("startDate")) {
				ob.setStartDate(jo.getString("startDate"));
			}
			if(jo.has("endDate")) {
				ob.setEndDate(jo.getString("endDate"));
			}
			return QueryDAO.getNHLAdvancedStats(ob, token).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JSONArray(e.getMessage()).toString();
		}
	}

	@ResponseBody
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			value = "/getCustomQuery",
			method = RequestMethod.POST)
	public String getCustomQuery(
			@RequestBody String input
	) {
		try {
			JSONObject jo = new JSONObject(input);
			String query = jo.getString("query");
			String token = jo.getString("token");
			return QueryDAO.getCustomQuery(query, token).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JSONArray(e.getMessage()).toString();
		}
	}

	@ResponseBody
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/genToken", method = RequestMethod.GET)
	public String genToken() {
		try {
			return QueryDAO.getUID().toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JSONArray(e.getMessage()).toString();
		}
	}
}
