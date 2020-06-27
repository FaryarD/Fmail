package client;

public class ClientMain {
	private static StartMenu start_menu;
	public static void main(String[] args) {
		try {
			start_menu=new StartMenu();
			start_menu.setVisible(true);
		} catch (Exception e) {}
		
	}
}
