package edu.iastate.mis438.mealtime;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TabHost;

public class Recipes extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); 
        setContentView(R.layout.recipes);
        
        TabHost tabs=(TabHost)findViewById(android.R.id.tabhost);
		
		tabs.setup();
		
		TabHost.TabSpec spec=tabs.newTabSpec("tag1");
		
		spec.setContent(R.id.tab1);
		spec.setIndicator("Breakfast");
		tabs.addTab(spec);
	
		spec=tabs.newTabSpec("tag2");
		spec.setContent(R.id.tab2);
		spec.setIndicator("Lunch");
		tabs.addTab(spec);	
		
		spec=tabs.newTabSpec("tag3");
		spec.setContent(R.id.tab3);
		spec.setIndicator("Dinner");
		tabs.addTab(spec);
	}

}
