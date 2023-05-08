package client.cryptography;

public class CaesarCipher {

	public String encrypt(String pswd) {
    	
		int i;
	    int key = pswd.length();
	    String encripted = "";

	    for (i = 0; i < key; i++) {
	        encripted = encripted + (char) (pswd.charAt(i) + key);
	    }

	    return (encripted);
    }
}
    
