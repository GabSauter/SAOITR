package client;

import org.mindrot.jbcrypt.BCrypt;

public class JBCrypt {

	private String hashedPassword;
	
	public JBCrypt(String password) {
		this.hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
	}

	public String getHashedPassword() {
		return hashedPassword;
	}
	
	
	
}
