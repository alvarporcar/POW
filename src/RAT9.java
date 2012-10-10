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


public class RAT9 extends AbstractTableModel implements Parametres, RXXX {

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

  public RAT9(String sAno, String sPeticionari) {
        int files;
        int cols;
        double proy,cert,peri;
        String peticionari,plas,total_asist, DirObra, InfEsc, InfVerbal, MemValorada, VisInformativa;

        String query0= "DROP TABLE IF EXISTS tempAT9a";
        String query1= "CREATE TABLE tempAT9a (ano char(4) default NULL,peticionario char(50) default NULL, ";
        query1 += "proy double(19,2) default NULL,cert double(19,2) default NULL,peri double(19,2) default NULL, ";
        query1 += "plas char(4) default 0,total_asist char(4) NOT NULL , ";
        query1 += "DirObra char(4) default 0,InfEsc char(4) default 0, ";
        query1 += "InfVerbal char(4) default 0,MemValorada char(4) default 0, ";
        query1 += "VisInformativa char(4) default 0)  ";

        String query2 = "SELECT ano,peticionario,sum(proyectado) as proy,sum(certificado) as cert,sum(peritado) as peri,";
        query2 += "sum(planos) as plas,COUNT(*) as total_asist FROM asist_tecnica ";
        query2 += "WHERE ((ano=\"" +sAno+ "\") AND (peticionario like \"%"+sPeticionari+"%\")) ";
        query2 += "GROUP BY peticionario";

        String query3 = "SELECT COUNT(*) as DirObra FROM asist_tecnica ";
        query3 += "WHERE ((ano=\"" +sAno+ "\") AND (peticionario like \"%"+sPeticionari+"%\") AND (tipo=\"DO\")) ";
        query3 += "GROUP BY peticionario";

        String query4 = "SELECT COUNT(*) as InfEsc FROM asist_tecnica ";
        query4 += "WHERE ((ano=\"" +sAno+ "\") AND (peticionario like \"%"+sPeticionari+"%\") AND (tipo=\"IE\")) ";
        query4 += "GROUP BY peticionario";

        String query5 = "SELECT COUNT(*) as InfVerbal FROM asist_tecnica ";
        query5 += "WHERE ((ano=\"" +sAno+ "\") AND (peticionario like \"%"+sPeticionari+"%\") AND (tipo=\"IV\")) ";
        query5 += "GROUP BY peticionario";

        String query6 = "SELECT COUNT(*) as MemValorada FROM asist_tecnica ";
        query6 += "WHERE ((ano=\"" +sAno+ "\") AND (peticionario like \"%"+sPeticionari+"%\") AND (tipo=\"MV\")) ";
        query6 += "GROUP BY peticionario";

        String query7 = "SELECT COUNT(*) as VisInformativa FROM asist_tecnica ";
        query7 += "WHERE ((ano=\"" +sAno+ "\") AND (peticionario like \"%"+sPeticionari+"%\") AND (tipo=\"VI\")) ";
        query7 += "GROUP BY peticionario";

        String query9 ="SELECT * FROM tempAT9a";

        try {
                Connection con = this.getConnection();

                Statement sentencia0 = con.createStatement();
                sentencia0.execute(query0);

                Statement sentencia1 = con.createStatement();
                sentencia1.execute(query1);

                Statement sentencia2 = con.createStatement();
                ResultSet rs2 = sentencia2.executeQuery(query2);
                rs2.first();
                peticionari = rs2.getString(2);
                proy = rs2.getDouble(3);
                cert = rs2.getDouble(4);
                peri = rs2.getDouble(5);
                plas = rs2.getString(6);
                total_asist = rs2.getString(7);
                rs2.close();

                Statement sentencia3 = con.createStatement();
                ResultSet rs3 = sentencia3.executeQuery(query3);
                if (rs3.first())
                  DirObra  = rs3.getString(1);
                else DirObra= "0";
                rs3.close();

                Statement sentencia4 = con.createStatement();
                ResultSet rs4 = sentencia4.executeQuery(query4);
                if (rs4.first())
                  InfEsc= rs4.getString(1);
                else InfEsc="0";
                rs4.close();

                Statement sentencia5 = con.createStatement();
                ResultSet rs5 = sentencia5.executeQuery(query5);
                if (rs5.first())
                  InfVerbal= rs5.getString(1);
                else InfVerbal="0";
                rs5.close();

                Statement sentencia6 = con.createStatement();
                ResultSet rs6 = sentencia6.executeQuery(query6);
                if (rs6.first())
                  MemValorada= rs6.getString(1);
                else MemValorada="0";
                rs6.close();

                Statement sentencia7 = con.createStatement();
                ResultSet rs7 = sentencia7.executeQuery(query7);
                if (rs7.first())
                  VisInformativa= rs7.getString(1);
                else VisInformativa="0";
                rs7.close();

                String query8 = "INSERT INTO tempAT9a values (\""+sAno+"\",\""+peticionari+"\","+proy+","+cert+","+peri+","+plas+","+total_asist+","+DirObra+","+InfEsc+","+InfVerbal+","+MemValorada+","+VisInformativa+")";
                Statement sentencia8 = con.createStatement();
                sentencia8.execute(query8);
                sentencia8.close();

                Statement sentencia9 = con.createStatement();
                ResultSet rs9 = sentencia9.executeQuery(query9);

                ResultSetMetaData rsmd1 = rs9.getMetaData();
                files=0;
                while (rs9.next()) {
                  files++;
                }
                cols = rsmd1.getColumnCount();

                f = new Object[files][cols];
                c = new Object[cols];

                for (int i=0;i<cols;i++) {
                  c[i] = rsmd1.getColumnName(i+1);
                }

                int x=0;
                rs9.beforeFirst();
                while (rs9.next()) {
                  for (int y=0; y<cols; y++) {
                    if ((y>=3) || (y<=11)) {
                      f[x][y] = rs9.getObject(y+1);
                    }
                    else
                      f[x][y] = rs9.getString(y+1);
                  }
                  x++;
                }
                sentencia9.close();
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
        if ((columnIndex>=3) || (columnIndex<=11)) {
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
        case 1: nomCol="peticionario";break;
        case 2: nomCol="proyectado";break;
        case 3: nomCol="certificado";break;
        case 4: nomCol="peritado";break;
        case 5: nomCol="planos";break;
        case 6: nomCol="total_asist";break;
        case 7: nomCol="DirObra";break;
        case 8: nomCol="InfEsc";break;
        case 9: nomCol="InfVerbal";break;
        case 10: nomCol="MemValorada";break;
        case 11: nomCol="VisInformativa";break;
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