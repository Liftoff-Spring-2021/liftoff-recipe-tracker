package org.launchcode.liftoffrecipetracker.models;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Entity;
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

//    private List<String> recipe = new ArrayList<String>();

    public User(){

    }

    public User(String username, String password, String email) {
        this.username = username;
        this.passwordHash = encoder.encode(password);
        this.email = email;
//        this.recipe = recipe;
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

//    public List<String> getRecipe() {
//        return recipe;
//    }
//
//    public void setRecipe(List<String> recipe) {
//        this.recipe = recipe;
//    }
}
