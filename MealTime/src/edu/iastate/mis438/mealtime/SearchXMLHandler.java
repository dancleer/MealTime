package edu.iastate.mis438.mealtime;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SearchXMLHandler extends DefaultHandler{

	
//	  <food>
//    <food_id>33689</food_id>
//    <food_name>Cheddar Cheese</food_name>
//    <food_type>Generic</food_type>
//    <food_url>http://www.fatsecret.com/calories-nutrition/usda/cheddar-cheese</food_url>
//    <food_description>Per 100g - Calories: 403kcal | Fat: 33.14g | Carbs: 1.28g | Protein: 24.90g</food_description>
//  </food>
	
	
	SearchData info = new SearchData();
	boolean foodname = false;
	boolean fooddescription = false;
	boolean foodId = false;
	String[] foodIDS = new String[5];
	String[] foodNames = new String[5];
	String[] foodDescriptions = new String[5];
	int h = 0;
	int i = 0;
	int j = 0;
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		//System.out.println("Start element: " + qName);
		if (qName.equalsIgnoreCase("food_id")){
			foodId = true;
		}
		if (qName.equalsIgnoreCase("food_name")){
			foodname = true;
		}
		if (qName.equalsIgnoreCase("food_description")){
			fooddescription = true;
		}
	}

	@Override
	public void characters(char[] arg0, int arg1, int arg2) throws SAXException {
		// TODO Auto-generated method stub
		if (foodId){
			String ID = new String(arg0, arg1, arg2);
			info.setFoodID(ID, h);
			foodId = false;
			h++;
			}
		if (foodname){
			String foodName = new String(arg0, arg1, arg2);
			info.setFoodName(foodName, i);
			foodname = false;
			i++;
			}
		if (fooddescription){
			String foodDescription = new String(arg0, arg1, arg2);
			info.setFoodDescription(foodDescription, j);
			fooddescription = false;
			j++;
		}
	}
	
	public String[] getFoodIDInformation(){
		h=0;
		i=0;
		j=0;
		return info.foodIDToString();
	}

	public String[] getFoodNameInformation(){
		h=0;
		i = 0;
		j = 0;
		return info.foodNameToString();
	}

	public String[] getFoodDescriptions(){
		h=0;
		j=0;
		i=0;
		return info.foodDescriptionsToString();
	}
}
