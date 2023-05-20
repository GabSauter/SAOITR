package client.views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import client.logic.User;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ShowIncidentsLayout extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JButton btnBack;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_3;
	private JComboBox<String> comboBox;
	private JTextField txtFieldInitialLane;
	private JTextField txtFieldFinalLane;
	private JTextField txtFieldHighway;
	private JButton btnShowIncidents;
	
	private User user;

	public ShowIncidentsLayout() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 629, 489);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 208, 593, 231);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Data", "Rodovia", "Km", "Tipo do incidente"
				}
		));
		
		btnBack = new JButton("<");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnBackAction();
			}
		});
		btnBack.setBounds(10, 11, 41, 23);
		contentPane.add(btnBack);
		
		lblNewLabel = new JLabel("Data");
		lblNewLabel.setBounds(10, 45, 76, 14);
		contentPane.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("Rodovia");
		lblNewLabel_1.setBounds(10, 70, 76, 14);
		contentPane.add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("Faixa Inicial");
		lblNewLabel_2.setBounds(10, 95, 76, 14);
		contentPane.add(lblNewLabel_2);
		
		lblNewLabel_4 = new JLabel("Período");
		lblNewLabel_4.setBounds(10, 120, 76, 14);
		contentPane.add(lblNewLabel_4);
		
		lblNewLabel_3 = new JLabel("Faixa Final");
		lblNewLabel_3.setBounds(192, 95, 76, 14);
		contentPane.add(lblNewLabel_3);
		
		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Manhã - 06:00 ~ 11:59", "Tarde - 12:00 ~ 17:59", "Noite - 18:00 ~ 23:59", "Madrugada - 00:00 ~ 05:59"}));
		comboBox.setSelectedIndex(0);
		comboBox.setBounds(96, 116, 184, 22);
		contentPane.add(comboBox);
		
		txtFieldInitialLane = new JTextField();
		txtFieldInitialLane.setBounds(96, 92, 86, 20);
		contentPane.add(txtFieldInitialLane);
		txtFieldInitialLane.setColumns(10);
		
		txtFieldFinalLane = new JTextField();
		txtFieldFinalLane.setColumns(10);
		txtFieldFinalLane.setBounds(270, 92, 86, 20);
		contentPane.add(txtFieldFinalLane);
		
		txtFieldHighway = new JTextField();
		txtFieldHighway.setBounds(96, 67, 172, 20);
		contentPane.add(txtFieldHighway);
		txtFieldHighway.setColumns(10);
		
		btnShowIncidents = new JButton("Buscar Incidentes");
		btnShowIncidents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnShowIncidents();
			}
		});
		btnShowIncidents.setBounds(10, 174, 172, 23);
		contentPane.add(btnShowIncidents);
	}

	private void btnBackAction() {
		new HomeLayout(user).setVisible(true);
		this.dispose();
	}
	
	private void btnShowIncidents() {
		
	}
}
