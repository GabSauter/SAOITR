package server;


import java.net.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.*;

public class EchoServer extends Thread {

    protected Socket clientSocket;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(10008);
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

        Gson gson = new Gson();

        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
            BufferedReader in = new BufferedReader(new InputStreamReader( clientSocket.getInputStream()));

            String inputLine;
            String outputLine = "";

            while ((inputLine = in.readLine()) != null){

                JsonObject json = new JsonObject();

                System.out.println ("Recebe do cliente: " + inputLine);

                JsonObject jsonObject = gson.fromJson(inputLine, JsonObject.class);
                int operation = jsonObject.get("id_operacao").getAsInt();

                switch (operation){
                    case 1: {
                        outputLine = logar(json);
                        break;
                    }
                }

                System.out.println("Envia para o cliente: " + outputLine);
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

    public boolean validarDados(){
        return true;
    }

    private String logar(JsonObject json){
        System.out.println("Operação de login");

        if(validarDados()){
            System.out.println("Deu certo");
            json.addProperty("codigo", 200);
            json.addProperty("token", 123);
            json.addProperty("id_usuario", gerarIdUsuario());
        }else{
            System.out.println("Deu errado");
            json.addProperty("codigo", 500);
            json.addProperty("mensagem", "Erro durante o login");
        }

        return json.toString();
    }

    private int gerarIdUsuario(){
        return 1;
    }
}