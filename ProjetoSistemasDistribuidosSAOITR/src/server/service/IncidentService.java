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
	
	public JsonArray searchIncidents(String highway, String date, String initialLane, String finalLane, int incident_type) throws SQLException, IOException {
		Connection conn = Database.connect();
		return new IncidentDAO(conn).searchIncidents(highway, date, initialLane, finalLane, incident_type);
	}
}
