package server.logic;

import java.io.IOException;
import java.sql.SQLException;

import com.google.gson.JsonObject;

import server.entities.Incident;
import server.service.IncidentService;

public class IncidentLogic {
	
	private Incident incident;
	private JsonObject json;
	//private IncidentValidation incidentValidation;
	
	public IncidentLogic(JsonObject json) {
		this.json = json;
		//this.incidentValidation = new IncidentValidation();
	}
	
	public String incidentReport() throws SQLException, IOException {
		this.incident = new Incident();
		
		try {
			//if(json.get("nome") != null && json.get("email") != null && json.get("senha") != null) {
				incident.setId_user(json.get("id_usuario").getAsInt());
				//incident.setDate(json.get("email").getAsString());
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
			return createResultJson(11, false);			
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
			default: {
				json.addProperty("codigo", 500);
				json.addProperty("mensagem", "Houve um erro, id da operação enviado inválido. id: " + idOperacao);
				return json.toString();
			}
		}
	}
}
