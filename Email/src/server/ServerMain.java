package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
	public static void main(String[] args) {
		System.out.println("Server started;)");
		ServerSocket server=null;
		try {
			server=new ServerSocket(12);
			while(true) {
				Socket socket=server.accept();
				ClientRequest req=new ClientRequest(socket);
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
