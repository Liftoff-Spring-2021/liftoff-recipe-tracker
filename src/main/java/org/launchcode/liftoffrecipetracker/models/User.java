package org.launchcode.liftoffrecipetracker.models;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User extends AbstractEntity {

    private String username;
    private String password;
    private String email;
//    private List<String> recipe = new ArrayList<String>();

    public User(){

    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
//        this.recipe = recipe;
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

//    public List<String> getRecipe() {
//        return recipe;
//    }
//
//    public void setRecipe(List<String> recipe) {
//        this.recipe = recipe;
//    }
}
