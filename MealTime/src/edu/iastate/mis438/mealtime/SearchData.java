package edu.iastate.mis438.mealtime;

import java.util.ArrayList;

public class SearchData {

	int foodID = 0;
	String foodName = null;
	String foodType = null;
	String foodDescription = null;
	//ArrayList<String> foodNames = new ArrayList<String>();
	//ArrayList<String> foodDescriptions = new ArrayList<String>();
	String[] foodNames = new String[5];
	String[] foodDescriptions = new String[5];
	
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
		return foodName;
	}
	public void setFoodName(String foodName, int index) {
		foodNames[index] = foodName;
	}
	public String getFoodType() {
		return foodType;
	}
	public void setFoodType(String foodType) {
		this.foodType = foodType;
	}
	public String getFoodDescription() {
		return foodDescription;
	}
	public void setFoodDescription(String foodDescription, int index) {
		foodDescriptions[index] = foodDescription;
	}
	public String[] foodNameToString(){
		String[] ret = new String[foodNames.length];
		for(int i = 0; i < foodNames.length; i++){
			ret[i] = foodNames[i];
		}
		return ret;
	}
	public String[] foodDescriptionsToString(){
		String[] ret = new String[foodDescriptions.length];
		for(int i = 0; i < foodDescriptions.length; i++){
			ret[i] = foodDescriptions[i];
		}
		return ret;
	}
}
