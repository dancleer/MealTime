package edu.iastate.mis438.mealtime;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class LoginRegisterXMLHandler extends DefaultHandler{
	
	LoginRegisterData info = new LoginRegisterData();
	boolean token = false;
	boolean tokenSecret = false;
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		if (qName.equalsIgnoreCase("auth_token")){
			token = true;
		}
		if (qName.equalsIgnoreCase("auth_secret")){
			tokenSecret = true;
		}
	}

	@Override
	public void characters(char[] arg0, int arg1, int arg2) throws SAXException {

		if (token){
			String tokenString = new String(arg0, arg1, arg2);
			info.setToken(tokenString);
			token = false;

			}
		if (tokenSecret){
			String tokenSecretString = new String(arg0, arg1, arg2);
			info.setTokenSecret(tokenSecretString);
			tokenSecret = false;

		}
	}

	public String getTokenInformation(){

		return info.tokenToString();
	}

	public String getTokenSecretInformation(){

		return info.tokenSecretToString();
	}
}

