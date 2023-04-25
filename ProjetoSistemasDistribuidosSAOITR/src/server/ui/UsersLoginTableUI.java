package server.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTable;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;

public class UsersLoginTableUI {

	private JFrame frmServerTabelaDe;
	private JTable loginTable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UsersLoginTableUI window = new UsersLoginTableUI();
					window.frmServerTabelaDe.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UsersLoginTableUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmServerTabelaDe = new JFrame();
		frmServerTabelaDe.setTitle("Server: Tabela de usuários logados");
		frmServerTabelaDe.setBounds(100, 100, 570, 404);
		frmServerTabelaDe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmServerTabelaDe.getContentPane().setLayout(null);
		
		loginTable = new JTable();
		loginTable.setBounds(10, 42, 534, 312);
		frmServerTabelaDe.getContentPane().add(loginTable);
		
		JLabel lblNewLabel = new JLabel("Usuários logados");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(202, 11, 138, 20);
		frmServerTabelaDe.getContentPane().add(lblNewLabel);
	}
}
