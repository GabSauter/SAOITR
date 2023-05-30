package server.logic;

import java.io.IOException;
import java.sql.SQLException;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import server.entities.Incident;
import server.entities.User;
import server.logic.utils.Tools;
import server.logic.validation.IncidentValidation;
import server.service.IncidentService;
import server.service.UserService;

public class IncidentLogic {
	
	private Incident incident;
	private JsonObject json;
	private IncidentValidation incidentValidation;
	
	private JsonArray incidentList;
	private String message;
	
	public IncidentLogic(JsonObject json) {
		this.json = json;
		this.incidentValidation = new IncidentValidation();
	}
	
	public String incidentReport() {
	    this.incident = new Incident();
	    
	    try {
	        if(json.get("id_usuario") != null && json.get("data") != null && json.get("rodovia") != null && json.get("km") != null && json.get("tipo_incidente") != null && json.get("token") != null) {
	            incident.setId_user(json.get("id_usuario").getAsInt());
	            incident.setToken(json.get("token").getAsString());
	            incident.setDate(json.get("data").getAsString());
	            incident.setHighway(json.get("rodovia").getAsString());
	            incident.setKm(json.get("km").getAsInt());
	            incident.setIncident_type(json.get("tipo_incidente").getAsInt());
	            
	            if(incidentValidation.validateReportIncident(this.incident)) {
	                new IncidentService().incidentReport(incident);
	                message = "Incidente reportado com sucesso";
	                System.out.println(message);
	                return new Tools().createResultJson(1, true, message, null, null, null);
	            } else {
	                message = "Erro reportar incidente - Erro de validação do incidente";
	                System.out.println(message);
	                return new Tools().createResultJson(1, false, message, null, null, null);
	            }
	        } else {
	            message = "Erro reportar incidente - Erro com null, o json pegou null em algum campo enviado";
	            System.out.println(message);
	            return new Tools().createResultJson(1, false, message, null, null, null);
	        }
	    } catch (SQLException e) {
	        message = "Erro reportar incidente - Erro de SQL: " + e.getMessage();
	        System.out.println(message);
	        return new Tools().createResultJson(1, false, message, null, null, null);
	    } catch (IOException e) {
	        message = "Erro reportar incidente - Erro de IOException: " + e.getMessage();
	        System.out.println(message);
	        return new Tools().createResultJson(1, false, message, null, null, null);
	    } catch(Exception e) {
	        message = "Erro reportar incidente - Erro de exceção: " + e.getMessage();
	        System.out.println(message);
	        return new Tools().createResultJson(1, false, message, null, null, null);           
	    }
	}

	public String searchIncidents() {
	    this.incident = new Incident();
	    try {
	        if(json.get("data") != null && json.get("rodovia") != null && json.get("periodo") != null) {
	            
	            incident.setDate(json.get("data").getAsString());
	            incident.setHighway(json.get("rodovia").getAsString());
	            int period = json.get("periodo").getAsInt();
	            
	            String initialLane = "";
	            String finalLane = "";
	            
	            if(json.get("faixa_km") != null && !json.get("faixa_km").isJsonNull() && !json.get("faixa_km").getAsString().equals("")) {
	                String[] lanes = json.get("faixa_km").getAsString().split("-");
	                initialLane = lanes[0];
	                finalLane = lanes[1];
	            }
	            String lanesRange = (json.get("faixa_km") != null && !json.get("faixa_km").isJsonNull() && !json.get("faixa_km").getAsString().equals("")) ? json.get("faixa_km").getAsString() : "";
	            if(incidentValidation.validateShowIncidentsList(this.incident, lanesRange)) {
	                String[] date = incident.getDate().split(" ");
	                incidentList = new IncidentService().searchIncidents(incident.getHighway(), date[0], initialLane, finalLane, period);
	                if(incidentList.size() == 0) {
	                	message = "Nenhum incidente encontrado";
	                	System.out.println(message);
		                return new Tools().createResultJson(4, true, message, incidentList, null, null);
	                }
	                message = "Lista de incidentes recuperada com sucesso";
	                return new Tools().createResultJson(4, true, message, incidentList, null, null);
	            } else {
	                message = "Erro solicitar lista de incidentes - Erro de validação";
	                System.out.println(message);
	                return new Tools().createResultJson(4, false, message, null, null, null);
	            }
	        } else {
	            message = "Erro solicitar lista de incidentes - Erro com null, o json pegou null em algum campo enviado";
	            System.out.println(message);
	            return new Tools().createResultJson(4, false, message, null, null, null);   
	        }
	    } catch (SQLException e) {
	        message = "Erro solicitar lista de incidentes - Erro de SQL: " + e.getMessage();
	        System.out.println(message);
	        return new Tools().createResultJson(4, false, message, null, null, null);
	    } catch (IOException e) {
	        message = "Erro solicitar lista de incidentes - Erro de IOException: " + e.getMessage();
	        System.out.println(message);
	        return new Tools().createResultJson(4, false, message, null, null, null);
	    } catch(Exception e) {
	        message = "Erro solicitar lista de incidentes - Erro de exceção: " + e.getMessage();
	        System.out.println(message);
	        return new Tools().createResultJson(4, false, message, null, null, null);        
	    }
	}

