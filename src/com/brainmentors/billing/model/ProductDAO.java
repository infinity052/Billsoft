package com.brainmentors.billing.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



import com.brainmentors.billing.utils.CommonDAO;

public interface ProductDAO extends CommonDAO {
	
	public static int getRowCount() {
		Connection con = null;
		Statement stmt=null;
		ResultSet rs = null;
		try {
			con=CommonDAO.getConnection();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			stmt=con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			rs=stmt.executeQuery("select * from product_mst;");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int rowCount = 0;
		try {
			while(rs.next()) {
				rowCount++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rowCount;
		
	}
	
	public static ResultSet getInventorySet() {
		Connection con = null;
		Statement stmt=null;
		ResultSet rs = null;
		try {
			con=CommonDAO.getConnection();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			stmt=con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			rs=stmt.executeQuery("select * from product_mst;");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
		
		
	}
	
	
	public static Product getProductByName(String name) throws ClassNotFoundException, SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Product product = null;
		try {
			con = CommonDAO.getConnection();
			pstmt = con.prepareStatement("select id, name, "
					+ "price, descr, imgpath,"
					+ " qt from product_mst where name=?");
			 pstmt.setString(1, name);
			 rs = pstmt.executeQuery();
			 if(rs.next()) {
				 product= new Product();
				 product.setId(rs.getInt("id"));
				 product.setName(rs.getString("name"));
				 product.setDesc(rs.getString("descr"));
				 product.setPrice(rs.getDouble("price"));
				 product.setQuantity(rs.getInt("qt"));
				 product.setPath(rs.getString("imgpath"));
			 }
				 
			
		}
		finally {
			if(rs!=null) {
				rs.close();
			}
			if(pstmt!=null) {
				pstmt.close();
			}
		}
		return product;
		
	}
	
	public static ArrayList<String> getProductNames() throws ClassNotFoundException, SQLException{
		
		ArrayList<String> names = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
		con = CommonDAO.getConnection();
		pstmt = con.prepareStatement("select  name from product_mst");
		rs = pstmt.executeQuery();
		while(rs.next()) {
			names.add(rs.getString("name"));
		}
		}
		finally {
			if(rs!=null) {
			rs.close();
			}
			if(pstmt!=null) {
			pstmt.close();
			}
		}
		return names;
	}
	public static boolean bulkUpload(ArrayList<Product> productList) throws ClassNotFoundException, SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		
		if(productList==null) {
			return false;
		}
		if(productList.size()==0) {
			return false;
		}
		
		con = CommonDAO.getConnection();
		con.setAutoCommit(false);
		Statement stmt=con.createStatement();
		
		stmt.execute("delete from product_mst;");
		
		pstmt = con.prepareStatement("insert into product_mst "
				+ "(id, name, descr, price, imgpath, qt) values(?,?,?,?,?,?) ");
		for(Product product :productList) {
			pstmt.setInt(1,product.getId() );
			pstmt.setString(2, product.getName());
			pstmt.setString(3, product.getDesc());
			pstmt.setDouble(4, product.getPrice());
			pstmt.setString(5, product.getPath());
			pstmt.setInt(6, product.getQuantity());
			pstmt.addBatch();
			
		}
		
		int results[] = pstmt.executeBatch();
		boolean rollBack = false;
		for(int r : results) {
			if(r<=0) {
				rollBack = true;
				break;
			}
		}
		if(rollBack) {
			con.rollback();
			return false;
		}
		else {
		con.commit();
		return true;
		}
	}
	
	private static int [] getUpdatedQuantities(ArrayList <Product> checkoutList) throws SQLException, ClassNotFoundException {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs[]=new ResultSet[checkoutList.size()];
		con=CommonDAO.getConnection();
		stmt=con.createStatement();
		int qt[]=new int[checkoutList.size()];
		con=CommonDAO.getConnection();
		for(int i=0;i<checkoutList.size();i++) {
			rs[i] = stmt.executeQuery("select qt from product_mst where id="+checkoutList.get(i).getId()+";");
			
			while(rs[i].next()) {
				qt[i]=rs[i].getInt(1);
				
			}	
			rs[i].close();
			
			}
		stmt.close();
		return qt;
		
//		System.out.println(qt);
	}
	
	public static boolean setQuantities(ArrayList <Product> checkoutList) throws SQLException, ClassNotFoundException {
		boolean rollback=false;
		Connection con = null;
		PreparedStatement stmt = null;
		con=CommonDAO.getConnection();
		
		
		int quantities[]=getUpdatedQuantities(checkoutList);
		int updatedQuantities[] = new int[checkoutList.size()];
		con.setAutoCommit(false);
		for(int i =0; i<checkoutList.size();i++) {
			updatedQuantities[i]=quantities[i]-checkoutList.get(i).getQuantity();
		}
		stmt =con.prepareStatement("update product_mst set qt=? where id=?;");
		for(int i=0;i<checkoutList.size();i++) {
			stmt.setInt(1, updatedQuantities[i]);
			stmt.setInt(2, checkoutList.get(i).getId());
			stmt.addBatch();
		}
		int results[]=stmt.executeBatch();
		for(int result : results) {
			if(result<=0) {
				rollback = true;
			}
		}
		
		if(rollback) {
			con.rollback();
			return false;
		}
		con.commit();
		return true;
	}
		
	
}
