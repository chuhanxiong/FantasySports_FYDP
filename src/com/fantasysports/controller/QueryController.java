package com.fantasysports.controller;

import com.fantasysports.DAO.QueryDAO;
import com.fantasysports.Model.League.Leagues;
import com.fantasysports.Model.NBAQueryObject;
import org.json.JSONArray;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/getNBAAdvancedStats", method = RequestMethod.POST)
	public String getNBAAdvancedStats(
			@RequestPart MultipartFile file,
			@PathVariable String token
	) {
		try {
			return QueryDAO.getNBAAdvancedStats(NBAQueryObject.convertFileToNBAQueryObject(file), token).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JSONArray(e.getMessage()).toString();
		}
	}

	@ResponseBody
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/getCustomQuery/{query}/{token}", method = RequestMethod.GET)
	public String getCustomQuery(
			@PathVariable String query,
			@PathVariable String token
	) {
		try {
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
