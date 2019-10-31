package com.brainmentors.billing.main;


import java.awt.EventQueue;


import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.brainmentors.billing.user.Login;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

import javax.swing.Timer;
import javax.swing.JProgressBar;
import javax.swing.JWindow;

public class IntroScreen extends JWindow {

	private JPanel contentPane;
	private JProgressBar progressBar;
	private Timer timer;
	private int counter;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IntroScreen frame = new IntroScreen();
//					frame.setResizable(false);
					frame.setBounds(300, 50, 700,600);
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
	
	private void goToLogin() {
		this.setVisible(false);
		this.dispose();
		Login login=new Login();
		login.setVisible(true);
	}
	public IntroScreen() {
//		setResizable(false);
		
		setBounds(100, 100, 1012, 533);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(IntroScreen.class.getResource("/com/brainmentors/billing/main/logo.jpg")));
		label.setBounds(98, -20, 500, 500);
		contentPane.add(label);
		
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setBounds(108, 485, 430, 21);
		contentPane.add(progressBar);
		loadScreen();
		
		
	}

	void loadScreen() {
		counter=0;
		timer=new Timer(20,(e)->{
			
		counter++;
		progressBar.setValue(counter);
		
		if(counter>=100) {
			timer.stop();
			goToLogin();
		}
			});
				
		timer.start();		
				
	}
}
