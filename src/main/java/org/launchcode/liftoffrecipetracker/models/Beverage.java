package org.launchcode.liftoffrecipetracker.models;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
    public class Beverage extends AbstractEntityName {

    //Class Variables

    @ManyToMany(mappedBy = "beverages")
    private final List<Recipe> recipes = new ArrayList<>();

    @ManyToOne
    private User user;

    @NotBlank(message = "Please include a description of your beverage.")
    @NotNull
    @Size(max=500)
    @FullTextField
    private String description;

    //Constructors

    public Beverage() {
    }

    //Getters and Setters

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

