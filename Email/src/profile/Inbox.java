package profile;

import java.io.Serializable;
import java.util.ArrayList;

public class Inbox implements Serializable{

	private static final long serialVersionUID = -1328989554186411612L;
	private ArrayList<Message>messages=new ArrayList<Message>();
	public Inbox() {
		messages=new ArrayList<Message>();
	}
	public void addMessage(Message msg) {
		messages.add(0, msg);
	}
	public void delMessage(Message msg) {
		messages.remove(msg);
	}
	public ArrayList<MessageInfo> getAllMessageInfo(){
		ArrayList<MessageInfo>msg_info=new ArrayList<MessageInfo>();
		for (int i=0;i<messages.size();i++) {
			msg_info.add(messages.get(i).info);
		}
		return msg_info;
	}
	public Message getMessageAt(int index) {
		return messages.get(index);
	}
}
