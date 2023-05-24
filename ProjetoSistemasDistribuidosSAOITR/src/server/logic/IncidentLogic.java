package server.logic;

import java.io.IOException;
import java.sql.SQLException;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import server.entities.Incident;
import server.logic.validation.IncidentValidation;
import server.service.IncidentService;

public class IncidentLogic {
	
	private Incident incident;
	private JsonObject json;
	private IncidentValidation incidentValidation;
	
	private JsonArray incidentList;
	
	public IncidentLogic(JsonObject json) {
		this.json = json;
		this.incidentValidation = new IncidentValidation();
	}
	
	public String incidentReport() {
		this.incident = new Incident();
		
		try {
			if(json.get("id_usuario") != null && json.get("data") != null && json.get("rodovia") != null && json.get("km") != null && json.get("tipo_incidente") != null) {
				incident.setId_user(json.get("id_usuario").getAsInt());
				incident.setDate(json.get("data").getAsString());
				incident.setHighway(json.get("rodovia").getAsString());
				incident.setKm(json.get("km").getAsInt());
				incident.setIncident_type(json.get("tipo_incidente").getAsInt());
				
				if(incidentValidation.validateReportIncident(this.incident)) {
					new IncidentService().incidentReport(incident);
					return createResultJson(4, true);
				}else {
					System.out.println("Erro reportar incidente - Erro de validação do incidente");
					return createResultJson(4, false);
				}
			}else {
				System.out.println("Erro reportar incidente - Erro com null, o json pegou null em algum campo enviado");
				return createResultJson(4, false);
			}
		}catch (SQLException e) {
			System.out.println("Erro reportar incidente - Erro de SQL: " + e.getMessage());
			return createResultJson(4, false);
		} catch (IOException e) {
			System.out.println("Erro reportar incidente - Erro de IOException: " + e.getMessage());
			return createResultJson(4, false);
		}catch(Exception e) {
			System.out.println("Erro reportar incidente - Erro de exceção: " + e.getMessage());
			return createResultJson(4, false);			
		}
	}
	
	public String searchIncidents() {
		this.incident = new Incident();
		try {
			if(json.get("data") != null && json.get("rodovia") != null && json.get("periodo") != null) {
				
				incident.setDate(json.get("data").getAsString());
				incident.setHighway(json.get("rodovia").getAsString());
				int period = json.get("periodo").getAsInt();
				
				String initialLane = null;
				String finalLane = null;
				
				if(json.get("faixa_km") != null && !json.get("faixa_km").isJsonNull()) {// Não quer funcionar
					String[] lanes = json.get("faixa_km").getAsString().split("-");
					initialLane = lanes[0];
					finalLane = lanes[1];
				}
				String lanesRange = (json.get("faixa_km") != null && !json.get("faixa_km").isJsonNull()) ? json.get("faixa_km").toString() : null;
				if(incidentValidation.validateShowIncidentsList(this.incident, lanesRange)) {
					
					incidentList = new IncidentService().searchIncidents(incident.getHighway(), incident.getDate(), initialLane, finalLane, period);
					return createResultJson(5, true);
				}else {
					System.out.println("Erro solicitar lista de incidentes - Erro de validação");
					return createResultJson(5, false);
				}
			}else {
				System.out.println("Erro solicitar lista de incidentes - Erro com null, o json pegou null em algum campo enviado");
				return createResultJson(5, false);	
			}
		}catch (SQLException e) {
			System.out.println("Erro solicitar lista de incidentes - Erro de SQL: " + e.getMessage());
			return createResultJson(5, false);
		} catch (IOException e) {
			System.out.println("Erro solicitar lista de incidentes - Erro de IOException: " + e.getMessage());
			return createResultJson(5, false);
		}catch(Exception e) {
			System.out.println("Erro solicitar lista de incidentes - Erro de exceção: " + e.getMessage());
			return createResultJson(5, false);			
		}
	}
	
	public String searchMyIncidents() {
		this.incident = new Incident();
		try {
			if(json.get("id_usuario") != null && json.get("token") != null) {
				
				incidentList = new IncidentService().searchMyIncidents(json.get("token").getAsString(), json.get("id_usuario").getAsInt());
				return createResultJson(6, true);
				
			}else {
				System.out.println("Erro solicitar lista de meus incidentes - Erro com null, o json pegou null em algum campo enviado");
				return createResultJson(6, false);
			}
		}catch (SQLException e) {
			System.out.println("Erro solicitar lista de meus incidentes - Erro de SQL: " + e.getMessage());
			return createResultJson(6, false);
		} catch (IOException e) {
			System.out.println("Erro solicitar lista de meus incidentes - Erro de IOException: " + e.getMessage());
			return createResultJson(6, false);
		}catch(Exception e) {
			System.out.println("Erro solicitar lista de meus incidentes - Erro de exceção: " + e.getMessage());
			return createResultJson(6, false);			
		}
	}
	
