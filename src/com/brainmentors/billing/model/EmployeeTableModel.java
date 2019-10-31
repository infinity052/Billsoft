package com.brainmentors.billing.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.AbstractTableModel;

import com.brainmentors.billing.user.EmployeeTableDAO;

public class EmployeeTableModel extends AbstractTableModel{
	
	String table[][]=new String[getRowCount()][4];
	ResultSet rs= EmployeeTableDAO.getEmployeeSet();
	public String []cols= {"Employee ID", "Username","Password","Status"};
	private void fillTable() throws SQLException {
		int rowCount=0;
	while(rs.next()) {
		for(int i=0;i<cols.length;i++) {
		table[rowCount][i] = rs.getString(i+1);
		
		System.out.println(table[rowCount][i]);
		}
		rowCount++;
		
	}
	
	
	}
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		
			try {
				return EmployeeTableDAO.getRowCount()+1;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 0;
		
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return cols.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		try {
			fillTable();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
	
		if(rowIndex>=0) {
			return table[rowIndex][columnIndex];
			
		}
		return columnIndex;
		
		
	}

}
