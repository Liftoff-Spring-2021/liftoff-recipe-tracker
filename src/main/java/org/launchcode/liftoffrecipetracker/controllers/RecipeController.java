package org.launchcode.liftoffrecipetracker.controllers;

import org.launchcode.liftoffrecipetracker.models.Recipe;
import org.launchcode.liftoffrecipetracker.models.data.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
@RequestMapping("recipes")
public class RecipeController {

	@Autowired
	private RecipeRepository recipeRepository;

	@GetMapping
	public String displayAllRecipes(Model model) {
		model.addAttribute("title", "All Recipes");
		model.addAttribute("recipes", recipeRepository.findAll());
		return "index";
	}

	@GetMapping("create")
	public String displayAddRecipeForm(Model model){
		model.addAttribute(new Recipe());
		return "recipes/create";
	}

	@PostMapping("add")
	public String processAddRecipeForm(@ModelAttribute  Recipe newRecipe, Errors errors, Model model) {
		if (errors.hasErrors()) {
			model.addAttribute("title", "Add Recipe");
			model.addAttribute("errorMsg", "Try Again, invalid data!");
			return "recipes/add";
		}
		recipeRepository.save(newRecipe);
		return "redirect:";
	}

	@GetMapping("view/{recipeId}")
	public String displayViewRecipe(Model model, @PathVariable int recipeId) {

		Optional<Recipe> result = recipeRepository.findById(recipeId);

		if (result.isPresent()) {
			Recipe recipe = (Recipe) result.get();
			model.addAttribute("recipe", recipe);
			return "recipes/view";
		} else {
			return "redirect:../";
		}
	}
}
