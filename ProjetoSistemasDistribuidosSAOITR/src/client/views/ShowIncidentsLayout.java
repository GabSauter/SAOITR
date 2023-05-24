package client.views;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import client.logic.Incident;
import client.logic.SocketLogic;
import client.logic.User;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
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
	private JComboBox<String> cbPeriod;
	private JTextField txtFieldInitialLane;
	private JTextField txtFieldFinalLane;
	private JTextField txtFieldHighway;
	private JButton btnShowIncidents;
	
	private User user;
	private JFormattedTextField txtFieldDate;

	public ShowIncidentsLayout(User user) {
		this.user = user;
		initComponents();
	}
	
	private void initComponents() {
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
		
		cbPeriod = new JComboBox<String>();
		cbPeriod.setModel(new DefaultComboBoxModel<String>(new String[] {"Manhã - 06:00 ~ 11:59", "Tarde - 12:00 ~ 17:59", "Noite - 18:00 ~ 23:59", "Madrugada - 00:00 ~ 05:59"}));
		cbPeriod.setSelectedIndex(0);
		cbPeriod.setBounds(96, 116, 184, 22);
		contentPane.add(cbPeriod);
		
		txtFieldInitialLane = new JTextField();
		txtFieldInitialLane.setBounds(96, 92, 86, 20);
		contentPane.add(txtFieldInitialLane);
		txtFieldInitialLane.setColumns(10);
		
		txtFieldFinalLane = new JTextField();
		txtFieldFinalLane.setColumns(10);
		txtFieldFinalLane.setBounds(270, 92, 86, 20);
		contentPane.add(txtFieldFinalLane);
		
		try {
			MaskFormatter highwayMask = new MaskFormatter("UU-###");
			txtFieldHighway = new JFormattedTextField(highwayMask);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		txtFieldHighway.setBounds(96, 67, 172, 20);
		contentPane.add(txtFieldHighway);
		txtFieldHighway.setColumns(10);
		
		btnShowIncidents = new JButton("Buscar Incidentes");
		btnShowIncidents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnShowIncidents();
			}
		});
		btnShowIncidents.setBounds(96, 149, 184, 23);
		contentPane.add(btnShowIncidents);
		
		try {
			MaskFormatter dataMask = new MaskFormatter("####-##-##");
			txtFieldDate = new JFormattedTextField(dataMask);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		txtFieldDate.setColumns(10);
		txtFieldDate.setBounds(96, 42, 172, 20);
		contentPane.add(txtFieldDate);
	}

	private void btnBackAction() {
		new HomeLayout(user).setVisible(true);
		this.dispose();
	}
	
	private void btnShowIncidents() {
		
		System.out.println("Cliente: Operação de procurar incidente");
		
		String date = txtFieldDate.getText().toString() + " 00:00:00";
		System.out.println(date);
		String highway = txtFieldHighway.getText().toString();
		String lanes = txtFieldInitialLane.getText().toString() + "-" + txtFieldFinalLane.getText().toString();
		if(lanes.equals("-"))
			lanes = "";
		
		int period = cbPeriod.getSelectedIndex() + 1;
		String userInput = new Incident().searchIncidents(highway, date, lanes, period);
		String inputLine = new SocketLogic().sendAndReceive(userInput);
		
		Incident incident = new Incident();
		incident.searchIncidentsResponse(inputLine);
		
        JsonObject jsonObject = new Gson().fromJson(inputLine, JsonObject.class);
        if(jsonObject != null) {
        	
        	int codigo = jsonObject.get("codigo").getAsInt();
        	if(codigo == 200) {
        		if(jsonObject.get("codigo") != null && !jsonObject.get("codigo").isJsonNull()) {
	        		DefaultTableModel model = (DefaultTableModel) table.getModel();
	        		model.fireTableDataChanged();
	        		model.setRowCount(0);
	        		
	        		List<Incident> incidents = new ArrayList<Incident>();
	        		
	        		for (JsonElement jsonElement : incident.getIncidentsList()) {
	        			Incident incident3 = new Incident();
	        			
	        			incident3.setId_incident(jsonElement.getAsJsonObject().get("id_incidente").getAsInt());
	        			incident3.setDate(jsonElement.getAsJsonObject().get("data").getAsString());
	        			incident3.setHighway(jsonElement.getAsJsonObject().get("rodovia").getAsString());
	        			incident3.setKm(jsonElement.getAsJsonObject().get("km").getAsInt());
	        			incident3.setIncident_type(jsonElement.getAsJsonObject().get("tipo_incidente").getAsInt());
	        			
	        			incidents.add(incident3);
	                }
	        		
	        		for(Incident incident2: incidents) {
	        			model.addRow(new Object[] {
	        					incident2.getDate(),
	        					incident2.getHighway(),
	        					incident2.getKm(),
	        					getIncidentTypeInString(incident2.getIncident_type())
	        			});
	        		}
        		}else
            		JOptionPane.showMessageDialog(this, "Não foi possível pegar código no jsonObject", "Operação de mostrar incidentes", JOptionPane.ERROR_MESSAGE);
        	}else {
        		JOptionPane.showMessageDialog(this, "Mostrar incidentes deu errado, talvez nenhum incidente foi encontrado", "Erro de mostrar incidentes", JOptionPane.ERROR_MESSAGE);
        	}
        }else {
        	JOptionPane.showMessageDialog(this, "JsonObject ta null.", "Erro de mostrar incidentes", JOptionPane.ERROR_MESSAGE);
        }
	}
	
	private String getIncidentTypeInString(int incidentType) {
		switch(incidentType) {
			case 1:{
				return "Vento";
			}
			case 2:{
				return "Chuva";
			}
			case 3:{
				return "Nevoeiro";
			}
			case 4:{
				return "Neve";
			}
			case 5:{
				return "Gelo na pista";
			}
			case 6:{
				return "Granizo";
			}
			case 7:{
				return "Trânsito parado";
			}
			case 8:{
				return "Filas de trânsito";
			}
			case 9:{
				return "Trânsito lento";
			}
			case 10:{
				return "Acidente desconhecido";
			}
			case 11:{
				return "Incidente desconhecido";
			}
			case 12:{
				return "Trabalhos na estrada";
			}
			case 13:{
				return "Bloqueio de pista";
			}
			case 14:{
				return "Bloqueio de estrada";
			}
		}
		return "Erro";
	}
}
