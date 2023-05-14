package server.logic;

import java.io.IOException;
import java.sql.SQLException;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import server.entities.User;
import server.logic.validation.UserValidation;
import server.service.UserService;

public class UserLogic {

	private User user;
	private JsonObject json;
	private UserValidation userValidation;
	
	public UserLogic(JsonObject json) {
		this.json = json;
		this.userValidation = new UserValidation();
	}
	
	public String userRegister() throws SQLException, IOException {
		this.user = new User();
		
		try {
			if(json.get("nome") != null && json.get("email") != null && json.get("senha") != null) {
				user.setName(json.get("nome").getAsString());
				user.setEmail(json.get("email").getAsString());
				user.setPassword(json.get("senha").getAsString());
				user.setToken("");
				
				if(userValidation.registerValidation(this.user)) {
		        	new UserService().register(user);
		        	return createResultJson(1, true);
				}else {
					System.out.println("Erro 1 - Erro de validação");
					return createResultJson(1, false);
				}
			}
		}catch(Exception e) {
			System.out.println("Erro 2 - Erro de exceção, ver se o banco de dados está rodando.");
			return createResultJson(11, false);			
		}
		System.out.println("Erro 3 - Erro estranho");
		return createResultJson(1, false);	
	}
	
	public String userLogin() throws SQLException, IOException {
		this.user = new User();
		
		try {
			String email = json.get("email").getAsString();
			String password = json.get("senha").getAsString();
			
			user.setEmail(email);
			user.setPassword(password);
			
			if(userValidation.loginValidation(user)) {
				this.user = new UserService().searchLogin(email, password);
				if(user == null) {
					System.out.println("User null");
					return createResultJson(3, false);
				}else {
					System.out.println("certo");
					return createResultJson(3, true);
				}
			}
			System.out.println("Erro de validação");
			return createResultJson(3, false);
		}catch(Exception e) {
			System.out.println("json null" + json);
			return createResultJson(3, false);
		}
		
	}
	
	public String userLogout() throws SQLException, IOException {
		this.user = new User();
		
		try {
			if(!json.get("token").equals(JsonNull.INSTANCE) && json.get("id_usuario") != null) {
				String token = json.get("token").getAsString();
				int id_usuario = json.get("id_usuario").getAsInt();
				
				this.user = new User();
				user.setToken(token);
				user.setIdUsuario(id_usuario);
				
				if(token.equals("")) // Quer dizer que não esta logado
					return createResultJson(9, false);
				
				if(userValidation.logoutValidation(user)){
					new UserService().logout(token, id_usuario);	
					return createResultJson(9, true);
				}
				return createResultJson(9, false);
			}else {
				return createResultJson(9, false);
			}
		}catch(Exception e) {
			return createResultJson(9, false);			
		}
	}
	
	private String createResultJson(int idOperacao, boolean correct) {
		
		JsonObject json = new JsonObject();
		
		switch(idOperacao) {
			case 1: {
				System.out.println("Operação de cadastro");
				if(correct) {
					json.addProperty("codigo", 200);
				}else {
					json.addProperty("codigo", 500);
					json.addProperty("mensagem", "Houve um erro de validação no cadastro.");
				}
				return json.toString();
			}
			case 11:{
				json.addProperty("codigo", 500);
				json.addProperty("mensagem", "Houve um erro de exceção, talvez o banco de dados não está rodando.");
				
				return json.toString();
			}
			case 3: {
				System.out.println("Operação de login");
				if(correct) {
					json.addProperty("codigo", 200);
					json.addProperty("token", this.user.getToken());
					json.addProperty("id_usuario", this.user.getIdUsuario());
				}else {
					json.addProperty("codigo", 500);
					json.addProperty("mensagem", "Email ou senha incorreto.");
				}
				return json.toString();
			}
			case 9: {
				System.out.println("Operação de logout");
				if(correct) {
					json.addProperty("codigo", 200);
				}else {
					json.addProperty("codigo", 500);
					json.addProperty("mensagem", "Houve um erro durante o logout.");
				}
				return json.toString();
			}
			default: {
				json.addProperty("codigo", 500);
				json.addProperty("mensagem", "Houve um erro, id da operação enviado inválido. id: " + idOperacao);
				return json.toString();
			}
		}
	}
}
