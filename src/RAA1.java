/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
//package infofisam075;

import java.sql.*;
import javax.swing.table.*;


public class RAA1 extends AbstractTableModel implements Parametres, RXXX{

    protected Object f[][];
    protected Object c[];

    static {
            try {
                    //registra els drivers de MySQL
                    Class.forName(driverClass);
            } catch ( ClassNotFoundException e) {
                    e.printStackTrace();
            }
    }

  public RAA1(String sMes, String sAno, String sOficina) {
        int files;
        int cols;


        String query0 = "DROP TABLE IF EXISTS tempaa1";
        
        String query = "CREATE TABLE tempaa1";
        query += " SELECT ano,mes,descripcion,tipo,ayto,udes FROM asist_admva,oficinas";
        query += " WHERE ((ano=\"" +sAno+ "\") AND (mes=\"" +sMes+ "\") AND (oficinas.descripcion=\"" +sOficina+ "\") AND";
        query += " (asist_admva.oficina=oficinas.codigo))";
        query += " ORDER BY ayto,tipo";
        
        String query1 = "ALTER TABLE tempaa1 ADD numeximits TINYINT(3) UNSIGNED";
        
        String query2 = " SELECT COUNT(DISTINCT(ayto)) as numeximits FROM asist_admva, oficinas";
        query2 += " WHERE ((ano=\"" +sAno+ "\") AND (mes=\"" +sMes+ "\") AND (oficinas.descripcion=\"" +sOficina+ "\") AND";
        query2 += " (asist_admva.oficina=oficinas.codigo))";
        
        
        try {
                Connection con = this.getConnection();
                
                Statement sentencia0 = con.createStatement();
                sentencia0.execute(query0);
                
                Statement sentencia = con.createStatement();
                sentencia.execute(query);
                
                Statement sentencia1 = con.createStatement();
                sentencia1.execute(query1);
                
                Statement sentencia2 = con.createStatement();
                ResultSet rs3 = sentencia2.executeQuery(query2);
                rs3.next();                           
                int numeximits = rs3.getInt("numeximits");
                
                String query3 = "UPDATE tempaa1 SET numeximits = "+numeximits;
                Statement sentencia3 = con.createStatement();
                sentencia3.execute(query3);
                
                String query4 = "SELECT * FROM  tempaa1";
                Statement sentencia4 = con.createStatement();
                ResultSet rs5 = sentencia4.executeQuery(query4);
                
                ResultSetMetaData rsmd1 = rs5.getMetaData();
                files=0;
                
                
                while (rs5.next()) {
                  files++;
                }
                cols = rsmd1.getColumnCount();

                f = new Object[files][cols];
                c = new Object[cols];

                for (int i=0;i<cols;i++) {
                  c[i] = rsmd1.getColumnName(i+1);
                }

                int x=0;
                rs5.beforeFirst();
                while (rs5.next()) {
                  for (int y=0; y<cols; y++) {
                    if ((y==5) || (y==6)) {
                      f[x][y] = rs5.getObject(y+1);
                    }
                    else
                      f[x][y] = rs5.getString(y+1);
                  }
                  x++;
                }
                sentencia.close();
                con.close();

        } catch (SQLException e) {
                        System.err.println("Error en la base de dades: "+e);

        }

  }

  public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,login,clau);
  }

      /**
     * Returns the number of rows in the table model.
     */
    public int getRowCount() {
        return f.length;
    }

    /**
     * Returns the number of columns in the table model.
     */
    public int getColumnCount() {
        return c.length;
    }

    /**
     * Returns the class of the data in the specified column.
     */
    public Class getColumnClass(int columnIndex) {
           return String.class;
    }

    /**
     * Returns the name of the specified column.
     */
    public String getColumnName(int columnIndex) {
      String nomCol="";
      switch (columnIndex) {
        case 0: nomCol="ano";break;
        case 1: nomCol="mes";break;
        case 2: nomCol="descripcion";break;
        case 3: nomCol="tipo";break;
        case 4: nomCol="ayto";break;
        case 5: nomCol="udes";break;
        case 6: nomCol="numeximits";break;
      }
      return nomCol;
    }

    /**
     * Returns the data value at the specified row and column.
     */
    public Object getValueAt(int row, int column) {
        return f[row][column];
    }



}