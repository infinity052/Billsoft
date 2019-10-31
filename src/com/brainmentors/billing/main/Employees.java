package com.brainmentors.billing.main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.brainmentors.billing.model.EmployeeTableModel;
import com.brainmentors.billing.user.UserDAO;

import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import javax.swing.border.MatteBorder;
import javax.swing.JScrollPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Employees extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField textField;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	
	private void close() {
		
			this.setVisible(false);
			this.dispose();
	}
	
	public Employees() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				close();
			}
		});
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 901, 477);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		EmployeeTableModel model = new EmployeeTableModel();
		contentPane.setLayout(null);
		
		JLabel lblEnterUid = new JLabel("Enter UID");
		lblEnterUid.setBounds(567, 43, 87, 35);
		lblEnterUid.setFont(new Font("Tahoma", Font.PLAIN, 18));
		contentPane.add(lblEnterUid);
		
		textField = new JTextField();
		textField.setBounds(678, 50, 96, 20);
		textField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Delete");
		btnNewButton.setBounds(688, 81, 87, 23);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteEntries();
			}
		});
		
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 7));
		contentPane.add(btnNewButton);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(30, 130, 806, 178);
		contentPane.add(scrollPane);
		
		scrollPane_1 = new JScrollPane();
		scrollPane.setColumnHeaderView(scrollPane_1);
		table = new JTable(model);
		for(int i=0;i<model.cols.length;i++) {
		table.getColumnModel().getColumn(i).setHeaderValue(model.cols[i]);}
		
		
		scrollPane.setViewportView(table);
		table.setBackground(new Color(240, 240, 240));
		table.setFont(new Font("Lucida Fax", Font.PLAIN, 16));
		table.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
	
	
	
	}
	void deleteEntries(){
		int uid=0; 
		
		try{uid = Integer.parseInt(textField.getText());}
		catch(Exception e) {
			JOptionPane.showMessageDialog(this, "Invalid character");
			return;
		}
		
		try {
			if(UserDAO.deleteEntry(uid)) {
				JOptionPane.showMessageDialog(this, "Entry has been deleted");
				this.dispose();
				this.setVisible(false);
				Employees employees = new Employees();
				employees.setVisible(true);
			}
			
			else {
				JOptionPane.showMessageDialog(this, "ID does not exist...");
			}
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
