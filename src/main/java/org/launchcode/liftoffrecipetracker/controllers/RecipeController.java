package org.launchcode.liftoffrecipetracker.controllers;


import org.launchcode.liftoffrecipetracker.models.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.launchcode.liftoffrecipetracker.data.RecipeRepository;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
		return "recipe/index";
	}

	@GetMapping("create")
	public String displayCreateRecipeForm(Model model) {
		model.addAttribute("title", "Create a Recipe");
		model.addAttribute(new Recipe());
		return "recipe/create";
	}

	@PostMapping("create")
	public String processCreateRecipeForm(@ModelAttribute @Valid Recipe recipe,
	                                      Errors errors,
	                                      Model model) {
		if (errors.hasErrors()) {
			model.addAttribute("title", "Create a Recipe");
			return "recipe/create";
		}

		recipeRepository.save(recipe);
		return "redirect:";
	}

	@GetMapping("detail")
	public String displayEventDetails(@RequestParam int recipeId,
	                                  Model model) {
		Optional<Recipe> result = recipeRepository.findById(recipeId);

		if (result.isEmpty()) {
			model.addAttribute("title", "Invalid Recipe ID: " + recipeId);
		} else {
			Recipe recipe = result.get();
			model.addAttribute("title", "Recipe Details: " + recipe.getName());
			model.addAttribute("recipe", recipe);
		}
		return "recipe/detail";
	}
}
