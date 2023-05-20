package server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import server.entities.Incident;

public class IncidentDAO {
	
	private Connection conn;
	
	public IncidentDAO(Connection conn) {
		this.conn = conn;
	}
	
	public void incidentReport(Incident incident) throws SQLException {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("insert into incident (id_user, date, highway, km, incident_type) values(?,?,?,?,?)");
			
			st.setInt(1, incident.getId_user());
			st.setDate(2, incident.getDate());
			st.setString(3, incident.getHighway());
			st.setInt(4, incident.getKm());
			st.setInt(5, incident.getIncident_type());
			
			st.executeUpdate();
		} finally {
			Database.endStatement(st);
			Database.disconnect();
		}
	}
	
	
}
