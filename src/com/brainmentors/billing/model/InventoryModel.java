package com.brainmentors.billing.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.AbstractTableModel;

public class InventoryModel extends AbstractTableModel{
	int rowCount = ProductDAO.getRowCount();
	Object table [] []= new Object[rowCount][6];
	public String []cols= {"Product ID","Product Name","Description","Rate","Image Path","Quantity"};
	
	
	public InventoryModel() {
		try {
			initialiseTable();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return rowCount;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 6;
	}

	private void initialiseTable() throws SQLException {
		ResultSet rs = ProductDAO.getInventorySet();
		int rowIndex=0;
		while(rs.next()) {
			
			for(int j=0;j<6;j++) {
				table[rowIndex][j]=rs.getObject(j+1);
				
			}
			rowIndex++;
		}
		
		
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return table[rowIndex][columnIndex];
	}

}
