package com.brainmentors.billing.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public interface CommonDAO extends ConfigReader {
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		
		Connection con = null;
		
		Class.forName(rb.getString(DRIVER));
		
		con = DriverManager.getConnection(rb.getString(DBURL)
				,rb.getString(USERID),rb.getString(PWD));
		return con;
	}


}
