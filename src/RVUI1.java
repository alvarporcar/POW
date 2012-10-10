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


public class RVUI1 extends AbstractTableModel implements Parametres, RXXX{

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

  public RVUI1(String sMes, String sAno, String sOficina) {
        int files;
        int cols;

        String query11 = "drop table if exists tempVUI1a";

        String query2 = "CREATE TABLE tempVUI1a ";
        query2 += "SELECT ano,mes,dia,descripcion,tipo,peticionario,depto,pub,num,asuntos ";
        query2 += "FROM vinf,oficinas ";
        query2 += "WHERE ((ano=\"" +sAno+ "\") AND (mes=\"" +sMes+ "\") AND (oficinas.descripcion=\"" +sOficina+ "\") AND (vinf.oficina=oficinas.codigo)) ";
        query2 += "ORDER BY depto";

        String query3 ="ALTER TABLE tempVUI1a ADD suma int(5) unsigned default '0'";

        String query4 = "Select sum(num) as suma from tempVUI1a";

        String query5 = "SELECT * FROM tempVUI1a";

        System.out.println(query2);
        System.out.println(query3);
        System.out.println(query4);
        System.out.println(query5);
        
        try {
                Connection con = this.getConnection();

                Statement sentencia11 = con.createStatement();
                sentencia11.execute(query11);

                Statement sentencia2 = con.createStatement();
                sentencia2.execute(query2);

                Statement sentencia3 = con.createStatement();
                sentencia3.execute(query3);

                Statement sentencia4 = con.createStatement();
                ResultSet rs4 = sentencia4.executeQuery(query4);
                rs4.first();
                int sumaNum;
                sumaNum = rs4.getInt(1);


                Statement sentencia5 = con.createStatement();
                ResultSet rs5 = sentencia5.executeQuery(query5);
                String query6;
                while (rs5.next()) {
                  query6 ="UPDATE tempVUI1a SET suma="+sumaNum;
                  Statement sentencia6 = con.createStatement();
                  sentencia6.execute(query6);
                }

                Statement sentencia = con.createStatement();
                ResultSet rs1 = sentencia.executeQuery(query5);
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
                    if (y==8) {
                        f[x][y] = new Integer(rs1.getString(y+1));
                    }
                    else {
                      f[x][y] = rs1.getString(y+1);
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
        if (columnIndex==8)
          return Integer.class;

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
        case 2: nomCol="dia";break;
        case 3: nomCol="descripcion";break;
        case 4: nomCol="tipo";break;
        case 5: nomCol="peticionario";break;
        case 6: nomCol="depto";break;
        case 7: nomCol="pub";break;
        case 8: nomCol="num";break;
        case 9: nomCol="asuntos";break;
        case 10: nomCol="suma";break;
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