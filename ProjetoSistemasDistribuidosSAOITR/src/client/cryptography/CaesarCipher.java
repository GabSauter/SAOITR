package client.cryptography;

public class CaesarCipher {

	public String encrypt(String pswd) {
    	
    	String encrypt = "";
    	
        for (int i = 0; i < pswd.length(); i++) {
            char c = pswd.charAt(i);
            int asciiValue = (int) c;
            int tamanhoLetra = Character.toString(c).length();
            int novoAsciiValue = asciiValue + tamanhoLetra;
            if (novoAsciiValue > 127) {
                novoAsciiValue = novoAsciiValue - 127 + 32;
            }
            char novoCaractere = (char) novoAsciiValue;
            encrypt += novoCaractere;
        }
        return encrypt;
    }
}
