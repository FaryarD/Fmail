package client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SignUp extends JFrame {

	private JPanel contentPane;
	private StartMenu start_menu;
	
	public SignUp(StartMenu start_menu) {
		this.start_menu=start_menu;
		setResizable(false);
		setTitle("FMAil | SIgn Up");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 231, 382);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnBackToMenu = new JButton("Back");
		btnBackToMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(start_menu!=null) {
					start_menu.setVisible(true);
					setVisible(false);
				}
				
			}
		});
		btnBackToMenu.setBounds(52, 308, 117, 25);
		contentPane.add(btnBackToMenu);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(54, 272, 117, 25);
		contentPane.add(btnNewButton);
	}

}
