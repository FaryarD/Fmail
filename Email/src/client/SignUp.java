package client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class SignUp extends JFrame {

	private JPanel contentPane;
	private JTextField name_text;
	private JTextField usrname_text;
	private JPasswordField password_text;
	private StartMenu start_menu;

	private JLabel warning_text;
	private Acount acount=null;
	private String usr_name,pass,name;
	private SignUp self=this;
	public SignUp(StartMenu start_menu) {
		usr_name=new String("f");
		pass=new String("1234");
		name=new String("s");
		this.start_menu=start_menu;
		setBackground(Color.DARK_GRAY);
		setResizable(false);
		setTitle("FMail | SignUp");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 364, 199);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton cancel_btn = new JButton("Cancel");
		cancel_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				backToMenu();
			}
		});
		cancel_btn.setBackground(Color.LIGHT_GRAY);
		cancel_btn.setBounds(190, 125, 117, 25);
		contentPane.add(cancel_btn);
		
		JButton signup_btn = new JButton("Sign Up");
		signup_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				acount=new Acount(name, usr_name, pass);
				SendRequest send_req=new SendRequest(self,SendRequest.REQ_SIGNUP , acount);
				send_req.start();
			}
		});
		signup_btn.setBackground(Color.LIGHT_GRAY);
		signup_btn.setBounds(60, 125, 117, 25);
		contentPane.add(signup_btn);
		
		JLabel lblName = new JLabel("Name");
		lblName.setForeground(Color.LIGHT_GRAY);
		lblName.setBounds(12, 21, 70, 15);
		contentPane.add(lblName);
		
		JLabel lblUserName = new JLabel("User Name");
		lblUserName.setForeground(Color.LIGHT_GRAY);
		lblUserName.setBounds(12, 48, 95, 15);
		contentPane.add(lblUserName);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(Color.LIGHT_GRAY);
		lblPassword.setBounds(12, 75, 70, 15);
		contentPane.add(lblPassword);
		
		name_text = new JTextField();
		name_text.setBackground(Color.WHITE);
		name_text.setBounds(126, 19, 160, 16);
		contentPane.add(name_text);
		name_text.setColumns(10);
		
		usrname_text = new JTextField();
		usrname_text.setColumns(10);
		usrname_text.setBounds(126, 46, 160, 16);
		contentPane.add(usrname_text);
		
		password_text = new JPasswordField();
		password_text.setBounds(126, 70, 160, 16);
		contentPane.add(password_text);
		
		warning_text = new JLabel("");
		warning_text.setForeground(Color.RED);
		warning_text.setHorizontalAlignment(SwingConstants.CENTER);
		warning_text.setBounds(33, 98, 278, 25);
		contentPane.add(warning_text);
	}
	private void backToMenu() {
		start_menu.setVisible(true);
		setVisible(false);
	}
	private boolean check_usrName(String usr_name) {
		return false;
	}
	public Acount getAcount() {
		return acount;
	}
	public void server_permission(boolean state) {
		if(state) {
		
			start_menu.is_SignedUp();
			setVisible(false);
		}
		else {
			warning_text.setText("User name is not available.Please choose another one.");
			acount=null;
		}
	}
}
