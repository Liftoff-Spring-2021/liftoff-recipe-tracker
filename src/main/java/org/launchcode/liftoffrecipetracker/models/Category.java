package org.launchcode.liftoffrecipetracker.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
public class Category  {

    //Class Variables
    @GeneratedValue
    @Id
    private int id;

    @Size(min = 4, max = 25, message = "Category name must be between 4 and 25 characters long")
    @NotBlank
    @NotNull
    private String name;

//    Add this after Recipe class is completed/extended
//    @OneToMany(mappedBy = "category")
//    private final List<Recipe> recipes = new ArrayList<>();


    //Constructors

    public Category(String name) {
        this.name = name;
    }

    public Category() {}

    //Methods



    //Getters and Setters


    public int getId() {
        return id;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public List<Recipe> getRecipes() {
//        return recipes;
//    }
}
