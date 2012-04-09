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

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class WeightTracker extends Activity implements OnClickListener{

	TextView tv, tv2;
	Button b;
	EditText current, goal, height;
	String tSecret;

	ArrayList<String> weight = new ArrayList<String>();
	
	private static String key = "9a6ae1fceea24f2894fba9311bfe20db";
	private static String secret = "549aeee4d7ec46198bd88e89986b38a1";
	private static final String HMAC_SHA1 = "HmacSHA1";
	private static final String ENC = "UTF-8";
	private static Base64 base64 = new Base64();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weighttracker);
		
		final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		if(prefs.getBoolean("firstTime", true)) {
			tv = (TextView)findViewById(R.id.tvWeightReport);
			tv.setText("Please visit the login page to get started!");
			b.setEnabled(false);
		}
		else if(prefs.getBoolean("weightFirstTime", true)){
			b = (Button)findViewById(R.id.bSetWeightTracker); 
			current = (EditText)findViewById(R.id.etCurrentWeight);
			goal = (EditText)findViewById(R.id.etGoalWeight);
			height = (EditText)findViewById(R.id.etHeight);
			tv = (TextView)findViewById(R.id.tvWeightReport);
			b.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					CreateWeight task = new CreateWeight();
					String[] pass = new String[5];
					pass[0] = current.getText().toString();
					pass[1] = goal.getText().toString();
					pass[2] = height.getText().toString();
					pass[3] = prefs.getString("token", null);
					pass[4] = prefs.getString("tokenSecret", null);
					tSecret = pass[4];
					task.execute(pass);
					SharedPreferences.Editor editor = prefs.edit();
					editor.putBoolean("weightFirstTime", false);
					editor.putString("goalWeight", goal.getText().toString());
					editor.commit();
				}
			});
		}else{
			setContentView(R.layout.weightresults);
			b = (Button)findViewById(R.id.bWeightResultsGo);
			tv2 = (TextView)findViewById(R.id.tvDisplayWeight);
			b.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v){
					GetWeight task = new GetWeight();
					current = (EditText)findViewById(R.id.etCurrentWeightResults);
					String[] pass = new String[3];
					pass[0] = current.getText().toString();
					pass[1] = prefs.getString("token", null);
					pass[2] = prefs.getString("tokenSecret", null);
					tSecret = pass[2];
					task.execute(pass);
				}
			});
		}
	}

	public class CreateWeight extends AsyncTask<String[], Void, String[]>{

		@Override
		protected String[] doInBackground(String[]... params) {
			try{
				List<NameValuePair> qparams = new ArrayList<NameValuePair>();
				// These params should be ordered alphabetically

				qparams.add(new BasicNameValuePair("current_height_cm", params[2].toString()));
				qparams.add(new BasicNameValuePair("current_weight_kg", params[0].toString()));
				qparams.add(new BasicNameValuePair("goal_weight_kg", params[1].toString()));
				qparams.add(new BasicNameValuePair("method", "weight.update"));
				qparams.add(new BasicNameValuePair("oauth_consumer_key", key));
				qparams.add(new BasicNameValuePair("oauth_nonce", "" + (int) (Math.random() * 100000000)));
				qparams.add(new BasicNameValuePair("oauth_signature_method", "HMAC-SHA1"));
				qparams.add(new BasicNameValuePair("oauth_timestamp", "" + (System.currentTimeMillis())));
				qparams.add(new BasicNameValuePair("oauth_token", params[3].toString()));
				qparams.add(new BasicNameValuePair("oauth_version", "1.0"));

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
				WeightTrackerXMLHandler handler = new WeightTrackerXMLHandler();
				xr.setContentHandler(handler);
				xr.parse(new InputSource(website.openStream()));
				String[] information = new String[2];
				information[0] = handler.getCurrentWeightInformation();
				return information;
			}catch (Exception e){
				e.printStackTrace();
			}
			return null;
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
			byte[] keyBytes = (secret + "&" + tSecret).getBytes(ENC);

			SecretKey key = new SecretKeySpec(keyBytes, HMAC_SHA1);

			Mac mac = Mac.getInstance(HMAC_SHA1);
			mac.init(key);

			// encode it, base64 it, change it to string and return.
			return new String(base64.encode(mac.doFinal(base.toString().getBytes(
					ENC))), ENC).trim();
		}

		protected String getCurrentWeight(){		
			return weight.get(0);
		}

		protected String getGoalWeight(){
			return weight.get(1);
		}
	}
	
	public class GetWeight extends AsyncTask<String[], Void, String[]>{

		@Override
		protected String[] doInBackground(String[]... params) {
			try{
				List<NameValuePair> qparams = new ArrayList<NameValuePair>();
				// These params should be ordered alphabetically

				qparams.add(new BasicNameValuePair("current_weight_kg", params[0].toString()));
				qparams.add(new BasicNameValuePair("method", "weight.update"));
				qparams.add(new BasicNameValuePair("oauth_consumer_key", key));
				qparams.add(new BasicNameValuePair("oauth_nonce", "" + (int) (Math.random() * 100000000)));
				qparams.add(new BasicNameValuePair("oauth_signature_method", "HMAC-SHA1"));
				qparams.add(new BasicNameValuePair("oauth_timestamp", "" + (System.currentTimeMillis())));
				qparams.add(new BasicNameValuePair("oauth_token", params[1].toString()));
				qparams.add(new BasicNameValuePair("oauth_version", "1.0"));

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
				WeightTrackerXMLHandler handler = new WeightTrackerXMLHandler();
				xr.setContentHandler(handler);
				xr.parse(new InputSource(website.openStream()));
				String[] information = new String[2];
				information[0] = handler.getCurrentWeightInformation();
				return information;
			}catch (Exception e){
				e.printStackTrace();
			}
			return null;
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
			byte[] keyBytes = (secret + "&" + tSecret).getBytes(ENC);

			SecretKey key = new SecretKeySpec(keyBytes, HMAC_SHA1);

			Mac mac = Mac.getInstance(HMAC_SHA1);
			mac.init(key);

			// encode it, base64 it, change it to string and return.
			return new String(base64.encode(mac.doFinal(base.toString().getBytes(
					ENC))), ENC).trim();
		}

		protected String getCurrentWeight(){		
			return weight.get(0);
		}

		protected String getGoalWeight(){
			return weight.get(1);
		}
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
