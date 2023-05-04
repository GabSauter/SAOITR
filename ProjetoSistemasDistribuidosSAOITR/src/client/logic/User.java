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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}

	public boolean isEstaLogado() {
		return estaLogado;
	}

	public void setEstaLogado(boolean estaLogado) {
		this.estaLogado = estaLogado;
	}
}
