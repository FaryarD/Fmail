package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import profile.Acount;
public class SendRequest extends Thread{
	public static final byte REQ_SIGNUP=52;
	public static final byte REQ_LOGIN=53;
	public static final byte REQ_GETNAME=54;
	public static final byte ANS_CONNECTIONPROB=89;
	public static final byte ANS_NACK=90;
	public static final byte ANS_ACK=91;
	public static final byte ANS_USR_DUP=92;
	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	public boolean is_ready=false;
	byte req;
	Object obj;
	private Acount acount;
	public SendRequest(Object obj,byte req,Acount acount) {
		this.obj=obj;
		this.req=req;
		this.acount=acount;
		
		is_ready=false;
	}
	public void run() {
		int i=0;
		
		while(is_ready==false && i<1000) {
			try {
				socket = new Socket("127.0.0.1", 1025);
				in = new DataInputStream(socket.getInputStream());
				out=new DataOutputStream(socket.getOutputStream());
				if(socket.isConnected()) {
					out.writeByte(req);
					if(in.readByte()==ANS_ACK) {
						is_ready=true;
					}
				}
			} catch (IOException e) {}
			
			i++;
		}
		if(is_ready) {
			if(req==REQ_SIGNUP) {
				SignUp signup_p=(SignUp) obj;
				byte ans=sendSignUpInfo();
				switch (ans) {
				case SendRequest.ANS_ACK:
					signup_p.server_permission(true);
					break;
				case SendRequest.ANS_USR_DUP:
					signup_p.server_permission(false);
					break;
				}
			}
			else if(req==REQ_LOGIN) {
				StartMenu start_menu=(StartMenu) obj;
				if(sendLogInInfo()==SendRequest.ANS_ACK) {
					start_menu.server_permission(true);
				}
				else {
					start_menu.server_permission(false);
				}
			}
			else if(req==REQ_GETNAME) {
				EmailPage email_p=(EmailPage) obj;
				if(sendLogInInfo()==ANS_ACK) {
					acount.setName(readSTR());
					email_p.refresh_profileName();
				}
			}
			
				
		}
		else {
			infoBox("1.sever may be down(contact with supervisors)\n2.Check Your Internet Connection", "Connection Faild!!!");
		}
	}
	public void sendSTR(String str) {
		try {
			out.writeUTF(str);
		} catch (IOException e) {}
	}
	public String readSTR() {
		String str="";
		try {
			 str=in.readUTF();
		} catch (IOException e) {}
		return str;
	}
	public byte readByte() {
		byte ans=ANS_CONNECTIONPROB;
		try {
			ans=in.readByte();
		} catch (IOException e) {}	
		return ans;
	}
	public byte sendSignUpInfo() {
		byte out=ANS_CONNECTIONPROB;
		try {
			String str=acount.getUsr_name()+" ; "+acount.getPassword()+" ; "+acount.getName();
			sendSTR(str);
			out=readByte();
		} catch (Exception e) {}
		
		return out;
	}
	public byte sendLogInInfo() {
		byte out=ANS_CONNECTIONPROB;
		try {
			String str=acount.getUsr_name()+" ; "+acount.getPassword();
			sendSTR(str);
			out=readByte();
			
		} catch (Exception e) {}
		return out;
	}
	
	 public static void infoBox(String infoMessage, String titleBar)
	    {
	        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
	    }
}
