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
								 @RequestParam(required = false) Integer beverageId,
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
		}

		return "recipe/index";
	}

	@GetMapping("create")
	public String displayCreateRecipeForm(Model model) {
		model.addAttribute("title", "Create a Recipe");
		model.addAttribute("categories", categoryRepository.findAll());
		model.addAttribute("beverages", beverageRepository.findAll());
		model.addAttribute(new Recipe());
		return "recipe/create";
	}

	@PostMapping("create")
	public String processCreateRecipeForm(@ModelAttribute @Valid Recipe newRecipe,
										  Errors errors, Model model, HttpSession userSession,
										  @RequestParam(required = false) List<Integer> categories,
										  @RequestParam(required = false) List<Integer> beverages
										  ) {

		if (errors.hasErrors()) {
			model.addAttribute("title", "Create a Recipe");
			model.addAttribute("categories", categoryRepository.findAll());
			model.addAttribute("beverages", beverageRepository.findAll());
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

		User user = authenticationController.getUserFromSession(userSession);
		newRecipe.setUser(user);

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
	public String displayDeleteRecipeForm(Model model, HttpSession userSession) {
		User user = authenticationController.getUserFromSession(userSession);
		model.addAttribute("title", "Delete Recipe");
		model.addAttribute("recipes", user.getRecipes());
		return "recipe/delete";
	}

	@PostMapping("delete")
	public String processDeleteRecipeForm(@RequestParam(required = false)
												  int[] recipeId) {

		if (recipeId != null) {
			for (int id : recipeId) {
				recipeRepository.deleteById(id);
			}
		}
		return "redirect:";
	}

	@GetMapping("edit/{recipeId}")
	public String displayEditRecipeForm(@PathVariable int recipeId, Model model){
		Optional<Recipe> result = recipeRepository.findById(recipeId);
		if(result.isEmpty()){
			model.addAttribute("title", "Invalid recipe ID" + recipeId);
		} else {
			Recipe recipe = result.get();
			model.addAttribute("title", "Edit Recipe: " + recipe.getName());
			model.addAttribute("recipe", recipe);
			model.addAttribute("categories", categoryRepository.findAll());
			model.addAttribute("beverages", beverageRepository.findAll());
		}
		return "recipe/edit";
	}

	@PostMapping("edit")
	public String processEditRecipeForm(int recipeId, String name, String ingredients, String directions,
										int servings, int cookTime, int prepTime, String image, Boolean favorite,
										@RequestParam(required = false) List<Integer> categories,
										@RequestParam(required = false) List<Integer> beverages){

		Optional<Recipe> optRecipe = recipeRepository.findById(recipeId);

		if (optRecipe.isPresent()) {

			Recipe recipe = optRecipe.get();

			if (categories == null) {
				recipe.removeAllCategories(recipe.getCategories());
			} else {
				List<Category> categoryObjects = (List<Category>) categoryRepository.findAllById(categories);
				recipe.removeAllCategories(recipe.getCategories());
				recipe.addCategories(categoryObjects);
			}

			if (beverages == null) {
				recipe.removeAllBeverages(recipe.getBeverages());
			} else {
				List<Beverage> beverageObjects = (List<Beverage>) beverageRepository.findAllById(beverages);
				recipe.removeAllBeverages(recipe.getBeverages());
				recipe.addBeverages(beverageObjects);
			}

			recipe.setName(name);
			recipe.setIngredients(ingredients);
			recipe.setDirections(directions);
			recipe.setServings(servings);
			recipe.setCookTime(cookTime);
			recipe.setPrepTime(prepTime);
			recipe.setImage(image);
			recipe.setFavorite(favorite);

			recipeRepository.save(recipe);
		}

		return "redirect:/recipes";
	}

	@GetMapping("copy/{recipeId}")
	public String displayCopyRecipeForm(@PathVariable int recipeId, Model model){
		Optional<Recipe> result = recipeRepository.findById(recipeId);
		if(result.isEmpty()){
			model.addAttribute("title", "Invalid Recipe ID" + recipeId);
		} else{
			Recipe recipe = result.get();
			model.addAttribute("title", "Copy Recipe" + recipe.getName());
			model.addAttribute("recipe", recipe);
			model.addAttribute("categories", categoryRepository.findAll());
			model.addAttribute("beverages", beverageRepository.findAll());
		}
		return"recipe/copy";
	}

	@PostMapping("copy")
	public String processCopyRecipeForm( @Valid @ModelAttribute Recipe recipe,
										Model model, Errors errors,
										 @RequestParam(required = false) List<Integer> categories,
										 @RequestParam(required = false) List<Integer> beverages,
	                                     HttpSession userSession){
		if(errors.hasErrors()){
			model.addAttribute("title", "Copy Recipe");
			model.addAttribute(new Recipe());
			return "recipe/copy";
		}

		if (categories == null) {
			recipe.removeAllCategories(recipe.getCategories());
		} else {
			List<Category> categoryObjects = (List<Category>) categoryRepository.findAllById(categories);
			recipe.removeAllCategories(recipe.getCategories());
			recipe.addCategories(categoryObjects);
		}

		if (beverages == null) {
			recipe.removeAllBeverages(recipe.getBeverages());
		} else {
			List<Beverage> beverageObjects = (List<Beverage>) beverageRepository.findAllById(beverages);
			recipe.removeAllBeverages(recipe.getBeverages());
			recipe.addBeverages(beverageObjects);
		}

		User user = authenticationController.getUserFromSession(userSession);
		recipe.setUser(user);

		recipeRepository.save(recipe);
		return"redirect:/recipes";
	}
}

