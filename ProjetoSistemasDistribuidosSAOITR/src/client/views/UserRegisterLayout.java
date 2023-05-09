package client.views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
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

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserRegisterLayout frame = new UserRegisterLayout();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

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
		btnRegisterUser.setBounds(10, 163, 89, 23);
		contentPane.add(btnRegisterUser);
	}
	
	private void btnRegisterUserAction() {
		
	}
	
	private void btnBackAction() {
		
	}
}
