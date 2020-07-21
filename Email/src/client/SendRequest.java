package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import client.EmailPage.state;
import profile.Acount;
import profile.Inbox;
import profile.MessageInfo;
import profile.Outbox;
public class SendRequest extends Thread{
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
			else if(req==REQ_GETINBOXINFO) {
				
				EmailPage email_p=(EmailPage)obj;
				if(sendLogInInfo()==ANS_ACK) {
					
					Inbox inbox=(Inbox) readObj();
					acount.setInbox(inbox);
					if(inbox==null) {
						email_p.setTableMode(state.Error);
					}
					else {
						email_p.setTableMode(state.INBOX);
						
					}
					email_p.refreshTable();
				}
			}
			else if(req==REQ_GETOUTBOXINFO) {
				
				EmailPage email_p=(EmailPage)obj;
				if(sendLogInInfo()==ANS_ACK) {
					
					Outbox outbox=(Outbox) readObj();
					acount.setOutbox(outbox);
					if(outbox==null) {
						email_p.setTableMode(state.Error);
					}
					else {
						email_p.setTableMode(state.OUTBOX);
						
					}
					email_p.refreshTable();
				}
			}
			else if(req==REQ_SENDMSG) {
				NewMessageForm msg_form=(NewMessageForm)obj;
				byte ans=ANS_CONNECTIONPROB;
				if(sendLogInInfo()==ANS_ACK) {
					if(sendDistUser(msg_form.getMsg().info.reciever_usr)==ANS_ACK) {
						sendObj(msg_form.getMsg());
						if(readByte()==ANS_ACK) {
							ans=ANS_ACK;
							System.out.println("here");
						}
					}
					else {
						ans=ANS_USER_NOTFOUND;
					}
				}
				System.out.println(ans);
				msg_form.serverResposne(ans);
				
			}
			else if(req==REQ_CHANGESPECS) {
				ChangeSpecs change_p=(ChangeSpecs) obj;
				byte ans=ANS_CONNECTIONPROB;
				if(sendLogInInfo()==ANS_ACK) {
					if(sendAcountInfo(change_p.getAcount_clone())==ANS_ACK) {
						ans=ANS_ACK;
					}
				}
				else {
					ans=ANS_NACK;
				}
				change_p.server_ans(ans);
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
	private void sendObj(Object obj) {
		try {
			ObjectOutputStream out_obj=new ObjectOutputStream(socket.getOutputStream());
			out_obj.writeObject(obj);
		} catch (IOException e) {}
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
	public byte sendAcountInfo(Acount ac) {
		byte out=ANS_CONNECTIONPROB;
		try {
			String str=ac.getPassword()+" ; "+ac.getName();
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
	private byte sendDistUser(String usr_name) {
		byte out=ANS_CONNECTIONPROB;
		try {
			sendSTR(usr_name);
			out=readByte();
			
		} catch (Exception e) {}
		return out;
	}
	 public static void infoBox(String infoMessage, String titleBar)
	    {
	        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
	    }
}
