package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientRequest extends Thread{
	public static final byte REQ_SIGNUP=52;
	public static final byte REQ_LOGIN=53;
	public static final byte REQ_GETNAME=54;
	public static final byte ANS_CONNECTIONPROB=89;
	public static final byte ANS_NACK=90;
	public static final byte ANS_ACK=91;
	public static final byte ANS_USR_DUP=92;
	private Socket socket;
	private Db_Clients db=null;
	private DataInputStream in;
	private DataOutputStream out;
	public ClientRequest(Socket socket,Db_Clients db) {
		this.socket=socket;
		this.db=db;
	}
	public void run() {
		
		try {
			in = new DataInputStream(this.socket.getInputStream());
			out=new DataOutputStream(this.socket.getOutputStream());
			byte req=in.readByte();
			switch (req) {
			case REQ_SIGNUP:
				sendByte(ANS_ACK);
				signUp();
				break;
			case REQ_LOGIN:
				sendByte(ANS_ACK);
				logIn();
				break;
			case REQ_GETNAME:
				sendByte(ANS_ACK);
				
				break;
				
			}
			//System.out.println(in.readUTF());
			//String[] tmp=str.split("#");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	private void signUp() {
		String[] data=readSTR().split(" ; ");
		if(db.is_usrName_exist(data[0])) {
			sendByte(ANS_USR_DUP);
		}
		else {
			db.addAcount(new ClientAcount(data[2], data[0], data[1]));
			System.out.println("New Acount added,Usr_name: "+ data[0]);
			sendByte(ANS_ACK);
			
		}
	}
	private void logIn() {
		String[] data=readSTR().split(" ; ");
		if(data.length==2) {
			if(db.checkToLogin(data[0],data[1])) {
				sendByte(ANS_ACK);
				System.out.println(data[0]+" have logined.");
			}
			else {
				
				sendByte(ANS_NACK);
				
				
			}
		}
		else sendByte(ANS_NACK);
	}
	private String readSTR() {
		String str=new String();
		try {
			str=in.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}
	private void sendByte(byte b) {
		try {
			out.writeByte(b);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

