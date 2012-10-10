
/**
 * Write a description of interface RXXX here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.sql.*;

public interface RXXX
{

    public Connection getConnection() throws SQLException;
    
      /**
     * Returns the number of rows in the table model.
     */
    public int getRowCount();

    /**
     * Returns the number of columns in the table model.
     */
    public int getColumnCount();

    /**
     * Returns the class of the data in the specified column.
     */
    public Class getColumnClass(int columnIndex);

    /**
     * Returns the name of the specified column.
     */
    public String getColumnName(int columnIndex);

    /**
     * Returns the data value at the specified row and column.
     */
    public Object getValueAt(int row, int column);
}
