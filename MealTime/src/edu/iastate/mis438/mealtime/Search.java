package edu.iastate.mis438.mealtime;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import edu.iastate.mis438.mealtime.SearchXMLHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class Search extends Activity implements OnClickListener{

	String pageNumber = "0";
	TextView tv;
	TextView tv1;
	TextView tv2;
	TextView tv3;
	TextView tv4;
	TextView tv5;
	EditText query;
	ListView lv;
	ArrayAdapter<String> aa;
	ArrayList<String> resultsList = new ArrayList<String>();
	ArrayList<String> descList = new ArrayList<String>();
	ArrayList<String> foodIDsList = new ArrayList<String>();

	private static String key = "9a6ae1fceea24f2894fba9311bfe20db";
	private static String secret = "549aeee4d7ec46198bd88e89986b38a1";
	private static final String HMAC_SHA1 = "HmacSHA1";
	private static final String ENC = "UTF-8";
	private static Base64 base64 = new Base64();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.search);
		Button b = (Button)findViewById(R.id.bSearchGo);
		Button next = (Button)findViewById(R.id.bNext);
		Button previous = (Button)findViewById(R.id.bPrevious);
		tv = (TextView)findViewById(R.id.tvSearch);
		tv1 = (TextView)findViewById(R.id.tvSearch1);
		tv2 = (TextView)findViewById(R.id.tvSearch2);
		tv3 = (TextView)findViewById(R.id.tvSearch3);
		tv4 = (TextView)findViewById(R.id.tvSearch4);
		tv5 = (TextView)findViewById(R.id.tvSearch5);
		query = (EditText)findViewById(R.id.etSearch);
//		lv = (ListView)findViewById(R.id.lvSearch);
//		aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, resultsList);
//		lv.setAdapter(aa);

		b.setOnClickListener(this);
		next.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				int pn = Integer.parseInt(pageNumber);
				pn++;
				pageNumber = Integer.toString(pn);
				SearchLookupTask task = new SearchLookupTask();
				task.execute(query.getText().toString());
//				String foodName = resultsList.get(0);
//				String foodDescription = descList.get(0);
//				Bundle b = new Bundle();
//				b.putString("Food: ", foodName);
//				b.putString("Desc", foodDescription);
//				Intent tent = new Intent(Search.this, SearchDetails.class);
//				tent.putExtras(b);
//				startActivity(tent);
				
			}
		});
		previous.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v){
				int pn = Integer.parseInt(pageNumber);
				if (pn > 0){
					pn--;
					//needed to update the pageNumber before the call to task.
					pageNumber = Integer.toString(pn);
					SearchLookupTask task = new SearchLookupTask();
					task.execute(query.getText().toString());
				}
				//do nothing
				
				//            else
				//				pn stays the same
				//			    and does not do another search        	  
				//pageNumber = Integer.toString(pn);
			}

		});
		tv1.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				int pn = Integer.parseInt(pageNumber);
				int op = 0;
				if(pn > 0){
					op = pn * 5;
				}
				String foodID = foodIDsList.get(op);
				
				String foodName = resultsList.get(op);
				String foodDescription = descList.get(op);

				Bundle b = new Bundle();
				
				b.putString("FoodID", foodID);
				
				b.putString("Food: ", foodName);
				b.putString("Desc", foodDescription);
				Intent tent = new Intent(Search.this, SearchDetails.class);
				tent.putExtras(b);
				startActivity(tent);
				
			}
		});
		tv2.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				int pn = Integer.parseInt(pageNumber);
				int op = 0;
				if(pn > 0){
					op = (pn * 5) + 1;
				}
				String foodID = foodIDsList.get(op);
				
				String foodName = resultsList.get(op);
				String foodDescription = descList.get(op);
//				String foodID = foodIDsList.get(op);

				Bundle b = new Bundle();
				
				b.putString("FoodID", foodID);
				
				b.putString("Food: ", foodName);
				b.putString("Desc", foodDescription);
				Intent tent = new Intent(Search.this, SearchDetails.class);
				tent.putExtras(b);
				startActivity(tent);
				
			}
		});
		tv3.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				int pn = Integer.parseInt(pageNumber);
				int op = 0;
				if(pn > 0){
					op = (pn * 5) + 2;
				}
				String foodID = foodIDsList.get(op);
				
				String foodName = resultsList.get(op);
				String foodDescription = descList.get(op);
//				String foodID = foodIDsList.get(op);

				Bundle b = new Bundle();
				
				b.putString("FoodID", foodID);
				
				b.putString("Food: ", foodName);
				b.putString("Desc", foodDescription);
				Intent tent = new Intent(Search.this, SearchDetails.class);
				tent.putExtras(b);
				startActivity(tent);
				
			}
		});
		tv4.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				int pn = Integer.parseInt(pageNumber);
				int op = 0;
				if(pn > 0){
					op = (pn * 5) + 3;
				}
				String foodID = foodIDsList.get(op);
				
				String foodName = resultsList.get(op);
				String foodDescription = descList.get(op);
