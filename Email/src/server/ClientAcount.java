package server;

import java.io.Serializable;

public class ClientAcount implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8301053891754985919L;
	private String name;
	private String usr_name;
	private String password;
	public ClientAcount(String name,String usr_name,String password) {
		this.name=name;
		this.usr_name=usr_name;
		this.password=password;
	}
	public String getName() {
		return name;
	}
	public String getUsr_name() {
		return usr_name;
	}
	public String getPassword() {
		return password;
	}
	public String toString() {
		return "name : "+name+"\tuser name: "+usr_name;
	}
}
