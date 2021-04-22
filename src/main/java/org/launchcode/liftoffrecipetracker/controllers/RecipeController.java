package org.launchcode.liftoffrecipetracker.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.launchcode.liftoffrecipetracker.data.RecipeRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("recipes")
public class RecipeController {


	@Autowired
	private RecipeRepository recipeRepository;

	@GetMapping
	public String displayAllRecipes(Model model) {
		model.addAttribute("title", "All Recipes");
		model.addAttribute("recipes", recipeRepository.findAll());
		return "recipe/index";
	}

}
