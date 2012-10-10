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


public class RAJ1 extends AbstractTableModel implements Parametres, RXXX{

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

  public RAJ1(String sMes, String sAno, String sOficina) {
        int files;
        int cols;

        String query0= "DROP TABLE IF EXISTS tempAJ1a";

        String query = "CREATE TABLE tempAJ1a ";
        query += "SELECT ano,mes,descripcion,peticionario,tipo,materia,asuntos,num,dia";
        query +=" FROM asist_juridica,oficinas";
        query +=" WHERE ((ano=\"" +sAno+ "\") AND (mes=\"" +sMes+ "\") AND (oficinas.descripcion=\"" +sOficina+ "\") AND (asist_juridica.oficina=oficinas.codigo))";
        query +=" ORDER BY materia,dia,peticionario";

        System.out.println(query);
        
        String query1 = "SELECT sum(num) FROM asist_juridica,oficinas";
        query1 += " WHERE ((ano=\"" +sAno+ "\") AND (mes=\"" +sMes+ "\") AND (oficinas.descripcion=\"" +sOficina+ "\") AND (asist_juridica.oficina=oficinas.codigo))";

        String query3 ="SELECT * FROM tempAJ1a";

        try {
                Connection con = this.getConnection();

                Statement sentencia0 = con.createStatement();
                sentencia0.execute(query0);

                Statement sentencia = con.createStatement();
                sentencia.execute(query);

                Statement sentencia1 = con.createStatement();
                ResultSet rs1 = sentencia1.executeQuery(query1);
                rs1.first();
                int suma = rs1.getInt(1);

                String query2 = "ALTER TABLE tempAJ1a ADD suma INT(3) DEFAULT " + suma;
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
                    if ((y==7) || (y==9)) {
                      f[x][y] = new Integer(rs3.getString(y+1));
                    }
                    else
                      f[x][y] = rs3.getString(y+1);
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
        if ((columnIndex==7) || (columnIndex==9)) {
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
        case 3: nomCol="peticionario";break;
        case 4: nomCol="tipo";break;
        case 5: nomCol="materia";break;
        case 6: nomCol="asuntos";break;
        case 7: nomCol="num";break;
        case 8: nomCol="dia";break;
        case 9: nomCol="suma";break;
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