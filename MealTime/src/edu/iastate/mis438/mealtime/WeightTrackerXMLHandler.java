package edu.iastate.mis438.mealtime;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class WeightTrackerXMLHandler extends DefaultHandler{
	
	WeightTrackerData info = new WeightTrackerData();
	boolean current = false;
	boolean goal = false;
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		if (qName.equalsIgnoreCase("weight_kg")){
			current = true;
		}
		if (qName.equalsIgnoreCase("auth_secret")){
			goal = true;
		}
	}

	@Override
	public void characters(char[] arg0, int arg1, int arg2) throws SAXException {

		if (current){
			String tokenString = new String(arg0, arg1, arg2);
			info.setCurrentWeight(tokenString);
			current = false;

			}
		if (goal){
			String tokenSecretString = new String(arg0, arg1, arg2);
			info.setGoalWeight(tokenSecretString);
			goal = false;

		}
	}

	public String getCurrentWeightInformation(){

		return info.currentToString();
	}

	public String getGoalWeightInformation(){

		return info.goalToString();
	}
}

