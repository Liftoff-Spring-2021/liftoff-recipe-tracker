package org.launchcode.liftoffrecipetracker.controllers;

import org.launchcode.liftoffrecipetracker.models.Recipe;
import org.launchcode.liftoffrecipetracker.service.RecipeSearchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("search")
public class SearchController {

	@Autowired
	private RecipeSearchServiceImpl recipeSearchService;

	//creates an iterable list of recipes found using our search method and adds it to the model
	@PostMapping("results")
	public String displaySearchResults(Model model,
	                                   @RequestParam String searchTerm) {
		Iterable<Recipe> recipes;
		recipes = recipeSearchService.recipeSearch(searchTerm);

		model.addAttribute("title", "Recipe Results");
		model.addAttribute("recipes", recipes);

		return "search/results";
	}
}
