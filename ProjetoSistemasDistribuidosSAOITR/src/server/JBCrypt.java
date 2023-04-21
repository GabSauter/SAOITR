package server;

import org.mindrot.jbcrypt.BCrypt;

public class JBCrypt {

	private String password = "qwert";
	private String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

	public String getHashedPassword() {
		return hashedPassword;
	}
	
	public void checkPasswork(String candidate) {
	
		if (BCrypt.checkpw(candidate, hashedPassword))
			System.out.println("It matches");
		else
			System.out.println("It does not match");
	}
}