	public String deleteIncident(){
		this.incident = new Incident();
		try {
			if(json.get("id_usuario") != null && json.get("token") != null && json.get("id_incidente") != null) {
				
				boolean hasItemDeleted = false;

				hasItemDeleted = new IncidentService().deleteIncident(json.get("token").getAsString(), json.get("id_usuario").getAsInt(), json.get("id_incidente").getAsInt());
				if(hasItemDeleted) {
					System.out.println("Incidente deletado com sucesso.");
					return createResultJson(7, true);
				}
				else {
					System.out.println("Erro deletar incidente - Incidente não encontrado.");
					return createResultJson(7, false);
				}

			}else {
				System.out.println("Erro solicitar lista de incidentes - Erro com null, o json pegou null em algum campo enviado");
				return createResultJson(7, false);	
			}
		}catch (SQLException e) {
			System.out.println("Erro deletar incidente - Erro de SQL: " + e.getMessage());
			return createResultJson(7, false);
		} catch (IOException e) {
			System.out.println("Erro deletar incidente - Erro de IOException: " + e.getMessage());
			return createResultJson(7, false);
		}catch(Exception e) {
			System.out.println("Erro deletar incidente - Erro de exceção: " + e.getMessage());
			return createResultJson(7, false);			
		}
	}
	
	public String editIncident() {
		this.incident = new Incident();
		try {
			if(json.get("id_usuario") != null && json.get("token") != null && json.get("id_incidente") != null && json.get("data") != null && json.get("km") != null&& json.get("rodovia") != null&& json.get("tipo_incidente") != null) {
				
				boolean hasItemEdited = false;
				
				hasItemEdited = new IncidentService().editIncident(json.get("token").getAsString(), json.get("id_usuario").getAsInt(), json.get("id_incidente").getAsInt(), json.get("data").getAsString(), json.get("rodovia").getAsString(), json.get("km").getAsInt(), json.get("tipo_incidente").getAsInt());
				if(hasItemEdited) {
					System.out.println("Incidente editado com sucesso.");
					return createResultJson(10, true);
				}else {
					System.out.println("Erro editar incidente - Incidente não encontrado.");
					return createResultJson(10, false);
				}
			}else {
				System.out.println("Erro solicitar lista de incidentes - Erro com null, o json pegou null em algum campo enviado");
				return createResultJson(10, false);
			}
		}catch (SQLException e) {
			System.out.println("Erro solicitar lista de incidentes - Erro de SQL: " + e.getMessage());
			return createResultJson(10, false);
		} catch (IOException e) {
			System.out.println("Erro editar incidente - Erro de IOException: " + e.getMessage());
			return createResultJson(10, false);
		}catch(Exception e) {
			System.out.println("Erro solicitar lista de incidentes - Erro de exceção: " + e.getMessage());
			return createResultJson(10, false);			
		}
	}
	
	private String createResultJson(int idOperacao, boolean correct) {
		
		JsonObject json = new JsonObject();
		
		switch(idOperacao) {
			case 4: {
				System.out.println("Operação de cadastro de incidente");
				if(correct) {
					json.addProperty("codigo", 200);
				}else {
					json.addProperty("codigo", 500);
					json.addProperty("mensagem", "Houve um erro de validação no cadastro de incidente.");
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
			case 6: {
				System.out.println("Operação de solicitação de lista de incidentes do usuário");
				if(correct) {
					json.addProperty("codigo", 200);
					json.add("lista_incidentes", incidentList);
				}else {
					json.addProperty("codigo", 500);
					json.addProperty("mensagem", "Houve um erro de buscar incidente do usuário.");
				}
				return json.toString();
			}
			case 7: {
				System.out.println("Operação de deletar incidente");
				if(correct) {
					json.addProperty("codigo", 200);
				}else {
					json.addProperty("codigo", 500);
					json.addProperty("mensagem", "Houve um erro ao tentar deletar incidente.");
				}
				return json.toString();
			}
			case 10: {
				System.out.println("Operação de editar incidente");
				if(correct) {
					json.addProperty("codigo", 200);
				}else {
					json.addProperty("codigo", 500);
					json.addProperty("mensagem", "Houve um erro ao tentar editar incidente.");
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
