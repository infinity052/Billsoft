package com.brainmentors.billing.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.brainmentors.billing.model.Product;
import com.brainmentors.billing.model.ProductDAO;
import com.brainmentors.billing.user.Login;
import com.brainmentors.billing.utils.ConfigReader;
import com.brainmentors.billing.utils.ExcelReader;
import javax.swing.JToolBar;

public class DashBoard extends JFrame {
	int employeeOrAdmin;
	private JPanel contentPane;
	
	private void doUpload() {
		JFileChooser browse = 
				new JFileChooser(ConfigReader.getValue(ConfigReader.BROWSE_PATH));
		browse.showOpenDialog(this);
		File file = browse.getSelectedFile();
		try {
			ArrayList<Product> productList = ExcelReader.readXLS(file);
			String msg = ProductDAO.bulkUpload(productList)?"Record Uploaded":"Not Uploaded";
			JOptionPane.showMessageDialog(this, msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SQLException|ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(file.getPath());
	}
	private void logout() {
		this.dispose();
		this.setVisible(false);
		Login login = new Login();
		login.setVisible(true);
	}
	
	private void createMenu() {
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		JMenu admin =new JMenu("File");
		menuBar.add(admin);
		JMenuItem upload = new JMenuItem("Upload");
		JMenuItem bill = new JMenuItem("Billing");
		bill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BillingScreen billingScreen = new BillingScreen();
				billingScreen.setVisible(true);
			}
		});
		upload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doUpload();
			}
		});
		if(employeeOrAdmin==2) {
		admin.add(upload);
		admin.addSeparator();}
		admin.add(bill);
		admin.addSeparator();
		JMenuItem exit = new JMenuItem("Logout");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logout();
			}
		});
		admin.add(exit);
		
		JMenu mnList = new JMenu("List");
		menuBar.add(mnList);
		if(employeeOrAdmin==2) {
		JMenuItem mntmEmployee = new JMenuItem("Employee");
		mntmEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Employees emp = new Employees();
				emp.setVisible(true);
				
			}
		});
		mnList.add(mntmEmployee);}
		
		JMenuItem mntmProducts = new JMenuItem("Products");
		mntmProducts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				productTable();
			}
		});
		mnList.add(mntmProducts);
		
		
	}
	
	

	/**
	 * Create the frame.
	 */
	public DashBoard(String userid,int employeeOrAdmin) {
		this.employeeOrAdmin=employeeOrAdmin;
		setTitle("DASHBOARD");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1353, 721);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel welcomelbl = new JLabel("Welcome "+userid);
		welcomelbl.setForeground(Color.RED);
		welcomelbl.setFont(new Font("Dialog", Font.BOLD, 32));
		welcomelbl.setBounds(22, 6, 569, 65);
		contentPane.add(welcomelbl);
		createMenu();
		JLabel label = new JLabel(getDate());
		label.setFont(new Font("Franklin Gothic Medium", Font.PLAIN, 25));
		label.setBounds(718, 21, 547, 41);
		contentPane.add(label);
	}
	
	private String getDate() {
		Date date=new Date();
		Locale locale=new Locale("en","IN");
		DateFormat df=DateFormat.getDateInstance(DateFormat.FULL, locale);
		return df.format(date);
	}
	private void productTable() {
		ProductTable pt = new ProductTable();
		pt.setVisible(true);
	}
}
