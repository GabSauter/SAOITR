package server.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import server.dao.Database;
import server.dao.IncidentDAO;
import server.entities.Incident;

public class IncidentService {
	
	public void incidentReport(Incident incident) throws SQLException, IOException {
		Connection conn = Database.connect();
		new IncidentDAO(conn).incidentReport(incident);
	}
}
