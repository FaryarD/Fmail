package client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import profile.Message;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridLayout;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Font;

public class MessageForm extends JFrame {

	private JPanel contentPane;
	private JTextField sender_txt;
	private JTextField subject_txt;
	private JScrollPane Message_scrollPane;
	private JTextArea message_text;

	public MessageForm(Message msg) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 301, 247);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblSender = new JLabel("Sender Username");
		panel.add(lblSender);
		
		sender_txt = new JTextField();
		sender_txt.setEditable(false);
		panel.add(sender_txt);
		sender_txt.setColumns(10);
		
		JLabel lblSubject = new JLabel("Subject");
		panel.add(lblSubject);
		
		subject_txt = new JTextField();
		subject_txt.setEditable(false);
		panel.add(subject_txt);
		subject_txt.setColumns(10);
		
		JLabel label = new JLabel("");
		panel.add(label);
		
		message_text= new JTextArea();
		message_text.setFont(new Font("SansSerif", Font.PLAIN, 14));
		message_text.setEditable(false);
		Message_scrollPane = new JScrollPane(message_text);
		contentPane.add(Message_scrollPane, BorderLayout.CENTER);
		
		sender_txt.setText(msg.info.sender_usr);
		subject_txt.setText(msg.info.subject);
		message_text.setText(msg.text);
		this.setTitle(msg.info.time);
		
	}

}
