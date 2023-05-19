package client.views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import client.logic.SocketLogic;
import client.logic.User;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class HomeLayout extends JFrame {

	private JPanel contentPane;
	
	private User user;

	public HomeLayout(User user) {
		this.user = user;
		this.initComponents();
	}
	
	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 498, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Página Inicial");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(188, 11, 107, 14);
		contentPane.add(lblNewLabel);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnLogoutAction();
			}
		});
		btnLogout.setBounds(206, 277, 89, 23);
		contentPane.add(btnLogout);
	}
	
	private void btnLogoutAction() {
        System.out.println("Cliente: Operação de logout");
    	String userInput = user.logout();
    	
    	String inputLine = new SocketLogic().sendAndReceive(userInput);
    	user.logoutResponse(inputLine);
    	
    	Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(inputLine, JsonObject.class);
        int codigo = 0;
        
        if(jsonObject != null) {
        	codigo = jsonObject.get("codigo").getAsInt();
	    	if(codigo == 200) {
	    		System.out.println("Logout feito com sucesso");
	    		this.user.setEstaLogado(false);
	    		new LoginLayout().setVisible(true);
    			this.dispose();
	    	} else {
	    		System.out.println(jsonObject.get("mensagem").getAsString());
	    		JOptionPane.showMessageDialog(this, "Houve um erro durante o logout.", "Erro de logout", JOptionPane.ERROR_MESSAGE);
	    	}
        }else {
        	System.out.println("Logout: JsonObject ta null");
        	JOptionPane.showMessageDialog(this, "JsonObject ta null.", "Erro de logout", JOptionPane.ERROR_MESSAGE);
        }
	}

}
