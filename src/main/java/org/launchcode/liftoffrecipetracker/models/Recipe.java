package org.launchcode.liftoffrecipetracker.models;

import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashMap;


@Entity
public class Recipe extends AbstractEntity {

	private HashMap<String, String> ingredients;

	@NotBlank(message = "Recipe name required.")
	@NotNull
	@Size(min=2,max=50, message = "Recipe name must be between 2 and 50 characters long.")
	private String name;

	@NotNull
	@NotBlank
	@Size(min=4, max=1000, message = "Directions are required.")
	private String directions;

	@NotBlank
	@Min(value = 1, message = "Must be at least one serving.")
	private int servings;

	private int cookTime;
	private int prepTime;
	private String image;

	public Recipe(HashMap<String, String> ingredients, String name, String directions) {
		this.ingredients = ingredients;
		this.name = name;
		this.directions= directions;
	}

	public Recipe() {
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HashMap<String, String> getIngredients() {
		return ingredients;
	}

	public void setIngredients(HashMap<String, String> ingredient) {
		this.ingredients = ingredients;
	}

	public String getDirections() {
		return directions;
	}

	public void setDirections(String directions) {
		this.directions = directions;
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


