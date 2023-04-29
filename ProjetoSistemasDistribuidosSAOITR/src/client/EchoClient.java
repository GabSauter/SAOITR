package client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class EchoClient {
    public static void main(String[] args) throws IOException {

        String serverHostname = "127.0.0.1";

        if (args.length > 0)
            serverHostname = args[0];
        System.out.println ("Attempting to connect to host " + serverHostname + " on port 10008.");

        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            echoSocket = new Socket(serverHostname, 10008);
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

        boolean estaAberto = true;

        int operation;

        do {
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
                }break;
                case 2: {
                	JsonObject json = new JsonObject();
                	userInput = cadastrar(json, input);
                }break;
                case 3: {
                    System.out.println("Fechando...");
                    estaAberto = false;
                }break;
            }
            if(!estaAberto)
                break;

            System.out.println("Envia para o servidor: " + userInput);
            out.println(userInput);
            
            try {
            	System.out.println("Recebe do servidor: " + in.readLine());
            }catch(Exception e){
            	System.out.println(e);
            }

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
			json.addProperty("senha", senha);
    	return json.toString();
    }
}


