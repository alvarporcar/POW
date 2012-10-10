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


public class RAA4 extends AbstractTableModel implements Parametres, RXXX{

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

  public RAA4(String sAno) {
        int files;
        int cols;

        String query11 = "drop table if exists tempAA2 ";
        String query22 = "drop table if exists tempAA2a ";

        String query1 = "create table tempAA2 ";
        query1 += "select ano, mes, descripcion as oficina,tipo,ayto,sum(udes) as num ";
        query1 += "from asist_admva,oficinas ";
        query1 += "where (ano=\""+sAno+"\") and (asist_admva.oficina=oficinas.codigo) ";
        query1 += "group by tipo,oficina ";
        query1 += "order by tipo";

        String query2 = "CREATE TABLE `tempAA2a` ( ";
        query2 += "`ano` char(4) default '0', ";
        query2 += "`mes` char(15) default '0', ";
        query2 += "`tipo` char(30) default '0', ";
        query2 += "`of_benassal` decimal(11,2) unsigned default '0', ";
        query2 += "`of_montanejos` decimal(11,2) unsigned default '0', ";
        query2 += "`of_morella` decimal(11,2) unsigned default '0', ";
        query2 += "`of_onda` decimal(11,2) unsigned default '0', ";
        query2 += "`of_segorbe` decimal(11,2) unsigned default '0', ";
        query2 += "`of_traiguera` decimal(11,2) unsigned default '0'";
        query2 += ")";

        String query3 = "select descripcion from tipo_asist_admva";

        String query4 = "select * from tempAA2";                

        try {
                Connection con = this.getConnection();

                Statement sentencia11 = con.createStatement();
                sentencia11.execute(query11);
                sentencia11.close();

                Statement sentencia22 = con.createStatement();
                sentencia22.execute(query22);
                sentencia22.close();

                Statement sentencia1 = con.createStatement();
                sentencia1.execute(query1);
                sentencia1.close();

                Statement sentencia2 = con.createStatement();
                sentencia2.execute(query2);
                sentencia2.close();

                Statement sentencia3 = con.createStatement();
                ResultSet rs3 = sentencia3.executeQuery(query3);

                Statement sentencia4 = con.createStatement();
                ResultSet rs4 = sentencia4.executeQuery(query4);

                String query7 = "SELECT COUNT(DISTINCT(ayto)) as numeximits, oficina";
                query7 += " FROM asist_admva,oficinas";
                query7 += " WHERE (ano=\""+sAno+"\") and (asist_admva.oficina=oficinas.codigo)";
                query7 += " group by oficina";
                query7 += " order by oficinas.descripcion";
                
                Statement sentencia7 = con.createStatement();
                ResultSet rs7 = sentencia7.executeQuery(query7);
                ResultSetMetaData rsmd7 = rs7.getMetaData();
                
                int eximits1, eximits2, eximits3, eximits4, eximits5, eximits6; 
                rs7.next();
                eximits1 = rs7.getInt("numeximits");
                rs7.next();
                eximits2 = rs7.getInt("numeximits");
                rs7.next();
                eximits3 = rs7.getInt("numeximits");
                rs7.next();
                eximits4 = rs7.getInt("numeximits");
                rs7.next();
                eximits5 = rs7.getInt("numeximits");
                rs7.next();
                eximits6 = rs7.getInt("numeximits");
                
                String query8 = "insert into tempAA2a values (\""+sAno+"\",\"\",\" ENT. LOCALES\","+eximits1+","+eximits2+","+eximits3+","+eximits4+","+eximits5+","+eximits6+")";
                Statement sentencia8 = con.createStatement();
                sentencia8.execute(query8);
                sentencia8.close();
                    
                    
                
                String query5 = "";
                String tipo ="";
                float quantitat,quantitat1,quantitat2,quantitat3,quantitat4,quantitat5,quantitat6;

                while (rs3.next()) {
                  tipo = rs3.getString("descripcion");
                  quantitat=0;quantitat1=0;quantitat2=0;quantitat3=0;quantitat4=0;quantitat5=0;quantitat6=0;
                  while (rs4.next()) {
                    if (tipo.equals(rs4.getString("tipo"))) {
                      quantitat=rs4.getFloat("num");
                      if (rs4.getString("oficina").equals("OF. BENASAL")) { quantitat1=quantitat;}
                      else if (rs4.getString("oficina").equals("OF. MONTANEJOS")) {quantitat2=quantitat;}
                           else if (rs4.getString("oficina").equals("OF. MORELLA")) {quantitat3=quantitat;}
                                else if (rs4.getString("oficina").equals("OF. ONDA")) {quantitat4=quantitat;}
                                     else if (rs4.getString("oficina").equals("OF. SEGORBE")) {quantitat5=quantitat;}
                                          else if (rs4.getString("oficina").equals("OF. TRAIGUERA")) {quantitat6=quantitat;}
                    }
                  }
                  query5 = "insert into tempAA2a values (\""+sAno+"\",\"\",\""+tipo+"\","+quantitat1+","+quantitat2+","+quantitat3+","+quantitat4+","+quantitat5+","+quantitat6+")";
                  Statement sentencia5 = con.createStatement();
                  sentencia5.execute(query5);
                  sentencia5.close();
                  rs4.beforeFirst();
                }
                sentencia3.close();sentencia4.close();
                               

             // select final per a formar la matriu AbstractTableModel
                String query6 = "select *,sum(of_traiguera+of_benassal+of_morella+of_onda+of_segorbe+of_montanejos)as total ";
                query6 += "from tempAA2a ";
                query6 += "group by tipo";

                Statement sentencia6 = con.createStatement();
                ResultSet rs6 = sentencia6.executeQuery(query6);
                ResultSetMetaData rsmd6 = rs6.getMetaData();

                files=0;
                while (rs6.next()) {
                  files++;
                }
                cols = rsmd6.getColumnCount();

                f = new Object[files][cols];
                c = new Object[cols];

                for (int i=0;i<cols;i++) {
                  c[i] = rsmd6.getColumnName(i+1);
                }

                int x=0;
                rs6.beforeFirst();
                while (rs6.next()) {
                  for (int y=0; y<cols; y++) {
                    if ((y>=3) && (y<=9)) {
                      f[x][y] = rs6.getObject(y+1);
                    }
                    else {
                      f[x][y] = rs6.getString(y+1);
                    }
                  }
                  x++;
                }
                sentencia6.close();
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
      if ((columnIndex>=3) && (columnIndex<=9)) {
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
        case 1: nomCol="mes";break;
        case 2: nomCol="tipo";break;
        case 3: nomCol="of_benassal";break;
        case 4: nomCol="of_montanejos";break;
        case 5: nomCol="of_morella";break;
        case 6: nomCol="of_onda";break;
        case 7: nomCol="of_segorbe";break;
        case 8: nomCol="of_traiguera";break;
        case 9: nomCol="total";break;
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