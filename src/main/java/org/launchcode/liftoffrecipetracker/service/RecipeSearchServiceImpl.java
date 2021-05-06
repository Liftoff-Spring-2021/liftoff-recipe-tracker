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

	//entity manager manages the persistence and indexing of our database
	private final EntityManager entityManager;

	//creates an instance of the entity manager to use within the service implementation
	@Autowired
	public RecipeSearchServiceImpl(final EntityManagerFactory entityManagerFactory) {
		this.entityManager = entityManagerFactory.createEntityManager();
	}

	//This creates an index of our already existing data after the constructor runs
	@PostConstruct
	public void initializeRecipeSearchIndex() {
		try {
			SearchSession searchSession = Search.session(entityManager);
			searchSession.massIndexer().startAndWait();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	//A basic search function which searches the name field in our recipe class and provides fuzzy matches
	//fuzzy matches allows for some variation in our search terms ex. ht -> hot or appel -> apple
	//we can further update this and our RecipeSearchService interface to build out search functions as necessary
	@Override
	public List<Recipe> recipeSearchByNameOrCategory(String searchTerm) {
		return Search.session(entityManager)
				.search(Recipe.class)
				.where(f -> f.match().field("name").field("categories.name").field("owners.name")
						.matching(searchTerm).fuzzy())
				.fetchAllHits();
	}
}
