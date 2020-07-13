package profile;

import java.io.Serializable;

public class Message implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6699989794637409961L;
	public Acount sender;
	public Acount reciever;
	public String subject;
	public String text;
}
