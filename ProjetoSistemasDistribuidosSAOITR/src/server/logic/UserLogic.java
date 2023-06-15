package server.logic;

import java.io.IOException;
import java.sql.SQLException;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import server.entities.User;
import server.logic.utils.Tools;
import server.logic.validation.UserValidation;
import server.service.UserService;

public class UserLogic {

	private User user;
	private JsonObject json;
	private UserValidation userValidation;
	
	private String message;
	
	public UserLogic(JsonObject json) {
		this.json = json;
		this.userValidation = new UserValidation();
	}
	
	public String userRegister() {
	    this.user = new User();
	    
	    try {
	        if(json.get("nome") != null && json.get("email") != null && json.get("senha") != null) {
	            user.setName(json.get("nome").getAsString());
	            user.setEmail(json.get("email").getAsString());
	            user.setPassword(json.get("senha").getAsString());
	            user.setToken("");
	            
	            if(userValidation.registerValidation(this.user)) {
	                new UserService().register(user);
	                message = "Usuário cadastrado com sucesso";
	                return new Tools().createResultJson(1, true, message, null, null, null);
	            } else {
	                message = "Erro cadastrar usuário - Erro de validação";
	                System.out.println(message);
	                return new Tools().createResultJson(1, false, message, null, null, null);
	            }
	            
	        } else {
	            message = "Erro cadastrar usuário - Erro com null, o json pegou null em algum campo enviado";
	            System.out.println(message);
	            return new Tools().createResultJson(1, false, message, null, null, null);
	        }
	    } catch (SQLException e) {
	        message = "Erro cadastrar usuário - Erro de SQL: " + e.getMessage();
	        System.out.println(message);
	        return new Tools().createResultJson(1, false, message, null, null, null);
	    } catch (IOException e) {
	        message = "Erro cadastrar usuário - Erro de IOException: " + e.getMessage();
	        System.out.println(message);
	        return new Tools().createResultJson(1, false, message, null, null, null);
	    } catch (Exception e) {
	        message = "Erro cadastrar usuário - Erro de exceção: " + e.getMessage();
	        System.out.println(message);
	        return new Tools().createResultJson(1, false, message, null, null, null);
	    }
	}

	public String userLogin() {
	    this.user = new User();
	    
	    try {
	        if(json.get("email") != null && json.get("senha") != null) {
	            user.setEmail(json.get("email").getAsString());
	            user.setPassword(json.get("senha").getAsString());
	            
	            if(userValidation.loginValidation(user)) {
	                this.user = new UserService().searchLogin(user.getEmail(), user.getPassword());
	                
	                if(user == null) {
	                    message = "Email ou senha errado";
	                    System.out.println(message);
	                    return new Tools().createResultJson(3, false, message, null, null, null);
	                } else {
	                    message = "Login bem-sucedido";
	                    System.out.println(message);
	                    return new Tools().createResultJson(3, true, message, null, null, user);
	                }
	            }
	            message = "Erro logar usuário - Erro de validação";
	            System.out.println(message);
	            return new Tools().createResultJson(3, false, message, null, null, null);
	        } else {
	            message = "Erro logar usuário - Erro com null, o json pegou null em algum campo enviado";
	            System.out.println(message);
	            return new Tools().createResultJson(3, false, message, null, null, null);
	        }
	    } catch (SQLException e) {
	        message = "Erro logar usuário - Erro de SQL: " + e.getMessage();
	        System.out.println(message);
	        return new Tools().createResultJson(3, false, message, null, null, null);
	    } catch (IOException e) {
	        message = "Erro logar usuário - Erro de IOException: " + e.getMessage();
	        System.out.println(message);
	        return new Tools().createResultJson(3, false, message, null, null, null);
	    } catch (Exception e) {
	        message = "Erro logar usuário - Erro de exceção: " + e.getMessage();
	        System.out.println(message);
	        return new Tools().createResultJson(3, false, message, null, null, null);
	    }
	}

