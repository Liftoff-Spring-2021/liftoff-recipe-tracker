package org.launchcode.liftoffrecipetracker.models;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

//@Indexed creates an index of the schema for searching purposes. Only entities with @Indexed will be indexed.
@Entity
@Indexed
public class Recipe extends AbstractEntityName {
	//properties
	@NotBlank
	@NotNull
	@Size(max=500)
	private String description;

	@NotBlank(message = "Ingredients are required and should be more than 5 characters.")
	@NotNull
	@Size(min=5, message = "")
	private String ingredients;

	@NotNull
	@NotBlank(message = "Directions are required and should be more than 5 characters.")
	@Size(min=5, message = "")
	private String directions;

	@NotNull
	@Min(value = 1, message = "Must be at least one serving.")
	private int servings;

	@NotNull
	@Min(value = 0, message = "Cook time must be a positive value.")
	private int cookTime;

	@NotNull
	@Min(value = 0, message = "Prep time must be a positive value.")
	private int prepTime;

	private String image;

	private boolean favorite;

	//@IndexedEmbedded allows for related classes to be indexed without using @Indexed on the embedded class
	@ManyToMany
	@IndexedEmbedded
	private List<Category> categories = new ArrayList<>();

	@ManyToMany
	@IndexedEmbedded
	private List<Beverage> beverages = new ArrayList<>();

	@ManyToOne
	private User user;

	//constructors
	public Recipe(String ingredients, String directions,int servings, int cookTime, int prepTime,
	              List<Category> categories, List<Beverage> beverages, User user, String description){
		this.ingredients = ingredients;
		this.directions = directions;
		this.servings = servings;
		this.cookTime = cookTime;
		this.prepTime = prepTime;
		this.categories = categories;
		this.beverages = beverages;
		this.user = user;
		this.description = description;
	}

	public Recipe() {
	}

	public void removeCategory(Category category) {
		this.categories.remove(category);
	}

	public void removeAllCategories(List<Category> categories) {
		this.categories.removeAll(categories);
	}

	public void addCategories(List<Category> categories) {
		this.categories.addAll(categories);
	}

	public void removeBeverage(Beverage beverage) {
		this.beverages.remove(beverage);
	}

	public void removeAllBeverages(List<Beverage> beverages) {
		this.beverages.removeAll(beverages);
	}

	public void addBeverages(List<Beverage> beverages) {
		this.beverages.addAll(beverages);
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

	public boolean isFavorite() {
		return favorite;
	}

	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}

	public List<Category> getCategories() { return categories; }

	public List<Beverage> getBeverages() {
		return beverages;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}


