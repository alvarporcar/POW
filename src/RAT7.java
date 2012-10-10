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


public class RAT7 extends AbstractTableModel implements Parametres, RXXX {

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

  public RAT7(String sAno) {
        int files;
        int cols;

        String query1 = "select ano, oficinas.descripcion as oficina,sum(proyectado) as proyectado,sum(certificado) as certificado, ";
        query1 += "sum(peritado) as peritado, sum(planos) as planos ";
        query1 += "from asist_tecnica,oficinas ";
        query1 += "where (ano=\""+sAno+"\") and (asist_tecnica.oficina=oficinas.codigo) ";
        query1 += "group by oficina ";

        try {
                Connection con = this.getConnection();

                Statement sentencia1 = con.createStatement();
                ResultSet rs1 = sentencia1.executeQuery(query1);
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
                    if ((y>=2) && (y<=5)) {
                      f[x][y] = rs1.getObject(y+1);
                    }
                    else {
                      f[x][y] = rs1.getString(y+1);
                    }
                  }
                  x++;
                }
                sentencia1.close();
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
      if ((columnIndex>=2) && (columnIndex<=5)) {
          return Float.class;
      }
      else {
        return String.class;
      }
    }

    /**
     * Returns the name of the specified column.
     */
    public String getColumnName(int columnIndex) {
      String nomCol="";
      switch (columnIndex) {
        case 0: nomCol="ano";break;
        case 1: nomCol="oficina";break;
        case 2: nomCol="proyectado";break;
        case 3: nomCol="certificado";break;
        case 4: nomCol="peritado";break;
        case 5: nomCol="planos";break;
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