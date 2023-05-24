package server.entities;

public class Incident {
	private int id_incident;
	private int id_user;
	private String token;
	private String date;
	private String highway;
	private int km;
	private int incident_type;
	
	public int getId_incident() {
		return id_incident;
	}
	public void setId_incident(int id_incident) {
		this.id_incident = id_incident;
	}
	public int getId_user() {
		return id_user;
	}
	public void setId_user(int id_user) {
		this.id_user = id_user;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getHighway() {
		return highway;
	}
	public void setHighway(String highway) {
		this.highway = highway;
	}
	public int getKm() {
		return km;
	}
	public void setKm(int km) {
		this.km = km;
	}
	public int getIncident_type() {
		return incident_type;
	}
	public void setIncident_type(int incident_type) {
		this.incident_type = incident_type;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	
}
