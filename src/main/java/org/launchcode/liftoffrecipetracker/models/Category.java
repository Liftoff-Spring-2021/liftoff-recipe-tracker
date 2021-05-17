package org.launchcode.liftoffrecipetracker.models;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category extends AbstractEntityName {

    //Class Variables

    @ManyToMany(mappedBy = "categories")
    private final List<Recipe> recipes = new ArrayList<>();

    @ManyToOne
    private User user;

    //Constructors

    public Category() {}

    // Getters and Setters

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
