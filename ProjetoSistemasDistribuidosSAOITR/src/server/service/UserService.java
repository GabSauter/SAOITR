package server.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

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

	public boolean isLoggedIn(int idUsuario) throws SQLException, IOException {
		Connection conn = Database.connect();
		return new UserDAO(conn).isLoggedIn(idUsuario);
	}

	public User updateRegister(User user) throws SQLException, IOException {
		Connection conn = Database.connect();
		return new UserDAO(conn).updateRegister(user);
	}

	public void deleteUserAccount(String token, int id_usuario, String email, String password) throws SQLException, IOException {
		Connection conn = Database.connect();
		new UserDAO(conn).deleteUserAccount(token, id_usuario, email, password);
	}
}
