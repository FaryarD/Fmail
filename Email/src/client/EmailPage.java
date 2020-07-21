package client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import profile.Acount;
import profile.Message;
import profile.MessageInfo;

import javax.swing.JToolBar;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EmailPage extends JFrame {
	public enum state{NONE,OUTBOX,INBOX,Error};
	private state table_mode=state.NONE;
	private JPanel contentPane;
	private Acount acount;
	private JMenuBar menu_bar;
	private JMenuItem profile_name;
	private JToolBar left_sidebar;
	private JButton receivedMSG_btn;
	private JButton sentMSG_btn;
	private JButton send_btn;
	private JButton log_out;
	private JScrollPane info_panel;
	private JTable table;
	private DefaultTableModel table_data = new DefaultTableModel(0, 0);
	private String[] column_names = { "Welcome to Fmail"};
	private JButton btnChangePass;
	private StartMenu start_menu;
	public EmailPage(StartMenu start_menu,Acount acount) {
		this.start_menu=start_menu;
		EmailPage self=this;
		this.acount=acount;
	
		//acount.receiveName();
		//System.out.println(acount.getName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 508, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		column_names=new String[1];
		column_names[0]="Welcome To Fmail!";
		table_data.setColumnIdentifiers(column_names);
		table = new JTable(); 
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				openMessage(table.getSelectedRow());
			}
		});
		table.setBounds(30, 40, 200, 300); 
		table.setModel(table_data);
		menu_bar=new JMenuBar();
		this.getContentPane().add(menu_bar, BorderLayout.PAGE_START);
		
		left_sidebar = new JToolBar("",1);
		send_btn=new JButton("New");
		send_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				NewMessageForm new_msgForm=new NewMessageForm(acount);
				new_msgForm.setVisible(true);
			}
		});
		receivedMSG_btn=new JButton("Inbox");
		receivedMSG_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				column_names[0]="Fetching Data...";
				table_data.setColumnIdentifiers(column_names);
				table.repaint();
				SendRequest req_inbox_info=new SendRequest(self,SendRequest.REQ_GETINBOXINFO, acount);
				req_inbox_info.start();
			}
		});
		sentMSG_btn=new JButton("Outbox");
		sentMSG_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				column_names[0]="Fetching Data...";
				table_data.setColumnIdentifiers(column_names);
				table.repaint();
				SendRequest req_outbox_info=new SendRequest(self,SendRequest.REQ_GETOUTBOXINFO, acount);
				req_outbox_info.start();
			}
		});
		log_out=new JButton("Log Out");
		log_out.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				logOut();
				
			}
		});
		left_sidebar.add(send_btn);
		left_sidebar.add(receivedMSG_btn);
		left_sidebar.add(sentMSG_btn);
		left_sidebar.add(log_out);
		contentPane.add(left_sidebar, BorderLayout.WEST);
		
		btnChangePass = new JButton("Change specs");
		btnChangePass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ChangeSpecs change=new ChangeSpecs(self,acount);
				change.setVisible(true);
			}
		});
		left_sidebar.add(btnChangePass);
		
		info_panel = new JScrollPane(table);
		contentPane.add(info_panel, BorderLayout.CENTER);
		SendRequest getName_req=new SendRequest(this, SendRequest.REQ_GETNAME, acount);
		getName_req.start();
	
		
	}
	public void setTableMode(state mode) {
		this.table_mode=mode;
	}
	public void refresh_profileName() {
		
		profile_name=new JMenuItem("Welcome "+acount.getName());
		menu_bar.add(profile_name);
		menu_bar.add(new JSeparator());
	
			
	
	}
	private void removeAllRows() {
		int c=table_data.getRowCount();
		for(int i=c-1;i>=0;i--) {
			table_data.removeRow(i);;
		}	
	}
	public void refreshTable() {
		removeAllRows();
		if(table_mode==state.INBOX) {
			table_data.setColumnIdentifiers(new String[] {"Sender","Subject","Date"});
			ArrayList<MessageInfo>msg_info=acount.getInbox().getAllMessageInfo();
			for(int i=0;i<msg_info.size();i++) {
				table_data.addRow(msg_info.get(i).getDataForTable());
			}
		}
		else if(table_mode==state.OUTBOX){
			table_data.setColumnIdentifiers(new String[] {"Reciever","Subject","Date"});
			ArrayList<MessageInfo>msg_info=acount.getOutbox().getAllMessageInfo();
			for(int i=0;i<msg_info.size();i++) {
				table_data.addRow(msg_info.get(i).getDataForTable());
			}
		}
		else if(table_mode==state.NONE){
			table_data.setColumnIdentifiers(new String[] {"Welcome To Fmail."});
			
		}
		else if(table_mode==state.Error) {
			table_data.setColumnIdentifiers(new String[] {"Somthing's Wrong.Check your connectivity Or contact with a supervisor"});
			
		}
	
		table.repaint();
	}
	private void openMessage(int index) {
		if(table_mode==state.INBOX) {
			Message msg=acount.getInbox().getMessageAt(index);
			MessageForm msgForm=new MessageForm(msg);
			msgForm.setVisible(true);
		}
		else if(table_mode==state.OUTBOX) {
			Message msg=acount.getOutbox().getMessageAt(index);
			MessageForm msgForm=new MessageForm(msg);
			msgForm.setVisible(true);
		}
	}
	public void logOut() {
		
		setVisible(false);
		start_menu.logOut();
	}

}
