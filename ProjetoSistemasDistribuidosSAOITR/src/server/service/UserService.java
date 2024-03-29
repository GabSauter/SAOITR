package server.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import server.dao.Database;
import server.dao.UserDAO;
import server.entities.User;

public class UserService {
	
	public void register(User user) throws SQLException, IOException {
		Connection conn = Database.connect();
		new UserDAO(conn).register(user);
	}
	
	public User searchLogin(String email, String password) throws SQLException, IOException {
		Connection conn = Database.connect();
		return new UserDAO(conn).searchLogin(email, password);
	}
	
	public void logout(String token, int id_usuario) throws SQLException, IOException {
		Connection conn = Database.connect();
		new UserDAO(conn).logout(token, id_usuario);
	}
	
	public boolean emailAlreadyExists(String email) throws SQLException, IOException {
		Connection conn = Database.connect();
		return new UserDAO(conn).emailAlreadyExists(email);
	}

	public boolean isLoggedIn(int idUsuario, String token) throws SQLException, IOException {
		Connection conn = Database.connect();
		return new UserDAO(conn).isLoggedIn(idUsuario, token);
	}

	public User updateRegister(User user) throws SQLException, IOException {
		Connection conn = Database.connect();
		return new UserDAO(conn).updateRegister(user);
	}

	public int deleteUserAccount(String token, int id_usuario, String email, String password) throws SQLException, IOException {
		Connection conn = Database.connect();
		return new UserDAO(conn).deleteUserAccount(token, id_usuario, email, password);
	}
	
	public List<User> getallLoggedIn() throws SQLException, IOException{
		Connection conn = Database.connect();
		return new UserDAO(conn).getallLoggedIn();
	}
}
