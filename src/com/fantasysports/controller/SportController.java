package com.fantasysports.controller;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fantasysports.DAO.SportDAO;
import com.fantasysports.Model.Sport;
import com.fantasysports.Model.Team;

@Controller
@RequestMapping("/sport")
public class SportController {
	private final static Logger logger = Logger
			.getLogger(SportController.class);

	/*
	 * Sport Register
	 */
	@ResponseBody
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/register/{name}/{imgURL}", method = RequestMethod.POST)
	public Sport registerSport(@PathVariable String name,
			@PathVariable String imgURL) {
		return SportDAO.createSport(new Sport(name, imgURL));
	}

	/*
	 * Get Teams
	 */
	@ResponseBody
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/getTeams/{id}", method = RequestMethod.GET)
	public ArrayList<Team> getSportTeams(@PathVariable int id) {
		return SportDAO.getTeams(id);
	}

	/*
	 * Get Sport imgURL
	 */
	@ResponseBody
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/getImgURL/{id}", method = RequestMethod.GET)
	public String getSportImgURL(@PathVariable int id) {
		try {
			Sport sport = SportDAO.getSport(id);
			return sport.getImgURL();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "FAIL";
		}
	}
}
