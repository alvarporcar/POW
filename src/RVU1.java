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


public class RVU1 extends AbstractTableModel implements Parametres, RXXX {

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

  public RVU1(String sMes, String sAno, String sOficina) {
        int files;
        int cols;



        String query = "SELECT *,sum(pubs+privs) as totalRegistres ,sum(pubs+privs-gva-admonestado) as dipcas";
        query +=" FROM vu,oficinas";
        query +=" WHERE ((ano=\"" +sAno+ "\") AND (mes=\"" +sMes+ "\") AND (oficinas.descripcion=\"" +sOficina+ "\") AND (vu.oficina=oficinas.codigo)) GROUP BY oficina";
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
                      f[x][y] = rs1.getString(y+1);
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
        if (((columnIndex>=0) && (columnIndex<=2)) || (columnIndex==44))
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
        case 0: nomCol="mes";break;
        case 1: nomCol="ano";break;
        case 2: nomCol="oficina";break;
        case 3: nomCol="pubs";break;
        case 4: nomCol="privs";break;
        case 5: nomCol="admonestado";break;
        case 6: nomCol="gva";break;
        case 7: nomCol="archivo";break;
        case 8: nomCol="arqueologia";break;
        case 9: nomCol="carreteras";break;
        case 10: nomCol="castillo";break;
        case 11: nomCol="contratacion";break;
        case 12: nomCol="cultura";break;
        case 13: nomCol="imprenta";break;
        case 14: nomCol="informatica";break;
        case 15: nomCol="intervencion";break;
        case 16: nomCol="medioamb";break;
        case 17: nomCol="museo";break;
        case 18: nomCol="org";break;
        case 19: nomCol="otam";break;
        case 20: nomCol="parque";break;
        case 21: nomCol="penyeta";break;
        case 22: nomCol="personal";break;
        case 23: nomCol="planificacion";break;
        case 24: nomCol="piscina";break;
        case 25: nomCol="presidencia";break;
        case 26: nomCol="publicaciones";break;
        case 27: nomCol="recaudacion";break;
        case 28: nomCol="reginterior";break;
        case 29: nomCol="restauracion";break;
        case 30: nomCol="secretaria";break;
        case 31: nomCol="sepam";break;
        case 32: nomCol="tesoreria";break;
        case 33: nomCol="ceramica";break;
        case 34: nomCol="escuelataurina";break;
        case 35: nomCol="hospital";break;
        case 36: nomCol="turismo";break;
        case 37: nomCol="bomberos";break;
        case 38: nomCol="mataplana";break;
        case 39: nomCol="matanorte";break;
        case 40: nomCol="bop";break;
        case 41: nomCol="granja";break;
        case 42: nomCol="reggeneral";break;
        case 44: nomCol="descripcion"; break; // nom  de l'oficina
        case 46: nomCol="totalRegistres";break;
        case 47: nomCol="dipcas";
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