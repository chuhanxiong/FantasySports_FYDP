package com.fantasysports.controller;

import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fantasysports.DAO.TeamDAO;
import com.fantasysports.Model.Player;
import com.fantasysports.Model.Team;

@Controller
@RequestMapping("/team")
public class TeamController {
	private final static Logger logger = Logger.getLogger(TeamController.class);

	/*
	 * Team Register
	 */
	@ResponseBody
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/register/{name}/{sportId}/{imgURL}", method = RequestMethod.POST)
	public Team registerTeam(@PathVariable String name,
			@PathVariable int sportId, @PathVariable String imgURL) {
		return TeamDAO.createTeam(new Team(sportId, name, imgURL));
	}

	/*
	 * Get Team Players
	 */
	@ResponseBody
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/getPlayers/{id}", method = RequestMethod.GET)
	public ArrayList<Player> getTeamPlayers(@PathVariable int id) {
		return TeamDAO.getTeamPlayers(id);
	}

	/*
	 * Add Team to Sport
	 */
	@ResponseBody
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/addTeamToSport/{id}/{sportId}", method = RequestMethod.PUT)
	public String addTeamToSport(@PathVariable int id, @PathVariable int sportId) {
		try {
			TeamDAO.addTeamToSport(id, sportId);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "FAIL";
		}
		return "OK";
	}

	/*
	 * Get Team imgURL
	 */
	@ResponseBody
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/getImgURL/{id}", method = RequestMethod.GET)
	public String getTeamImgURL(@PathVariable int id) {
		try {
			Team team = TeamDAO.getTeam(id);
			return team.getImgURL();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "FAIL";
		}
	}
}
