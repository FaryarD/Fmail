package client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;
import javax.swing.JToolBar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.JTextArea;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class EmailPage extends JFrame {

	private JPanel contentPane;
	private Acount acount;
	private JMenuBar menu_bar;
	private JMenuItem profile_name;
	private JToolBar left_sidebar;
	private JButton receivedMSG_btn;
	private JButton sentMSG_btn;
	private JButton send_btn;
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
		
		left_sidebar = new JToolBar("sda",1);
		send_btn=new JButton("New");
		send_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		receivedMSG_btn=new JButton("Recieved");
		sentMSG_btn=new JButton("Sent");
		left_sidebar.add(send_btn);
		left_sidebar.add(receivedMSG_btn);
		left_sidebar.add(sentMSG_btn);
		contentPane.add(left_sidebar, BorderLayout.WEST);
		SendRequest getName_req=new SendRequest(this, SendRequest.REQ_GETNAME, acount);
		getName_req.start();
	
		
	}
	public void refresh_profileName() {
		
		profile_name=new JMenuItem("Welcome "+acount.getName());
		menu_bar.add(profile_name);
		menu_bar.add(new JSeparator());
	
			
	
	}

}
