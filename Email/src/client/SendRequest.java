package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class SendRequest {
	public enum Requests{SIGNUP};
	private Requests req;
	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	public SendRequest(Requests req) {
		this.req=req;
		Socket socket;
		try {
			socket = new Socket("127.0.0.1", 1024);
			in = new DataInputStream(socket.getInputStream());
			out=new DataOutputStream(socket.getOutputStream());
			out.writeUTF(req+"");
		} catch (UnknownHostException e) {
		} catch (IOException e) {}
	}
	public void send(String str) {
		try {
			out.writeUTF(str);
		} catch (IOException e) {}
	}
	public String read() {
		String str="";
		try {
			 str=in.readUTF();
		} catch (IOException e) {}
		return str;
	}
}
