package org.launchcode.liftoffrecipetracker.service;

import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.launchcode.liftoffrecipetracker.models.Recipe;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class RecipeSearchServiceImpl implements RecipeSearchService {

	private final EntityManager entityManager;

	@Autowired
	public RecipeSearchServiceImpl(final EntityManagerFactory entityManagerFactory) {
		this.entityManager = entityManagerFactory.createEntityManager();
	}

	@PostConstruct
	public void initializeRecipeSearchIndex() {
		try {
			SearchSession searchSession = Search.session(entityManager);
			searchSession.massIndexer().startAndWait();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	@Override
	public List<Recipe> recipeSearch(String searchTerm) {
		return Search.session(entityManager).search(Recipe.class)
				.where(f -> f.match().field("name").matching(searchTerm).fuzzy()).fetchAllHits();
	}
}
