package client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import profile.Acount;

import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class ChangeSpecs extends JFrame {

	private JPanel contentPane;
	private JTextField name_txt;
	private JPasswordField new_pass_txt;
	private JLabel lblOldPassword;
	private JPasswordField old_pass_txt;
	private JLabel lblNewLabel_1;
	private JLabel Alert_lbl;
	private Acount acount;
	private Acount acount_clone;
	private EmailPage email_p;
	/**
	 * Create the frame.
	 */
	public ChangeSpecs(EmailPage email_p,Acount acount) {
		this.email_p=email_p;
		this.acount=acount;
		ChangeSpecs self=this;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 277, 219);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewName = new JLabel("New Name");
		lblNewName.setBounds(12, 12, 102, 15);
		contentPane.add(lblNewName);
		
		name_txt = new JTextField();
		name_txt.setBounds(142, 10, 114, 19);
		contentPane.add(name_txt);
		name_txt.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("New password");
		lblNewLabel.setBounds(12, 39, 114, 15);
		contentPane.add(lblNewLabel);
		
		new_pass_txt = new JPasswordField();
		new_pass_txt.setBounds(142, 37, 114, 19);
		contentPane.add(new_pass_txt);
		
		lblOldPassword = new JLabel("Password");
		lblOldPassword.setBounds(12, 80, 102, 15);
		contentPane.add(lblOldPassword);
		
		old_pass_txt = new JPasswordField();
		old_pass_txt.setBounds(142, 80, 114, 20);
		contentPane.add(old_pass_txt);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(acount.getPassword().equals(new String(old_pass_txt.getPassword()))) {
					String pass=new String(new_pass_txt.getPassword());
					if(pass.length()==0)pass=acount.getPassword();
					acount_clone=new Acount(name_txt.getText(), acount.getUsr_name(), pass);
					SendRequest change_req=new SendRequest(self, SendRequest.REQ_CHANGESPECS, acount);
					change_req.start();
				}
				else {
					Alert_lbl.setText("Entered Password is wrong.");
				}
			}
		});
		btnOk.setBounds(41, 120, 73, 25);
		contentPane.add(btnOk);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnCancel.setBounds(132, 120, 81, 25);
		contentPane.add(btnCancel);
		
		lblNewLabel_1 = new JLabel("dont fill it if you dont want to change ");
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 10));
		lblNewLabel_1.setBounds(41, 53, 270, 15);
		contentPane.add(lblNewLabel_1);
		
		name_txt.setText(acount.getName());
		
		Alert_lbl = new JLabel("");
		Alert_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		Alert_lbl.setForeground(Color.RED);
		Alert_lbl.setBounds(93, 162, 70, 15);
		contentPane.add(Alert_lbl);
		
	}
	public Acount getAcount_clone() {
		return acount_clone;
	}

	public void server_ans(byte ans) {
		if(ans==SendRequest.ANS_ACK) {
			acount.setName(acount_clone.getName());
			acount.setPassword(acount_clone.getPassword());
			email_p.logOut();
			dispose();
		}
		else {
			Alert_lbl.setText("Something went Wrong!!!");
		}
	}
}
