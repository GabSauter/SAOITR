package server.logic;

import java.io.IOException;
import java.sql.SQLException;
import com.google.gson.JsonObject;
import server.entities.User;
import server.service.UserService;

public class UserLogic {

	private User user;
	private JsonObject json;
	
	public UserLogic(JsonObject json) {
		this.json = json;
	}
	
	public String userRegister() throws SQLException, IOException {
		this.user = new User();
		
		user.setName(json.get("nome").getAsString());
		user.setEmail(json.get("email").getAsString());
		user.setPassword(json.get("senha").getAsString());
		user.setToken("");
		
		if(registerValidation(this.user)) {
        	new UserService().register(user);
        	return createResultJson(1, true);
		}else {
			return createResultJson(1, false);
		}
		
	}
	
	public String userLogin(String email, String password) throws SQLException, IOException {
		
		this.user = new User();
		user.setEmail(email);
		user.setPassword(password);
		
		if(loginValidation(user)) {
			this.user = new UserService().searchLogin(email, password);
			if(user == null) {
				return createResultJson(3, false);
			}else {
				return createResultJson(3, true);
			}
		}
			
		return createResultJson(3, false);
	}
	
	private boolean loginValidation(User user) {
		if(user.getEmail().length() < 16 || user.getEmail().length() > 50 ||
		   user.getPassword().length() < 8 || user.getPassword().length() > 32 || // Precisa ver aqui porque vai ser um hash
		   !user.getEmail().contains("@"))
			return false;
		
		return true;
	}
	
	private boolean registerValidation(User user) {
		if(user.getName().length() < 3 || user.getName().length() > 32 ||
		   user.getEmail().length() < 16 || user.getEmail().length() > 50 ||
		   user.getPassword().length() < 8 || user.getPassword().length() > 32 || // Precisa ver aqui porque vai ser um hash
		   !user.getEmail().contains("@")
		   /*Verificar no banco de dados se ja tem um email igual*/)
			return false;
		
		return true;
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
			default: {
				json.addProperty("codigo", 500);
				json.addProperty("mensagem", "Houve um erro, id da operação enviado inválido. id: " + idOperacao);
				return json.toString();
			}
		}
	}
}
