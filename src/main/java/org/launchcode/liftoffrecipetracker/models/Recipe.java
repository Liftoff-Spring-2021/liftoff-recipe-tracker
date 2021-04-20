package org.launchcode.liftoffrecipetracker.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.HashMap;


@Entity
public class Recipe extends AbstractEntity {

	private HashMap<String, String> ingredient;

	private String name;
	private int servings;
	private int cookTime;
	private int prepTime;
	private String images;

	//@ManyToMany
	private ArrayList<Recipe> recipes;

	public Recipe(HashMap<String, String> ingredient, String name) {
		this.ingredient = ingredient;
		this.name = name;
	}


	public Recipe(){}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HashMap<String, String> getIngredient() {
		return ingredient;
	}

	public void setIngredient(HashMap<String, String> ingredient) {
		this.ingredient = ingredient;
	}

	public int getServings() {
		return servings;
	}

	public void setServings(int servings) {
		this.servings = servings;
	}

	public int getCookTime() {
		return cookTime;
	}

	public void setCookTime(int cookTime) {
		this.cookTime = cookTime;
	}

	public int getPrepTime() {
		return prepTime;
	}

	public void setPrepTime(int prepTime) {
		this.prepTime = prepTime;
	}

	public ArrayList<Recipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(ArrayList<Recipe> recipes) {
		this.recipes = recipes;
	}

}

