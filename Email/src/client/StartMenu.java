package client;

import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Font;
import java.awt.Color;

import profile.Acount;
public class StartMenu extends JFrame {

	private JPanel contentPane;
	private SignUp signup;
	private Acount acount;
	private EmailPage email_page;
	private JTextField txtbox_usrname;
	private JPasswordField txtbox_pass;
	private JLabel warning_txt;
	private StartMenu self=this;
	public StartMenu() {
		setResizable(false);
		setTitle("FMail");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 236, 271);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton signup_btn = new JButton("Sign Up");
		signup_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openNewSignUpFrame();
				
			}
		});
		signup_btn.setBounds(58, 166, 117, 25);
		contentPane.add(signup_btn);
		
		JButton login_btn = new JButton("Log In");
		login_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(acount==null) {
					if(txtbox_usrname.getText().length()>0 && txtbox_pass.getPassword().length>0) {
						acount=new Acount(txtbox_usrname.getText(),String.copyValueOf(txtbox_pass.getPassword()));
						SendRequest send_req=new SendRequest(self,SendRequest.REQ_LOGIN,acount);
						send_req.start();
					}
				}
				else {
					SendRequest send_req=new SendRequest(self,SendRequest.REQ_LOGIN,acount);
					send_req.start();
				}
			
				
			}
		});
		login_btn.setBounds(58, 108, 117, 25);
		contentPane.add(login_btn);
		
		txtbox_usrname = new JTextField();
		txtbox_usrname.setBounds(100, 20, 114, 19);

		contentPane.add(txtbox_usrname);
		txtbox_usrname.setColumns(10);
		
		txtbox_pass = new JPasswordField();
		txtbox_pass.setBounds(100, 50, 114, 19);
		contentPane.add(txtbox_pass);
		
		JLabel lblOr = new JLabel("Or...");
		lblOr.setBounds(80, 139, 70, 15);
		contentPane.add(lblOr);
		
		JLabel lblUserName = new JLabel("User Name");
		lblUserName.setBounds(10, 20, 82, 15);
		contentPane.add(lblUserName);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(10, 50, 70, 15);
		contentPane.add(lblPassword);
		
		warning_txt = new JLabel("");
		warning_txt.setForeground(Color.RED);
		warning_txt.setFont(new Font("Dialog", Font.BOLD, 10));
		warning_txt.setBounds(10, 77, 216, 15);
		contentPane.add(warning_txt);
	}
	private void openNewSignUpFrame() {
		signup=new SignUp(this);
		signup.setVisible(true);
		setVisible(false);
	}
	public void LogInToAcount() {
		warning_txt.setText("");
		email_page=new EmailPage(this, acount);
		email_page.setVisible(true);
		setVisible(false);
	}
	public void logOut() {
		acount=null;
		setVisible(true);
	}
	public void server_permission(boolean per) {
		if(per) {
			LogInToAcount();
		}
		else {
			warning_txt.setText("User name or pass is Wrong!!!");
			acount=null;
		}
	}
	public void is_SignedUp() {
		acount=signup.getAcount();
		if(acount!=null) {
			LogInToAcount();
		}
	}
}
