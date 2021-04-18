package org.launchcode.liftoffrecipetracker.models;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Category  {

    //Class Variables
    @Id
    private int id;
    // do we want to specify size for category names?
    //@Size(min = 4, max = 25, message = 'Category name must be between 4 and 25 characters long')
    //@NotBlank
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

    public void setId(int id) {
        this.id = id;
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
