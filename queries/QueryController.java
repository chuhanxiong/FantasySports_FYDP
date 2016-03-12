package com.fantasysports.controller;

import org.json.JSONArray;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fantasysports.DAO.QueryDAO;
import com.fantasysports.Model.League.Leagues;
import com.fantasysports.Model.NBAQueryObject;

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
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/getPlayerCurrentOverallStats/{playerName}/{league}", method = RequestMethod.GET)
	public String getPlayerCurrentOverallStats(@PathVariable String playerName,
			@PathVariable Leagues league) {
		try {
			return QueryDAO
					.getPlayerCurrentOverallStats(playerName, league, "")
					.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JSONArray(e.getMessage()).toString();
		}
	}

	@ResponseBody
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/getPlayerDailyStats/{playerName}/{league}/{date}", method = RequestMethod.GET)
	public String getPlayerDailyStats(@PathVariable String playerName,
			@PathVariable Leagues league, @PathVariable String date) {
		try {
			return QueryDAO.getPlayerDailyStats(playerName, league, date, "")
					.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JSONArray(e.getMessage()).toString();
		}
	}

	@ResponseBody
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/getPlayerPeriodicStats/{playerName}/{league}/{startDate}/{endDate}", method = RequestMethod.GET)
	public String getPlayerPeriodicStats(@PathVariable String playerName,
			@PathVariable Leagues league, @PathVariable String startDate,
			@PathVariable String endDate) {
		try {
			return QueryDAO.getPlayerPeriodicStats(playerName, league,
					startDate, endDate, "").toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JSONArray(e.getMessage()).toString();
		}
	}

	@ResponseBody
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/getTeamCurrentOverallStats/{teamName}/{league}", method = RequestMethod.GET)
	public String getTeamCurrentOverallStats(@PathVariable String teamName,
			@PathVariable Leagues league) {
		try {
			return QueryDAO.getTeamCurrentOverallStats(teamName, league, "")
					.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JSONArray(e.getMessage()).toString();
		}
	}

	@ResponseBody
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/getPlayerPeriodicStats/{teamName}/{league}/{date}", method = RequestMethod.GET)
	public String getTeamDailyStats(@PathVariable String teamName,
			@PathVariable Leagues league, @PathVariable String date) {
		try {
			return QueryDAO.getTeamDailyStats(teamName, league, date, "")
					.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JSONArray(e.getMessage()).toString();
		}
	}

	@ResponseBody
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/getTeamPeriodStats/{teamName}/{league}/{startDate}/{endDate}", method = RequestMethod.GET)
	public String getTeamPeriodStats(@PathVariable String teamName,
			@PathVariable Leagues league, @PathVariable String startDate,
			@PathVariable String endDate) {
		try {
			return QueryDAO.getPlayerPeriodicStats(teamName, league, startDate,
					endDate, "").toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JSONArray(e.getMessage()).toString();
		}
	}

	@ResponseBody
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/getNBAAdvancedStats", method = RequestMethod.POST)
	public String getNBAAdvancedStats(@RequestPart MultipartFile file) {
		try {
			return QueryDAO.getNBAAdvancedStats(NBAQueryObject.convertFileToNBAQueryObject(file), "").toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JSONArray(e.getMessage()).toString();
		}
	}

}
