package edu.iastate.mis438.mealtime;

public class WeightTrackerData {

	String current = null;
	String goal = null;

	public void setCurrentWeight(String tok) {
		current = tok;
	}
	public void setGoalWeight(String tokSec) {
		goal = tokSec;
	}
	public String currentToString(){
		return current;
	}
	public String goalToString(){
		return goal;
	}
}

