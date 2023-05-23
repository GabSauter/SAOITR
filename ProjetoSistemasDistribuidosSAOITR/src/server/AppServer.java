package server;

import javax.swing.SwingUtilities;

import server.views.ServerPortConnectionUI;

public class AppServer {

	public static void main(String[] args) {
		
    	SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ServerPortConnectionUI connectionLayout = new ServerPortConnectionUI();
                connectionLayout.setVisible(true);
            }
        });
	}

}
