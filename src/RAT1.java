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


public class RAT1 extends AbstractTableModel implements Parametres, RXXX{

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

  public RAT1(String sMes, String sAno, String sOficina) {
        int files;
        int cols;

        String query0= "DROP TABLE IF EXISTS tempAT1a";

        String query = "CREATE TABLE tempAT1a ";
        query += "SELECT ano,mes,descripcion,expdte,nombre,tipo,peticionario,proyectado,certificado, ";
        query += "peritado,planos,situacion,dia ";
        query += "FROM asist_tecnica,oficinas ";
        query += "WHERE ((ano=\"" +sAno+ "\") AND (mes=\"" +sMes+ "\") AND (oficinas.descripcion=\"" +sOficina+ "\") AND (asist_tecnica.oficina=oficinas.codigo))";
        query += "ORDER BY expdte,peticionario,dia";

        String query1 ="SELECT * FROM tempAT1a";

        try {
                Connection con = this.getConnection();

                Statement sentencia0 = con.createStatement();
                sentencia0.execute(query0);

                Statement sentencia = con.createStatement();
                sentencia.execute(query);

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
                    if ((y>=7) || (y<=9)) {
                      f[x][y] = rs1.getObject(y+1);
                    }
                    else if (y==10) {
                            f[x][y] = new Integer(rs1.getString(y+1));
                          }
                          else
                              f[x][y] = rs1.getString(y+1);
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
        if ((columnIndex>=7) || (columnIndex<=9)) {
          return Float.class;
        }
        else if (columnIndex==10) {
          return Integer.class;
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
        case 1: nomCol="mes";break;
        case 2: nomCol="descripcion";break;
        case 3: nomCol="expdte";break;
        case 4: nomCol="nombre";break;
        case 5: nomCol="tipo";break;
        case 6: nomCol="peticionario";break;
        case 7: nomCol="proyectado";break;
        case 8: nomCol="certificado";break;
        case 9: nomCol="peritado";break;
        case 10: nomCol="planos";break;
        case 11: nomCol="situacion";break;
        case 12: nomCol="dia";break;
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