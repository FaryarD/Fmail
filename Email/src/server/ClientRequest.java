package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import profile.Acount;
import profile.Message;

public class ClientRequest extends Thread{
	public static final byte REQ_SIGNUP=52;
	public static final byte REQ_LOGIN=53;
	public static final byte REQ_GETNAME=54;
	public static final byte REQ_GETINBOXINFO=55;
	public static final byte REQ_GETOUTBOXINFO=56;
	public static final byte REQ_SENDMSG=57;
	public static final byte REQ_CHANGESPECS=58;
	public static final byte ANS_CONNECTIONPROB=89;
	public static final byte ANS_NACK=90;
	public static final byte ANS_ACK=91;
	public static final byte ANS_USR_DUP=92;
	public static final byte ANS_USER_NOTFOUND=93; 
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
		Acount acount;
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
				acount=checkAcount();
				sendName(acount);
				break;
			case REQ_GETINBOXINFO:
				sendByte(ANS_ACK);
				acount=checkAcount();
				if(acount==null)sendByte(ANS_NACK);
				else{
					sendByte(ANS_ACK);
					sendObj(acount.getInbox());
				}
				break;
			case REQ_GETOUTBOXINFO:
				sendByte(ANS_ACK);
				acount=checkAcount();
				if(acount==null)sendByte(ANS_NACK);
				else{
					sendByte(ANS_ACK);
					sendObj(acount.getOutbox());
				}
				break;
			case REQ_SENDMSG:
				sendByte(ANS_ACK);
				acount=checkAcount();
				if(acount==null)sendByte(ANS_NACK);
				else{
					sendByte(ANS_ACK);
					String dist_usr=readSTR();
					Acount dist_ac=db.getAcount(dist_usr);
					if(dist_ac==null)sendByte(ANS_NACK);
					else {
						sendByte(ANS_ACK);
						Message msg=(Message) readObj();
						
						if(msg==null) {
							sendByte(ANS_NACK);
						}
						else{
							
							sendByte(ANS_ACK);
							msg.info.setTime();
							dist_ac.addToInbox(msg);
							acount.addToOutbox(msg);
							db_rw.saveDb();
						}
						
					}
				}
				break;
			case REQ_CHANGESPECS:
				System.out.println(" REQ_CHANGESPECS");
				sendByte(ANS_ACK);
				acount=checkAcount();
				if(acount==null)sendByte(ANS_NACK);
				else {
					sendByte(ANS_ACK);
					getNewInfo(acount);
					db_rw.saveDb();
				}
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
			db.addAcount(new Acount(data[2], data[0], data[1]));
			System.out.println("New Acount added,Usr_name: "+ data[0]);
			sendByte(ANS_ACK);
			db_rw.saveDb();
			
		}
	}
	private void getNewInfo(Acount acount) {
		String[] data=readSTR().split(" ; ");
		if(data.length==2) {
			acount.setName(data[1]);
			acount.setPassword(data[0]);
			sendByte(ANS_ACK);
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
	private void sendObj(Object obj) {
		try {
			ObjectOutputStream out_obj=new ObjectOutputStream(socket.getOutputStream());
			out_obj.writeObject(obj);
		} catch (IOException e) {}
	}
	public Object readObj() {
		Object obj=null;
		ObjectInputStream in_obj;
		try {
			in_obj = new ObjectInputStream(socket.getInputStream());
			obj=in_obj.readObject();
		} catch (IOException e) {}
		catch (ClassNotFoundException e) {}
		
		return obj;
	}
	private void sendName(Acount acount) {
		
		if(acount==null) {
			sendByte(ANS_NACK);
		}
		else {
			sendByte(ANS_ACK);
			System.out.println(acount.getUsr_name()+" : getName_REQ");
			sendSTR(acount.getName());
		}
	}
	private Acount checkAcount() {
		Acount acount =null;
		String[] data=readSTR().split(" ; ");
		if(data.length==2) {
			acount=db.getAcount(data[0],data[1]);
		}
		return acount;
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