//				String foodID = foodIDsList.get(op);

				Bundle b = new Bundle();
				
				b.putString("FoodID", foodID);
				
				b.putString("Food: ", foodName);
				b.putString("Desc", foodDescription);
				Intent tent = new Intent(Search.this, SearchDetails.class);
				tent.putExtras(b);
				startActivity(tent);
				
			}
		});
		tv5.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				int pn = Integer.parseInt(pageNumber);
				int op = 0;
				if(pn > 0){
					op = (pn * 5) + 4;
				}
				String foodID = foodIDsList.get(op);
				
				String foodName = resultsList.get(op);
				String foodDescription = descList.get(op);
//				String foodID = foodIDsList.get(op);

				Bundle b = new Bundle();
				
				b.putString("FoodID", foodID);
				
				b.putString("Food: ", foodName);
				b.putString("Desc", foodDescription);
				
				Intent tent = new Intent(Search.this, SearchDetails.class);
				tent.putExtras(b);
				startActivity(tent);
				
			}
		});

	}

	public void onClick(View v) {

				SearchLookupTask task = new SearchLookupTask();
				task.execute(query.getText().toString());

	}
	
		private class SearchLookupTask extends AsyncTask<String, Void, String[]>{
	
			@Override
			protected String[] doInBackground(String... params) {
				String[] query = params;
				String searchTerm = query[0];
				try{
					List<NameValuePair> qparams = new ArrayList<NameValuePair>();
					// These params should ordered in key
	
					// qparams.add(new BasicNameValuePair("food_id", "33691"));
					qparams.add(new BasicNameValuePair("max_results", "5"));
					qparams.add(new BasicNameValuePair("method", "foods.search"));
					qparams.add(new BasicNameValuePair("oauth_consumer_key", key));
					qparams.add(new BasicNameValuePair("oauth_nonce", "" + (int) (Math.random() * 100000000)));
					qparams.add(new BasicNameValuePair("oauth_signature_method", "HMAC-SHA1"));
					qparams.add(new BasicNameValuePair("oauth_timestamp", "" + (System.currentTimeMillis())));
					qparams.add(new BasicNameValuePair("oauth_version", "1.0"));
					qparams.add(new BasicNameValuePair("page_number", pageNumber));
					qparams.add(new BasicNameValuePair("search_expression", searchTerm));

					// generate the oauth_signature
					String signature = getSignature(URLEncoder.encode(
							"http://platform.fatsecret.com/rest/server.api", ENC),
							URLEncoder.encode(URLEncodedUtils.format(qparams, ENC), ENC));
	
					// add it to params list
					qparams.add(new BasicNameValuePair("oauth_signature", signature));
	
					// generate URI which lead to access_token and token_secret.
					URI uri = URIUtils.createURI("http", "platform.fatsecret.com", -1, "/rest/server.api", URLEncodedUtils.format(qparams, ENC), null);
	
					URL website = new URL(uri.toString());
	
					SAXParserFactory spf = SAXParserFactory.newInstance();
					SAXParser sp = spf.newSAXParser();
					XMLReader xr = sp.getXMLReader();
					SearchXMLHandler handler = new SearchXMLHandler();
					xr.setContentHandler(handler);
					xr.parse(new InputSource(website.openStream()));
					
					String[] foodIDs = handler.getFoodIDInformation();
					
					String[] information = handler.getFoodNameInformation();
					String[] descriptions = handler.getFoodDescriptions();

					for(int i = 0; i < information.length; i++){
						foodIDsList.add(foodIDs[i]);
						
						resultsList.add(information[i]);
						descList.add(descriptions[i]);
					}
					return information;
				}catch (Exception e){
					e.printStackTrace();
				}
	
				return null;
			}
	
	
			@Override
			protected void onPostExecute(String[] result) {
				tv1.setText(result[0].toString());
				tv2.setText(result[1].toString());
				tv3.setText(result[2].toString());
				tv4.setText(result[3].toString());
				tv5.setText(result[4].toString());
			}
	
	
			private String getSignature(String url, String params)
					throws UnsupportedEncodingException, NoSuchAlgorithmException,
					InvalidKeyException {
				/**
				 * base has three parts, they are connected by "&": 1) protocol 2) URL
				 * (need to be URLEncoded) 3) Parameter List (need to be URLEncoded).
				 */
				StringBuilder base = new StringBuilder();
				base.append("GET&");
				base.append(url);
				base.append("&");
				base.append(params);
				// yea, don't ask me why, it is needed to append a "&" to the end of
				// secret key.
				byte[] keyBytes = (secret + "&").getBytes(ENC);
	
				SecretKey key = new SecretKeySpec(keyBytes, HMAC_SHA1);
	
				Mac mac = Mac.getInstance(HMAC_SHA1);
				mac.init(key);
	
				// encode it, base64 it, change it to string and return.
				return new String(base64.encode(mac.doFinal(base.toString().getBytes(
						ENC))), ENC).trim();
			}
	
	}
}
