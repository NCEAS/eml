package org.ecoinformatics.datamanager.transpose;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.ucsb.nceas.utilities.OrderedMap;

public class DataTranspose {

	
	public static List transpose(ResultSet rs, int idCol, int pivotCol, boolean omitIdValues) throws SQLException {
		OrderedMap table = new OrderedMap();
		OrderedMap widestRow = new OrderedMap();
		int colCount = rs.getMetaData().getColumnCount();
		String idColName = rs.getMetaData().getColumnName(idCol);
		
		while (rs.next()) {
			String id = rs.getString(idCol);
			String pivotValue = rs.getString(pivotCol);
			
			//look up the row we are working on
			OrderedMap row = (OrderedMap) table.get(id);
			if (row == null) {
				row = new OrderedMap();
			}
			
			//get the values for this pivot
			for (int i = 1; i <= colCount; i++) {
				if (i != pivotCol) {
					String colName = rs.getMetaData().getColumnName(i);
					//annotate the column name with the pivot column value if not the id column
					if (i != idCol) {
						colName = pivotValue + "_" + colName;
					}
					String value = rs.getString(i);
					row.put(colName, value);
				}
			}
			//track the headers - the values are junk
			widestRow.putAll(row);
			
			//put the row back (or maybe it's the first time)
			table.put(id, row);	
		}
		
		//now make it into a list
		List retTable = new ArrayList();
		
		//do the header, based on widest entry
		List header = new ArrayList(widestRow.keySet());
		retTable.add(header.toArray(new String[0]));
		
		//now the value rows
		Iterator rowIter = table.values().iterator();
		int rowCount = 1;
		while (rowIter.hasNext()) {
			OrderedMap rowMap = (OrderedMap) rowIter.next();
			List row = new ArrayList();
			//iterate over the widest row's columns
			Iterator columnIter = widestRow.keySet().iterator();
			while (columnIter.hasNext()) {
				Object key = columnIter.next();
				Object value = rowMap.get(key);
				//hide the value used for Ids - just increment row
				if (key.equals(idColName) && omitIdValues) {
					value = String.valueOf(rowCount);
				}
				row.add(value);
			}
			rowCount++;
			retTable.add(row.toArray(new String[0]));
		}
		
		return retTable;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
