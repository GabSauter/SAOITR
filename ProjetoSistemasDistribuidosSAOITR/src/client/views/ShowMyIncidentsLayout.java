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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tableMouseClickedAction();
			}
		});
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"ID", "Data", "Rodovia", "Km", "Tipo do incidente"
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
		
		JLabel lblNewLabel_1 = new JLabel("*Clique no incidente para editar ou deletar");
		lblNewLabel_1.setBounds(407, 94, 234, 14);
		contentPane.add(lblNewLabel_1);
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
	        					incident2.getId_incident(),
	        					incident2.getDate(),
	        					incident2.getHighway(),
	        					incident2.getKm(),
	        					getIncidentTypeInString(incident2.getIncident_type())
	        			});
	        		}
        		}else
            		JOptionPane.showMessageDialog(this, "Não foi possível pegar código no jsonObject", "Operação de mostrar meus incidentes", JOptionPane.ERROR_MESSAGE);
        	}else {
        		JOptionPane.showMessageDialog(this, "Mostrar meus incidentes deu errado", "Erro de mostrar meus incidentes", JOptionPane.ERROR_MESSAGE);
        	}
        }else {
        	JOptionPane.showMessageDialog(this, "JsonObject ta null.", "Erro de mostrar meus incidentes", JOptionPane.ERROR_MESSAGE);
        }
	}
	
	private void tableMouseClickedAction() {
		int row = table.getSelectedRow();
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		int id = Integer.parseInt(model.getValueAt(row, 0).toString());
		String highway = model.getValueAt(row, 2).toString();
		int km = Integer.parseInt(model.getValueAt(row, 3).toString());
		int incidentType = getIncidentTypeFromString(model.getValueAt(row, 4).toString());
        String[] options = { "Editar", "Excluir" };

        int result = JOptionPane.showOptionDialog(null,
                "Escolha uma opção:",
                "Editar ou excluir?",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]);

        if (result == 0) {
            System.out.println("Botão editar foi clicado");
            new EditIncidentReportedLayout(user, highway, km, incidentType, id).setVisible(true);
    		this.dispose();
        } else if (result == 1) {
            System.out.println("Botão excluir foi clicado");
            System.out.println("Cliente: Operação de deletar incidente do usuário");
            
            String userInput = new Incident().deleteIncident(user.getToken(), user.getId_usuario(), id);
    		String inputLine = new SocketLogic().sendAndReceive(userInput);
    		
    		Incident incident = new Incident();
    		incident.deleteIncidentResponse(inputLine);
    		
            JsonObject jsonObject = new Gson().fromJson(inputLine, JsonObject.class);
            if(jsonObject != null) {
            	int codigo = jsonObject.get("codigo").getAsInt();
            	if(codigo == 200) {
            		JOptionPane.showMessageDialog(this, "Deletar Incidente", "Incidente deletado com sucesso", JOptionPane.INFORMATION_MESSAGE);
            		model.removeRow(row);
            	}else {
            		JOptionPane.showMessageDialog(this, "Deletar Incidente", "Erro ao tentar deletar Incidente", JOptionPane.ERROR_MESSAGE);
            	}
            }else {
            	JOptionPane.showMessageDialog(this, "JsonObject ta null.", "Erro de mostrar meus incidentes", JOptionPane.ERROR_MESSAGE);
            }
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
	
	private int getIncidentTypeFromString(String incidentTypeString) {
		switch(incidentTypeString) {
			case "Vento": {
				return 1;
			}
			case "Chuva": {
				return 2;
			}
			case "Nevoeiro": {
				return 3;
			}
			case "Neve": {
				return 4;
			}
			case "Gelo na pista": {
				return 5;
			}
			case "Granizo": {
				return 6;
			}
			case "Trânsito parado": {
				return 7;
			}
			case "Filas de trânsito": {
				return 8;
			}
			case "Trânsito lento": {
				return 9;
			}
			case "Acidente desconhecido": {
				return 10;
			}
			case "Incidente desconhecido": {
				return 11;
			}
			case "Trabalhos na estrada": {
				return 12;
			}
			case "Bloqueio de pista": {
				return 13;
			}
			case "Bloqueio de estrada": {
				return 14;
			}
			default: {
				return -1; // Return -1 for unknown incident types
			}
		}
	}
}
