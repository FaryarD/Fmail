package server;

import java.util.ArrayList;

public class Db_Clients {
	private ArrayList<ClientAcount>clients;
	public Db_Clients() {
		clients=new ArrayList<ClientAcount>();
	}
	public void addAcount(ClientAcount acount) {
		clients.add(acount);
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
}
