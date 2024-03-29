package server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
	
	public User searchLogin(String email, String password) throws SQLException {
		
		PreparedStatement st = null;
		PreparedStatement st2 = null;
		ResultSet rs = null;
		
		try {
			st2 = conn.prepareStatement("update user set token = ? where email = ? and password = ?");
			st2.setString(1, UUID.randomUUID().toString().replaceAll("-", "")); //Token sempre da 32 character
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
	
	public void logout(String token, int id_usuario) throws SQLException {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("update user set token = ? where id = ? and token = ?");
			st.setString(1, "");
			st.setInt(2, id_usuario);
			st.setString(3, token);
			
			st.executeUpdate();
		} finally {
			Database.endStatement(st);
			Database.finalizarResultSet(rs);
			Database.disconnect();
		}
	}
	
	public boolean emailAlreadyExists(String email) throws SQLException {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("select * from user where email = ?");
			st.setString(1, email);
			
			rs = st.executeQuery();
			
			if(rs.next()) {
				System.out.println("Email já está em uso");
				return true;
			}
			
		} finally {
			Database.endStatement(st);
			Database.finalizarResultSet(rs);
			Database.disconnect();
		}
		
		return false;
	}

	public boolean isLoggedIn(int idUsuario, String token) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("select * from user where id = ? and token = ?");
			st.setInt(1, idUsuario);
			st.setString(2, token);
			
			rs = st.executeQuery();
			if(rs.next()) {
				if(!token.equals("")) {
					System.out.println("Esta logado");
					return true;
				}
			}
			
		} finally {
			Database.endStatement(st);
			Database.finalizarResultSet(rs);
			Database.disconnect();
		}
		System.out.println("Não esta logado");
		return false;
	}

	public User updateRegister(User user) throws SQLException {
		PreparedStatement st = null;
		PreparedStatement st2 = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("update user set name = ?, email = ?, password = ?, token = ? where id = ?");
			
			st.setString(1, user.getName());
			st.setString(2, user.getEmail());
			st.setString(3, user.getPassword());
			st.setString(4, UUID.randomUUID().toString().replaceAll("-", ""));
			st.setInt(5, user.getIdUsuario());
			
			st.executeUpdate();
			
			st2 = conn.prepareStatement("select * from user where email = ? and password = ?");
			st2.setString(1, user.getEmail());
			st2.setString(2, user.getPassword());
			
			rs = st2.executeQuery();
			
			if(rs.next()) {
				User updatedUser = new User();
				updatedUser.setIdUsuario(rs.getInt("id"));
				updatedUser.setName(rs.getString("name"));
				updatedUser.setEmail(rs.getString("email"));
				updatedUser.setPassword(rs.getString("password"));
				updatedUser.setToken(rs.getString("token"));
				return updatedUser;
			}
			
		} finally {
			Database.endStatement(st);
			Database.endStatement(st2);
			Database.finalizarResultSet(rs);
			Database.disconnect();
		}
		
		return null;
	}

	public int deleteUserAccount(String token, int id_usuario, String email, String password) throws SQLException {
		PreparedStatement st = null;
		int linhasManipuladas = 0;
		try {
			st = conn.prepareStatement("delete from user where email = ? and password = ? and id = ? and token = ?");
			
			st.setString(1, email);
			st.setString(2, password);
			st.setInt(3, id_usuario);
			st.setString(4, token);
			
			linhasManipuladas = st.executeUpdate();
		} finally {
			Database.endStatement(st);
			Database.disconnect();
		}
		return linhasManipuladas;
	}
	
	public List<User> getallLoggedIn() throws SQLException{
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("select * from user where token != ''");
			rs = st.executeQuery();
			
			List<User> loggedInUsers = new ArrayList<User>();
			
			while(rs.next()) {
				User loggedInUser = new User();
				loggedInUser.setIdUsuario(rs.getInt("id"));
				loggedInUser.setName(rs.getString("name"));
				loggedInUser.setEmail(rs.getString("email"));
				loggedInUser.setPassword(rs.getString("password"));
				loggedInUser.setToken(rs.getString("token"));
				loggedInUsers.add(loggedInUser);
			}
			
			return loggedInUsers;
			
		} finally {
			Database.endStatement(st);
			Database.finalizarResultSet(rs);
			Database.disconnect();
		}
	}
}
