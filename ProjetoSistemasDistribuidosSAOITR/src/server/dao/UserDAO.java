package server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
	
	public void update(User user) {
		
	}
	
	public int delete(int id_usuario) {
		
		return 0;
	}
	
	public User searchLogin(String email, String password) throws SQLException {
		
		PreparedStatement st = null;
		PreparedStatement st2 = null;
		ResultSet rs = null;
		
		try {
			st2 = conn.prepareStatement("update user set token = ? where email = ? and password = ?");
			st2.setString(1, "token123");
			st2.setString(2, email);
			st2.setString(3, password);
			
			st2.executeUpdate();
			
			st = conn.prepareStatement("select * from user where email = ? and password = ?");
			st.setString(1, email);
			st.setString(2, password);
			
			rs = st.executeQuery();
			
			if(rs.next()) {
				User user = new User();
				
				user.setIdUsuario(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setToken(rs.getString("token"));
				
				return user;
			}
			
		} finally {
			Database.endStatement(st);
			Database.finalizarResultSet(rs);
			Database.disconnect();
		}
		
		return null;
	}
	
	public int logout(int id_usuario) {
		
		return 0;
	}
}
