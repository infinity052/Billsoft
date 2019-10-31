package com.brainmentors.billing.user;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.brainmentors.billing.utils.CommonDAO;

public class EmployeeTableDAO {

	public static int getRowCount() throws SQLException {
		Connection con = null;
		
		Statement stmt = null;
		
		try {
			con = CommonDAO.getConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		stmt=con.createStatement();
		ResultSet rs = stmt.executeQuery("select * from user_mst");
		int rowCount=0;
		while(rs.next()) {
			rowCount++;
		}
		return rowCount;
		
	}
	
	public static ResultSet getEmployeeSet() {
Connection con = null;
		
		Statement stmt = null;
		
		try {
			con = CommonDAO.getConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			stmt=con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			ResultSet rs = stmt.executeQuery("select * from user_mst;");
			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
		
	}
	
}
