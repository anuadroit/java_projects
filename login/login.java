package myprojectjava;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.awt.event.ActionListener;

public class anubhavchat extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtHello;
	private JTextField textField;
	private JLabel lblNewLabel_1;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					anubhavchat frame = new anubhavchat();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public anubhavchat() {
		setTitle("Login");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 340, 410);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Name : ");
		lblNewLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		lblNewLabel.setBounds(137, 39, 49, 14);
		contentPane.add(lblNewLabel);
		
		txtHello = new JTextField();
		txtHello.setBounds(64, 62, 195, 20);
		contentPane.add(txtHello);
		txtHello.setColumns(10);
		
		JLabel lblIpAdress = new JLabel("ip adress:");
		lblIpAdress.setFont(new Font("Segoe UI Black", Font.PLAIN, 13));
		lblIpAdress.setBounds(129, 111, 66, 14);
		contentPane.add(lblIpAdress);
		
		textField = new JTextField();
		textField.setBounds(69, 129, 185, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		lblNewLabel_1 = new JLabel("Port :");
		lblNewLabel_1.setFont(new Font("Arial Black", Font.BOLD | Font.ITALIC, 12));
		lblNewLabel_1.setBounds(137, 177, 49, 14);
		contentPane.add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(92, 205, 140, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		
		JButton btnNewButton = new JButton("LogIn");
		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				String name=txtHello.getText();
				String ipadr=textField.getText();
				int port = Integer.parseInt(textField_1.getText());
				dispose();
				new clientbox(name,ipadr,port);
			}
		});
		
		btnNewButton.setFont(new Font("Segoe Print", Font.BOLD | Font.ITALIC, 13));
		btnNewButton.setBounds(117, 310, 115, 23);
		contentPane.add(btnNewButton);
		
	
	}
}
