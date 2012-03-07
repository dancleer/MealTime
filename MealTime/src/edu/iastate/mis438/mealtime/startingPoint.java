package edu.iastate.mis438.mealtime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
//I edited this directly from google code, not from eclipse. <-- Derik
// Did we want a list view for this first prototype or actual buttons? 
//***This is to create a listview on the home screen. We would still need
//to change the items in the android manafest and main layout.***
public class startingPoint extends Activity {

	ImageButton aboutButton, foodHistoryButton, loginRegisterButton, recipesButton, searchButton, weightTrackerButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		foodHistoryButton = (ImageButton) findViewById(R.id.bfoodHistory);
		foodHistoryButton.setBackgroundColor(android.R.color.transparent);
		foodHistoryButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {			
				startActivity(new Intent(getApplicationContext(), FoodHistory.class));
			}
		});
		
		aboutButton = (ImageButton) findViewById(R.id.babout);
		aboutButton.setBackgroundColor(android.R.color.transparent);
		aboutButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {			
				startActivity(new Intent(getApplicationContext(), About.class));
			}
		});
		
		loginRegisterButton = (ImageButton) findViewById(R.id.bloginRegister);
		loginRegisterButton.setBackgroundColor(android.R.color.transparent);
		loginRegisterButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {			
				startActivity(new Intent(getApplicationContext(), LoginRegister.class));
			}
		});
		
		recipesButton = (ImageButton) findViewById(R.id.brecipes);
		recipesButton.setBackgroundColor(android.R.color.transparent);
		recipesButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {			
				startActivity(new Intent(getApplicationContext(), Recipes.class));
			}
		});
		
		searchButton = (ImageButton) findViewById(R.id.bsearch);
		searchButton.setBackgroundColor(android.R.color.transparent);
		searchButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {			
				startActivity(new Intent(getApplicationContext(), Search.class));
			}
		});
		
		weightTrackerButton = (ImageButton) findViewById(R.id.bweightTracker);
		weightTrackerButton.setBackgroundColor(android.R.color.transparent);
		weightTrackerButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {			
				startActivity(new Intent(getApplicationContext(), WeightTracker.class));
			}
		});
	}
}