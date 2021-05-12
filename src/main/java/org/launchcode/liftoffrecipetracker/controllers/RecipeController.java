package org.launchcode.liftoffrecipetracker.controllers;

import org.launchcode.liftoffrecipetracker.data.BeverageRepository;
import org.launchcode.liftoffrecipetracker.data.CategoryRepository;
import org.launchcode.liftoffrecipetracker.models.Beverage;
import org.launchcode.liftoffrecipetracker.models.Category;
import org.launchcode.liftoffrecipetracker.models.Recipe;
import org.launchcode.liftoffrecipetracker.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.launchcode.liftoffrecipetracker.data.RecipeRepository;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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

	@Autowired
	BeverageRepository beverageRepository;

	@Autowired
	AuthenticationController authenticationController;

	@GetMapping
	public String displayRecipes(@RequestParam(required = false) Integer categoryId,
								 @RequestParam(required = false) Integer beverageId, HttpSession userSession,
	                             Model model) {
		if ((categoryId == null) && (beverageId == null)) {
			model.addAttribute("title", "All Recipes");
			model.addAttribute("recipes", recipeRepository.findAll());
		} else if (beverageId == null) {
			Optional<Category> result = categoryRepository.findById(categoryId);
			if (result.isEmpty()) {
				model.addAttribute("title", "Invalid Category ID: " + categoryId);
			} else {
				Category category = result.get();
				model.addAttribute("title", "Recipes in Category: " + category.getName());
				model.addAttribute("recipes", category.getRecipes());
			}
		} else if (categoryId == null) {
			Optional<Beverage> result = beverageRepository.findById(beverageId);
			if (result.isEmpty()) {
				model.addAttribute("title", "Invalid Beverage ID: " + beverageId);
			} else {
				Beverage beverage = result.get();
				model.addAttribute("title", "Recipes with Beverage: " + beverage.getName());
				model.addAttribute("recipes", beverage.getRecipes());
			}

		} else if (userSession == null) {
			User user = authenticationController.getUserFromSession(userSession);
				model.addAttribute("title", "Recipes with Owners: " + user.getUsername());
				model.addAttribute("recipes", user.getRecipes());
			}

		return "recipe/index";
	}

		@GetMapping("create")
		public String displayCreateRecipeForm (Model model, HttpSession userSession){
			model.addAttribute("title", "Create a Recipe");
			model.addAttribute("categories", categoryRepository.findAll());
			model.addAttribute("beverages", beverageRepository.findAll());
			model.addAttribute("user", authenticationController.getUserFromSession(userSession));
			model.addAttribute(new Recipe());
			return "recipe/create";
		}

		@PostMapping("create")
		public String processCreateRecipeForm (@ModelAttribute @Valid Recipe newRecipe,
				Errors errors, Model model,
				@RequestParam(required = false) List < Integer > categories,
				@RequestParam(required = false) List < Integer > beverages, HttpSession userSession){


			if (errors.hasErrors()) {
				model.addAttribute("title", "Create a Recipe");
				model.addAttribute("categories", categoryRepository.findAll());
				model.addAttribute("beverages", beverageRepository.findAll());
				model.addAttribute("user", authenticationController.getUserFromSession(userSession));
				return "recipe/create";
			} else if ((categories != null) && (beverages != null)) {
				List<Category> categoryObjects = (List<Category>) categoryRepository.findAllById(categories);
				newRecipe.addCategories(categoryObjects);
				List<Beverage> beverageObjects = (List<Beverage>) beverageRepository.findAllById(beverages);
				newRecipe.addBeverages(beverageObjects);
			} else if (categories != null) {
				List<Category> categoryObjects = (List<Category>) categoryRepository.findAllById(categories);
				newRecipe.addCategories(categoryObjects);
			} else if (beverages != null) {
				List<Beverage> beverageObjects = (List<Beverage>) beverageRepository.findAllById(beverages);
				newRecipe.addBeverages(beverageObjects);
			}
			else if (userSession != null) {
				User user = authenticationController.getUserFromSession(userSession);
					newRecipe.setUser(user);
				}

			recipeRepository.save(newRecipe);
			return "redirect:";
		}


	@GetMapping("detail")
	public String displayRecipeDetails(@RequestParam int recipeId,
	                                   Model model, HttpSession userSession) {
		Optional<Recipe> result = recipeRepository.findById(recipeId);

		if (result.isEmpty()) {
			model.addAttribute("title", "Invalid Recipe ID: " + recipeId);
		} else {
			Recipe recipe = result.get();
			model.addAttribute("title", "Recipe Details: " + recipe.getName());
			// gets user from userSession
			model.addAttribute("user", authenticationController.getUserFromSession(userSession));
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
