package client.logic;

import java.sql.Date;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Incident {
	
	private int id_incident;
	private User user;
	private Date date;
	private String highway;
	private int km;
	private int incident_type;
	
	public String reportIncident(int id_incident, String token, int id_user, Date date, String highway, int km, int incident_type) {
		JsonObject json = new JsonObject();
		
		this.user.setId_usuario(id_user);
		this.user.setToken(token);
		this.date = date;
		this.highway = highway;
		this.km = km;
		this.incident_type = incident_type;
		
		json.addProperty("id_operacao", 4);
		json.addProperty("id_usuario", this.user.getId_usuario());
		json.addProperty("id_usuario", this.user.getToken());
		json.addProperty("date", this.date.toString());
		json.addProperty("rodovia", this.highway);
		json.addProperty("km", this.km);
		json.addProperty("tipo_incidente", this.incident_type);
		
		return json.toString();
	}
	
	public void reportIncidentResponse(String inputLine) {
		Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(inputLine, JsonObject.class);
        int codigo = 0;
        
        if(jsonObject != null) {
        	codigo = jsonObject.get("codigo").getAsInt();
	    	if(codigo == 200) {
	    		System.out.println("Incidente reportado com sucesso");
	    	} else {
	    		System.out.println(jsonObject.get("mensagem").getAsString());
	    	}
        }else {
        	System.out.println("Incidente reportado: JsonObject ta null");
        }
	}
	
	public int getId_incident() {
		return id_incident;
	}
	public void setId_incident(int id_incident) {
		this.id_incident = id_incident;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
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
	
	
}
