package myprojectjava.server;

import java.net.InetAddress;


public class Server {
	private InetAddress ipad;
	private int port;
	private int id;
	private String name;
	public int  attempt=0;
	
	Server(String name,InetAddress ip,int port,int id){
		this.port=port;
		ipad=ip;
		this.name=name;
		this.id=id;
	}
	public int getid(){
		return id;
	}
	public InetAddress getaddress(){
		return ipad;
	}
	public int getport(){
		return port;
	}
	public String getname(){
		return name;
	}

}
