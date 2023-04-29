package client.logic;

import com.google.gson.JsonObject;

public class User {
	
	private String name;
	private String email;
	private String password;
	
	private String token;
	private int id_usuario;
	private boolean estaLogado;
	
	public String login(String email, String password) {
		JsonObject json = new JsonObject();
		
		this.email = email;
		this.password = password;
		
		json.addProperty("id_operacao", 1);
		json.addProperty("email", this.email);
		json.addProperty("password", this.password);
		
		return json.toString();
	}
	
	public String cadastrar(String name, String email, String password) {
		JsonObject json = new JsonObject();
		
		this.name = name;
		this.email = email;
		this.password = password;
		
		json.addProperty("id_operacao", 2);
		json.addProperty("nome", this.name);
		json.addProperty("email", this.email);
		json.addProperty("password", this.password);
		
		return json.toString();
	}
	
	public String logout() {
		JsonObject json = new JsonObject();
		
		json.addProperty("id_operacao", 9);
		json.addProperty("token", this.token);
		json.addProperty("id_usuario", this.id_usuario);
		
		return json.toString();
	}
}
