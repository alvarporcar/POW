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


public class RAJ5 extends AbstractTableModel implements Parametres,RXXX {

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

  public RAJ5(String sAno) {
        int files;
        int cols;

        String query11 = "drop table if exists tempAJ5 ";
        String query22 = "drop table if exists tempAJ5a ";

        String query1 = "create table tempAJ5 ";
        query1 += "select ano,descripcion as oficina,materia,sum(num) as num ";
        query1 += "from asist_juridica,oficinas ";
        query1 += "where (ano=\""+sAno+"\") and (asist_juridica.oficina=oficinas.codigo) ";
        query1 += "group by materia,oficina ";
        query1 += "order by materia";

        String query2 = "CREATE TABLE `tempAJ5a` ( ";
        query2 += "`ano` char(4) default '0', ";
        query2 += "`materia` char(15) default '0', ";
        query2 += "`of_benassal` int(3) unsigned default '0', ";
        query2 += "`of_montanejos` int(3) unsigned default '0', ";
        query2 += "`of_morella` int(3) unsigned default '0', ";
        query2 += "`of_onda` int(3) unsigned default '0', ";
        query2 += "`of_segorbe` int(3) unsigned default '0', ";
        query2 += "`of_traiguera` int(3) unsigned default '0'";
        query2 += ") ";

        String query3 = "Select * from tipo_asist_jur";

        String query4 = "select * from tempAJ5";

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

                String query5 = "";
                String materia ="";
                int quantitat,quantitat1,quantitat2,quantitat3,quantitat4,quantitat5,quantitat6;

                while (rs3.next()) {
                  materia = rs3.getString("abreviatura");
                  quantitat=0;quantitat1=0;quantitat2=0;quantitat3=0;quantitat4=0;quantitat5=0;quantitat6=0;
                  while (rs4.next()) {
                    if (materia.equals(rs4.getString("materia"))) {
                      quantitat=rs4.getInt("num");
                      if (rs4.getString("oficina").equals("OF. BENASAL")) { quantitat1=quantitat;}
                      else if (rs4.getString("oficina").equals("OF. MONTANEJOS")) {quantitat2=quantitat;}
                           else if (rs4.getString("oficina").equals("OF. MORELLA")) {quantitat3=quantitat;}
                                else if (rs4.getString("oficina").equals("OF. ONDA")) {quantitat4=quantitat;}
                                     else if (rs4.getString("oficina").equals("OF. SEGORBE")) {quantitat5=quantitat;}
                                          else if (rs4.getString("oficina").equals("OF. TRAIGUERA")) {quantitat6=quantitat;}
                    }
                  }
                  query5 = "insert into tempAJ5a values (\""+sAno+"\",\""+materia+"\","+quantitat1+","+quantitat2+","+quantitat3+","+quantitat4+","+quantitat5+","+quantitat6+")";
                  Statement sentencia5 = con.createStatement();
                  sentencia5.execute(query5);
                  sentencia5.close();
                  rs4.beforeFirst();
                }
                sentencia3.close();sentencia4.close();

             // select final per a formar la matriu AbstractTableModel
                String query6 = "select *,sum(of_traiguera+of_benassal+of_morella+of_onda+of_segorbe+of_montanejos)as total ";
                query6 += "from tempAJ5a ";
                query6 += "group by materia";

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
                    if ((y>=2) && (y<=8)) {
                      f[x][y] = new Integer(rs6.getString(y+1));
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
      if ((columnIndex>=2) && (columnIndex<=8)) {
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
        case 1: nomCol="materia";break;
        case 2: nomCol="of_benassal";break;
        case 3: nomCol="of_montanejos";break;
        case 4: nomCol="of_morella";break;
        case 5: nomCol="of_onda";break;
        case 6: nomCol="of_segorbe";break;
        case 7: nomCol="of_traiguera";break;
        case 8: nomCol="total";break;
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