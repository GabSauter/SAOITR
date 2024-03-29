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
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UserRegisterLayout extends JFrame {

	private JPanel contentPane;
	private JTextField txtFieldName;
	private JTextField txtFieldEmail;
	private JPasswordField txtFieldPassword;
	private JButton btnBack;
	private JLabel lblNewLabel_3;
	private JButton btnRegisterUser;

	public UserRegisterLayout() {
		this.initComponents();
	}

	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 310, 248);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Nome:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setBounds(10, 82, 46, 14);
		contentPane.add(lblNewLabel);

		txtFieldName = new JTextField();
		txtFieldName.setBounds(66, 79, 200, 20);
		contentPane.add(txtFieldName);
		txtFieldName.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Email:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setBounds(10, 110, 46, 14);
		contentPane.add(lblNewLabel_1);

		txtFieldEmail = new JTextField();
		txtFieldEmail.setColumns(10);
		txtFieldEmail.setBounds(66, 107, 200, 20);
		contentPane.add(txtFieldEmail);

		JLabel lblNewLabel_2 = new JLabel("Senha:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_2.setBounds(10, 138, 46, 14);
		contentPane.add(lblNewLabel_2);

		txtFieldPassword = new JPasswordField();
		txtFieldPassword.setBounds(66, 135, 200, 20);
		contentPane.add(txtFieldPassword);

		btnBack = new JButton("<");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnBackAction();
			}
		});
		btnBack.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnBack.setBounds(10, 11, 46, 23);
		contentPane.add(btnBack);

		lblNewLabel_3 = new JLabel("Cadastrar");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_3.setBounds(66, 45, 200, 23);
		contentPane.add(lblNewLabel_3);

		btnRegisterUser = new JButton("Cadastrar");
		btnRegisterUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnRegisterUserAction();
			}
		});
		btnRegisterUser.setBounds(66, 166, 200, 23);
		contentPane.add(btnRegisterUser);
	}

	private void btnRegisterUserAction() {
		try {
			System.out.println("Cliente: Operação de cadastro");

			String name = txtFieldName.getText().toString();
			String email = txtFieldEmail.getText().toString();

			String password = String.valueOf(txtFieldPassword.getPassword());
			password = new CaesarCipher().encrypt(password);

			String userInput = new User().register(name, email, password);

			String inputLine = new SocketLogic().sendAndReceive(userInput);

			User user = new User();
			user.registerResponse(inputLine);

			Gson gson = new Gson();
			JsonObject jsonObject = gson.fromJson(inputLine, JsonObject.class);
			int codigo = 0;

			if (jsonObject != null) {
				if (jsonObject.get("codigo") != null && !jsonObject.get("codigo").isJsonNull()) {
					codigo = jsonObject.get("codigo").getAsInt();
					if (codigo == 200) {
						System.out.println("Cadastrado com sucesso");
						JOptionPane.showMessageDialog(this, "Cadastrado com sucesso", "Operação de Cadastro",
								JOptionPane.INFORMATION_MESSAGE);
						new LoginLayout().setVisible(true);
						this.dispose();
					} else {
						System.out.println(jsonObject.get("mensagem").getAsString());
						JOptionPane.showMessageDialog(this, "Falha no cadastro", "Operação de Cadastro",
								JOptionPane.ERROR_MESSAGE);
					}
				} else
					JOptionPane.showMessageDialog(this, "Não foi possível pegar código no jsonObject",
							"Operação de Cadastro", JOptionPane.ERROR_MESSAGE);
			} else {
				System.out.println("Cadastro: JsonObject ta null");
				JOptionPane.showMessageDialog(this, "JsonObject ta null", "Operação de Cadastro",
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (JsonSyntaxException e) {
			JOptionPane.showMessageDialog(null, "Houve erro com Json null.");
		}catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Houve erro inesperado.");
		}
	}

	private void btnBackAction() {
		new LoginLayout().setVisible(true);
		this.dispose();
	}
}
