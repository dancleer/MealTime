package edu.iastate.mis438.mealtime;

import android.app.Activity;
import android.os.Bundle;

public class About extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); 
        setContentView(R.layout.about);
        //getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.about);
	}
	


}
