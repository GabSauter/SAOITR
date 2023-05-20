package server.logic.validation;

import java.io.IOException;
import java.sql.SQLException;

import server.entities.User;
import server.service.UserService;

public class UserValidation {
	
	public boolean loginValidation(User user) {
		if(user.getEmail().length() < 16 || user.getEmail().length() > 50 ||
		   user.getPassword().length() < 8 || user.getPassword().length() > 32 ||
		   !user.getEmail().contains("@"))
			return false;
		
		return true;
	}
	
	public boolean registerValidation(User user) throws SQLException, IOException {
		if(user.getName().length() < 3 || user.getName().length() > 32 ||
		   user.getEmail().length() < 16 || user.getEmail().length() > 50 ||
		   user.getPassword().length() < 8 || user.getPassword().length() > 255 ||
		   !user.getEmail().contains("@") ||
		   user.getName().matches("[0-9]+")||
		   new UserService().emailAlreadyExists(user.getEmail()))
			return false;
		
		return true;
	}
	
	public boolean updateRegisterValidation(User user) throws SQLException, IOException {
		if(user.getName().length() < 3 || user.getName().length() > 32 ||
		   user.getEmail().length() < 16 || user.getEmail().length() > 50 ||
		   user.getPassword().length() < 8 || user.getPassword().length() > 255 ||
		   !user.getEmail().contains("@") ||
		   user.getName().matches("[0-9]+")||
		   new UserService().emailAlreadyExists(user.getEmail()) || // Precisa ver aqui caso o usuario deseja manter o mesmo email
		   !new UserService().isLoggedIn(user.getIdUsuario(), user.getToken()))
			return false;
		
		return true;
	}
	
	public boolean logoutValidation(User user) throws SQLException, IOException {
		if(user.getToken().length() < 16 || user.getToken().length() > 36)
			return false;
		
		return true;
	}
}
