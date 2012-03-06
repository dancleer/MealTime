package edu.iastate.mis438.mealtime;

import android.app.Activity;
import android.os.Bundle;
//I edited this directly from google code, not from eclipse. <-- Derik
// Did we want a list view for this first prototype or actual buttons? 
//***This is to create a listview on the home screen. We would still need
//to change the items in the android manafest and main layout.***
public class startingPoint extends Activity {
  String classes[] = {"StartingPoint", "Search", "Login", "Recipes", "Food History", "Weight Tracker"};

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		//which list item was clicked
		String stringPosition = classes[position];
		
		try{
		//this tries to load up the activity that was clicked from the main menu. StartingPoint is the homescreen I'm assuming? 
      //We still need a splash screen.
		Class ourClass = Class.forName("edu.iastate.mis438.mealtime." + stringPosition);
		Intent ourIntent = new Intent(startingPoint.this, ourClass);
		startActivity(ourIntent);
		
		} 
		catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}
}