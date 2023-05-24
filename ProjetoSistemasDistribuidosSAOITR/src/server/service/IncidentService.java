package server.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.google.gson.JsonArray;

import server.dao.Database;
import server.dao.IncidentDAO;
import server.entities.Incident;

public class IncidentService {
	
	public void incidentReport(Incident incident) throws SQLException, IOException {
		Connection conn = Database.connect();
		new IncidentDAO(conn).incidentReport(incident);
	}
	
	public JsonArray searchIncidents(String highway, String date, String initialLane, String finalLane, int period) throws SQLException, IOException {
		Connection conn = Database.connect();
		return new IncidentDAO(conn).searchIncidents(highway, date, initialLane, finalLane, period);
	}
	
	public JsonArray searchMyIncidents(int user_id) throws SQLException, IOException {
		Connection conn = Database.connect();
		return new IncidentDAO(conn).searchMyIncidents(user_id);
	}
	
	public boolean deleteIncident(int user_id, int incident_id) throws SQLException, IOException {
		Connection conn = Database.connect();
		return new IncidentDAO(conn).deleteIncident(user_id, incident_id);
	}
	
	public boolean editIncident(int user_id, int incident_id, String data, String highway, int km, int incident_type) throws SQLException, IOException {
		Connection conn = Database.connect();
		return new IncidentDAO(conn).editIncident(user_id, incident_id, data, highway, km, incident_type);
	}
}
