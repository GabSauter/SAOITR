package client;

import java.io.*;
import java.net.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import client.cryptography.CaesarCipher;
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
        User user = new User();

        boolean estaAberto = true;

        int operation;
        
        do {
            System.out.println("Escolha qual operacao voce deseja fazer: \n 1. Cadastrar\n 3. Logar\n 9. Logout\n 50. Fechar");
            try {
            	operation = (int) input.nextInt();
            }catch(InputMismatchException e) {
            	System.out.println("Por favor escreva um número e não uma string");
            	operation = -1;
            }
            input.nextLine();
            switch (operation){
                case 1: {
                	userInput="";
                	System.out.println("Cliente: Operação de cadastro");
                	
            		System.out.println("Nome: ");
            		String name = input.nextLine();
            	      
            		System.out.println("Email: ");
            		String email = input.nextLine();
            				
            		System.out.println("Senha: ");
            		String password = input.nextLine();
            		password = new CaesarCipher().encrypt(password);
                	
                	userInput = new User().register(name, email, password);
                	break;
                }
                case 3: {
                	userInput="";
                	if(!user.isEstaLogado()) {
                		System.out.println("Cliente: Operação de login");
                		
                		System.out.println("Email: ");
                		String email = stdIn.readLine();
                		
                		System.out.println("Senha: ");
                		String password = stdIn.readLine();
                		password = new CaesarCipher().encrypt(password);
                		userInput = new User().login(email, password);
                	}else {
                		System.out.println("Você ja está logado. De um logout se quiser logar denovo.");
                		operation = -1;
                	}
                    
	            	break;
                }
                case 9: {
                	userInput="";
                    System.out.println("Cliente: Operação de logout");
                	userInput = user.logout();
                	break;
                }
                case 50: {
                	userInput="";
                	System.out.println("Fechando...");
                    estaAberto = false;
                    break;
                }
                default : {
                	System.out.println("Operação inválida");
                	operation = -1;
                }
            }
            
            if(!estaAberto)
            	break;
            
            if(operation != -1) {
            	System.out.println("Envia para o servidor: " + userInput);
                out.println(userInput);
            }
            
            String inputLine = "";
            try {
            	inputLine = in.readLine();
            	System.out.println("Recebe do servidor: " + inputLine);
            }catch(Exception e){
            	System.out.println(e);
            }
                
            switch(operation) {
            	case 1: {
            		user.registerResponse(inputLine);
            		break;
            	}
            	case 3: {
            		user.loginResponse(inputLine);
            		System.out.println(user);
            		break;
            	}
            	case 9:{
            		user.logoutResponse(inputLine);
            		break;
            	}
            }

        } while (estaAberto);

        out.close();
        in.close();
        input.close();
        stdIn.close();
        echoSocket.close();
    }
    
    
}


