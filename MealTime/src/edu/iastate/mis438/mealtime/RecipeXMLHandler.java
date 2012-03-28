package edu.iastate.mis438.mealtime;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class RecipeXMLHandler extends DefaultHandler{

	
//	  <food>
//    <food_id>33689</food_id>
//    <food_name>Cheddar Cheese</food_name>
//    <food_type>Generic</food_type>
//    <food_url>http://www.fatsecret.com/calories-nutrition/usda/cheddar-cheese</food_url>
//    <food_description>Per 100g - Calories: 403kcal | Fat: 33.14g | Carbs: 1.28g | Protein: 24.90g</food_description>
//  </food>
	
	
	RecipeData info = new RecipeData();
	boolean recipename = false;
	boolean recipedescription = false;
	int i = 0;
	int j = 0;
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		//System.out.println("Start element: " + qName);
		if (qName.equalsIgnoreCase("recipe_name")){
			recipename = true;
		}
		if (qName.equalsIgnoreCase("recipe_description")){
			recipedescription = true;
		}
	}

	@Override
	public void characters(char[] arg0, int arg1, int arg2) throws SAXException {
		// TODO Auto-generated method stub
		if (recipename){
			String foodName = new String(arg0, arg1, arg2);
			info.setFoodName(foodName, i);
			recipename = false;
			i++;
			}
		if (recipedescription){
			String foodDescription = new String(arg0, arg1, arg2);
			info.setFoodDescription(foodDescription, j);
			recipedescription = false;
			j++;
		}
	}

	public String[] getRecipeNameInformation(){
		i = 0;
		j = 0;
		return info.recipeNameToString();
	}

	public String[] getRecipeDescriptions(){
		j=0;
		i=0;
		return info.recipeDescriptionsToString();
	}
}
