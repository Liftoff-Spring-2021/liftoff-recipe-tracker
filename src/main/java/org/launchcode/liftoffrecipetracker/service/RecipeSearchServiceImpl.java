package org.launchcode.liftoffrecipetracker.service;

import org.hibernate.search.mapper.orm.Search;
import org.launchcode.liftoffrecipetracker.models.Recipe;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;

public class RecipeSearchServiceImpl implements RecipeSearchService {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<Recipe> recipeSearch(String searchTerm) {
		return Search.session(entityManager).search(Recipe.class)
				.where(f -> f.match().field("name").matching(searchTerm)).fetchAllHits();
	}
}
