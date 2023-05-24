package server.logic.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import server.entities.Incident;
import server.entities.User;

public class Tools {

public String createResultJson(int tipo, boolean correct, String message, JsonArray incidentList, Incident incident, User user) {
		
		JsonObject json = new JsonObject();
		
		switch(tipo) {
			case 1: { // Usado em: 1, 4, 7, 8, 9, 10
				if(correct) {
					json.addProperty("codigo", 200);
				}else {
					json.addProperty("codigo", 500);
					json.addProperty("mensagem", message);
				}
				return json.toString();
			}
			case 2: { // Usado em: 2
				System.out.println("Operação de solicitação de lista de incidentes");
				if(correct) {
					json.addProperty("codigo", 200);
					json.addProperty("token", user.getToken());
				}else {
					json.addProperty("codigo", 500);
					json.addProperty("mensagem", message);
				}
				return json.toString();
			}
			case 3: { // Usado em: 3
				if(correct) {
					json.addProperty("codigo", 200);
					json.addProperty("token", user.getToken());
					json.addProperty("id_usuario", user.getIdUsuario());
				}else {
					json.addProperty("codigo", 500);
					json.addProperty("mensagem", message);
				}
				return json.toString();
			}
			case 4: { // Usado em: 5, 6
				if(correct) {
					json.addProperty("codigo", 200);
					json.add("lista_incidentes", incidentList);
				}else {
					json.addProperty("codigo", 500);
					json.addProperty("mensagem", message);
				}
				return json.toString();
			}
			default: {
				json.addProperty("codigo", 500);
				json.addProperty("mensagem", "Houve um erro, tipo inválido.");
				return json.toString();
			}
		}
	}
}
