package org.launchcode.liftoffrecipetracker.Models;

import javax.persistence.Entity;
import java.util.HashMap;

@Entity
public class User {

    private String username;
    private String password;
    private String email;
    private HashMap Recipe = new HashMap();


    public User(String username, String password, String email, HashMap recipe) {
        this.username = username;
        this.password = password;
        this.email = email;
        Recipe = recipe;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public HashMap getRecipe() {
        return Recipe;
    }

    public void setRecipe(HashMap recipe) {
        Recipe = recipe;
    }
}
