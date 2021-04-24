package org.launchcode.liftoffrecipetracker.service;

import org.launchcode.liftoffrecipetracker.models.Recipe;

import java.util.List;

public interface RecipeSearchService {
	List<Recipe> recipeSearch(String searchTerm);
}
