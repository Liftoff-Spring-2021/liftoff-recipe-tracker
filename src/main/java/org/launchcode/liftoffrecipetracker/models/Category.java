package org.launchcode.liftoffrecipetracker.models;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Category extends AbstractEntity  {

    //Class Variables
    @Size(min = 4, max = 25, message = "Category name must be between 4 and 25 characters long")
    @NotBlank
    @NotNull
    @FullTextField
    private String name;

    @ManyToMany(mappedBy = "categories")
    private final List<Recipe> recipes = new ArrayList<>();

    //Constructors

    public Category(String name) {
        this.name = name;
    }

    public Category() {}

    //Methods
    //Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }
}
