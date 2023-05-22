package client.views;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import client.logic.Incident;
import client.logic.SocketLogic;
import client.logic.User;

import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class ShowMyIncidentsLayout extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private User user;
	
	public ShowMyIncidentsLayout(User user) {
		this.user = user;
		initComponents();
		
	}
	
	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 667, 443);
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
		btnBack.setBounds(10, 11, 52, 23);
		contentPane.add(btnBack);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 119, 631, 274);
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
		
		JLabel lblNewLabel = new JLabel("Meus Incidentes");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(72, 15, 557, 14);
		contentPane.add(lblNewLabel);
		
		JButton btnGetMyIncidentsList = new JButton("Solicitar meus incidentes");
		btnGetMyIncidentsList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnGetMyIncidentsListAction();
			}
		});
		btnGetMyIncidentsList.setBounds(10, 85, 157, 23);
		contentPane.add(btnGetMyIncidentsList);
	}
	
	private void btnBackAction() {
		new HomeLayout(user).setVisible(true);
		this.dispose();
	}
	
	private void btnGetMyIncidentsListAction() {
		System.out.println("Cliente: Operação de solicitação de lista de incidentes do usuário");
		
		String userInput = new Incident().requestMyIncidentsList(user.getToken(), user.getId_usuario());
		String inputLine = new SocketLogic().sendAndReceive(userInput);
		
		Incident incident = new Incident();
		incident.searchIncidentsResponse(inputLine);
		
        JsonObject jsonObject = new Gson().fromJson(inputLine, JsonObject.class);
        if(jsonObject != null) {
        	int codigo = jsonObject.get("codigo").getAsInt();
        	if(codigo == 200) {
        		
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
        		
        	}else {
        		JOptionPane.showMessageDialog(this, "Mostrar meus incidentes deu errado", "Erro de mostrar meus incidentes", JOptionPane.ERROR_MESSAGE);
        	}
        }else {
        	JOptionPane.showMessageDialog(this, "JsonObject ta null.", "Erro de mostrar meus incidentes", JOptionPane.ERROR_MESSAGE);
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
