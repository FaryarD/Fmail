package profile;

import java.io.Serializable;

import server.Db_Clients;
import server.Db_readWrite;

public class Acount implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7285872682565775115L;
	private String name;
	private String usr_name;
	private String password;
	private Inbox inbox;
	private Outbox outbox;
	public Acount(String name,String usr_name,String password) {
		this.name=name;
		this.usr_name=usr_name;
		this.password=password;
		this.inbox=new Inbox();
		this.setOutbox(new Outbox());
	}
	public Acount(String usr_name,String password) {
		this.name="None";
		this.usr_name=usr_name;
		this.password=password;
		this.inbox=new Inbox();
		this.setOutbox(new Outbox());
	}
	public Inbox getInbox() {
		return inbox;
	}
	public void setInbox(Inbox inbox) {
		this.inbox=inbox;
	}
	public Outbox getOutbox() {
		return outbox;
	}
	public void setOutbox(Outbox outbox) {
		this.outbox = outbox;
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
	public void setPassword(String password) {
		this.password=password;
	}
	public String toString() {
		return "name : "+name+" | "+"user name : "+usr_name;
	}
	public void addToInbox(Message msg) {
		inbox.addMessage(msg);
	}
	public void addToOutbox(Message msg) {
		outbox.addMessage(msg);

	}
}
