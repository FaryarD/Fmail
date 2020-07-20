package profile;

import java.io.Serializable;

public class Acount implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7285872682565775115L;
	private String name;
	private String usr_name;
	private String password;
	private Inbox inbox;
	public Acount(String name,String usr_name,String password) {
		this.name=name;
		this.usr_name=usr_name;
		this.password=password;
	}
	public Acount(String usr_name,String password) {
		this.name="None";
		this.usr_name=usr_name;
		this.password=password;
	}
	public Inbox getInbox() {
		return inbox;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name=name;
	}
	public String getUsr_name() {
		return usr_name;
	}
	public String getPassword() {
		return password;
	}
	public String toString() {
		return "name : "+name+" | "+"user name : "+usr_name;
	}
	public void addToInbox(Message msg) {
		inbox.addMessage(msg);
	}
}
