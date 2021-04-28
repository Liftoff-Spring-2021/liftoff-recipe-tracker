package org.launchcode.liftoffrecipetracker.models;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
    public class Beverage extends AbstractRecommendations{
    //public class Beverage extends AbstractEntity {

        //Class Variables
//        @Size(min = 4, max = 50, message = "Beverage name must be between 4 and 50 characters long")
//        @NotBlank
//        @NotNull
//        private String name;

    @ManyToMany(mappedBy = "beverages")
    private final List<Recipe> recipes = new ArrayList<>();

        //Constructors

//        public Beverage(String name) {
//            this.name = name;
//        }

        public Beverage() {
        }

        //Methods
        //Getters and Setters


//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
    public List<Recipe> getRecipes() {
        return recipes;
    }
}

