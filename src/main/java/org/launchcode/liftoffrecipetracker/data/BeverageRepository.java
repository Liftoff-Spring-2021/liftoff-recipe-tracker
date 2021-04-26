package org.launchcode.liftoffrecipetracker.data;

import org.launchcode.liftoffrecipetracker.models.Beverage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


    @Repository
    public interface BeverageRepository extends CrudRepository<Beverage, Integer> {

    }

