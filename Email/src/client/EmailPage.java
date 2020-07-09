package client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

public class EmailPage extends JFrame {

	private JPanel contentPane;
	private Acount acount;
	private JMenuBar menu_bar;
	private JMenuItem profile_name;
	public EmailPage(StartMenu start_menu,Acount acount) {
		this.acount=acount;
		//acount.receiveName();
		//System.out.println(acount.getName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		menu_bar=new JMenuBar();
		this.getContentPane().add(menu_bar, BorderLayout.PAGE_START);
		SendRequest getName=new SendRequest(this, SendRequest.REQ_GETNAME, acount);
		getName.start();
		menu_bar.add(new JSeparator());
		profile_name=new JMenuItem(acount.getName());
		
	}
	

}
