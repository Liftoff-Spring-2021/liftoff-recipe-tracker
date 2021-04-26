package org.launchcode.liftoffrecipetracker.controllers;


import org.launchcode.liftoffrecipetracker.data.CategoryRepository;
import org.launchcode.liftoffrecipetracker.models.Category;
import org.launchcode.liftoffrecipetracker.models.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.launchcode.liftoffrecipetracker.data.RecipeRepository;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("recipes")
public class RecipeController {

	@Autowired
	private RecipeRepository recipeRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@GetMapping
	public String displayRecipes(@RequestParam(required = false) Integer categoryId,
	                             Model model) {
		if (categoryId == null) {
			model.addAttribute("title", "All Recipes");
			model.addAttribute("recipes", recipeRepository.findAll());
		} else {
			Optional<Category> result = categoryRepository.findById(categoryId);
			if (result.isEmpty()) {
				model.addAttribute("title", "Invalid Category ID: " + categoryId);
			} else {
				Category category = result.get();
				model.addAttribute("title", "Recipes in Category: " + category.getName());
				model.addAttribute("recipes", category.getRecipes());
			}
		}
		return "recipe/index";
	}

	@GetMapping("create")
	public String displayCreateRecipeForm(Model model) {
		model.addAttribute("title", "Create a Recipe");
		model.addAttribute("categories", categoryRepository.findAll());
		model.addAttribute(new Recipe());
		return "recipe/create";
	}

	@PostMapping("create")
	public String processCreateRecipeForm(@ModelAttribute @Valid Recipe newRecipe,
	                                      Errors errors, Model model,
	                                      @RequestParam(required = false) List<Integer> categories) {
		if (errors.hasErrors()) {
			model.addAttribute("title", "Create a Recipe");
			model.addAttribute("categories", categoryRepository.findAll());
			return "recipe/create";
		} else if (categories != null){
			List<Category> categoryObjects = (List<Category>) categoryRepository.findAllById(categories);
			newRecipe.addCategories(categoryObjects);
		}

		recipeRepository.save(newRecipe);
		return "redirect:";
	}

	@GetMapping("detail")
	public String displayRecipeDetails(@RequestParam int recipeId,
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
	@GetMapping("delete")
	public String displayDeleteRecipeForm(Model model){
		model.addAttribute("title", "Delete Recipe");
		model.addAttribute("recipes", recipeRepository.findAll());
		return"recipe/delete";
	}

	@PostMapping("delete")
	public String processDeleteRecipeForm( @RequestParam(required = false)
													   int[] recipeId){

		if(recipeId != null){
			for(int id : recipeId){
				recipeRepository.deleteById(id);
			}
		}
		return "redirect:";
	}
}
