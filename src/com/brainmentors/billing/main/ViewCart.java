package com.brainmentors.billing.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;

import com.brainmentors.billing.model.Product;
import com.brainmentors.billing.model.ProductDAO;
import com.brainmentors.billing.model.ProductTableModel;
import java.awt.Button;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class ViewCart extends JFrame {
	ArrayList <Product> checkoutList= new ArrayList();
	ProductTableModel model;
	private JPanel contentPane;
	private JTable table;
	ArrayList <Product> productList=null;
	private void printIt() {
		try {
			table.print();
			
		} catch (PrinterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void windowClose() {
		this.dispose();
		this.setVisible(false);
	}
	
	public ViewCart(ArrayList<Product> list) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				windowClose();
			}
		});
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 477);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.productList=list;
		JLabel lblItemsInCart = new JLabel("Items in Cart");
		lblItemsInCart.setForeground(Color.RED);
		lblItemsInCart.setHorizontalAlignment(SwingConstants.CENTER);
		lblItemsInCart.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		lblItemsInCart.setBounds(96, 31, 218, 16);
		contentPane.add(lblItemsInCart);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 82, 335, 140);
		contentPane.add(scrollPane);
		model = new ProductTableModel(list);
		table = new JTable(model);
		table.getColumnModel().getColumn(0).setHeaderValue("Product ID");
		table.getColumnModel().getColumn(1).setHeaderValue("Product Name");
		table.getColumnModel().getColumn(2).setHeaderValue("Rate");
		table.getColumnModel().getColumn(3).setHeaderValue("Quantity");
		table.getColumnModel().getColumn(4).setHeaderValue("Amount");
		scrollPane.setViewportView(table);
		
		JButton btnPrint = new JButton("Print");
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			printIt();
			}
		});
		btnPrint.setBounds(6, 29, 117, 29);
		contentPane.add(btnPrint);
		
		JLabel lblGst = new JLabel("GST @18%");
		lblGst.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblGst.setBounds(128, 233, 95, 29);
		contentPane.add(lblGst);
		
		JLabel lblTotalAmount = new JLabel("Total Amount");
		lblTotalAmount.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblTotalAmount.setBounds(118, 273, 105, 19);
		contentPane.add(lblTotalAmount);
		
		JLabel tax = new JLabel(""+getTax());
		tax.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tax.setBounds(234, 242, 82, 14);
		contentPane.add(tax);
		
		JLabel total = new JLabel(""+getTotal());
		total.setFont(new Font("Tahoma", Font.PLAIN, 15));
		total.setBounds(244, 277, 82, 14);
		contentPane.add(total);
		
		JButton btnCheckout = new JButton("Checkout");
		btnCheckout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					checkout();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnCheckout.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnCheckout.setBounds(261, 312, 117, 36);
		contentPane.add(btnCheckout);
	}
	
	private double getTax() {
		return getAmount()*0.18;
		
	}
	
	private double getTotal() {
		return getAmount()+getTax();
	}
	
	private double getAmount() {
		double totalAmount=0;
		for(int i=0;i<table.getRowCount();i++) {
			totalAmount+=Double.parseDouble(table.getValueAt(i, 4).toString());
		}
		return totalAmount;
	}
	
	private void databaseUpdate() throws ClassNotFoundException, SQLException {
		if(checkoutList.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Cart Empty.");
			return;
		}
		int n=JOptionPane.showConfirmDialog(this, "Are you sure you want to purchase these Items?");
		  if(n==JOptionPane.NO_OPTION) {
			  return;
		  }
		  if(n==JOptionPane.CANCEL_OPTION) {
			  return;
		  }
		  if(n==JOptionPane.YES_OPTION) {
			  if(ProductDAO.setQuantities(checkoutList)) {
				  
				  JOptionPane.showMessageDialog(this, "Bon Appetit :D");
				  this.dispose();
				  this.setVisible(false);
				  
			  }
			  else
			  {
				  JOptionPane.showMessageDialog(this, "Some error occured. Please contact System Administrator :(");
			  }
				  
		  }
		  
	}
	
	private void checkout() throws ClassNotFoundException, SQLException {
		for(int i=0;i<table.getRowCount();i++) {
			Product product = new Product();
			product.setId(Integer.parseInt(table.getValueAt(i, 0).toString()));
			product.setQuantity(Integer.parseInt(table.getValueAt(i, 3).toString()));
			
			checkoutList.add(product);
			
		}
		databaseUpdate();
		
	}
}
