package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import client.Acount;

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
	Db_readWrite db_rw;
	public ClientRequest(Socket socket,Db_Clients db,Db_readWrite db_rw) {
		this.db_rw=db_rw;
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
				System.out.println(" getName_REQ");
				sendByte(ANS_ACK);
				sendName();
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
			db_rw.saveDb();
			
		}
	}
	private void logIn() {
		String[] data=readSTR().split(" ; ");
		if(data.length==2) {
			if(db.checkToLogin(data[0],data[1])) {
				sendByte(ANS_ACK);
				System.out.println(data[0]+" : Logined");
			}
			else {
				
				sendByte(ANS_NACK);
				
				
			}
		}
		else sendByte(ANS_NACK);
	}
	private void sendName() {
		String[] data=readSTR().split(" ; ");
		System.out.println(data[0]+data[1]);
		if(data.length==2) {
			ClientAcount acount=db.getAcount(data[0],data[1]);
				
				if(acount==null) {
					sendByte(ANS_NACK);
				}
				else {
					sendByte(ANS_ACK);
					System.out.println(acount.getUsr_name()+" : getName_REQ");
					sendSTR(acount.getName());
				}
			}
			else {
				
				sendByte(ANS_NACK);
				
			}
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
	private void sendSTR(String str) {
		
		try {
			out.writeUTF(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void sendByte(byte b) {
		try {
			out.writeByte(b);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

