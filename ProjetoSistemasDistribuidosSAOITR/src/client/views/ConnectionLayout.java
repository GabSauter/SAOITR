package client.views;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JFormattedTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ConnectionLayout extends JFrame {

	private JPanel contentPane;
	private JTextField txtFieldPort;
	
	public ConnectionLayout() {
		initComponents();
	}
	
	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 291, 203);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnConnectAction();
			}
		});
		btnConnect.setBounds(21, 70, 231, 23);
		contentPane.add(btnConnect);
		
		txtFieldPort = new JTextField();
		txtFieldPort.setBounds(103, 39, 149, 20);
		contentPane.add(txtFieldPort);
		txtFieldPort.setColumns(10);
		
		JLabel lblIp = new JLabel("Server IP:");
		lblIp.setBounds(21, 11, 80, 14);
		contentPane.add(lblIp);
		
		JLabel lblPort = new JLabel("Port:");
		lblPort.setBounds(21, 42, 80, 14);
		contentPane.add(lblPort);
		
		JLabel lblConnectionResult = new JLabel("");
		lblConnectionResult.setVerticalAlignment(SwingConstants.TOP);
		lblConnectionResult.setToolTipText("Result");
		lblConnectionResult.setBounds(21, 104, 231, 49);
		contentPane.add(lblConnectionResult);
		
		JFormattedTextField txtFieldIp = new JFormattedTextField();
		txtFieldIp.setBounds(103, 8, 149, 20);
		contentPane.add(txtFieldIp);
	}
	
	private void btnConnectAction() {
		
	}
}
