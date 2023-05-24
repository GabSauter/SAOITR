package server.logic.validation;

import java.io.IOException;
import java.sql.SQLException;

import server.entities.User;
import server.service.UserService;

public class UserValidation {
	
	public boolean loginValidation(User user) {
	    boolean isValid = true;
	    if (user.getEmail().length() < 16 || user.getEmail().length() > 50) {
	        System.out.println("Erro: O tamanho do email deve estar entre 16 e 50 caracteres.");
	        isValid = false;
	    }
	    if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
	        System.out.println("Erro: O tamanho da senha deve estar entre 8 e 32 caracteres.");
	        isValid = false;
	    }
	    if (!user.getEmail().contains("@")) {
	        System.out.println("Erro: Formato de email inválido.");
	        isValid = false;
	    }
	    if (!isValid) {
	        System.out.println("Erro durante a validação do login.");
	    }

	    return isValid;
	}
	
	public boolean registerValidation(User user) throws SQLException, IOException {
	    boolean isValid = true;

	    if (user.getName().length() < 3 || user.getName().length() > 32) {
	        System.out.println("Erro: O tamanho do nome deve estar entre 3 e 32 caracteres.");
	        isValid = false;
	    }
	    if (user.getEmail().length() < 16 || user.getEmail().length() > 50) {
	        System.out.println("Erro: O tamanho do email deve estar entre 16 e 50 caracteres.");
	        isValid = false;
	    }
	    if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
	        System.out.println("Erro: O tamanho da senha deve estar entre 8 e 32 caracteres.");
	        isValid = false;
	    }
	    if (!user.getEmail().contains("@")) {
	        System.out.println("Erro: Formato de email inválido.");
	        isValid = false;
	    }
	    if (user.getName().matches(".*\\d.*")) {
	        System.out.println("Erro: O nome não pode conter números.");
	        isValid = false;
	    }
	    if (new UserService().emailAlreadyExists(user.getEmail())) {
	        System.out.println("Erro: O email já está em uso.");
	        isValid = false;
	    }
	    if (!isValid) {
	        System.out.println("Erro durante a validação do registro.");
	    }
	    return isValid;
	}
	
	public boolean updateRegisterValidation(User user) throws SQLException, IOException {
	    boolean isValid = true;
	    if (user.getName().length() < 3 || user.getName().length() > 32) {
	        System.out.println("Erro: O tamanho do nome deve estar entre 3 e 32 caracteres.");
	        isValid = false;
	    }
	    if (user.getEmail().length() < 16 || user.getEmail().length() > 50) {
	        System.out.println("Erro: O tamanho do email deve estar entre 16 e 50 caracteres.");
	        isValid = false;
	    }
	    if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
	        System.out.println("Erro: O tamanho da senha deve estar entre 8 e 32 caracteres.");
	        isValid = false;
	    }
	    if (!user.getEmail().contains("@")) {
	        System.out.println("Erro: Formato de email inválido.");
	        isValid = false;
	    }
	    if (user.getName().matches(".*\\d.*")) {
	        System.out.println("Erro: O nome não pode conter números.");
	        isValid = false;
	    }
	    if (new UserService().emailAlreadyExists(user.getEmail())) {
	        System.out.println("Erro: O email já está em uso.");
	        isValid = false;
	    }
	    if (!new UserService().isLoggedIn(user.getIdUsuario(), user.getToken())) {
	        System.out.println("Erro: O usuário não está logado ou sua conta logou denovo, logue denovo para recuperar o token.");
	        isValid = false;
	    }
	    if (!isValid) {
	        System.out.println("Erro durante a validação da atualização do registro.");
	    }
	    return isValid;
	}
	
	public boolean logoutValidation(User user) throws SQLException, IOException {
		if(user.getToken().length() < 16 || user.getToken().length() > 36) {
			System.out.println("Erro durante a validação do logout: O tamanho do token deve estar entre 16 e 36 caracteres.");
			return false;
		}
		
		return true;
	}
}
