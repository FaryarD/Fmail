package client;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import profile.Acount;
import profile.Message;
import profile.MessageInfo;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Color;

public class NewMessageForm extends JFrame {

	private JPanel contentPane;
	private JTextField username_txt;
	private JTextField subject_txt;
	private JScrollPane Message_scrollPane;
	private JTextArea message_text;
	private JPanel panel_1;
	private JButton Send_btn;
	private JButton cancel_btn;
	private Message msg=null;
	private JLabel Alert_lbl;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewMessageForm frame = new NewMessageForm(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public NewMessageForm(Acount acount) {
		NewMessageForm self=this;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 349, 296);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblSender = new JLabel("Distination User name");
		panel.add(lblSender);
		
		username_txt = new JTextField();
		username_txt.getDocument().addDocumentListener(new DocumentListener() {
			public void removeUpdate(DocumentEvent arg0) {
				checkValues();
			}
			public void insertUpdate(DocumentEvent arg0) {
				checkValues();
			
			}
			public void changedUpdate(DocumentEvent arg0) {
				checkValues();
			}
		});
		panel.add(username_txt);
		username_txt.setColumns(10);
		
		JLabel lblSubject = new JLabel("Subject");
		panel.add(lblSubject);
		
		subject_txt = new JTextField();
		subject_txt.getDocument().addDocumentListener(new DocumentListener() {
			public void removeUpdate(DocumentEvent arg0) {
				checkValues();
			}
			public void insertUpdate(DocumentEvent arg0) {
				checkValues();
			
			}
			public void changedUpdate(DocumentEvent arg0) {
				checkValues();
			}
		});
		panel.add(subject_txt);
		subject_txt.setColumns(10);
		
		JLabel label = new JLabel("");
		panel.add(label);
		
		message_text= new JTextArea();
		message_text.setFont(new Font("SansSerif", Font.PLAIN, 14));
		Message_scrollPane = new JScrollPane(message_text);
		message_text.getDocument().addDocumentListener(new DocumentListener() {
			public void removeUpdate(DocumentEvent arg0) {
				checkValues();
			}
			public void insertUpdate(DocumentEvent arg0) {
				checkValues();
			
			}
			public void changedUpdate(DocumentEvent arg0) {
				checkValues();
			}
		});
		contentPane.add(Message_scrollPane, BorderLayout.CENTER);
		
		panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		
		Send_btn = new JButton("Send");
		Send_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				msg=new Message(message_text.getText(), new MessageInfo(acount.getUsr_name(), username_txt.getText(), subject_txt.getText()));
				SendRequest req=new SendRequest(self, SendRequest.REQ_SENDMSG, acount);
				req.start();
			}
		});
		panel_1.setLayout(new GridLayout(2, 2, 0, 0));
		Send_btn.setEnabled(false);
		panel_1.add(Send_btn);
		
		cancel_btn = new JButton("Cancel");
		cancel_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		panel_1.add(cancel_btn);
		
		Alert_lbl = new JLabel("");
		Alert_lbl.setForeground(Color.RED);
		Alert_lbl.setHorizontalAlignment(SwingConstants.LEFT);
		panel_1.add(Alert_lbl);
	}
	public Message getMsg() {
		return msg;
	}

	private void checkValues() {
		if(username_txt.getText().length()>0 && subject_txt.getText().length()>0 && message_text.getText().length()>0) {
			Send_btn.setEnabled(true);
		}
		else {
			Send_btn.setEnabled(false);
		}
	}
	public void serverResposne(byte ans) {
		System.out.println("fdaf");
		if(ans==SendRequest.ANS_ACK) {
			
			this.dispose();
		}
		else if(ans==SendRequest.ANS_USER_NOTFOUND) {
			Alert_lbl.setText("Distination User Not Found");
		}
		else {
			Alert_lbl.setText("Unknown Error.");
			System.out.println("fggggg");
		}
	}

}
