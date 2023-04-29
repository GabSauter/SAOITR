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
	
	public void searchAll(User user) throws SQLException, IOException {
		Connection conn = Database.connect();
		new UserDAO(conn).searchAll();
	}
	
	public void deleteUser(int id_user) throws SQLException, IOException {
		Connection conn = Database.connect();
		new UserDAO(conn).delete(id_user);
	}
	
	public User searchLogin(String email, String password) throws SQLException, IOException {
		Connection conn = Database.connect();
		return new UserDAO(conn).searchLogin(email, password);
	}
}
