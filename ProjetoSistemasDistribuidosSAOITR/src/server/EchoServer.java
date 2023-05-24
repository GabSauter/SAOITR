package server;

import java.net.*;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import server.logic.IncidentLogic;
import server.logic.UserLogic;

import java.io.*;

public class EchoServer extends Thread {

	protected Socket clientSocket;

	public EchoServer(Socket clientSoc) {
		clientSocket = clientSoc;
		start();
	}

	public void run() {
		System.out.println("New Communication Thread Started");
		try {
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
			try {
				String inputLine;
				String outputLine = "";

				while ((inputLine = in.readLine()) != null) {

					System.out.println(
							"-------------------------------------------------------------------------------------------");
					System.out.println("Recebe do cliente: " + inputLine);

					int operation = 0;
					JsonObject jsonObject = null;
					Gson gson = new Gson();

					jsonObject = gson.fromJson(inputLine, JsonObject.class);
					if (jsonObject != null)
						if (jsonObject.get("id_operacao") != null)
							operation = jsonObject.get("id_operacao").getAsInt();

					switch (operation) {
						case 1: { // Register
	
							UserLogic userLogic = new UserLogic(jsonObject);
							outputLine = userLogic.userRegister();
	
							break;
						}
						case 2: {
							
							UserLogic userLogic = new UserLogic(jsonObject);
							outputLine = userLogic.userUpdateRegister();
							
							break;
						}
						case 3: { // Login
	
							UserLogic userLogic = new UserLogic(jsonObject);
							outputLine = userLogic.userLogin();
	
							break;
						}
						case 4: {
							IncidentLogic incidentLogic = new IncidentLogic(jsonObject);
							outputLine = incidentLogic.incidentReport();
	
							break;
						}
						case 5: {
							IncidentLogic incidentLogic = new IncidentLogic(jsonObject);
							outputLine = incidentLogic.searchIncidents();
	
							break;
						}
						case 6: {
							IncidentLogic incidentLogic = new IncidentLogic(jsonObject);
							outputLine = incidentLogic.searchMyIncidents();
	
							break;
						}
						case 7: {
							IncidentLogic incidentLogic = new IncidentLogic(jsonObject);
							outputLine = incidentLogic.deleteIncident();
	
							break;
						}
						case 8: { 
							
							UserLogic userLogic = new UserLogic(jsonObject);
							outputLine = userLogic.deleteUserAccount();
	
							break;
						}
						case 9: { // logout
	
							UserLogic userLogic = new UserLogic(jsonObject);
							outputLine = userLogic.userLogout();
	
							break;
						}
						case 10: { // 
							
							IncidentLogic incidentLogic = new IncidentLogic(jsonObject);
							outputLine = incidentLogic.editIncident();
	
							break;
						}
						default: {
							System.out.println("Operação inválida");
							JsonObject json = new JsonObject();
							json.addProperty("codigo", 500);
							json.addProperty("mensagem", "Houve um erro id operação.");
							outputLine = json.toString();
						}
					}

					System.out.println("Envia para o cliente: " + outputLine);
					System.out.println(
							"-------------------------------------------------------------------------------------------");
					out.println(outputLine);
				}
			} catch (JsonSyntaxException e) {
				JsonObject json = new JsonObject();
				json.addProperty("codigo", 500);
				json.addProperty("mensagem", "Houve um erro com json null.");
				System.out.println("Envia para o cliente: " + json.toString());
				System.out.println(
						"-------------------------------------------------------------------------------------------");
				out.println(json.toString());
			}

			out.close();
			in.close();
			clientSocket.close();
		} catch (IOException e) {
			System.err.println("Problem with Communication Server");
		}
	}
}