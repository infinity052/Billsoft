package com.brainmentors.billing.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.brainmentors.billing.utils.CommonDAO;

public class UserDAO {
	public boolean register(String uid, String pwd) throws ClassNotFoundException, SQLException {
		
		
		Connection con = null;
		Statement stmt = null;
		try {
		con = CommonDAO.getConnection();

		stmt = con.createStatement();
		int recordCount = stmt.executeUpdate("insert into user_mst (userid,password) values('"+uid+"','"+pwd+"');");
		
		if(recordCount>0) {
			return true;
			
		}
		else {
			return false;
			//System.out.println("Invalid Userid or Password");
		}
		}
		finally {
		
		stmt.close();
		//con.close();
		}
		
	}
	public boolean isUserExist(String uid) throws ClassNotFoundException, SQLException {
		
		ResultSet rs = null;
		Connection con = null;
		Statement stmt = null;
		try {
		con = CommonDAO.getConnection();

		stmt = con.createStatement();
		rs = stmt.executeQuery("select userid "
				+ "from user_mst where "
				+ "userid='"+uid+"'");
		
		if(rs.next()) {
			return true;
			//System.out.println("Welcome "+uid);
		}
		else {
			return false;
			//System.out.println("Invalid Userid or Password");
		}
		}
		finally {
		rs.close();
		stmt.close();
		//con.close();
		}
		
	}
	
	private static boolean employeeCheck(String uid, String pwd) throws ClassNotFoundException, SQLException {
		
				ResultSet rs = null;
				Connection con = null;
				//Statement stmt = null;
				PreparedStatement stmt = null;
				try {
				con = CommonDAO.getConnection();

				//stmt = con.createStatement();
				stmt = con.prepareStatement("select userid "
						+ "from user_mst where userid=? and password=?");
						
//				rs = stmt.executeQuery("select userid "
//						+ "from user_mst where "
//						+ "userid='"+uid+"' and "
//								+ "password="+pwd);
				stmt.setString(1, uid);
				stmt.setString(2, pwd);
				rs = stmt.executeQuery();
				//stmt.setInt(2, Integer.parseInt(pwd));
				System.out.println("Userid is "+uid);
				if(rs.next()) {
					return true;
					//System.out.println("Welcome "+uid);
				}
				else {
					return false;
					
				}
				}
				finally {
				rs.close();
				stmt.close();
//				con.close();
				}
	}
	
	private static boolean adminCheck(String uid, String pwd) throws SQLException, ClassNotFoundException {			
		Connection con = null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		con=CommonDAO.getConnection();
		pstmt = con.prepareStatement("select * from admin_acc where userid=? and password=? ;");
		
		pstmt.setString(1, uid);
		pstmt.setString(2,pwd);
		rs=pstmt.executeQuery();
		
		if(rs.next()) {
			return true;
		}
		else 
		return false;
		
	}
				
	public static int loginCheck(String uid, String pwd) throws SQLException, ClassNotFoundException{
		if(employeeCheck(uid,pwd)==true) {
			return 1;
		}
		else if(adminCheck(uid,pwd)==true) {
			return 2;
		}
		
		return 3;
		}
		
	public static boolean deleteEntry(int uid) throws SQLException, ClassNotFoundException {
		Connection con = null;
		Statement pstmt=null;
		
		con=CommonDAO.getConnection();
		pstmt = con.createStatement();
		int n=pstmt.executeUpdate("delete from user_mst where uid="+uid+";");
		
		if(n>0) {
			return true;
					
		}
		
		return false;
		
	
		
		
	}

}
