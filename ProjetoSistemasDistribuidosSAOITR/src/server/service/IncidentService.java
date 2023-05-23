package server.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.google.gson.JsonArray;

import server.dao.Database;
import server.dao.IncidentDAO;
import server.entities.Incident;

public class IncidentService {
	
	//IO exception é erro no Database.connect(), e o SQL é erro no SQL
	
	public void incidentReport(Incident incident) throws SQLException, IOException {
		Connection conn = Database.connect();
		new IncidentDAO(conn).incidentReport(incident);
	}
	
	public JsonArray searchIncidents(String highway, String date, String initialLane, String finalLane, int period) throws SQLException, IOException {
		Connection conn = Database.connect();
		return new IncidentDAO(conn).searchIncidents(highway, date, initialLane, finalLane, period);
	}
	
	public JsonArray searchMyIncidents(String token, int user_id) throws SQLException, IOException {
		Connection conn = Database.connect();
		return new IncidentDAO(conn).searchMyIncidents(token, user_id);
	}
	
	public boolean deleteIncident(String token, int user_id, int incident_id) throws SQLException, IOException {
		Connection conn = Database.connect();
		return new IncidentDAO(conn).deleteIncident(token, user_id, incident_id);
	}
	
	public boolean editIncident(String token, int user_id, int incident_id, String data, String highway, int km, int incident_type) throws SQLException, IOException {
		Connection conn = Database.connect();
		return new IncidentDAO(conn).editIncident(token, user_id, incident_id, data, highway, km, incident_type);
	}
}
