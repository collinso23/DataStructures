package edu.wit.cs.comp2000;

public class Password {
	String salt;
    String hash;
	
	public Password(String s, String h) {
		salt = s;
        hash = h;
	}
	
	public String getSalt() {
		return salt;
	}
	
	public String getHash() { return hash; }
	
	
}
