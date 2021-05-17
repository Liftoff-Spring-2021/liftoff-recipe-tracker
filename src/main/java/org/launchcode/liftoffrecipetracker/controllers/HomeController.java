package org.launchcode.liftoffrecipetracker.controllers;

import org.launchcode.liftoffrecipetracker.data.RecipeRepository;
import org.launchcode.liftoffrecipetracker.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller("/")
public class HomeController {

	@Autowired
	AuthenticationController authenticationController;

	@Autowired
	RecipeRepository recipeRepository;

	@GetMapping
	public String displayIndex()
	{
		return "redirect:/home";
	}

	@GetMapping("/home")
	public String displayHome(@RequestParam(required = false) Integer recipeId, Model model, HttpSession userSession) {
		User user = authenticationController.getUserFromSession(userSession);
		model.addAttribute("title", "Liftoff Recipe Tracker");
		model.addAttribute("user", user);
		model.addAttribute("recipe", recipeRepository.findAll());

		return "index";
	}


}