	public String userLogout() {
	    try {
	        if(!json.get("token").equals(JsonNull.INSTANCE) && json.get("id_usuario") != null && json.get("token") != null) {
	            String token = json.get("token").getAsString();
	            int id_usuario = json.get("id_usuario").getAsInt();
	            
	            this.user = new User();
	            user.setToken(token);
	            user.setIdUsuario(id_usuario);
	            
	            if(token.equals("")) // Quer dizer que não esta logado
	                return new Tools().createResultJson(1, false, message, null, null, null);
	            
	            if(userValidation.logoutValidation(user)){
	                new UserService().logout(token, id_usuario);
	                message = "Logout bem-sucedido";
	                System.out.println(message);
	                return new Tools().createResultJson(1, true, message, null, null, null);
	            }
	            
	            message = "Erro logout - Erro de validação";
	            System.out.println(message);
	            return new Tools().createResultJson(1, false, message, null, null, null);
	        } else {
	            message = "Erro logout - Erro com null, o json pegou null em algum campo enviado";
	            System.out.println(message);
	            return new Tools().createResultJson(1, false, message, null, null, null);
	        }
	    } catch (SQLException e) {
	        message = "Erro logout - Erro de SQL: " + e.getMessage();
	        System.out.println(message);
	        return new Tools().createResultJson(1, false, message, null, null, null);
	    } catch (IOException e) {
	        message = "Erro logout - Erro de IOException: " + e.getMessage();
	        System.out.println(message);
	        return new Tools().createResultJson(1, false, message, null, null, null);
	    } catch (Exception e) {
	        message = "Erro logout - Erro de exceção: " + e.getMessage();
	        System.out.println(message);
	        return new Tools().createResultJson(1, false, message, null, null, null);
	    }
	}

	public String userUpdateRegister(){
	    this.user = new User();
	    
	    try {
	        if(json.get("nome") != null && json.get("email") != null && json.get("senha") != null && json.get("token") != null && json.get("id_usuario") != null) {
	            user.setName(json.get("nome").getAsString());
	            user.setEmail(json.get("email").getAsString());
	            user.setPassword(json.get("senha").getAsString());
	            user.setToken(json.get("token").getAsString());
	            user.setIdUsuario(json.get("id_usuario").getAsInt());
	            
	            if(userValidation.updateRegisterValidation(this.user)) {
	                this.user = new UserService().updateRegister(user);
	                message = "Cadastro atualizado com sucesso";
	                System.out.println(message);
	                return new Tools().createResultJson(2, true, message, null, null, user);
	            } else {
	                message = "Erro editar cadastro - Erro de validação";
	                System.out.println(message);
	                return new Tools().createResultJson(2, false, message, null, null, null);
	            }
	        }
	        message = "Erro editar cadastro - Erro com null, o json pegou null em algum campo enviado";
	        System.out.println(message);
	        return new Tools().createResultJson(2, false, message, null, null, null);
	    } catch (SQLException e) {
	        message = "Erro editar cadastro - Erro de SQL: " + e.getMessage();
	        System.out.println(message);
	        return new Tools().createResultJson(2, false, message, null, null, null);
	    } catch (IOException e) {
	        message = "Erro editar cadastro - Erro de IOException: " + e.getMessage();
	        System.out.println(message);
	        return new Tools().createResultJson(2, false, message, null, null, null);
	    } catch (Exception e) {
	        message = "Erro editar cadastro - Erro de exceção: " + e.getMessage();
	        System.out.println(message);
	        return new Tools().createResultJson(2, false, message, null, null, null);
	    }
	}

	public String deleteUserAccount() {
	    try {
	        if(json.get("token") != null && json.get("id_usuario") != null && json.get("email") != null && json.get("senha") != null) {
	            
	            this.user = new User();
	            user.setToken(json.get("token").getAsString());
	            user.setIdUsuario(json.get("id_usuario").getAsInt());
	            user.setEmail(json.get("email").getAsString());
	            user.setPassword(json.get("senha").getAsString());
	            
	            if(user.getToken().equals("")) {
	                message = "Erro deletar cadastro - Usuário não está logado";
	                System.out.println(message);
	                return new Tools().createResultJson(1, false, message, null, null, null);
	            }
	            int isManipulated = new UserService().deleteUserAccount(user.getToken(), user.getIdUsuario(), user.getEmail(), user.getPassword());
	            if(isManipulated > 0) {
	            	message = "Cadastro excluído com sucesso";
	            	System.out.println(message);
	            	return new Tools().createResultJson(1, true, message, null, null, null);
	            }
	            message = "Erro deletar cadastro - Erro ao deletar usuário";
	            System.out.println(message);
	            return new Tools().createResultJson(1, false, message, null, null, null);
	        } else {
	            message = "Erro deletar cadastro - Erro com null, o json pegou null em algum campo enviado";
	            System.out.println(message);
	            return new Tools().createResultJson(1, false, message, null, null, null);
	        }
	    } catch (SQLException e) {
	        message = "Erro deletar cadastro - Erro de SQL: " + e.getMessage();
	        System.out.println(message);
	        return new Tools().createResultJson(1, false, message, null, null, null);
	    } catch (IOException e) {
	        message = "Erro deletar cadastro - Erro de IOException: " + e.getMessage();
	        System.out.println(message);
	        return new Tools().createResultJson(1, false, message, null, null, null);
	    } catch (Exception e) {
	        message = "Erro deletar cadastro - Erro de exceção: " + e.getMessage();
	        System.out.println(message);
	        return new Tools().createResultJson(1, false, message, null, null, null);
	    }
	}
	
}
