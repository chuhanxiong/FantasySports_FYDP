package com.fantasysports.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/message")
public class HelloWorldController {
 
	@ResponseBody
	@RequestMapping(value="/{message}",method = RequestMethod.GET)
	   public String printHello(@PathVariable String message,ModelMap model) {
	      return "Micheal Wu is: " + message;
	   }
}