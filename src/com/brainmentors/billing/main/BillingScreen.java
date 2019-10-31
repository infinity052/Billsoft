package com.brainmentors.billing.main;


import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;



import com.brainmentors.billing.model.Product;
import com.brainmentors.billing.model.ProductDAO;
import javax.swing.JSpinner;

public class BillingScreen extends JFrame {
	
	

	private JPanel contentPane;

	
	/**
	 * Create the frame.
	 */
	JComboBox comboBox = new JComboBox();
	private void fillCombo() {
		try {
			comboBox.addItem("Select Item");
			for(String name : ProductDAO.getProductNames()) {
				comboBox.addItem(name);
			}
		} catch (ClassNotFoundException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	Product product = null;
	void search() {
		String selectedItem = (String)comboBox.getSelectedItem();
		
		try {
			product = ProductDAO.getProductByName(selectedItem);
			productPhoto.setIcon(new ImageIcon(BillingScreen.class.getResource(product.getPath())));
			productname.setText(product.getName());
			productprice.setText(String.valueOf(product.getPrice()));
			productdesc.setText(product.getDesc());
			product.setQuantity(1);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	void askForClose() {
		int choice = JOptionPane.showConfirmDialog(this, "Do u want to leave this window","brain mentors",JOptionPane.YES_NO_OPTION);
		if(choice == JOptionPane.YES_OPTION) {
			this.setVisible(false);
			this.dispose();
		}
		
	}
	JLabel productPhoto = new JLabel("");
	JLabel productname = new JLabel("");
	JLabel productprice = new JLabel("");
//	JLabel productdesc = new JLabel("");
	JLabel productdesc = new JLabel("");
	JSpinner spinner;
			
	public BillingScreen() {
			this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				askForClose();
			}
		});
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		fillCombo();
		comboBox.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		comboBox.setBounds(160, 29, 180, 27);
		contentPane.add(comboBox);
		
		JLabel lblChooseItem = new JLabel("Choose Item");
		lblChooseItem.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblChooseItem.setBounds(18, 33, 142, 23);
		contentPane.add(lblChooseItem);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				search();
				
			}
		});
		btnSearch.setBounds(231, 58, 117, 29);
		contentPane.add(btnSearch);
		
		JButton viewCart = new JButton("View Cart");
		viewCart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ViewCart v = new ViewCart(billProduct);
				v.setVisible(true);
				
			}
		});
		viewCart.setBounds(350, 58, 117, 29);
		contentPane.add(viewCart);
		
		
		productPhoto.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		productPhoto.setHorizontalAlignment(SwingConstants.CENTER);
		productPhoto.setBounds(10, 126, 400, 400);
		//contentPane.add(productPhoto);
		
		productname.setFont(new Font("Dialog", Font.PLAIN, 18));
		productname.setHorizontalAlignment(SwingConstants.CENTER);
		productname.setBounds(988, 92, 171, 27);
		contentPane.add(productname);
		
		
		productprice.setHorizontalAlignment(SwingConstants.CENTER);
		productprice.setFont(new Font("Dialog", Font.PLAIN, 18));
		productprice.setBounds(988, 180, 171, 27);
		contentPane.add(productprice);
		
		
		
//		productdesc.setHorizontalAlignment(SwingConstants.CENTER);
//		productdesc.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
//		productdesc.setBounds(1000, 500, 171, 27);
//		contentPane.add(productdesc);
		JButton addCart = new JButton("Add");
		addCart.setBounds(1086, 589, 100, 27);
		addCart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				addToBill();
			}
		});
		contentPane.add(addCart);
		
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 126, 550, 400);
		scrollPane.setViewportView(productPhoto);
		contentPane.add(scrollPane);
		productdesc.setFont(new Font("Tahoma", Font.ITALIC, 18));
		
		
		productdesc.setBounds(797, 308, 478, 188);
		contentPane.add(productdesc);
		
		JLabel lblProductName = new JLabel("Product Name");
		lblProductName.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblProductName.setBounds(787, 78, 152, 57);
		contentPane.add(lblProductName);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblPrice.setBounds(787, 178, 76, 29);
		contentPane.add(lblPrice);
		
		JLabel lblDescription = new JLabel("Description");
		lblDescription.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblDescription.setBounds(787, 277, 127, 27);
		contentPane.add(lblDescription);
		
		spinner = new JSpinner();
		spinner.setValue(1);
		spinner.setBounds(963, 589, 76, 27);
		contentPane.add(spinner);
	}
	private void listAmbiguity() {
		for(int i=0;i<billProduct.size();i++) {
			for(int j=i+1; j<billProduct.size();j++) {
				if(billProduct.get(i).getId()==billProduct.get(j).getId()) {
					billProduct.get(i).setQuantity(billProduct.get(i).getQuantity()+1);
					billProduct.remove(j);
				}
			}
			
					}
	}
	
	
	private ArrayList<Product> billProduct = new ArrayList<>();
	private void addToBill() {
		
		
		int qt = (int)spinner.getValue();
		product.setQuantity(product.getQuantity()+qt-1);
		billProduct.add(product);
		JOptionPane.showMessageDialog(this, "Product Added in a Cart");
		listAmbiguity();
		spinner.setValue(1);
		
	}
}
