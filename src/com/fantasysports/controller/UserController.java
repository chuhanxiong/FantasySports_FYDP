package com.fantasysports.controller;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fantasysports.DAO.UserDAO;
import com.fantasysports.Model.User;
import com.fantasysports.util.StringParser;

@Controller
@RequestMapping("/user")
public class UserController {
	private final static Logger logger = Logger.getLogger(UserController.class);

	/*
	 * User Register
	 */
	@ResponseBody
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/register/{name}/{password}", method = RequestMethod.POST)
	public User registerUser(@PathVariable String name,
			@PathVariable String password) {
		return UserDAO.createUser(new User(name, password));
	}

	/*
	 * Update User
	 */
	@ResponseBody
	@RequestMapping(value = "/update/{userId}/{score}", method = RequestMethod.PUT)
	public String updateUser(@PathVariable int userId, @PathVariable int score) {
		User user = new User();
		try {
			user.setId(userId);
			user.setScore(score);
			UserDAO.updateUser(user);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "FAIL";
		}

		return "OK";
	}

	/*
	 * Register Players for User
	 */
	@ResponseBody
	@RequestMapping(value = "/registerUserPlayers/{userId}/{playerIds}", method = RequestMethod.PUT)
	public String registerUserPlayers(@PathVariable int userId,
			@PathVariable String playerIds) {
		User user = new User();
		try {
			user.setId(userId);
			user.setPlayerList(StringParser.stringToList(playerIds));
			UserDAO.updateUser(user);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "FAIL";
		}

		return "OK";
	}

	/*
	 * User Login
	 */
	@ResponseBody
	@RequestMapping(value = "/login/{name}/{password}", method = RequestMethod.GET)
	public User login(@PathVariable String name, @PathVariable String password) {
		User user = null;
		try {
			user = UserDAO.getUser(name, password);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return user;
	}

	// TODO get user final scores

}
