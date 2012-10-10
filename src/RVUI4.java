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


public class RVUI4 extends AbstractTableModel implements Parametres, RXXX{

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

  public RVUI4(String sAno) {
        int files;
        int cols;

        String query11 = "drop table if exists tempVUI2 ";
        String query22 = "drop table if exists tempVUI2a ";

        String query1 = "create table tempVUI2 ";
        query1 += "select ano, depto, descripcion AS oficina,sum(num) as num ";
        query1 += "from vinf,oficinas ";
        query1 += "where (ano=\""+sAno+"\") and (vinf.oficina=oficinas.codigo) ";
        query1 += "group by depto,oficina ";
        query1 += "order by depto";

        //String query2 =
        String query2="";
        query2 += "CREATE TABLE `tempVUI2a` ( ";
        query2 += "`ano` char(4) default '0', ";
        query2 += "`depto` char(20) default '0', ";
        query2 += "`of_benassal` int(5) unsigned default '0', ";
        query2 += "`of_montanejos` int(5) unsigned default '0', ";
        query2 += "`of_morella` int(5) unsigned default '0', ";
        query2 += "`of_onda` int(5) unsigned default '0', ";
        query2 += "`of_segorbe` int(5) unsigned default '0', ";
        query2 += "`of_traiguera` int(5) unsigned default '0', ";
        query2 += "`publicos` int(5) unsigned default '0', ";
        query2 += "`privados` int(5) unsigned default '0' ";
        query2 += ")";

        String query3 = "select descripcion from deptos";

        String query4 = "select * from tempVUI2";


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
                String departament ="";
                int quantitat,quantitat1,quantitat2,quantitat3,quantitat4,quantitat5,quantitat6;

                while (rs3.next()) {
                  departament = rs3.getString("descripcion");
                  quantitat=0;quantitat1=0;quantitat2=0;quantitat3=0;quantitat4=0;quantitat5=0;quantitat6=0;
                  while (rs4.next()) {
                    if (departament.equals(rs4.getString("depto"))) {
                      quantitat=rs4.getInt("num");
                      if (rs4.getString("oficina").equals("OF. BENASAL")) { quantitat1=quantitat;}
                      else if (rs4.getString("oficina").equals("OF. MONTANEJOS")) {quantitat2=quantitat;}
                           else if (rs4.getString("oficina").equals("OF. MORELLA")) {quantitat3=quantitat;}
                                else if (rs4.getString("oficina").equals("OF. ONDA")) {quantitat4=quantitat;}
                                     else if (rs4.getString("oficina").equals("OF. SEGORBE")) {quantitat5=quantitat;}
                                          else if (rs4.getString("oficina").equals("OF. TRAIGUERA")) {quantitat6=quantitat;}
                    }
                  }
                  query5 = "insert into tempVUI2a values (\""+sAno+"\",\""+departament+"\","+quantitat1+","+quantitat2+","+quantitat3+","+quantitat4+","+quantitat5+","+quantitat6+",0,0)";
                  Statement sentencia5 = con.createStatement();
                  sentencia5.execute(query5);
                  sentencia5.close();
                  rs4.beforeFirst();
                }
                sentencia3.close();sentencia4.close();

                String query7 = "select sum(of_benassal),sum(of_montanejos),";
                query7 += "sum(of_morella),sum(of_onda),";
                query7 += "sum(of_segorbe),sum(of_traiguera) ";
                query7 += "from tempVUI2a ";
                query7 += "where (depto<>\"ADMONESTADO\") and (depto<>\"GVA\")";
                Statement sentencia7 = con.createStatement();
                ResultSet rs7 = sentencia7.executeQuery(query7);
                rs7.first();
                quantitat1=rs7.getInt(1);
                quantitat2=rs7.getInt(2);
                quantitat3=rs7.getInt(3);
                quantitat4=rs7.getInt(4);
                quantitat5=rs7.getInt(5);
                quantitat6=rs7.getInt(6);
                sentencia7.close();

                String query8 = "insert into tempVUI2a values (\""+sAno+"\",\"DIPUTACION\","+quantitat1+","+quantitat2+","+quantitat3+","+quantitat4+","+quantitat5+","+quantitat6+",0,0)";
                Statement sentencia8 = con.createStatement();
                sentencia8.execute(query8);
                sentencia8.close();

                // calcula el total de registres particulars
                String query9 = "select sum(num) ";
                query9 += "from vinf ";
                query9 += "where (ano=\""+sAno+"\") and (pub=\"N\") ";
                int totalParticulars;
                Statement sentencia9 = con.createStatement();
                ResultSet rs9 = sentencia9.executeQuery(query9);
                rs9.first();
                totalParticulars=rs9.getInt(1);
                sentencia9.close();

                //inserta el numero de privats en la tabla
                String query10 = "update tempVUI2a set privados="+totalParticulars;
                Statement sentencia10 = con.createStatement();
                sentencia10.execute(query10);
                sentencia10.close();

                //calcula el numero de registres p�blics
                String query12 = "select sum(of_benassal+of_montanejos+of_morella+of_onda+of_segorbe+of_traiguera) ";
                query12 +="from tempVUI2a ";
                query12 +="where (depto=\"gva\") or (depto=\"admonestado\") or (depto=\"diputacion\")";
                int totalPublics;
                Statement sentencia12 = con.createStatement();
                ResultSet rs12 = sentencia12.executeQuery(query12);
                rs12.first();
                totalPublics=rs12.getInt(1)-totalParticulars;
                sentencia12.close();

                //inserta el numero de publics en la tabla
                String query13 = "update tempVUI2a set publicos="+totalPublics;
                Statement sentencia13 = con.createStatement();
                sentencia13.execute(query13);
                sentencia13.close();


               // select final per a formar la matriu AbstractTableModel
                String query6 = "select *,sum(of_benassal+of_montanejos+of_morella+of_onda+of_segorbe+of_traiguera) as total ";
                query6 += "from tempVUI2a ";
                query6 += "where (depto=\"admonestado\") or (depto=\"gva\") or (depto=\"diputacion\") ";
                query6 += "group by depto";
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
                    if ((y==0) || (y==1)){
                        f[x][y] = rs6.getString(y+1);
                    }
                    else {
                      f[x][y] = new Integer(rs6.getString(y+1));
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
        if ((columnIndex==0) || (columnIndex==1))
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
        case 0: nomCol="ano";break;
        case 1: nomCol="depto";break;
        case 2: nomCol="of_benassal";break;
        case 3: nomCol="of_montanejos";break;
        case 4: nomCol="of_morella";break;
        case 5: nomCol="of_onda";break;
        case 6: nomCol="of_segorbe";break;
        case 7: nomCol="of_traiguera";break;
        case 8: nomCol="publicos";break;
        case 9: nomCol="privados";break;
        case 10: nomCol="total";break;
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