package server.views;

import java.awt.EventQueue;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.google.gson.JsonElement;

import client.logic.Incident;
import server.EchoServer;
import server.entities.User;
import server.logic.UserLogic;
import server.service.UserService;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class ServerPortConnectionUI extends JFrame {

	private JPanel contentPane;
	private JTextField txtFieldPort;
	private JTable loginTable;
	
	ServerSocket serverSocket = null;
	private JButton btnRefresh;
	private JScrollPane scrollPane;
	
	public ServerPortConnectionUI() {
		setTitle("Servidor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 578, 457);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Escolha a porta:");
		lblNewLabel.setBounds(10, 15, 96, 14);
		contentPane.add(lblNewLabel);
		
		txtFieldPort = new JTextField();
		txtFieldPort.setBounds(116, 12, 108, 20);
		contentPane.add(txtFieldPort);
		txtFieldPort.setColumns(10);
		
		JButton btnConnectServer = new JButton("Ligar Servidor");
		btnConnectServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnConnectServerAction();
			}
		});
		btnConnectServer.setBounds(234, 11, 118, 23);
		contentPane.add(btnConnectServer);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 95, 534, 312);
		contentPane.add(scrollPane);
		
		loginTable = new JTable();
		scrollPane.setViewportView(loginTable);
		loginTable.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Id", "Nome", "Email", "Senha", "Token"
				}
		));
		loginTable.setEnabled(false);
		
		btnRefresh = new JButton("Atualizar");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnRefreshAction();
			}
		});
		btnRefresh.setBounds(10, 61, 89, 23);
		contentPane.add(btnRefresh);
	}
	
	private void btnConnectServerAction() {
		Thread serverThread = new Thread(() -> {
			try {
				serverSocket = new ServerSocket(Integer.parseInt(txtFieldPort.getText().toString()));
				System.out.println("Connection Socket Created");
				
				try {
					while (true) {
						System.out.println("Waiting for Connection");
						new EchoServer(serverSocket.accept());
					}
				} catch (IOException e) {
					System.err.println("Accept failed.");
					JOptionPane.showMessageDialog(this, "Accept failed.", "Accept failed.", JOptionPane.ERROR_MESSAGE);
					System.exit(1);
				}
			} catch (IOException e) {
				System.err.println("Could not listen on port");
				JOptionPane.showMessageDialog(this, "Could not listen on port", "Could not listen on port", JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
			finally {
				try {
					assert serverSocket != null;
					serverSocket.close();
				} catch (IOException e) {
					System.err.println("Could not close port");
					JOptionPane.showMessageDialog(this, "Could not close port", "Could not close port", JOptionPane.ERROR_MESSAGE);
					System.exit(1);
				}
			}
		});
		serverThread.start();   
	}
	
	private void btnRefreshAction() {
		UserService userService = new UserService();
		List<User> loggedUsers = new ArrayList<User>();
		try {
			loggedUsers = userService.getallLoggedIn();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		
		DefaultTableModel model = (DefaultTableModel) loginTable.getModel();
		model.fireTableDataChanged();
		model.setRowCount(0);
		
		for(User loggedUser: loggedUsers) {
			model.addRow(new Object[] {
					loggedUser.getIdUsuario(),
					loggedUser.getName(),
					loggedUser.getEmail(),
					loggedUser.getPassword(),
					loggedUser.getToken()
			});
		}
	}
}
