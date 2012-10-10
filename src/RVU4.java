
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


public class RVU4 extends AbstractTableModel implements Parametres, RXXX {

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

  public RVU4(String sAno) {
        int files;
        int cols;


        String query = "SELECT ano,SUM(pubs) AS pubs,SUM(privs) AS privs,SUM(admonestado) AS admonestado,SUM(gva) AS gva,";
        query += "SUM(pubs+privs-gva-admonestado) AS diputacion,descripcion,SUM(pubs+privs) AS totalRegistres FROM vu,oficinas ";
        query += "WHERE ((ano=\"" +sAno+ "\") AND (vu.oficina=oficinas.codigo)) GROUP BY oficina ORDER BY descripcion";

        System.out.println(query);

        try {
                Connection con = this.getConnection();
                Statement sentencia = con.createStatement();
                ResultSet rs1 = sentencia.executeQuery(query);
                ResultSetMetaData rsmd1 = rs1.getMetaData();
                files=0;
                while (rs1.next()) {
                  files++;
                }
                cols = rsmd1.getColumnCount();

                f = new Object[files][cols];
                c = new Object[cols];

                for (int i=0;i<cols;i++) {
                  c[i] = rsmd1.getColumnName(i+1);
                }

                int x=0;
                rs1.beforeFirst();
                while (rs1.next()) {
                  for (int y=0; y<cols; y++) {
                      if ((y==0) || (y==6)) {
                        f[x][y] = rs1.getString(y+1);
                      }
                      else {
                        f[x][y] = new Integer(rs1.getString(y+1));
                      }
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
        if ((columnIndex==0) || (columnIndex==6))
          return String.class;

        else
          return Integer.class;
    }

    /**
     * Returns the name of the specified column.
     */
    public String getColumnName(int columnIndex) {
      String nomCol="";
      switch (columnIndex) {
        case 0: nomCol="ano";break;
        case 1: nomCol="pubs";break;
        case 2: nomCol="privs";break;
        case 3: nomCol="admonestado";break;
        case 4: nomCol="gva";break;
        case 5: nomCol="diputacion";break;
        case 6: nomCol="descripcion"; break; // nom  de l'oficina
        case 7: nomCol="totalRegistres";break;
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