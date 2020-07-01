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
}
