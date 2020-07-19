package profile;

import java.io.Serializable;

public class MessageInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7257849368157455180L;
	public Acount sender;
	public Acount reciever;
	public String subject;
}
