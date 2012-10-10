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


public class RAT8 extends AbstractTableModel implements Parametres, RXXX {

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

  public RAT8(String sAno, String sPeticionari) {
        int files;
        int cols;

        String query0= "DROP TABLE IF EXISTS tempAT8a";

        String query = "CREATE TABLE tempAT8a ";
        query += "SELECT ano,nombre,tipo,peticionario,materia,proyectado,certificado, ";
        query += "peritado,planos,situacion,expdte ";
        query += "FROM asist_tecnica ";
        query += "WHERE ((ano=\"" +sAno+ "\") AND (peticionario like \"%"+sPeticionari+"%\")) ";
        query += "ORDER BY expdte";

        String query1 ="SELECT * FROM tempAT8a";

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
                    if ((y>=5) || (y<=8)) {
                      f[x][y] = rs1.getObject(y+1);
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
        if ((columnIndex>=5) || (columnIndex<=8)) {
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
        case 1: nomCol="nombre";break;
        case 2: nomCol="tipo";break;
        case 3: nomCol="peticionario";break;
        case 4: nomCol="materia";break;
        case 5: nomCol="proyectado";break;
        case 6: nomCol="certificado";break;
        case 7: nomCol="peritado";break;
        case 8: nomCol="planos";break;
        case 9: nomCol="situacion";break;
        case 10: nomCol="expdte";break;
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