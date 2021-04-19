package org.launchcode.liftoffrecipetracker.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;


@Entity
public class Recipe {

	@GeneratedValue
	@Id
	private int id;

	private String name;
	private String ingredients;
	private int servings;
	private int cookTime;
	private int prepTime;

	//@ManyToMany
	private ArrayList<Recipe> recipes;

	public Recipe(String name,String ingredients) {
		this.name = name;
		this.ingredients = ingredients;
	}
	public Recipe(){}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIngredients() {
		return ingredients;
	}

	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
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
}

