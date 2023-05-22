package client.logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import client.views.ConnectionLayout;

public class SocketLogic {
	
	public Socket echoSocket = null;
    public static PrintWriter out = null;
    public static BufferedReader in = null;

	public boolean socketConnectClient(String ip, int port, ConnectionLayout connectionLayout) {
    	String serverHostname = ip;
    	
        System.out.println ("Attempting to connect to host " + serverHostname);
        
        try {
        	this.echoSocket = new Socket(serverHostname, port);
        	SocketLogic.out = new PrintWriter(echoSocket.getOutputStream(), true);
            SocketLogic.in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            return true;
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + serverHostname);
            connectionLayout.setTextLblConnectionResult("Don't know about host: " + serverHostname);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for " + "the connection to: " + serverHostname);
            connectionLayout.setTextLblConnectionResult("Couldn't get I/O for " + "the connection to: " + serverHostname);
        } catch (IllegalArgumentException e) {
        	System.err.println("Port out of range");
            connectionLayout.setTextLblConnectionResult("Port out of range");
        }
        return false;
	}
	
	public String sendAndReceive(String userInput) {
		try {
			System.out.println("Envia para o servidor: " + userInput);
            out.println(userInput);
			
        	String inputLine = SocketLogic.in.readLine();
        	System.out.println("Recebe do servidor: " + inputLine);
        	return inputLine;
        }catch(Exception e){
        	System.out.println(e);
        }
		return "";
	}
}
