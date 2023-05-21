package server.logic;

import java.io.IOException;
import java.sql.SQLException;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import server.entities.Incident;
import server.service.IncidentService;

public class IncidentLogic {
	
	private Incident incident;
	private JsonObject json;
	//private IncidentValidation incidentValidation;
	
	private JsonArray incidentList;
	
	public IncidentLogic(JsonObject json) {
		this.json = json;
		//this.incidentValidation = new IncidentValidation();
	}
	
	public String incidentReport() throws SQLException, IOException {
		this.incident = new Incident();
		
		try {
			//if(json.get("nome") != null && json.get("email") != null && json.get("senha") != null) {
				incident.setId_user(json.get("id_usuario").getAsInt());
				incident.setDate(json.get("data").getAsString());
				incident.setHighway(json.get("rodovia").getAsString());
				incident.setKm(json.get("km").getAsInt());
				incident.setIncident_type(json.get("tipo_incidente").getAsInt());
				
				//if(incidentValidation.registerValidation(this.user)) {
		        	new IncidentService().incidentReport(incident);
		        	return createResultJson(4, true);
				//}else {
				//	System.out.println("Erro 1 - Erro de validação");
				//	return createResultJson(1, false);
				//}
			//}
		}catch(Exception e) {
			System.out.println("Erro 2 - Erro de exceção, ver se o banco de dados está rodando.");
			e.printStackTrace();
			return createResultJson(4, false);			
		}
		//System.out.println("Erro 3 - Erro com null");
		//return createResultJson(1, false);	
	}
	
	public String searchIncidents() throws SQLException, IOException {
		this.incident = new Incident();
		//String highway, String date, String initialLane, String finalLane
		try {
			//if(json.get("nome") != null && json.get("email") != null && json.get("senha") != null) {
				
				incident.setDate(json.get("data").getAsString());
				incident.setHighway(json.get("rodovia").getAsString());
				incident.setIncident_type(json.get("tipo_incident").getAsInt());
				
				String initialLane = null;
				String finalLane = null;
				
				if(json.get("faixa_km") != null) {
					String[] lanes = json.get("faixa_km").getAsString().split("-");
					initialLane = lanes[0];
					finalLane = lanes[1];
				}
				
				//if(incidentValidation.registerValidation(this.user)) {
					incidentList = new IncidentService().searchIncidents(incident.getHighway(), incident.getDate(), initialLane, finalLane, incident.getIncident_type());
		        	return createResultJson(5, true);
				//}else {
				//	System.out.println("Erro 1 - Erro de validação");
				//	return createResultJson(1, false);
				//}
			//}
		}catch(Exception e) {
			System.out.println("Erro 2 - Erro de exceção, ver se o banco de dados está rodando.");
			e.printStackTrace();
			return createResultJson(5, false);			
		}
		//System.out.println("Erro 3 - Erro com null");
		//return createResultJson(1, false);	
	}
	
	private String createResultJson(int idOperacao, boolean correct) {
		
		JsonObject json = new JsonObject();
		
		switch(idOperacao) {
			case 4: {
				System.out.println("Operação de cadastro");
				if(correct) {
					json.addProperty("codigo", 200);
				}else {
					json.addProperty("codigo", 500);
					json.addProperty("mensagem", "Houve um erro de validação no cadastro.");
				}
				return json.toString();
			}
			case 5: {
				System.out.println("Operação de solicitação de lista de incidentes");
				if(correct) {
					json.addProperty("codigo", 200);
					json.add("lista_incidentes", incidentList);
				}else {
					json.addProperty("codigo", 500);
					json.addProperty("mensagem", "Houve um erro de buscar incidente.");
				}
				return json.toString();
			}
			default: {
				json.addProperty("codigo", 500);
				json.addProperty("mensagem", "Houve um erro, id da operação enviado inválido. id: " + idOperacao);
				return json.toString();
			}
		}
	}
}
