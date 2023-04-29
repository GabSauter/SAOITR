package server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import server.entities.User;

public class UserDAO {
	
	private Connection conn;
	
	public UserDAO(Connection conn) {
		this.conn = conn;
	}
	
	public void register(User user) throws SQLException {
		PreparedStatement st = null;
		
		try {
			
			st = conn.prepareStatement("insert into user (name, email, password, token) values(?,?,?,?)");
			
			st.setString(1, user.getName());
			st.setString(2, user.getEmail());
			st.setString(3, user.getPassword());
			st.setString(4, user.getToken());
			
			st.executeUpdate();
		} finally {
			Database.endStatement(st);
			Database.disconnect();
		}
	}
	
	public List<User> searchAll() {

		return null;
	}
	
	public void updater(User user) {
		
	}
	
	public int delete(int id_usuario) {
		
		return 0;
	}
	
	public int logout(int id_usuario) {
		
		return 0;
	}
}
