package myprojectjava.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class Servermain {

	private static DatagramSocket socket;
	private DatagramPacket packet;
	private	InetAddress ip;
	private Thread run,receive,manage,send;
	private static int index;
	private List<Integer> list = new ArrayList<Integer>();
	private List<Integer> response=new ArrayList<Integer>();
	private List<Server> client = new ArrayList<Server>();
	
	public static void main(String[] args) {
		try {
			socket = new DatagramSocket(8192);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new Servermain();
	}

	public Servermain(){
		new Thread("main"){
			public void run(){
				receive();	
				manage();
			}
		}.start();
		
	};
	
	public void receive(){
		receive=new Thread("recieve"){
		public void run(){
			while(true){
			byte[] data= new byte[2048];
		packet=new DatagramPacket(data,data.length);
		try {
			socket.receive(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		decode(packet);
			}}
		};
		receive.start();
		};
		
		
	
	public void manage(){
		manage=new Thread("manage"){
			public void run(){
				while(true){
				toall("/i/",0);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				for(int i=0;i<client.size();i++){
				Server c = client.get(i);
					if(!response.contains(c.getid()))
					{if(c.attempt>5)
						disconnect(c.getid(),false);
					else
						c.attempt++;
					}
					else
						{response.remove(new Integer(c.getid()));
					c.attempt=0;}
				}
				}
			}
		};
		manage.start();
		
	}
	
	private void disconnect(int id,boolean bool){
		for(int i=0;i<client.size();i++)
			{
			if(client.get(i).getid()==id)
			{client.remove(i);
			
		if(bool)
		{
			System.out.println("disconnected from id : "+ id);
		}else{
			System.out.println("connection timed out from id : " + id);
		}
		break;
			}
			}
		
	}
	
	public void toall(String str,int id){
		for(int i=0;i<client.size();i++)
		{if(str.startsWith("/i/")){
		 str = "/i/" + client.get(i).getid() + "/e/";
			packet= new DatagramPacket(str.getBytes(),str.length(),client.get(i).getaddress(),client.get(i).getport());
		}else if(client.get(i).getid()!=id)
			packet= new DatagramPacket(str.getBytes(),str.length(),client.get(i).getaddress(),client.get(i).getport());
		
		try {
			socket.send(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	
	public void decode(final DatagramPacket packet){
		Thread decode = new Thread("decode"){
			String s=new String(packet.getData());
			public void run(){
				
				if(s.startsWith("/s/"))
				{	
					client.add(new Server(s.split("/s/|/e/")[1],packet.getAddress(),packet.getPort(),getnewid()));
					System.out.println(s.split("/s/|/e/")[1] + " is connected! "+ client.size() + " " + client.get(0).getid());
				}
				else if(s.startsWith("/m/")){
					for(int i=0;i<client.size();i++){
						if(packet.getAddress()==client.get(i).getaddress() && packet.getPort()==client.get(i).getport())
							toall(s,client.get(i).getid());
					break;}	System.out.println(s.split("/m/|/e/")[1]);
					
				}else if(s.startsWith("/i/")){
					response.add(Integer.parseInt(s.split("/i/|/e/")[1]));
				}
			}
		};
		decode.start();
	};
	
	public void send(){
		send=new Thread("send"){
			public void run(){
				while(true){
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				}
			}
		};
		send.start();
	};
	
	public int getnewid(){
		for(int i=1;i<=10000;i++)
			list.add(i);
		Collections.shuffle(list);
		if(index ==10000) index=0;
			return list.get(index++);
	}
}
