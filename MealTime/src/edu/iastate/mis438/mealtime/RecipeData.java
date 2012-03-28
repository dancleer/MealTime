package edu.iastate.mis438.mealtime;

import java.util.ArrayList;

public class RecipeData {

	int foodID = 0;
	String recipeName = null;
	String recipeType = null;
	String recipeDescription = null;
	//ArrayList<String> foodNames = new ArrayList<String>();
	//ArrayList<String> foodDescriptions = new ArrayList<String>();
	String[] recipeNames = new String[5];
	String[] recipeDescriptions = new String[5];
	
//	  <food>
//	    <food_id>33689</food_id>
//	    <food_name>Cheddar Cheese</food_name>
//	    <food_type>Generic</food_type>
//	    <food_url>http://www.fatsecret.com/calories-nutrition/usda/cheddar-cheese</food_url>
//	    <food_description>Per 100g - Calories: 403kcal | Fat: 33.14g | Carbs: 1.28g | Protein: 24.90g</food_description>
//	  </food>
	public int getFoodID() {
		return foodID;
	}
	public void setFoodID(int foodID) {
		this.foodID = foodID;
	}
	public String getFoodName() {
		return recipeName;
	}
	public void setFoodName(String foodName, int index) {
		recipeNames[index] = foodName;
	}
	public String getFoodType() {
		return recipeType;
	}
	public void setFoodType(String foodType) {
		this.recipeType = foodType;
	}
	public String getFoodDescription() {
		return recipeDescription;
	}
	public void setFoodDescription(String foodDescription, int index) {
		recipeDescriptions[index] = foodDescription;
	}
	public String[] recipeNameToString(){
		String[] ret = new String[recipeNames.length];
		for(int i = 0; i < recipeNames.length; i++){
			ret[i] = recipeNames[i];
		}
		return ret;
	}
	public String[] recipeDescriptionsToString(){
		String[] ret = new String[recipeDescriptions.length];
		for(int i = 0; i < recipeDescriptions.length; i++){
			ret[i] = recipeDescriptions[i];
		}
		return ret;
	}
}

