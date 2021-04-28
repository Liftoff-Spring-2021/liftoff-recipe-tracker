package org.launchcode.liftoffrecipetracker.service;

import org.launchcode.liftoffrecipetracker.models.Recipe;

import java.util.List;

//interface that contains our search methods which need to be implemented in the RecipeSearchServiceImpl class
//this is extended on our RecipeRepository
public interface RecipeSearchService {
	List<Recipe> recipeSearchByNameOrCategory(String searchTerm);
}
