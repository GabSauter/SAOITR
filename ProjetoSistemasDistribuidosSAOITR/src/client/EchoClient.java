package client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

import org.mindrot.jbcrypt.BCrypt;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import client.logic.User;

public class EchoClient {
    public static void main(String[] args) throws IOException {

        String serverHostname = "127.0.0.1";
    	//String serverHostname = "10.20.8.179";
    	
        if (args.length > 0)
            serverHostname = args[0];
        System.out.println ("Attempting to connect to host " + serverHostname + " on port 10008.");

        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            //echoSocket = new Socket(serverHostname, 10008);
        	echoSocket = new Socket(serverHostname, 23000);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + serverHostname);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for " + "the connection to: " + serverHostname);
            System.exit(1);
        }

        Scanner input = new Scanner(System.in);
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput = "";
        String email;
        String senha;
        Login login = new Login();
        Gson gson = new Gson();
        
        User user = new User();

        boolean estaAberto = true;

        int operation;
        
        

        do {
        	estaAberto = true;
            System.out.println("Escolha qual operacao voce deseja fazer: \n 1. Login\n 2. Cadastro\n 3. Fechar");
            operation = (int) input.nextInt();
            input.nextLine();
            switch (operation){
                case 1: {
                    login.setId_operacao(3);
                    System.out.println("Email: ");
                    email = stdIn.readLine();
                    login.setEmail(email);

                    System.out.println("Senha: ");
                    senha = stdIn.readLine();
                    login.setSenha(senha);
                    
                    userInput = gson.toJson(login);
                    System.out.println("Envia para o servidor: " + userInput);
                    out.println(userInput);
                    String inputLine = "";
                    try {
                    	inputLine = in.readLine();
                    	System.out.println("Recebe do servidor: " + inputLine);
                    }catch(Exception e){
                    	System.out.println(e);
                    }
                    System.out.println("continua...");
                    Gson gson2 = new Gson();
                    JsonObject jsonObject2 = new JsonObject();
	            	jsonObject2 = gson2.fromJson(inputLine, JsonObject.class);
	            	int codigo = jsonObject2.get("codigo").getAsInt();
	            	
	            	if(codigo == 200) {
	            		user.setId_usuario(jsonObject2.get("id_usuario").getAsInt());
	            		user.setToken(jsonObject2.get("token").getAsString());
	            		System.out.println(user.getId_usuario() + user.getToken());
	            	}
                    
	            	break;
                }
                case 2: {
                	JsonObject json = new JsonObject();
                	userInput = cadastrar(json, input);
                	System.out.println("Envia para o servidor: " + userInput);
                    out.println(userInput);
                }break;
                case 3: {
                    System.out.println("Fechando...");
                    estaAberto = false;
                }break;
                case 4: {
                	userInput = user.logout();
                	System.out.println("Envia para o servidor: " + userInput);
                    out.println(userInput);
                }break;
            }
            if(!estaAberto)
            	break;
                

            
            
            
//	            	Gson gson2 = new Gson();
//	            	JsonObject jsonObject = gson2.fromJson(in, JsonObject.class);
//	            	int codigo = jsonObject.get("codigo").getAsInt();
//	            	
//	            	if(codigo == 200) {
//	            		user.setId_usuario(jsonObject.get("id_usuario").getAsInt());
//	            		user.setToken(jsonObject.get("token").getAsString());
//	            		System.out.println(user.getId_usuario() + user.getToken());
//	            	}
//	            	break;

//            Gson gson2 = new Gson();
//
//            JsonObject jsonObject = gson2.fromJson(in, JsonObject.class);
//            int codigo = jsonObject.get("codigo").getAsInt();
//            System.out.println(codigo);
//
//            if(codigo == 200){
//                System.out.println(jsonObject.get("mensagem").getAsString());
//            }else if(codigo == 500){
//                System.out.println(jsonObject.get("mensagem").getAsString());
//            }else{
//                System.out.println("Vish");
//            }

        } while (estaAberto);

        out.close();
        in.close();
        input.close();
        stdIn.close();
        echoSocket.close();
    }
    
    private static String cadastrar(JsonObject json, Scanner input){
			System.out.println("Cliente: Operação de cadastro");
      
			json.addProperty("id_operacao", 1);
			
			System.out.println("Nome: ");
			String nome = input.nextLine();
			json.addProperty("nome", nome);
      
			System.out.println("Email: ");
			String email = input.nextLine();
			json.addProperty("email", email);
			
			System.out.println("Senha: ");
			String senha = input.nextLine();
			//String hashed = BCrypt.hashpw(senha, BCrypt.gensalt());
			//System.out.println(hashed);
			
			
			
			json.addProperty("senha", senha);
    	return json.toString();
    }
}