	public String searchMyIncidents() {
	    this.incident = new Incident();
	    try {
	        if(json.get("id_usuario") != null && json.get("token") != null) {
	        	if (!new UserService().isLoggedIn(json.get("id_usuario").getAsInt(), json.get("token").getAsString())) {
	        		message = "Erro solicitar lista de meus incidentes - Usuário não está logado ou token está diferente.";
		            System.out.println(message);
		            return new Tools().createResultJson(4, false, message, null, null, null);
	    	    }
	            incidentList = new IncidentService().searchMyIncidents(json.get("id_usuario").getAsInt());
	            if(incidentList.size() == 0) {
                	message = "Nenhum incidente encontrado";
	                return new Tools().createResultJson(4, false, message, incidentList, null, null);
                }
	            message = "Lista de meus incidentes recuperada com sucesso";
	            return new Tools().createResultJson(4, true, message, incidentList, null, null);
	            
	        } else {
	            message = "Erro solicitar lista de meus incidentes - Erro com null, o json pegou null em algum campo enviado";
	            System.out.println(message);
	            return new Tools().createResultJson(4, false, message, null, null, null);
	        }
	    } catch (SQLException e) {
	        message = "Erro solicitar lista de meus incidentes - Erro de SQL: " + e.getMessage();
	        System.out.println(message);
	        return new Tools().createResultJson(4, false, message, null, null, null);
	    } catch (IOException e) {
	        message = "Erro solicitar lista de meus incidentes - Erro de IOException: " + e.getMessage();
	        System.out.println(message);
	        return new Tools().createResultJson(4, false, message, null, null, null);
	    } catch(Exception e) {
	        message = "Erro solicitar lista de meus incidentes - Erro de exceção: " + e.getMessage();
	        System.out.println(message);
	        return new Tools().createResultJson(4, false, message, null, null, null);        
	    }
	}

	public String deleteIncident(){
	    this.incident = new Incident();
	    try {
	        if(json.get("id_usuario") != null && json.get("token") != null && json.get("id_incidente") != null) {
	            
	            boolean hasItemDeleted = false;
	            if (!new UserService().isLoggedIn(json.get("id_usuario").getAsInt(), json.get("token").getAsString())) {
	        		message = "Erro deletar incidente - Usuário não está logado ou token está diferente.";
		            System.out.println(message);
		            return new Tools().createResultJson(1, false, message, null, null, null);
	    	    }
	            hasItemDeleted = new IncidentService().deleteIncident(json.get("id_usuario").getAsInt(), json.get("id_incidente").getAsInt());
	            if(hasItemDeleted) {
	                message = "Incidente deletado com sucesso";
	                System.out.println(message);
	                return new Tools().createResultJson(1, true, message, null, null, null);
	            } else {
	                message = "Erro deletar incidente - Incidente não encontrado";
	                System.out.println(message);
	                return new Tools().createResultJson(1, false, message, null, null, null);
	            }

	        } else {
	            message = "Erro solicitar lista de incidentes - Erro com null, o json pegou null em algum campo enviado";
	            System.out.println(message);
	            return new Tools().createResultJson(1, false, message, null, null, null);
	        }
	    } catch (SQLException e) {
	        message = "Erro deletar incidente - Erro de SQL: " + e.getMessage();
	        System.out.println(message);
	        return new Tools().createResultJson(1, false, message, null, null, null);
	    } catch (IOException e) {
	        message = "Erro deletar incidente - Erro de IOException: " + e.getMessage();
	        System.out.println(message);
	        return new Tools().createResultJson(1, false, message, null, null, null);
	    } catch(Exception e) {
	        message = "Erro deletar incidente - Erro de exceção: " + e.getMessage();
	        System.out.println(message);
	        return new Tools().createResultJson(1, false, message, null, null, null);        
	    }
	}

	public String editIncident() {
	    this.incident = new Incident();
	    try {
	        if(json.get("id_usuario") != null && json.get("token") != null && json.get("id_incidente") != null && json.get("data") != null && json.get("km") != null&& json.get("rodovia") != null&& json.get("tipo_incidente") != null) {
	            
	            boolean hasItemEdited = false;
	            if (!new UserService().isLoggedIn(json.get("id_usuario").getAsInt(), json.get("token").getAsString())) {
	        		message = "Erro editar incidente - Usuário não está logado ou token está diferente.";
		            System.out.println(message);
		            return new Tools().createResultJson(1, false, message, null, null, null);
	    	    }
	            hasItemEdited = new IncidentService().editIncident(json.get("id_usuario").getAsInt(), json.get("id_incidente").getAsInt(), json.get("data").getAsString(), json.get("rodovia").getAsString(), json.get("km").getAsInt(), json.get("tipo_incidente").getAsInt());
	            if(hasItemEdited) {
	                message = "Incidente editado com sucesso";
	                System.out.println(message);
	                return new Tools().createResultJson(1, true, message, null, null, null);
	            } else {
	                message = "Erro editar incidente - Incidente não encontrado";
	                System.out.println(message);
	                return new Tools().createResultJson(1, false, message, null, null, null);
	            }
	        } else {
	            message = "Erro solicitar lista de incidentes - Erro com null, o json pegou null em algum campo enviado";
	            System.out.println(message);
	            return new Tools().createResultJson(1, false, message, null, null, null);
	        }
	    } catch (SQLException e) {
	        message = "Erro solicitar lista de incidentes - Erro de SQL: " + e.getMessage();
	        System.out.println(message);
	        return new Tools().createResultJson(1, false, message, null, null, null);
	    } catch (IOException e) {
	        message = "Erro editar incidente - Erro de IOException: " + e.getMessage();
	        System.out.println(message);
	        return new Tools().createResultJson(1, false, message, null, null, null);
	    } catch(Exception e) {
	        message = "Erro solicitar lista de incidentes - Erro de exceção: " + e.getMessage();
	        System.out.println(message);
	        return new Tools().createResultJson(1, false, message, null, null, null);        
	    }
	}
	
}
