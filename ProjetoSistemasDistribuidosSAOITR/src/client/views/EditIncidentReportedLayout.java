package client.views;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import client.logic.Incident;
import client.logic.SocketLogic;
import client.logic.User;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.awt.event.ActionEvent;

public class EditIncidentReportedLayout extends JFrame {

	private JPanel contentPane;
	private JTextField txtFieldHighway;
	private JTextField txtFieldKm;

	private User user;
	private JComboBox<String> cbIncidentType;

	private String highway;
	private int km;
	private int incidentType;
	private int incidentId;

	public EditIncidentReportedLayout(User user, String highway, int km, int incidentType, int incidentId) {
		this.user = user;
		this.highway = highway;
		this.km = km;
		this.incidentType = incidentType;
		this.incidentId = incidentId;
		initComponents();
	}

	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 394, 235);
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

		JLabel lblRodovia = new JLabel("Rodovia:");
		lblRodovia.setBounds(10, 48, 46, 14);
		contentPane.add(lblRodovia);

		try {
			MaskFormatter highwayMask = new MaskFormatter("UU-###");
			txtFieldHighway = new JFormattedTextField(highwayMask);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		txtFieldHighway.setColumns(10);
		txtFieldHighway.setBounds(111, 45, 196, 20);
		contentPane.add(txtFieldHighway);
		txtFieldHighway.setText(this.highway);

		JLabel lblKm = new JLabel("Km:");
		lblKm.setBounds(10, 76, 46, 14);
		contentPane.add(lblKm);

		txtFieldKm = new JTextField();
		txtFieldKm.setColumns(10);
		txtFieldKm.setBounds(111, 73, 196, 20);
		contentPane.add(txtFieldKm);
		txtFieldKm.setText(this.km + "");

		JLabel lblTipoIncidente = new JLabel("Tipo Incidente:");
		lblTipoIncidente.setBounds(10, 104, 92, 14);
		contentPane.add(lblTipoIncidente);

		cbIncidentType = new JComboBox<String>();
		cbIncidentType
				.setModel(new DefaultComboBoxModel<String>(new String[] { "1 - Vento", "2 - Chuva", "3 - Nevoeiro",
						"4 - Neve", "5 - Gelo na pista", "6 - Granizo", "7 - Trânsito parado", "8 - Filas de Trânsito",
						"9 - Trânsito Lento", "10 - Acidente Desconhecido", "11 - Incidente Desconhecido",
						"12 - Trabalhos na estrada", "13 - Bloqueio de pista", "14 - Bloqueio de estrada" }));
		cbIncidentType.setSelectedIndex(incidentType - 1);
		cbIncidentType.setBounds(111, 100, 196, 22);
		contentPane.add(cbIncidentType);

		JButton btnEditIncidentReported = new JButton("Editar Incidente");
		btnEditIncidentReported.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnIncidentReportAction();
			}
		});
		btnEditIncidentReported.setBounds(111, 133, 196, 23);
		contentPane.add(btnEditIncidentReported);
	}

	private void btnIncidentReportAction() {
		try {
			System.out.println("Cliente: Operação de reportar incidente");

			final DateTimeFormatter CUSTOM_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime ldt = LocalDateTime.now();
			String date = ldt.format(CUSTOM_FORMATTER);

			String highway = txtFieldHighway.getText().toString();
			int km = Integer.parseInt(txtFieldKm.getText().toString());
			int incidentType = cbIncidentType.getSelectedIndex() + 1;

			String userInput = new Incident().editReportedIncident(user.getToken(), user.getId_usuario(), date, highway,
					km, incidentType, incidentId);
			String inputLine = new SocketLogic().sendAndReceive(userInput);

			Incident incident = new Incident();
			incident.editReportedIncidentResponse(inputLine);

			JsonObject jsonObject = new Gson().fromJson(inputLine, JsonObject.class);
			int codigo = 0;
			if (jsonObject != null) {
				if (jsonObject.get("codigo") != null && !jsonObject.get("codigo").isJsonNull()) {
					codigo = jsonObject.get("codigo").getAsInt();
					if (codigo == 200) {
						System.out.println("Incidente reportado com sucesso");
						JOptionPane.showMessageDialog(this, "Incidente reportado editado com sucesso",
								"Operação de editar Incidente reportado", JOptionPane.INFORMATION_MESSAGE);
						new ShowMyIncidentsLayout(user).setVisible(true);
						this.dispose();
					} else {
						System.out.println(jsonObject.get("mensagem").getAsString());
						JOptionPane.showMessageDialog(this, "Falha em editar Incidente reportado",
								"Operação de editar Incidente reportado", JOptionPane.ERROR_MESSAGE);
					}
				} else
					JOptionPane.showMessageDialog(this, "Não foi possível pegar código no jsonObject",
							"Operação de editar Incidente reportado", JOptionPane.ERROR_MESSAGE);
			} else {
				System.out.println("Incidente reportado: JsonObject ta null");
				JOptionPane.showMessageDialog(this, "JsonObject ta null", "Operação de editar Incidente reportado",
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (JsonSyntaxException e) {
			JOptionPane.showMessageDialog(null, "Houve erro com Json null.");
		}catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Houve erro inesperado.");
		}
	}

	private void btnBackAction() {
		new ShowMyIncidentsLayout(user).setVisible(true);
		this.dispose();
	}
}
