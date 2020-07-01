package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientRequest extends Thread{
	Socket socket;
	public ClientRequest(Socket socket) {
		this.socket=socket;
	}
	public void run() {
		
		try {
			DataInputStream in = new DataInputStream(this.socket.getInputStream());
			DataOutputStream out=new DataOutputStream(this.socket.getOutputStream());
			System.out.println(in.readUTF());
			System.out.println(in.readUTF());
			//String[] tmp=str.split("#");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

