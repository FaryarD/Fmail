package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
	private static Db_Clients db=null;
	private static final int port=1025;
	
	public static void main(String[] args) {
		Db_readWrite db_rw=new Db_readWrite();
		db=db_rw.getDb();
		ServerSocket server=null;
		System.out.println(db.toString());
		try {
			server=new ServerSocket(port);
			System.out.println("Server Started at port "+port);
			while(true) {
				Socket socket=server.accept();
				ClientRequest req=new ClientRequest(socket,db,db_rw);
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
