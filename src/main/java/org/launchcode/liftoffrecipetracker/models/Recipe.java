package org.launchcode.liftoffrecipetracker.models;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;
import org.launchcode.liftoffrecipetracker.data.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

//@Indexed creates an index of the schema for searching purposes. Only entities with @Indexed will be indexed.
@Entity
@Indexed
public class Recipe extends AbstractEntity {
	//properties
	@NotBlank
	@NotNull
	@Size(min=5, message = "Ingredients are required.")
	private String ingredients;

	//@FullTextField annotation creates an index of this property that is full-text searchable
	@NotBlank(message = "Recipe name required.")
	@NotNull
	@Size(min=2,max=50, message = "Recipe name must be between 2 and 50 characters long.")
	@FullTextField
	private String name;

	@NotNull
	@NotBlank
	@Size(min=5, message = "Directions are required.")
	private String directions;

	@NotNull
	@Min(value = 1, message = "Must be at least one serving.")
	private int servings;

	@NotNull
	@Min(value = 1, message = "Cook time must be a positive value greater than 1.")
	private int cookTime;

	@NotNull
	@Min(value = 1, message = "Cook time must be a positive value greater than 1.")
	private int prepTime;

	private String image;

	//@IndexedEmbedded allows for related classes to be indexed without using @Indexed on the embedded class
	@ManyToMany
	@IndexedEmbedded
	private List<Category> categories = new ArrayList<>();

	@ManyToOne
	@IndexedEmbedded
	private  Integer users;

	//constructors
	public Recipe(String ingredients, String name, String directions,int servings, int cookTime, int prepTime,
	              List<Category> categories, Integer users){
		this.ingredients = ingredients;
		this.name = name;
		this.directions = directions;
		this.servings = servings;
		this.cookTime = cookTime;
		this.prepTime = prepTime;
		this.categories = categories;
		this.users = users;
	}

	public Recipe() {
	}

	//getters & setters
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

	public int getPrepTime() {
		return prepTime;
	}

	public void setPrepTime(int prepTime) {
		this.prepTime = prepTime;
	}

	public int getCookTime() {
		return cookTime;
	}

	public void setCookTime(int cookTime) {
		this.cookTime = cookTime;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<Category> getCategories() { return categories; }

	public void addCategories(List<Category> categories) {
		this.categories.addAll(categories);
	}

	public Integer getUsers() {
		return users;
	}

	public void addUsers(Integer owners) {
		this.users= users;
	}
}


