package edu.iastate.mis438.mealtime;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SearchDetails extends Activity{

	TextView tv;
	TextView tv1;
	String gotBundle;
	String gotBundleDesc;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.searchdetails);
		
		Bundle b = getIntent().getExtras();
		gotBundle = b.getString("Food: ");
		Bundle bb = getIntent().getExtras();
		gotBundleDesc = bb.getString("Desc");
		tv = (TextView)findViewById(R.id.tvResultsFoodName);
		tv1 = (TextView)findViewById(R.id.tvResultsFoodDescription);
		tv.setText(gotBundle);
		tv1.setText(gotBundleDesc);
		
	}

	
	
}
