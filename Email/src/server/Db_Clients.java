package server;

import java.io.Serializable;
import java.util.ArrayList;

import profile.Acount;



public class Db_Clients implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6322456842046159481L;
	private ArrayList<Acount>clients;
	public Db_Clients() {
		clients=new ArrayList<Acount>();
	}
	public void addAcount(Acount acount) {
		clients.add(acount);
	}
	public String toString() {
		String out=new String();
		for(int i=0;i<clients.size();i++) {
			out+=clients.get(i).toString()+"\n";
		}
		return out;
	}
	public boolean is_usrName_exist(String usr_name) {
		boolean out=false;
		for(int i=0;i<clients.size();i++) {
			if(clients.get(i).getUsr_name().equals(usr_name)) {
				out=true;
			}
		}
		return out;
	}
	public boolean checkToLogin(String usr_name,String pass) {
		boolean out=false;
		for(int i=0;i<clients.size();i++) {
			if(clients.get(i).getUsr_name().equals(usr_name)) {
				if(clients.get(i).getPassword().equals(pass)) {
					out=true;
				}
			}
		}
		return out;
	}
	public Acount getAcount(String usr_name,String pass) {
		Acount out=null;
		for(int i=0;i<clients.size();i++) {
			if(clients.get(i).getUsr_name().equals(usr_name)) {
				if(clients.get(i).getPassword().equals(pass)) {
					out=clients.get(i);
				}
			}
		}
		return out;
	}
}
