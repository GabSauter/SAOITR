package client.logic;

import javax.swing.JOptionPane;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

public class Incident {

	private int id_incident;
	private User user;
	private String date;
	private String highway;
	private int km;
	private int incident_type;

	private JsonArray incidentsList;

	public String reportIncident(String token, int id_user, String date, String highway, int km, int incident_type) {
		JsonObject json = new JsonObject();
		this.user = new User();
		this.user.setId_usuario(id_user);
		this.user.setToken(token);
		this.date = date;
		this.highway = highway;
		this.km = km;
		this.incident_type = incident_type;

		json.addProperty("id_operacao", 4);
		json.addProperty("id_usuario", this.user.getId_usuario());
		json.addProperty("token", this.user.getToken());
		json.addProperty("data", this.date.toString());
		json.addProperty("rodovia", this.highway);
		json.addProperty("km", this.km);
		json.addProperty("tipo_incidente", this.incident_type);

		return json.toString();
	}

	public void reportIncidentResponse(String inputLine) {
		try {
			Gson gson = new Gson();
			JsonObject jsonObject = gson.fromJson(inputLine, JsonObject.class);
			int codigo = 0;

			if (jsonObject != null) {
				if (jsonObject.get("codigo") != null && !jsonObject.get("codigo").isJsonNull()) {
					codigo = jsonObject.get("codigo").getAsInt();
					if (codigo == 200) {
						System.out.println("Incidente reportado com sucesso");
					} else {
						System.out.println(jsonObject.get("mensagem").getAsString());
					}
				} else {
					System.out.println("Incidente reportado: código está null");
				}
			} else {
				System.out.println("Incidente reportado: JsonObject ta null");
			}
		} catch (JsonSyntaxException e) {
			JOptionPane.showMessageDialog(null, "Houve erro com Json null.");
		}
	}

	public String searchIncidents(String highway, String date, String lanes, int period) { // lanes (faixa de km):
																							// 123-456
		JsonObject json = new JsonObject();

		json.addProperty("id_operacao", 5);
		json.addProperty("rodovia", highway);
		json.addProperty("data", date);
		json.addProperty("faixa_km", lanes);
		json.addProperty("periodo", period);

		return json.toString();
	}

	public void searchIncidentsResponse(String inputLine) {
		try {
			Gson gson = new Gson();
			JsonObject jsonObject = gson.fromJson(inputLine, JsonObject.class);
			int codigo = 0;

			if (jsonObject != null) {
				if (jsonObject.get("codigo") != null && !jsonObject.get("codigo").isJsonNull()) {
					codigo = jsonObject.get("codigo").getAsInt();
					if (codigo == 200) {
						System.out.println("Procurar incidentes reportado com sucesso");
						this.incidentsList = jsonObject.get("lista_incidentes").getAsJsonArray();
					} else {
						System.out.println(jsonObject.get("mensagem").getAsString());
					}
				} else
					System.out.println("Procurar incidente reportado: codigo ta null");
			} else {
				System.out.println("Procurar incidente reportado: JsonObject ta null");
			}
		} catch (JsonSyntaxException e) {
			JOptionPane.showMessageDialog(null, "Houve erro com Json null.");
		}
	}

	public String requestMyIncidentsList(String token, int user_id) {
		JsonObject json = new JsonObject();

		json.addProperty("id_operacao", 6);
		json.addProperty("token", token);
		json.addProperty("id_usuario", user_id);

		return json.toString();
	}

	public void requestMyIncidentsListResponse(String inputLine) {
		try {
			Gson gson = new Gson();
			JsonObject jsonObject = gson.fromJson(inputLine, JsonObject.class);
			int codigo = 0;

			if (jsonObject != null) {
				if (jsonObject.get("codigo") != null && !jsonObject.get("codigo").isJsonNull()) {
					codigo = jsonObject.get("codigo").getAsInt();
					if (codigo == 200) {
						System.out.println("Solicitar incidentes reportado pelo usuário: sucesso");
						this.incidentsList = jsonObject.get("lista_incidentes").getAsJsonArray();
					} else {
						System.out.println(jsonObject.get("mensagem").getAsString());
					}
				} else
					System.out.println("Solicitar incidentes reportado pelo usuário: codigo ta null");
			} else {
				System.out.println("Solicitar incidentes reportado pelo usuário: JsonObject ta null");
			}
		} catch (JsonSyntaxException e) {
			JOptionPane.showMessageDialog(null, "Houve erro com Json null.");
		}
	}

	public String deleteIncident(String token, int user_id, int incident_id) {
		JsonObject json = new JsonObject();

		json.addProperty("id_operacao", 7);
		json.addProperty("token", token);
		json.addProperty("id_usuario", user_id);
		json.addProperty("id_incidente", incident_id);

		return json.toString();
	}

	public void deleteIncidentResponse(String inputLine) {
		try {
			Gson gson = new Gson();
			JsonObject jsonObject = gson.fromJson(inputLine, JsonObject.class);
			int codigo = 0;

			if (jsonObject != null) {
				if (jsonObject.get("codigo") != null && !jsonObject.get("codigo").isJsonNull()) {
					codigo = jsonObject.get("codigo").getAsInt();
					if (codigo == 200) {
						System.out.println("Deletar incidente do usuário: sucesso");
					} else {
						System.out.println(jsonObject.get("mensagem").getAsString());
					}
				} else
					System.out.println("Deletar incidente do usuário: codigo ta null");
			} else {
				System.out.println("Deletar incidente do usuário: JsonObject ta null");
			}
		} catch (JsonSyntaxException e) {
			JOptionPane.showMessageDialog(null, "Houve erro com Json null.");
		}
	}

	public String editReportedIncident(String token, int userId, String date, String highway, int km, int incidentType,
			int incidentId) {
		JsonObject json = new JsonObject();

		json.addProperty("id_operacao", 10);
		json.addProperty("token", token);
		json.addProperty("id_usuario", userId);
		json.addProperty("id_incidente", incidentId);
		json.addProperty("data", date);
		json.addProperty("rodovia", highway);
		json.addProperty("km", km);
		json.addProperty("tipo_incidente", incidentType);

		return json.toString();
	}

	public void editReportedIncidentResponse(String inputLine) {
		try {
			Gson gson = new Gson();
			JsonObject jsonObject = gson.fromJson(inputLine, JsonObject.class);
			int codigo = 0;

			if (jsonObject != null) {
				if (jsonObject.get("codigo") != null && !jsonObject.get("codigo").isJsonNull()) {
					codigo = jsonObject.get("codigo").getAsInt();
					if (codigo == 200) {
						System.out.println("Editar incidente do usuário: sucesso");
					} else {
						System.out.println(jsonObject.get("mensagem").getAsString());
					}
				} else
					System.out.println("Editar incidente do usuário: codigo ta null");
			} else {
				System.out.println("Editar incidente do usuário: JsonObject ta null");
			}
		} catch (JsonSyntaxException e) {
			JOptionPane.showMessageDialog(null, "Houve erro com Json null.");
		}
	}

	public int getId_incident() {
		return id_incident;
	}

	public void setId_incident(int id_incident) {
		this.id_incident = id_incident;
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

	public JsonArray getIncidentsList() {
		return incidentsList;
	}

	public void setIncidentsList(JsonArray incidentsList) {
		this.incidentsList = incidentsList;
	}
}
