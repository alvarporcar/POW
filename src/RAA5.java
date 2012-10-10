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


public class RAA5 extends AbstractTableModel implements Parametres, RXXX{

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

  public RAA5(String sAno, String sPeticionari) {
        int files;
        int cols;

        String querya = "DROP TABLE IF EXISTS tempAA5a ";

        String query = "CREATE TABLE tempAA5a ";
        query += "SELECT ano,mes,tipo,ayto,udes ";
        query += "FROM asist_admva,mesos ";
        query += "WHERE (ano=\"" +sAno+ "\") AND (ayto like \"%"+sPeticionari+"%\") AND (mes=mesos.descripcio) ";
        query += "ORDER BY mesos.codi,tipo";

        String query1a = "DROP TABLE IF EXISTS tempAA5b";

        String query1= "CREATE TABLE tempAA5b ";
        query1 += "SELECT ano,mes,tipo,ayto,sum(udes) ";
        query1 += "FROM asist_admva,mesos ";
        query1 += "WHERE (ano=\"" +sAno+ "\") AND (ayto like \"%"+sPeticionari+"%\") AND (mes=mesos.descripcio) ";
        query1 += "GROUP BY tipo ";
        query1 += "ORDER BY tipo";

        String query1b = "UPDATE tempAA5b SET mes=\"TOTAL ASISTENCIAS\" ";

        String query2="INSERT INTO tempAA5b SELECT * FROM tempAA5a";

        String query3="SELECT * FROM tempAA5b";


        try {
                Connection con = this.getConnection();

                Statement sentenciaa = con.createStatement();
                sentenciaa.execute(querya);

                Statement sentencia = con.createStatement();
                sentencia.execute(query);

                Statement sentencia1a = con.createStatement();
                sentencia1a.execute(query1a);

                Statement sentencia1 = con.createStatement();
                sentencia1.execute(query1);

                Statement sentencia1b = con.createStatement();
                sentencia1b.execute(query1b);

                Statement sentencia2 = con.createStatement();
                sentencia2.execute(query2);

                Statement sentencia3 = con.createStatement();
                ResultSet rs3 = sentencia3.executeQuery(query3);
                ResultSetMetaData rsmd3 = rs3.getMetaData();

                files=0;
                while (rs3.next()) {
                  files++;
                }
                cols = rsmd3.getColumnCount();

                f = new Object[files][cols];
                c = new Object[cols];

                for (int i=0;i<cols;i++) {
                  c[i] = rsmd3.getColumnName(i+1);
                }

                int x=0;
                rs3.beforeFirst();
                while (rs3.next()) {
                  for (int y=0; y<cols; y++) {
                    if (y==4) {
                        f[x][y] = rs3.getObject(y+1);
                    }
                    else {
                      f[x][y] = rs3.getString(y+1);
                    }
                  }
                  x++;
                }
                sentencia3.close();
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
        if ((columnIndex==4))
          return Float.class;
        else
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
        case 2: nomCol="tipo";break;
        case 3: nomCol="ayto";break;
        case 4: nomCol="udes";break;
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