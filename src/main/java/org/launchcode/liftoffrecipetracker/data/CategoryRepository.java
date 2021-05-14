package org.launchcode.liftoffrecipetracker.data;

import org.launchcode.liftoffrecipetracker.models.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {
}
