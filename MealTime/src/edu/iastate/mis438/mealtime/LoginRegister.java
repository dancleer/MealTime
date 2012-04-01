package edu.iastate.mis438.mealtime;

import java.io.File;
import java.io.FileOutputStream;
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
import android.widget.TextView;

public class LoginRegister extends Activity implements OnClickListener{

	TextView tv;
	Button b;

	ArrayList<String> tokenNSecret = new ArrayList<String>();

	private static String key = "9a6ae1fceea24f2894fba9311bfe20db";
	private static String secret = "549aeee4d7ec46198bd88e89986b38a1";
	private static final String HMAC_SHA1 = "HmacSHA1";
	private static final String ENC = "UTF-8";
	private static Base64 base64 = new Base64();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		b = (Button)findViewById(R.id.bGetStarted);  
		b.setOnClickListener(this);
	}


	public void onClick(View v) {
		// TODO Auto-generated method stub

		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		if(prefs.getBoolean("firstTime", true)) {
			SharedPreferences.Editor editor = prefs.edit();

			try {
				CreateToken task = new CreateToken();
				task.execute("");
				editor.putBoolean("firstTime", true);
				editor.putString("token", task.getToken());
				editor.putString("tokenSecret", task.getTokenSecret());
				editor.commit();
			} catch (Exception e) {
				e.printStackTrace();
			}

			tv = (TextView)findViewById(R.id.tvLoginMessage);

			tv.setText("Thank you!");
			b.setEnabled(false);
		}else{
			b.setEnabled(false);
			tv = (TextView)findViewById(R.id.tvLoginMessage);
			tv.setText("You've already started!");
		}
	}

	public String getToken(){
		return tokenNSecret.get(0);
	}

	public String getSecret(){
		return tokenNSecret.get(1);
	}

	public class CreateToken extends AsyncTask<String, Void, String[]>{

		@Override
		protected String[] doInBackground(String... params) {
			//			Void[] query = params;
			//			Void searchTerm = query[0];
			try{
				List<NameValuePair> qparams = new ArrayList<NameValuePair>();
				// These params should ordered in key

				qparams.add(new BasicNameValuePair("method", "profile.create"));
				qparams.add(new BasicNameValuePair("oauth_consumer_key", key));
				qparams.add(new BasicNameValuePair("oauth_nonce", "" + (int) (Math.random() * 100000000)));
				qparams.add(new BasicNameValuePair("oauth_signature_method", "HMAC-SHA1"));
				qparams.add(new BasicNameValuePair("oauth_timestamp", "" + (System.currentTimeMillis())));
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
				LoginRegisterXMLHandler handler = new LoginRegisterXMLHandler();
				xr.setContentHandler(handler);
				xr.parse(new InputSource(website.openStream()));
				String[] information = new String[2];
				information[0] = handler.getTokenInformation();
				information[1] = handler.getTokenSecretInformation();
				return information;
			}catch (Exception e){
				tv.setText("error");
			}

			return null;
		}

		@Override
		protected void onPostExecute(String[] result) {
//			SharedPreferences prefs1 = PreferenceManager.getDefaultSharedPreferences(LoginRegister.this);
//			SharedPreferences.Editor editor = prefs1.edit();
//			editor.putString("token", tokenNSecret.get(0));
//			editor.putString("tokenSecret", tokenNSecret.get(1));
//			editor.commit();
			tokenNSecret.add(result[0]);
			tokenNSecret.add(result[1]);

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

		protected String getToken(){		
			return tokenNSecret.get(0);
		}

		protected String getTokenSecret(){
			return tokenNSecret.get(1);
		}
	}
}
