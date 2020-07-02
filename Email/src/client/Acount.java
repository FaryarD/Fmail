package client;

public class Acount {
	private String name;
	private String usr_name;
	private String password;
	public Acount(String name,String usr_name,String password) {
		this.name=name;
		this.usr_name=usr_name;
		this.password=password;
	}
	public Acount(String usr_name,String password) {
		this.usr_name=usr_name;
		this.password=password;
	}
	public String getName() {
		return name;
	}
	public String getUsr_name() {
		return usr_name;
	}
	public String getPassword() {
		return password;
	}
	public void LoadName() {
		SendRequest send_req=new SendRequest(this,SendRequest.REQ_GETNAME,this);
		send_req.start();
	}
}
