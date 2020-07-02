package server;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
	private static Db_Clients db=null;
	private static final int port=1025;
	public static void main(String[] args) {
		db=new Db_Clients();
		
		
		ServerSocket server=null;
		try {
			server=new ServerSocket(port);
			System.out.println("Server Started at port "+port);
			while(true) {
				Socket socket=server.accept();
				ClientRequest req=new ClientRequest(socket,db);
				req.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				server.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

	}
	
}
