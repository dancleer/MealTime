package edu.iastate.mis438.mealtime;

public class LoginRegisterData {

	String token = null;
	String tokenSecret = null;

	public void setToken(String tok) {
		token = tok;
	}
	public void setTokenSecret(String tokSec) {
		tokenSecret = tokSec;
	}
	public String tokenToString(){
		return token;
	}
	public String tokenSecretToString(){
		return tokenSecret;
	}
}

