package com.fantasysports.controller;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fantasysports.DAO.PlayerDAO;
import com.fantasysports.Model.Player;


@Controller
@RequestMapping("/player")
public class PlayerController {
	private final static Logger logger = Logger
			.getLogger(PlayerController.class);

	/*
	 * Normal Player Register
	 */
	@ResponseBody
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/register/{teamId}/{name}", method = RequestMethod.POST)
	public Player registerNormalPlayer(@PathVariable int teamId,
			@PathVariable String name) {
		return PlayerDAO.createPlayer(new Player(teamId, name));
	}

	/*
	 * User Player Register
	 */
	@ResponseBody
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/registerUserPlayer/{userId}/{teamId}/{name}", method = RequestMethod.POST)
	public Player registerUserPlayer(@PathVariable int userId,
			@PathVariable int teamId, @PathVariable String name) {
		return PlayerDAO.createPlayer(new Player(userId, teamId, name, System
				.currentTimeMillis()));
	}

	/*
	 * Update Player Score
	 */
	@ResponseBody
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/updateScore/{id}/{score}", method = RequestMethod.PUT)
	public String updatePlayerScore(@PathVariable int id,
			@PathVariable int score) {
		try {
			PlayerDAO.updatePlayerScore(id, score);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "FAIL";
		}
		return "OK";
	}

	/*
	 * Get Player imgURL
	 */
	@ResponseBody
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/getImgURL/{id}", method = RequestMethod.GET)
	public String getPlayerImgURL(@PathVariable int id) {
		try {
			Player player = PlayerDAO.getPlayer(id);
			return player.getImgURL();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "FAIL";
		}
	}
}
