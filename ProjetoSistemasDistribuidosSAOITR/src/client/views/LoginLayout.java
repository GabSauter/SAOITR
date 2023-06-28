package client.views;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import client.cryptography.CaesarCipher;
import client.logic.SocketLogic;
import client.logic.User;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginLayout extends JFrame {

	private JPanel contentPane;
	private JTextField txtFieldEmail;
	private JPasswordField txtFieldPassword;
	
	private String userInput;

	public LoginLayout() {
		this.initComponents();
	}
	
	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 307, 207);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel txtEmail = new JLabel("Email:");
		txtEmail.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtEmail.setBounds(36, 56, 46, 14);
		contentPane.add(txtEmail);
		
		txtFieldEmail = new JTextField();
		txtFieldEmail.setBounds(92, 53, 159, 20);
		contentPane.add(txtFieldEmail);
		txtFieldEmail.setColumns(10);
		
		JLabel txtSenha = new JLabel("Senha:");
		txtSenha.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtSenha.setBounds(36, 89, 46, 14);
		contentPane.add(txtSenha);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSubmitAction();
			}
		});
		btnSubmit.setBounds(36, 114, 105, 23);
		contentPane.add(btnSubmit);
		
		JButton btnCreateAccount = new JButton("Cadastrar");
		btnCreateAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCreateAccountAction();
			}
		});
		btnCreateAccount.setBounds(146, 114, 105, 23);
		contentPane.add(btnCreateAccount);
		
		JLabel lblNewLabel = new JLabel("SAOITR");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblNewLabel.setBounds(36, 11, 221, 34);
		contentPane.add(lblNewLabel);
		
		txtFieldPassword = new JPasswordField();
		txtFieldPassword.setBounds(92, 84, 159, 20);
		contentPane.add(txtFieldPassword);
	}
	
	private void btnSubmitAction() {
		System.out.println("Cliente: Operação de login");
		
		String email = txtFieldEmail.getText().toString();
		String password = String.valueOf(txtFieldPassword.getPassword());
		password = new CaesarCipher().encrypt(password);
		
		userInput = new User().login(email, password);
		String inputLine = new SocketLogic().sendAndReceive(userInput);
		
		User user = new User();
		user.loginResponse(inputLine);
		
		try {
			JsonObject jsonObject = new Gson().fromJson(inputLine, JsonObject.class);
			if(jsonObject != null) {
				if(jsonObject.get("codigo") != null && !jsonObject.get("codigo").isJsonNull()) {
					int codigo = jsonObject.get("codigo").getAsInt();
					if(codigo == 200) {
						new HomeLayout(user).setVisible(true);
						this.dispose();
					}else {
						JOptionPane.showMessageDialog(this, "Email ou senha incorreta.", "Erro de login", JOptionPane.ERROR_MESSAGE);
					}
				}else
					JOptionPane.showMessageDialog(this, "Não foi possível pegar código no jsonObject", "Operação de login", JOptionPane.ERROR_MESSAGE);
			}else {
				JOptionPane.showMessageDialog(this, "JsonObject ta null.", "Erro de login", JOptionPane.ERROR_MESSAGE);
			}
		}catch(JsonSyntaxException e) {
			JOptionPane.showMessageDialog(null, "Houve erro com Json null.");
		}
	}
	
	private void btnCreateAccountAction() {
		new UserRegisterLayout().setVisible(true);
		this.dispose();
	}

	public String getUserInput() {
		return userInput;
	}

	public void setUserInput(String userInput) {
		this.userInput = userInput;
	}
}
