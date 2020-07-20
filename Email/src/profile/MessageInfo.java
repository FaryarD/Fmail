package profile;

import java.io.Serializable;

public class MessageInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7257849368157455180L;
	public String sender_usr;
	public String reciever_usr;
	public String subject;
	public MessageInfo(String sender_usr,String reciever_usr,String subject) {
		
	}
	public String [] getDataForTable() {
		String []out=new String [2];
		out[0]=sender_usr;
		out[1]=subject;
		return out;
	}
}
