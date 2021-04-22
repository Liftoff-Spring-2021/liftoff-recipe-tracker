package org.launchcode.liftoffrecipetracker.controllers;


import org.launchcode.liftoffrecipetracker.models.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.launchcode.liftoffrecipetracker.data.RecipeRepository;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

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
}
