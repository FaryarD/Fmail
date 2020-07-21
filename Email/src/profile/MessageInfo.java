package profile;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7257849368157455180L;
	public String sender_usr;
	public String reciever_usr;
	public String subject;
	public String time="NONE";
	public MessageInfo(String sender_usr,String reciever_usr,String subject) {
		this.sender_usr=sender_usr;
		this.reciever_usr=reciever_usr;
		this.subject=subject;
		
	}
	public String [] getDataForTable() {
		String []out=new String [3];
		out[0]=reciever_usr;
		out[1]=subject;
		out[2]=time;
		return out;
	}
	public void setTime() {
	    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
	    Date date = new Date();  
	    time=formatter.format(date);
	}
}
