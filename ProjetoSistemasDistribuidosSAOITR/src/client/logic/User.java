package client.logic;

import com.google.gson.Gson;
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
		
		json.addProperty("id_operacao", 3);
		json.addProperty("email", this.email);
		json.addProperty("senha", this.password);
		
		return json.toString();
	}
	
	public void loginResponse(String inputLine) {
		Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(inputLine, JsonObject.class);
        
        if(jsonObject != null) {
        	int codigo = jsonObject.get("codigo").getAsInt();
        	
        	if(codigo == 200) {
        		this.setId_usuario(jsonObject.get("id_usuario").getAsInt());
        		this.setToken(jsonObject.get("token").getAsString());
        		this.setEstaLogado(true);
        	}else {
        		System.out.println(jsonObject.get("mensagem").getAsString());
        	}
        }else {
        	System.out.println("Erro de login: JsonObject ta null");
        }
    	
	}

	public String register(String name, String email, String password) {
		JsonObject json = new JsonObject();
		
		this.name = name;
		this.email = email;
		this.password = password;
		
		json.addProperty("id_operacao", 1);
		json.addProperty("nome", this.name);
		json.addProperty("email", this.email);
		json.addProperty("senha", this.password);
		
		return json.toString();
	}
	
	public void registerResponse(String inputLine) {
		Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(inputLine, JsonObject.class);
        int codigo = 0;
        
        if(jsonObject != null) {
        	codigo = jsonObject.get("codigo").getAsInt();
	    	if(codigo == 200) {
	    		System.out.println("Cadastrado com sucesso");
	    	} else {
	    		System.out.println(jsonObject.get("mensagem").getAsString());
	    	}
        }else {
        	System.out.println("Cadastro: JsonObject ta null");
        }
	}
	
	public String logout() {
		JsonObject json = new JsonObject();
		
		json.addProperty("id_operacao", 9);
		json.addProperty("token", this.token);
		json.addProperty("id_usuario", this.id_usuario);
		
		this.setToken("");
		this.setId_usuario(0);
		
		return json.toString();
	}
	
	public void logoutResponse(String inputLine) {
		Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(inputLine, JsonObject.class);
        int codigo = 0;
        
        if(jsonObject != null) {
        	codigo = jsonObject.get("codigo").getAsInt();
	    	if(codigo == 200) {
	    		System.out.println("Logout feito com sucesso");
	    		this.setEstaLogado(false);
	    	} else {
	    		System.out.println(jsonObject.get("mensagem").getAsString());
	    	}
        }else {
        	System.out.println("Logout: JsonObject ta null");
        }
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
	
	@Override
	public String toString() {
		return "User [name=" + name + ", email=" + email + ", password=" + password + ", token=" + token
				+ ", id_usuario=" + id_usuario + ", estaLogado=" + estaLogado + "]";
	}
}
