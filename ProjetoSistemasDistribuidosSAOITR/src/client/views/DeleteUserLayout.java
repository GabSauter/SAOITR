package client.views;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import client.cryptography.CaesarCipher;
import client.logic.SocketLogic;
import client.logic.User;

public class DeleteUserLayout extends JFrame {

	private JPanel contentPane;

	User user;
	private JTextField txtFieldEmail;
	private JPasswordField txtFieldPassword;
	
	public DeleteUserLayout(User user) {
		this.user = user;
		initComponents();
	}
	
	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 422, 205);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel txtEmail = new JLabel("Email:");
		txtEmail.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtEmail.setBounds(83, 57, 33, 14);
		contentPane.add(txtEmail);
		
		txtFieldEmail = new JTextField();
		txtFieldEmail.setBounds(132, 54, 160, 20);
		contentPane.add(txtFieldEmail);
		txtFieldEmail.setColumns(10);
		
		JLabel txtSenha = new JLabel("Senha:");
		txtSenha.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtSenha.setBounds(83, 82, 38, 14);
		contentPane.add(txtSenha);
		
		JButton btnDelete = new JButton("Deletar conta");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnDeleteAction();
			}
		});
		btnDelete.setBounds(165, 110, 97, 23);
		contentPane.add(btnDelete);
		
		JLabel lblNewLabel = new JLabel("Deletar conta");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblNewLabel.setBounds(136, 11, 156, 32);
		contentPane.add(lblNewLabel);
		
		txtFieldPassword = new JPasswordField();
		txtFieldPassword.setBounds(132, 79, 160, 20);
		contentPane.add(txtFieldPassword);
		
		JButton btnBack = new JButton("<");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnBackAction();
			}
		});
		btnBack.setBounds(10, 11, 51, 23);
		contentPane.add(btnBack);
	}
	
	private void btnDeleteAction() {
		
		System.out.println("Cliente: Operação de deletar usuário");
		
		String email = txtFieldEmail.getText().toString();
		String password = String.valueOf(txtFieldPassword.getPassword());
		password = new CaesarCipher().encrypt(password);
		
		String userInput = new User().deleteAccount(email, password, user.getToken(), user.getId_usuario());
		String inputLine = new SocketLogic().sendAndReceive(userInput);
		
		User user = new User();
		user.deleteAccountResponse(inputLine);
		
        JsonObject jsonObject = new Gson().fromJson(inputLine, JsonObject.class);
        if(jsonObject != null) {
        	if(jsonObject.get("codigo") != null && !jsonObject.get("codigo").isJsonNull()) {
	        	int codigo = jsonObject.get("codigo").getAsInt();
	        	if(codigo == 200) {
	        		new LoginLayout().setVisible(true);
	    			this.dispose();
	        	}else {
	        		JOptionPane.showMessageDialog(this, "Email ou senha incorreta.", "Erro de deletar cadastro", JOptionPane.ERROR_MESSAGE);
	        	}
        	}else
        		JOptionPane.showMessageDialog(this, "Não foi possível pegar código no jsonObject", "Operação de deletar cadastro", JOptionPane.ERROR_MESSAGE);
        }else {
        	JOptionPane.showMessageDialog(this, "JsonObject ta null.", "Erro de deletar cadastro", JOptionPane.ERROR_MESSAGE);
        }
	}
	
	private void btnBackAction() {
		new HomeLayout(user).setVisible(true);
		this.dispose();
	}
}
