package com.brainmentors.billing.main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.brainmentors.billing.model.InventoryModel;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ProductTable extends JFrame {

	private JPanel contentPane;
	private JTable table;

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
	public ProductTable() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				close();
			}}
			);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 759, 431);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblInventory = new JLabel("Inventory");
		lblInventory.setForeground(Color.RED);
		lblInventory.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblInventory.setBounds(299, 11, 154, 50);
		contentPane.add(lblInventory);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(117, 95, 528, 238);
		contentPane.add(scrollPane);
		InventoryModel model = new InventoryModel();
		table = new JTable(model);
		for(int i=0;i<model.cols.length;i++) {
			table.getColumnModel().getColumn(i).setHeaderValue(model.cols[i]);
		}
		scrollPane.setViewportView(table);
	}
}
