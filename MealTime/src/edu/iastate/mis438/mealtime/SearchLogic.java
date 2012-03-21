package edu.iastate.mis438.mealtime;

import android.app.Activity;
import android.os.Bundle;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;




public class SearchLogic extends Activity {
    /** Called when the activity is first created. */
	
    private static String key = "9a6ae1fceea24f2894fba9311bfe20db";
    private static String secret = "549aeee4d7ec46198bd88e89986b38a1";
    private static final String HMAC_SHA1 = "HmacSHA1";
    private static final String ENC = "UTF-8";
    private static Base64 base64 = new Base64();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

        /**
         * 
         * @param url
         *            the url for "request_token" URLEncoded.
         * @param params
         *            parameters string, URLEncoded.
         * @return
         * @throws UnsupportedEncodingException
         * @throws NoSuchAlgorithmException
         * @throws InvalidKeyException
         */
        private static String getSignature(String url, String params)
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

        /**
         * @param args
         * @throws IOException
         * @throws ClientProtocolException
         * @throws URISyntaxException
         * @throws NoSuchAlgorithmException
         * @throws InvalidKeyException
         */
        public static void main(String[] args) throws ClientProtocolException,
                IOException, URISyntaxException, InvalidKeyException,
                NoSuchAlgorithmException {

            HttpClient httpclient = new DefaultHttpClient();
            List<NameValuePair> qparams = new ArrayList<NameValuePair>();
            // These params should ordered in key
            
            qparams.add(new BasicNameValuePair("method", "foods.search"));
            qparams.add(new BasicNameValuePair("oauth_consumer_key", key));
            qparams.add(new BasicNameValuePair("oauth_nonce", "" + (int) (Math.random() * 100000000)));
            qparams.add(new BasicNameValuePair("oauth_signature_method", "HMAC-SHA1"));
            qparams.add(new BasicNameValuePair("oauth_timestamp", "" + (System.currentTimeMillis())));
            qparams.add(new BasicNameValuePair("oauth_version", "1.0"));
            qparams.add(new BasicNameValuePair("search_expression", "beer"));
            
            // generate the oauth_signature
            String signature = getSignature(URLEncoder.encode(
                    "http://platform.fatsecret.com/rest/server.api", ENC),
                    URLEncoder.encode(URLEncodedUtils.format(qparams, ENC), ENC));

            // add it to params list
            qparams.add(new BasicNameValuePair("oauth_signature", signature));

            // generate URI which lead to access_token and token_secret.
            URI uri = URIUtils.createURI("http", "platform.fatsecret.com", -1, "/rest/server.api", URLEncodedUtils.format(qparams, ENC), null);


            HttpGet httpget = new HttpGet(uri);


            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                int len;
                byte[] tmp = new byte[2048];
                while ((len = instream.read(tmp)) != -1) {
                    System.out.println(new String(tmp, 0, len, ENC));
                }
            }
        }

    }
