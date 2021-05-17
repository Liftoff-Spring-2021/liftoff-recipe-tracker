package org.launchcode.liftoffrecipetracker.controllers;

import org.launchcode.liftoffrecipetracker.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller("/")
public class HomeController {

	@Autowired
	AuthenticationController authenticationController;

	//User user = authenticationController.getUserFromSession(userSession);
	@GetMapping
	public String displayIndex()
	{
		return "redirect:/home";
	}

	@GetMapping("/home")
	public String displayHome(Model model, HttpSession userSession) {
		User user = authenticationController.getUserFromSession(userSession);
		model.addAttribute("title", "Liftoff Recipe Tracker");
		model.addAttribute("user", user);

		return "index";
	}


}
