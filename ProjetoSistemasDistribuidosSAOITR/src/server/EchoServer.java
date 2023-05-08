package server;


import java.net.*;
import java.sql.SQLException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import server.logic.UserLogic;

import java.io.*;

public class EchoServer extends Thread {

    protected Socket clientSocket;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;

        try {
            //serverSocket = new ServerSocket(23000);
            serverSocket = new ServerSocket(24001);
            System.out.println ("Connection Socket Created");
            try {
                while (true){
                    System.out.println ("Waiting for Connection");
                    new EchoServer (serverSocket.accept());
                }
            }
            catch (IOException e){
                System.err.println("Accept failed.");
                System.exit(1);
            }
        }
        catch (IOException e){
            System.err.println("Could not listen on port: 10008.");
            System.exit(1);
        }
        finally{
            try {
                assert serverSocket != null;
                serverSocket.close();
            }
            catch (IOException e){
                System.err.println("Could not close port: 10008.");
                System.exit(1);
            }
        }
    }

    private EchoServer (Socket clientSoc){
        clientSocket = clientSoc;
        start();
    }

    public void run(){
        System.out.println ("New Communication Thread Started");
        try {
        	PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
            BufferedReader in = new BufferedReader(new InputStreamReader( clientSocket.getInputStream()));
            
            try {
            	
            
            String inputLine;
            String outputLine = "";

            while ((inputLine = in.readLine()) != null){

            	System.out.println("-------------------------------------------------------------------------------------------");
                System.out.println ("Recebe do cliente: " + inputLine);
                
                int operation = 0;
                JsonObject jsonObject = null;
                Gson gson = new Gson();
                
                jsonObject = gson.fromJson(inputLine, JsonObject.class);
                if(jsonObject != null)
                	if(jsonObject.get("id_operacao") != null) 
                		operation = jsonObject.get("id_operacao").getAsInt();
                
                switch (operation){
	                case 1:{ // Register
	                	
	                	UserLogic userLogic = new UserLogic(jsonObject);
	                	try {
	                		outputLine = userLogic.userRegister();
	                	}catch(SQLException | IOException e) {
	            			System.out.println(e.getMessage());
	                	}
	                	
	                	break;
	                }
                    case 3: { // Login
                    	
                    	UserLogic userLogic = new UserLogic(jsonObject);
	                	try {
	                		outputLine = userLogic.userLogin();
	                	}catch(SQLException | IOException e) {
	            			System.out.println(e.getMessage());
	                	}
	                	
                        break;
                    }
                    case 9: { // logout
                    	
                    	UserLogic userLogic = new UserLogic(jsonObject);
                    	try {
	                		outputLine = userLogic.userLogout();
	                	}catch(SQLException | IOException e) {
	            			System.out.println(e.getMessage());
	                	}
                    	
                    	break;
                    }
                    default:{
                    	System.out.println("Operação inválida");
                    	JsonObject json = new JsonObject();
                    	json.addProperty("codigo", 500);
    					json.addProperty("mensagem", "Houve um erro id operação.");
                    	outputLine = json.toString();
                    }
                    
                }

                System.out.println("Envia para o cliente: " + outputLine);
                System.out.println("-------------------------------------------------------------------------------------------");
                out.println(outputLine);
            }
            }catch(JsonSyntaxException e) {
            	JsonObject json = new JsonObject();
            	json.addProperty("codigo", 500);
				json.addProperty("mensagem", "Houve um erro com json null.");
				System.out.println("Envia para o cliente: " + json.toString());
                System.out.println("-------------------------------------------------------------------------------------------");
            	out.println(json.toString());
            }

            out.close();
            in.close();
            clientSocket.close();
        }
        catch (IOException e){
            System.err.println("Problem with Communication Server");
            //System.exit(1);
        }
    }
}