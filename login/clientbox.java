package myprojectjava;

import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JTextArea;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.UIManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class clientbox extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private String name, ipadress;
	private int port;
	private DatagramSocket Soket;
	private InetAddress ip;
	private Thread thread;
	private int id;
	
	private JPanel contentPane;
	private JTextField textField;
	private JTextArea txtmessage;
	/**
	 * Create the frame.
	 */
	public clientbox(String name, String ipadr, int port) {
		
		this.name = name;
		ipadress = ipadr;
		this.port = port;
		
		if(!connection(ipadress))
		{
			messenger("not connected to anyone!");	
		}else{
			messenger("connection attempted!");	
		}
		generatewindow();
		receive();
		
	}

	private boolean connection(String addres){

		try {
			Soket=new DatagramSocket();
			ip=InetAddress.getByName(addres);
			String s=new String("/s/"+ name  +"/e/");
			sendmesg(s.getBytes());
		} catch (Exception e) {	
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private void receive(){
		Thread receive=new Thread("client listen"){
		public void run(){
			while(true){
			byte[] data=new byte[2048];
		DatagramPacket packet = new DatagramPacket(data,data.length);
		try {
			Soket.receive(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 listen(new String(packet.getData()));
			}
			}
		};
		receive.start();
	};
	
	public void listen(String msg){
		Thread listen = new Thread("listen"){
		public void run(){
			if(msg.startsWith("/i/")){
				sendmesg(msg.getBytes());
				setid(Integer.parseInt(msg.split("/i/|/e/")[1]));
			}else if(msg.startsWith("/m/")){
				messenger(msg.split("/m/|/e/")[1]);
			}
		};	
		};
		listen.start();
	};
	
	private void sendmesg(byte[] data){
		thread = new Thread("Send"){
			public void run(){
				try {
					DatagramPacket packet = new DatagramPacket(data,data.length,ip,8192);
					Soket.send(packet);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		thread.start();
	};
	
	private void messenger(String message) {
		txtmessage.append(message + "\n\r");
		textField.setText("");
	}
	
	private void send(String message){
		if(message.equals(""))
			return;
		messenger(name + ": " + message);
		
		message = "/m/" + name + " : " + message + "/e/";
		sendmesg(message.getBytes());
		
	}
	
public void setid(int id){
	this.id=id;
}

public int getid(){
	return id;
} 

public void generatewindow(){
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setTitle("Client");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{560, 40};
		gbl_contentPane.rowHeights = new int[]{50, 300,50};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 0.0};
		contentPane.setLayout(gbl_contentPane);
		
		txtmessage = new JTextArea();
		txtmessage.setEnabled(false);
		txtmessage.setEditable(false);
		JScrollPane scrolltxt=new JScrollPane(txtmessage);
		GridBagConstraints gbc_txtmessage = new GridBagConstraints();
		gbc_txtmessage.fill = GridBagConstraints.BOTH;
		gbc_txtmessage.insets = new Insets(5, 0, 5, 0);
		gbc_txtmessage.gridx = 0;
		gbc_txtmessage.gridy = 0;
		gbc_txtmessage.gridwidth=2;
		gbc_txtmessage.gridheight = 2;
		contentPane.add(scrolltxt, gbc_txtmessage);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent k) {
				if(k.getKeyCode()==KeyEvent.VK_ENTER)
					send(textField.getText());
			}
		});
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5,0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 2;
		contentPane.add(textField, gbc_textField);
		textField.setColumns(10);
		
		
		
		JButton btnSend = new JButton("SEND");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				send(textField.getText());
			}
		});
		
		
		GridBagConstraints gbc_btnSend = new GridBagConstraints();
		gbc_btnSend.insets = new Insets(0, 0, 5, 0);
		gbc_btnSend.gridx = 1;
		gbc_btnSend.gridy = 2;
		contentPane.add(btnSend, gbc_btnSend);
		setVisible(true);
		textField.requestFocusInWindow();
	}

	
}
