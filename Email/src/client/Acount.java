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
		this.name="None";
		this.usr_name=usr_name;
		this.password=password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name=name;
	}
	public String getUsr_name() {
		return usr_name;
	}
	public String getPassword() {
		return password;
	}
}
