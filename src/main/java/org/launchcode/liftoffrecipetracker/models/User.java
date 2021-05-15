package org.launchcode.liftoffrecipetracker.models;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User extends AbstractEntity {

    @NotNull
    private String username;

    private String email;

    @NotNull
    private String passwordHash;

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @OneToMany(mappedBy = "user")
    private final List<Recipe> recipes = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private final List<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private final List<Beverage> beverages = new ArrayList<>();

    public User(){

    }

    public User(String username, String password, String email) {
        this.username = username;
        this.passwordHash = encoder.encode(password);
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, passwordHash);
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void addRecipes(List<Recipe> recipes) {
        this.recipes.addAll(recipes);
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void addCategories(List<Category> categories) {
        this.categories.addAll(categories);
    }

    public List<Beverage> getBeverages() {
        return beverages;
    }

    public void addBeverages(List<Beverage> beverages) {
        this.beverages.addAll(beverages);
    }
}
