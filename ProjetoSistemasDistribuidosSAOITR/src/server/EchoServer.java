package server;


import java.net.*;
import java.sql.SQLException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import server.logic.UserLogic;

import java.io.*;

public class EchoServer extends Thread {

    protected Socket clientSocket;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(23000);
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

            String inputLine;
            String outputLine = "";

            while ((inputLine = in.readLine()) != null){

            	System.out.println("-------------------------------------------------------------------------------------------");
                System.out.println ("Recebe do cliente: " + inputLine);

                JsonObject json = new JsonObject();
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(inputLine, JsonObject.class);
                int operation = jsonObject.get("id_operacao").getAsInt();
                
                JBCrypt bcrypt = new JBCrypt();

                switch (operation){
	                case 1:{ // Cadastrar
	                	UserLogic userLogic = new UserLogic(jsonObject);
	                	try {
	                		outputLine = userLogic.userRegister();
	                	}catch(SQLException | IOException e) {
	            			System.out.println(e.getMessage());
	                	}
	                	
	                	break;
	                }
                    case 3: { // Login
                    	String email = jsonObject.get("email").getAsString();
                    	String senha = jsonObject.get("senha").getAsString();
                    	
                    	UserLogic userLogic = new UserLogic(jsonObject);
	                	try {
	                		outputLine = userLogic.userLogin(email, senha);
	                	}catch(SQLException | IOException e) {
	            			System.out.println(e.getMessage());
	                	}
	                	
                        break;
                    }
                    case 9: {
                    	String token = jsonObject.get("token").getAsString();
                    	int id_usuario = jsonObject.get("id_usuario").getAsInt();
                    	
                    	UserLogic userLogic = new UserLogic(jsonObject);
                    	
                    	try {
	                		outputLine = userLogic.userLogout(token, id_usuario);
	                	}catch(SQLException | IOException e) {
	            			System.out.println(e.getMessage());
	                	}
                    }
                    
                }

                System.out.println("Envia para o cliente: " + outputLine);
                System.out.println("-------------------------------------------------------------------------------------------");
                out.println(outputLine);
            }

            out.close();
            in.close();
            clientSocket.close();
        }
        catch (IOException e){
            System.err.println("Problem with Communication Server");
            System.exit(1);
        }
    }
}