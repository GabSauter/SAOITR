package client.views;

import java.text.ParseException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import client.logic.User;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class IncidentReportLayout extends JFrame {

	private JPanel contentPane;
	private JFormattedTextField txtFieldDate;
	private JTextField txtFieldHighway;
	private JTextField txtFieldKm;
	
	private User user;
	
	public IncidentReportLayout(User user) {
		this.user = user;
		initComponents();
	}
	
	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnBack = new JButton("<");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnBackAction();
			}
		});
		btnBack.setBounds(10, 11, 47, 23);
		contentPane.add(btnBack);
		
		JLabel lblNewLabel = new JLabel("Data:");
		lblNewLabel.setBounds(10, 60, 46, 14);
		contentPane.add(lblNewLabel);
		
		txtFieldDate = new JFormattedTextField();
		txtFieldDate.setBounds(111, 57, 196, 20);
		contentPane.add(txtFieldDate);
		txtFieldDate.setColumns(10);
		MaskFormatter maskData;
		try {
			maskData = new MaskFormatter("####/##/## ##:##:##");
			maskData.install(txtFieldDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		JLabel lblRodovia = new JLabel("Rodovia:");
		lblRodovia.setBounds(10, 88, 46, 14);
		contentPane.add(lblRodovia);
		
		txtFieldHighway = new JTextField();
		txtFieldHighway.setColumns(10);
		txtFieldHighway.setBounds(111, 85, 196, 20);
		contentPane.add(txtFieldHighway);
		
		JLabel lblKm = new JLabel("Km:");
		lblKm.setBounds(10, 116, 46, 14);
		contentPane.add(lblKm);
		
		txtFieldKm = new JTextField();
		txtFieldKm.setColumns(10);
		txtFieldKm.setBounds(111, 113, 196, 20);
		contentPane.add(txtFieldKm);
		
		JLabel lblTipoIncidente = new JLabel("Tipo Incidente:");
		lblTipoIncidente.setBounds(10, 144, 92, 14);
		contentPane.add(lblTipoIncidente);
		
		JComboBox<String> cbIncidentType = new JComboBox<String>();
		cbIncidentType.setModel(new DefaultComboBoxModel<String>(new String[] {"1 - Vento", "2 - Chuva", "3 - Nevoeiro", "4 - Neve", "5 - Gelo na pista", "6 - Granizo", "7 - Trânsito parado", "8 - Filas de Trânsito", "9 - Trânsito Lento", "10 - Acidente Desconhecido", "11 - Incidente Desconhecido", "12 - Trabalhos na estrada", "13 - Bloqueio de pista", "14 - Bloqueio de estrada"}));
		cbIncidentType.setSelectedIndex(0);
		cbIncidentType.setBounds(111, 140, 196, 22);
		contentPane.add(cbIncidentType);
		
		JButton btnIncidentReport = new JButton("Reportar");
		btnIncidentReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnIncidentReportAction();
			}
		});
		btnIncidentReport.setBounds(10, 169, 89, 23);
		contentPane.add(btnIncidentReport);
	}
	
	private void btnIncidentReportAction() {
		
	}

	private void btnBackAction() {
		new HomeLayout(user).setVisible(true);
		this.dispose();
	}
}
