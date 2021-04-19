package org.launchcode.liftoffrecipetracker.models.data;


import org.launchcode.liftoffrecipetracker.models.Recipe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Integer> {
}
