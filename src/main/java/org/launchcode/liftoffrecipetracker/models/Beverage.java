package org.launchcode.liftoffrecipetracker.models;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
    public class Beverage extends AbstractRecommendations{

        //Class Variables

    @ManyToMany(mappedBy = "beverages")
    private final List<Recipe> recipes = new ArrayList<>();

        //Constructors

        public Beverage() {
        }

        //Getters and Setters

    public List<Recipe> getRecipes() {
        return recipes;
    }
}

