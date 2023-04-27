package client.views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;

public class LoginLayout extends JFrame {

	private JPanel contentPane;
	private JTextField txtFieldEmail;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginLayout frame = new LoginLayout();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginLayout() {
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
		btnSubmit.setBounds(36, 114, 105, 23);
		contentPane.add(btnSubmit);
		
		JButton btnCreateAccount = new JButton("Cadastrar");
		btnCreateAccount.setBounds(146, 114, 105, 23);
		contentPane.add(btnCreateAccount);
		
		JLabel lblNewLabel = new JLabel("SAOITR");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblNewLabel.setBounds(36, 11, 221, 34);
		contentPane.add(lblNewLabel);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(92, 84, 159, 20);
		contentPane.add(passwordField);
	}
}
