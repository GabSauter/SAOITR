package server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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
			st.setString(2, incident.getDate());
			st.setString(3, incident.getHighway());
			st.setInt(4, incident.getKm());
			st.setInt(5, incident.getIncident_type());
			
			st.executeUpdate();
		} finally {
			Database.endStatement(st);
			Database.disconnect();
		}
	}
	
	public JsonArray searchIncidents(String highway, String date, String initialLane, String finalLane, int period) throws SQLException { //String date no formato YYYY-MM-DD HH:MM:SS 
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			
			String periodCondition;
            switch (period) {
                case 1: // Morning
                    periodCondition = " HOUR(date) BETWEEN 6 AND 11 ";
                    break;
                case 2: // Afternoon
                    periodCondition = " HOUR(date) BETWEEN 12 AND 17 ";
                    break;
                case 3: // Nights
                    periodCondition = " HOUR(date) BETWEEN 18 AND 23 ";
                    break;
                case 4: // Dawn
                    periodCondition = " HOUR(date) BETWEEN 0 AND 5 ";
                    break;
                default:
                    throw new IllegalArgumentException("Invalid period value: " + period);
            }
            
			String query = "SELECT * FROM incident WHERE highway = ? AND DATE(date) = ? AND" + periodCondition;

            if (initialLane != null && finalLane != null) {
                query += " AND km BETWEEN ? AND ?";
            }
            
            query += " ORDER BY date";

            st = conn.prepareStatement(query);
            
            st.setString(1, highway);
            st.setString(2, date);
            //st.setInt(3, period);

            if (initialLane != null && finalLane != null) {
                st.setString(3, initialLane);
                st.setString(4, finalLane);
            }

            rs = st.executeQuery();

            JsonArray incidents = new JsonArray();
			
			while(rs.next()) {
				JsonObject incident = new JsonObject();
				
				incident.addProperty("data", rs.getString("date"));
				incident.addProperty("rodovia", rs.getString("highway"));
				incident.addProperty("id_incidente", rs.getInt("id_incident"));
				incident.addProperty("tipo_incidente", rs.getInt("incident_type"));
				incident.addProperty("km", rs.getInt("km"));
				
				incidents.add(incident);
			}
			
			return incidents;
			
		} finally {
			Database.endStatement(st);
			Database.finalizarResultSet(rs);
			Database.disconnect();
		}
	}
	
}
