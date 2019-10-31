package com.brainmentors.billing.user;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.brainmentors.billing.main.DashBoard;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
					Login frame = new Login();
					frame.setVisible(true);
				
	}
	private void register() {
		this.setVisible(false);
		this.dispose();
		Register reg = new Register();
		reg.setVisible(true);
	}
	
	
	
	private void loginCheck() throws ClassNotFoundException{
		String userid = textField.getText();
		
		String password = new String(passwordField.getPassword());
		//String password = passwordField.getText();
		UserDAO userDAO = new UserDAO();
		try {
			String message = "Invalid Userid or Password";
			int isFound = userDAO.loginCheck(userid, password);
			if(isFound==1 || isFound==2) {
				if(isFound==2) {
					userid="Admin";
				}
				message="Welcome "+userid;
				this.setVisible(false);
				this.dispose();
				DashBoard dashBoard = new DashBoard(userid,isFound);
				dashBoard.setExtendedState(JFrame.MAXIMIZED_BOTH);
				dashBoard.setVisible(true);
			}
			JOptionPane.showMessageDialog(this, message);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//String password = passwordField.getText()
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 369, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Login");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 32));
		lblNewLabel.setBounds(108, 21, 119, 41);
		contentPane.add(lblNewLabel);
		
		JLabel lblUserid = new JLabel("Username");
		lblUserid.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblUserid.setBounds(18, 109, 82, 16);
		contentPane.add(lblUserid);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblPassword.setBounds(18, 161, 84, 16);
		contentPane.add(lblPassword);
		
		textField = new JTextField();
		textField.setBounds(136, 107, 159, 26);
		contentPane.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		//passwordField= new JTextField();
		passwordField.setEchoChar('#');
		passwordField.setBounds(136, 159, 159, 26);
		contentPane.add(passwordField);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					loginCheck();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnLogin.setIcon(null);
		btnLogin.setBounds(6, 219, 105, 29);
		contentPane.add(btnLogin);
		
		JButton btnReset = new JButton("Reset");
		btnReset.setBounds(122, 219, 105, 29);
		contentPane.add(btnReset);
		
		JButton btnNewButton = new JButton("Register");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				register();
			}
		});
		btnNewButton.setBounds(245, 219, 105, 29);
		contentPane.add(btnNewButton);
	}
}
