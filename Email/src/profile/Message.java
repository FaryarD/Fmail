package profile;

import java.io.Serializable;

public class Message implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6699989794637409961L;
	public MessageInfo info;
	public String text;
	public Message(String text,MessageInfo info) {
		this.info=info;
		this.text=text;
	}
}
