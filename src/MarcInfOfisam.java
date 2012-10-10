/**
 * <p>Title: MarcOfisam</p>
 * <p>Description: Defineix el Formulari Principal dels tipus d'Informes.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Alvar Porcar</p>
 * @author unascribed
 * @version 1.0
 */
 

import java.awt.*;
import java.util.Calendar;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.sql.*;

import com.jrefinery.report.JFreeReport;
import com.jrefinery.report.io.ReportGenerator;
import com.jrefinery.report.preview.PreviewFrame;
import com.jrefinery.report.util.ExceptionDialog;
import com.jrefinery.ui.RefineryUtilities;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import javax.swing.event.*;
import javax.swing.table.*;

public class MarcInfOfisam extends JFrame implements Parametres {
    JPanel contentPane;
    
    static {
            try {
                    //registra els drivers de MySQL
                    Class.forName(driverClass);
            } catch ( ClassNotFoundException e) {
                    e.printStackTrace();
            }
    }


  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel3 = new JLabel();
  JLabel jLabel4 = new JLabel();
  JLabel jLabel5 = new JLabel();
  JLabel jLabel33 = new JLabel();  

  public JComboBox jComboMes;
  public JComboBox jComboOfisams = new JComboBox();


  public JTextField jTextPeticionari = new JTextField();
  JToolBar jToolBar1 = new JToolBar();
  JButton jButton2 = new JButton();
  TitledBorder titledBorder1;
  
  Calendar c1 = Calendar.getInstance();
  public JTextField jTextAny = new JTextField(Integer.toString(c1.get(Calendar.YEAR)));
  public JTextField jTextAnyFinal = new JTextField();
  
  ImageIcon imatgePrint = new ImageIcon("imatges/Print24.gif");
  ImageIcon imatgePreview= new ImageIcon("imatges/PrintPreview24.gif");
  ImageIcon imatgePageSetup= new ImageIcon("imatges/PageSetup24.gif");
  ImageIcon imatgeInformation= new ImageIcon("imatges/Information24.gif");
  JButton jButton4 = new JButton();
  
  protected JFreeReport report1;
  protected PreviewFrame frame1;

  DefaultListModel modelLlista;
  JList jList1;
  JScrollPane jScrollPane1;
  
  public Font font1;
  JLabel jLabel6 = new JLabel();
  JLabel jLabel7 = new JLabel();
  JLabel jLabel8 = new JLabel();
  JLabel jLabel9 = new JLabel();
  JLabel jLabel10 = new JLabel();
  JLabel jLabel11 = new JLabel();

  protected RVU1 data1;
  protected RVU2 data2;
  protected RVU3 data3;
  protected RVU4 data4;
  protected RVU5 data31;
  protected RVUI1 data5;
  protected RVUI2 data6;
  protected RVUI3 data7;
  protected RVUI4 data8;
  protected RVUI5 data9;
  protected RVUI6 data32;
  protected RAA1 data10;
  protected RAA2 data11;
  protected RAA3 data12;
  protected RAA4 data13;
  protected RAA5 data14;
  protected RAA6 data33;
  protected RAJ1 data15;
  protected RAJ2 data16;
  protected RAJ3 data17;
  protected RAJ4 data18;
  protected RAJ5 data19;
  protected RAJ6 data20;
  protected RAJ7 data21;
  protected RAJ8 data34;
  protected RAT1 data22;
  protected RAT2 data23;
  protected RAT3 data24;
  protected RAT4 data25;
  protected RAT5 data26;
  protected RAT6 data27;
  protected RAT7 data28;
  protected RAT8 data29;
  protected RAT9 data30;
  protected RAT10 data35;
  protected RVU6 data36;
  protected RVUI7 data37;  
  protected RAA7 data38;  
  protected RAJ9 data39;
  protected RAT11 data40;

  private ResourceBundle m_resources;

  private ResourceBundle getResources ()
  {
    return m_resources;
  }



  //Constructor de la classe. Construeix el Frame
  MarcInfOfisam() {
      
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    
    //setIconImage(Toolkit.getDefaultToolkit().createImage(MarcInfOfisam.class.getResource("[Your Icon]")));

    contentPane = (JPanel) this.getContentPane();
    titledBorder1 = new TitledBorder("");    

    contentPane.setLayout(null);
    this.setSize(new Dimension(900, 670));
    this.setTitle("INFORMES POW-JAVA");

    //Omple el combo dels tipus d'informes. S'ha modificat per a que no incloga ni els de Assistencia T�cnica ni els d'Inform�tica
    modelLlista = new DefaultListModel();

    jLabel4.setText("TIPUS D\'INFORME");
    // Rectangle(int x, int y, ample, alt). La coordenada (0,0) comen�a a contar des del cant� esquerre superior.
    jLabel4.setBounds(new Rectangle(551, 15, 110, 17));   
    
    // Afegeix a la llista modelLlista tots els tipus de reports disponibles.
    try {
        Connection con = getConnection();
        
        String query1 = "select descripcio from reports";
        Statement sentencia1 = con.createStatement();
        ResultSet rs1 = sentencia1.executeQuery(query1);
  
        rs1.beforeFirst();
        while (rs1.next()){
        	if (!rs1.getString("descripcio").startsWith("AI")) {
        		modelLlista.addElement(rs1.getString("descripcio"));
        	}     
        }        
        sentencia1.close();
        
    } catch (SQLException e) {
        System.err.println("Error en la base de dades: "+e);

    }          
    jList1 = new JList(modelLlista);
    jList1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    // Afegeix la barra d'escroll a la llista
    jScrollPane1 = new JScrollPane(jList1);

    jLabel1.setText("ANY");
    jLabel1.setBounds(new Rectangle(13, 66, 41, 17));    
    jTextAny.setBounds(new Rectangle(53, 63, 61, 21));
    
    
    // omple el combo de Mesos
    jLabel2.setText("MES");
    jLabel2.setBounds(new Rectangle(130, 63, 41, 17));
    String mesos[] = {"","ENERO","FEBRERO","MARZO","ABRIL", "MAYO", "JUNIO","JULIO","AGOSTO","SEPTIEMBRE","OCTUBRE","NOVIEMBRE","DICIEMBRE"};
    jComboMes = new JComboBox(mesos);
    jComboMes.setSelectedIndex(-1);
    jComboMes.setBounds(new Rectangle(166, 62, 125, 21));


    // omple el combo d'Ofisams
    jLabel3.setText("OFISAM");
    jLabel3.setBounds(new Rectangle(318, 62, 68, 17));
    
    try {
        Connection con = getConnection();
        
        String query1 = "SELECT descripcion FROM oficinas";
        Statement sentencia1 = con.createStatement();
        ResultSet rs1 = sentencia1.executeQuery(query1);
        
        jComboOfisams.addItem("");
        rs1.beforeFirst();
        while (rs1.next()) {
        	if (!rs1.getString("descripcion").equals("SEPAM")){
        		jComboOfisams.addItem(rs1.getString("descripcion"));       	
            }                    
        }                 
        sentencia1.close();
        
    } catch (SQLException e) {
        System.err.println("Error en la base de dades: "+e);

    }          
    jComboOfisams.setBounds(new Rectangle(380, 62, 125, 21));

    // omple el combo d'Anys Finals
    jLabel33.setText("ANY FINAL");
    jLabel33.setBounds(new Rectangle(13, 100, 80, 17));    
    jTextAnyFinal.setBounds(new Rectangle(95, 97, 61, 21));
            
    jLabel5.setText("PETICIONARI / AJUNTAM.");
    jLabel5.setBounds(new Rectangle(13, 175, 171, 24));
    jTextPeticionari.setBounds(new Rectangle(188, 173, 317, 21));

    // Barra de ferramentes de la part inferior
    jToolBar1.setBorder(BorderFactory.createEtchedBorder());
    jToolBar1.setFloatable(false);
    jToolBar1.setBounds(new Rectangle(3, 581, 515, 38));
    jButton2.setBorder(null);
    jButton2.setMaximumSize(new Dimension(40, 40));
    jButton2.setMinimumSize(new Dimension(40, 40));
    jButton2.setToolTipText("Elaborar informe");
    jButton2.setIcon(imatgePreview);
    jButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton2_actionPerformed(e);
      }
    });
    jButton4.setBorder(null);
    jButton4.setMaximumSize(new Dimension(40, 40));
    jButton4.setMinimumSize(new Dimension(40, 40));
    jButton4.setToolTipText("Informaci�");
    jButton4.setIcon(imatgeInformation);
   
    jScrollPane1 = new JScrollPane();
    jScrollPane1.setBounds(new Rectangle(551, 37, 324, 350));
    jScrollPane1.setViewportView(jList1);
    jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        jList1_valueChanged(e);
      }
    });


    jLabel6.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel6.setForeground(SystemColor.desktop);
    jLabel6.setText("VU : VENTANILLA UNICA");
    jLabel6.setBounds(new Rectangle(108, 314, 189, 17));
    jLabel7.setBounds(new Rectangle(108, 346, 236, 17));
    jLabel7.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel7.setForeground(SystemColor.desktop);
    jLabel7.setToolTipText("");
    jLabel7.setText("VUI : VENTANILLA UNICA INFORMACIO");
    jLabel8.setBounds(new Rectangle(108, 378, 206, 17));
    jLabel8.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel8.setForeground(SystemColor.desktop);
    jLabel8.setText("AA : ASSISTENCIA ADMINISTRATIVA");
    jLabel9.setBounds(new Rectangle(108, 410, 189, 17));
    jLabel9.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel9.setForeground(SystemColor.desktop);
    jLabel9.setText("AJ : ASSISTENCIA JURIDICA");
    //jLabel10.setBounds(new Rectangle(108, 443, 189, 17));
    //jLabel10.setFont(new java.awt.Font("Dialog", 1, 12));
    //jLabel10.setForeground(SystemColor.desktop);
    //jLabel10.setText("AT : ASSISTENCIA TECNICA");
    jLabel11.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel11.setForeground(SystemColor.desktop);
    jLabel11.setText("ABREVIATURES");
    jLabel11.setBounds(new Rectangle(72, 285, 114, 17));
    
    // Afegeix els components als seus contenidors.
    jToolBar1.add(jButton2, null);
    jToolBar1.add(jButton4, null);
    contentPane.add(jToolBar1, null);
    contentPane.add(jLabel10, null);
    contentPane.add(jLabel9, null);
    contentPane.add(jLabel8, null);
    contentPane.add(jLabel6, null);
    contentPane.add(jLabel7, null);
    contentPane.add(jLabel11, null);
    contentPane.add(jScrollPane1, null);
    contentPane.add(jLabel4, null);
    contentPane.add(jTextAny);
    contentPane.add(jTextAnyFinal);
    contentPane.add(jLabel33, null);
    contentPane.add(jComboMes, null);
    contentPane.add(jLabel1, null);
    contentPane.add(jLabel2, null);
    contentPane.add(jLabel3, null);
    contentPane.add(jComboOfisams, null);
    contentPane.add(jTextPeticionari, null);
    contentPane.add(jLabel5, null);
    

  }
  
  public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,login,clau);
  }
  
  //M�tode que s'executa quan la finestra es tancada
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      try {
        Connection con = getConnection();
        String query1 = "DROP TABLE IF EXISTS tempaa1,tempaa2,tempaa2a,tempaa5a,tempaa5b,tempaj1a,tempaj2,tempaj2a,tempaj3,tempaj3a,";
        query1 += "tempaj4a,tempaj5,tempaj5a,tempaj6,tempaj6a,tempaj7a,tempaj7b,tempat1a,tempat2,tempat2a,";
        query1 += "tempat3,tempat3a,tempat5,tempat5a,tempat6,tempat6a,tempat8a,tempat9a,tempvui1a,tempvui2a,tempvui2,tempvui3a";
        Statement sentencia1 = con.createStatement();
        sentencia1.execute(query1);
        sentencia1.close();
        System.exit(0);
      } catch (SQLException er) {
            System.err.println("Error en la base de dades: "+e);

      }
    }
  }


  // M�tode que s'executa en premer el JButton2  
  void jButton2_actionPerformed(ActionEvent e) {
    String sMes = (String) jComboMes.getSelectedItem();
    String sAny = jTextAny.getText();
    String sAnyFinal = jTextAnyFinal.getText();
    String sOficina = (String) jComboOfisams.getSelectedItem();
    String sPeticionari = jTextPeticionari.getText();
    switch (jList1.getSelectedIndex()) {
      case 0:
        data1 = new RVU1 (sMes,sAny,sOficina);
        preview("rVU1.xml",data1);
        break;
      case 1:
        data2 = new RVU2 (sMes,sAny);
        preview("rVU2.xml",data2);
        break;
      case 2:
        data3 = new RVU3 (sAny,sOficina);
        preview("rVU3.xml",data3);
        break;
      case 3:
        data4 = new RVU4 (sAny);
        preview("rVU4.xml",data4);
        break;
      case 4:
        data5 = new RVUI1 (sMes,sAny,sOficina);
        preview("rVUI1.xml",data5);
        break;
      case 5:
        data6 = new RVUI2 (sMes,sAny);
        preview("rVUI2.xml",data6);
        break;
      case 6:
        data7 = new RVUI3 (sAny,sOficina);
        preview("rVUI3.xml",data7);
        break;
      case 7:
        data8 = new RVUI4 (sAny);
        preview("rVUI4.xml",data8);
        break;
      case 8:
        data9 = new RVUI5(sAny,sPeticionari);
        preview("rVUI5.xml",data9);
        break;
      case 9:
        data10 = new RAA1(sMes,sAny,sOficina);
        preview("rAA1.xml",data10);
        break;
      case 10:
        data11 = new RAA2(sMes,sAny);
        preview("rAA2.xml",data11);
        break;
      case 11:
        data12 = new RAA3(sAny,sOficina);
        preview("rAA3.xml",data12);
        break;
      case 12:
        data13 = new RAA4(sAny);
        preview("rAA4.xml",data13);
        break;
      case 13:
        data14 = new RAA5(sAny,sPeticionari);
        preview("rAA5.xml",data14);
        break;
      case 14:
        data15 = new RAJ1(sMes,sAny,sOficina);
        preview("rAJ1.xml",data15);
        break;
      case 15:
        data16 = new RAJ2(sMes,sAny);
        preview("rAJ2.xml",data16);
        break;
      case 16:
        data17 = new RAJ3(sMes,sAny);
        preview("rAJ3.xml",data17);
        break;
      case 17:
        data18 = new RAJ4(sAny,sOficina);
        preview("rAJ4.xml",data18);
        break;
      case 18:
        data19 = new RAJ5(sAny);
        preview("rAJ5.xml",data19);
        break;
      case 19:
        data20 = new RAJ6(sAny);
        preview("rAJ6.xml",data20);
        break;
      case 20:
        data21 = new RAJ7(sAny,sPeticionari);
        preview("rAJ7.xml",data21);
        break;
      case 21:
          data22 = new RAT1(sMes,sAny,sOficina);
          preview("rAT1.xml",data22);
          break;      
      case 22:
          data23 = new RAT2(sMes,sAny);
          preview("rAT2.xml",data23);
          break;    	  
      case 23:
          data24 = new RAT3(sMes,sAny);
          preview("rAT3.xml",data24);
          break;         
      case 24:
          data25 = new RAT4(sMes,sAny);
          preview("rAT4.xml",data25);
          break;
      case 25:
          data26 = new RAT5(sAny);
          preview("rAT5.xml",data26);
          break;          
      case 26:
          data27 = new RAT6(sAny);
          preview("rAT6.xml",data27);
          break;	
      case 27:
          data28 = new RAT7(sAny);
          preview("rAT7.xml",data28);
          break;   	  
      case 28:
    	  data30 = new RAT9(sAny,sPeticionari);
          preview("rAT9.xml",data30);
          break;  	    
      case 29:
      	  data31 = new RVU5(sAny,sAnyFinal);
          preview("rVU5.xml",data31);
          break;   
      case 30:
          data32 = new RVUI6(sAny,sAnyFinal);
          preview("rVUI6.xml",data32);
          break;    	  
      case 31:
    	  data33 = new RAA6(sAny,sAnyFinal);
          preview("rAA6.xml", data33);
          break;      	  
      case 32:
       	  data34 = new RAJ8(sAny,sAnyFinal);
          preview("rAJ8.xml",data34);
          break;    	     	  
      case 33:
    	  data35 = new RAT10(sAny,sAnyFinal);
          preview("rAT10.xml",data35);
          break;
      case 34:
          data29 = new RAT8(sAny,sPeticionari);
          preview("rAT8.xml",data29);
          break;   
      case 35:
    	  data36 = new RVU6(sAny,sAnyFinal);
          preview("rVU6.xml",data36);
          break;         	  
      case 36:
       	  data37 = new RVUI7(sAny,sAnyFinal);
          preview("rVUI7.xml", data37);
          break;        
      case 37:
    	  data38 = new RAA7(sAny,sAnyFinal);
          preview("rAA7.xml",data38);
          break;     	  	    
      case 38:
       	  data39 = new RAJ9(sAny,sAnyFinal);
          preview("rAJ9.xml",data39);
          break;      
      case 39:
        data40 = new RAT11(sAny,sAnyFinal);
        preview("rAT11.xml",data40);
        break;
          
    }
  }

  public void preview (String urlname, TableModel data)
  {
    URL in = getClass ().getResource (urlname);
    if (in == null)
    {

      JOptionPane.showMessageDialog (this,
              MessageFormat.format(getResources().getString("report.definitionnotfound"), new Object[]{ urlname }),
              getResources().getString("error"), JOptionPane.ERROR_MESSAGE);
      return;
    }
    ReportGenerator gen = ReportGenerator.getInstance ();

    JFreeReport report1 = null;
    try
    {
      report1 = gen.parseReport (in, in);

    }
    catch (Exception ioe)
    {
      showExceptionDialog("report.definitionfailure", ioe);
      return;
    }

    if (report1 == null)
    {
      JOptionPane.showMessageDialog (this,
              MessageFormat.format(getResources().getString("report.definitionnull"), new Object[]{ urlname }),
              getResources().getString("error"), JOptionPane.ERROR_MESSAGE);
    }

    switch (jList1.getSelectedIndex()) {
      case 0:
          report1.setData (data1);
          break;
      case 1:
          report1.setData (data2);
          break;
      case 2:
          report1.setData (data3);
          break;
      case 3:
          report1.setData (data4);
          break;
      case 4:
          report1.setData(data5);
          break;
      case 5:
          report1.setData(data6);
          break;
      case 6:
          report1.setData(data7);
          break;
      case 7:
          report1.setData(data8);
          break;
      case 8:
          report1.setData(data9);
          break;
      case 9:
          report1.setData(data10);
          break;
      case 10:
          report1.setData(data11);
          break;
      case 11:
          report1.setData(data12);
          break;
      case 12:
          report1.setData(data13);
          break;
      case 13:
          report1.setData(data14);
          break;
      case 14:
          report1.setData(data15);
          break;
      case 15:
          report1.setData(data16);
          break;
      case 16:
          report1.setData(data17);
          break;
      case 17:
          report1.setData(data18);
          break;
      case 18:
          report1.setData(data19);
          break;
      case 19:
          report1.setData(data20);
          break;
      case 20:
          report1.setData(data21);
          break;
      case 21:
          report1.setData(data22);
          break;
      case 22:
          report1.setData(data23);
          break;
      case 23:
          report1.setData(data24);
          break;
      case 24:
          report1.setData(data25);
          break;
      case 25:
          report1.setData(data26);
          break;
      case 26:
          report1.setData(data27);
          break;
      case 27:
          report1.setData(data28);
          break;
      case 28:
          report1.setData(data30);
          break;
      case 29:
          report1.setData(data31);
          break;
      case 30:
          report1.setData(data32);
          break;
      case 31:
          report1.setData(data33);
          break;
      case 32:
          report1.setData(data34);
          break;
      case 33:
          report1.setData(data35);
          break;
      case 34:
          report1.setData(data29);
          break;
      case 35:
          report1.setData(data36);
          break;
      case 36:
          report1.setData(data37);
          break;
      case 37:
          report1.setData(data38);
          break;
      case 38:
          report1.setData(data39);
          break;
      case 39:
          report1.setData(data40);
          break;

      }

    frame1 = new PreviewFrame (report1);
    frame1.pack ();
    RefineryUtilities.positionFrameRandomly (frame1);
    frame1.show ();
    frame1.requestFocus ();
  }

  /**
   * Shows the exception dialog by using localized messages. The message base is
   * used to construct the localisation key by appending ".title" and ".message" to the
   * base name.
   */
  private void showExceptionDialog (String localisationBase, Exception e)
  {
    ExceptionDialog.showExceptionDialog (
            getResources().getString(localisationBase + ".title"),
            MessageFormat.format(
                    getResources().getString(localisationBase + ".message"),
                    new Object[]{ e.getLocalizedMessage()}
            ),
            e);
  }

  // Aquest m�tode s'activa quan es polsa un valor de la llista  
  void jList1_valueChanged(ListSelectionEvent e) {
    String report = new String((String) jList1.getSelectedValue());
    if (report.equals("VU/ MES-OFICINA")) {
      jTextPeticionari.setEnabled(false);
      jTextAny.setEnabled(true);
      jTextAnyFinal.setEnabled(false);  
      jComboMes.setEnabled(true);
      jComboOfisams.setEnabled(true);
    }
    if (report.equals("VU/ RESUM MENSUAL")) {
      jTextPeticionari.setEnabled(false);
      jTextAny.setEnabled(true);
      jTextAnyFinal.setEnabled(false);            
      jComboMes.setEnabled(true);
      jComboOfisams.setEnabled(false);
    }
    if (report.equals("VU/ ANY-OFICINA")) {
      jTextPeticionari.setEnabled(false);
      jTextAny.setEnabled(true);
      jTextAnyFinal.setEnabled(false);            
      jComboMes.setEnabled(false);
      jComboOfisams.setEnabled(true);
    }
    if (report.equals("VU/ RESUM ANUAL")) {
      jTextPeticionari.setEnabled(false);
      jTextAny.setEnabled(true);
      jTextAnyFinal.setEnabled(false);            
      jComboMes.setEnabled(false);
      jComboOfisams.setEnabled(false);
    }
    if (report.equals("VU/ EVOLUCIO INTERANUAL")) {
      jTextPeticionari.setEnabled(false);
      jTextAny.setEnabled(true);
      jTextAnyFinal.setEnabled(true);
      jComboMes.setEnabled(false);
      jComboOfisams.setEnabled(false);
    } 
    if (report.equals("VU/ EVOLUCIO INTERANUAL / OFISAM")) {
      jTextPeticionari.setEnabled(false);
      jTextAny.setEnabled(true);
      jTextAnyFinal.setEnabled(true);
      jComboMes.setEnabled(false);
      jComboOfisams.setEnabled(false);
    }       
    if (report.equals("VUI/ MES-OFICINA (DETALL)")) {
      jTextPeticionari.setEnabled(false);
      jTextAny.setEnabled(true);
      jTextAnyFinal.setEnabled(false);            
      jComboMes.setEnabled(true);
      jComboOfisams.setEnabled(true);
    }
    if (report.equals("VUI/ RESUM MENSUAL")) {
      jTextPeticionari.setEnabled(false);
      jTextAny.setEnabled(true);
      jTextAnyFinal.setEnabled(false);            
      jComboMes.setEnabled(true);      
      jComboOfisams.setEnabled(false);
    }
    if (report.equals("VUI/ ANY-OFICINA (DETALL)")) {
      jTextPeticionari.setEnabled(false);
      jTextAny.setEnabled(true);
      jTextAnyFinal.setEnabled(false);            
      jComboMes.setEnabled(false);
      jComboOfisams.setEnabled(true);
    }
    if (report.equals("VUI/ RESUM ANUAL")) {
      jTextPeticionari.setEnabled(false);
      jTextAny.setEnabled(true);
      jTextAnyFinal.setEnabled(false);            
      jComboMes.setEnabled(false);
      jComboOfisams.setEnabled(false);
    }
    if (report.equals("VUI/ ASSISTENCIES A PETICIONARI (ANUAL)")) {
      jTextPeticionari.setEnabled(true);
      jTextAny.setEnabled(true);
      jTextAnyFinal.setEnabled(false);            
      jComboMes.setEnabled(false);
      jComboOfisams.setEnabled(false);
    }
    if (report.equals("VUI/ EVOLUCIO INTERANUAL")) {
      jTextPeticionari.setEnabled(false);
      jTextAny.setEnabled(true);
      jTextAnyFinal.setEnabled(true);
      jComboMes.setEnabled(false);
      jComboOfisams.setEnabled(false);
    }    
    if (report.equals("VUI/ EVOLUCIO INTERANUAL / OFISAM")) {
      jTextPeticionari.setEnabled(false);
      jTextAny.setEnabled(true);
      jTextAnyFinal.setEnabled(true);
      jComboMes.setEnabled(false);
      jComboOfisams.setEnabled(false);
    }       
    if (report.equals("AA/ MES-OFICINA")) {
      jTextPeticionari.setEnabled(false);
      jTextAny.setEnabled(true);
      jTextAnyFinal.setEnabled(false);            
      jComboMes.setEnabled(true);
      jComboOfisams.setEnabled(true);
    }
    if (report.equals("AA/ RESUM MENSUAL")) {
      jTextPeticionari.setEnabled(false);
      jTextAny.setEnabled(true);
      jTextAnyFinal.setEnabled(false);            
      jComboMes.setEnabled(true);
      jComboOfisams.setEnabled(false);
    }
    if (report.equals("AA/ ANY-OFICINA")) {
      jTextPeticionari.setEnabled(false);
      jTextAny.setEnabled(true);
      jTextAnyFinal.setEnabled(false);            
      jComboMes.setEnabled(false);
      jComboOfisams.setEnabled(true);
    }
    if (report.equals("AA/ RESUM ANUAL")) {
      jTextPeticionari.setEnabled(false);
      jTextAny.setEnabled(true);
      jTextAnyFinal.setEnabled(false);            
      jComboMes.setEnabled(false);
      jComboOfisams.setEnabled(false);
    }
    if (report.equals("AA/ ASSISTENCIES A AJUNTAMENT (ANUAL)")) {
      jTextPeticionari.setEnabled(true);
      jTextAny.setEnabled(true);
      jTextAnyFinal.setEnabled(false);            
      jComboMes.setEnabled(false);
      jComboOfisams.setEnabled(false);
    }
    if (report.equals("AA/ EVOLUCIO INTERANUAL")) {
      jTextPeticionari.setEnabled(false);
      jTextAny.setEnabled(true);
      jTextAnyFinal.setEnabled(true);
      jComboMes.setEnabled(false);
      jComboOfisams.setEnabled(false);
    }    
    if (report.equals("AA/ EVOLUCIO INTERANUAL / OFISAM")) {
      jTextPeticionari.setEnabled(false);
      jTextAny.setEnabled(true);
      jTextAnyFinal.setEnabled(true);
      jComboMes.setEnabled(false);
      jComboOfisams.setEnabled(false);
    }      
    if (report.equals("AJ/ MES-OFICINA")) {
      jTextPeticionari.setEnabled(false);
      jTextAny.setEnabled(true);
      jTextAnyFinal.setEnabled(false);            
      jComboMes.setEnabled(true);
      jComboOfisams.setEnabled(true);
    }
     if (report.equals("AJ/ RESUM MENSUAL PER MATERIES")) {
     jTextPeticionari.setEnabled(false);
      jTextAny.setEnabled(true);
      jTextAnyFinal.setEnabled(false);            
      jComboMes.setEnabled(true);
      jComboOfisams.setEnabled(false);
    }
    if (report.equals("AJ/ RESUM MENSUAL PER TIPUS ASSISTENCIA")) {
      jTextPeticionari.setEnabled(false);
      jTextAny.setEnabled(true);
      jTextAnyFinal.setEnabled(false);            
      jComboMes.setEnabled(true);
      jComboOfisams.setEnabled(false);
    }
    if (report.equals("AJ/ ANY-OFICINA")) {
      jTextPeticionari.setEnabled(false);
      jTextAny.setEnabled(true);
      jTextAnyFinal.setEnabled(false);            
      jComboMes.setEnabled(false);
      jComboOfisams.setEnabled(true);
    }
    if (report.equals("AJ/ RESUM ANUAL PER MATERIES")) {
      jTextPeticionari.setEnabled(false);
      jTextAny.setEnabled(true);
      jTextAnyFinal.setEnabled(false);            
      jComboMes.setEnabled(false);
      jComboOfisams.setEnabled(false);
    }
    if (report.equals("AJ/ RESUM ANUAL PER TIPUS ASSISTENCIA")) {
      jTextPeticionari.setEnabled(false);
      jTextAny.setEnabled(true);
      jTextAnyFinal.setEnabled(false);            
      jComboMes.setEnabled(false);
      jComboOfisams.setEnabled(false);
    }
    if (report.equals("AJ/ ASSISTENCIES A AJUNTAMENT (ANUAL)")) {
      jTextPeticionari.setEnabled(true);
      jTextAny.setEnabled(true);
      jTextAnyFinal.setEnabled(false);            
      jComboMes.setEnabled(false);
      jComboOfisams.setEnabled(false);
    }
    if (report.equals("AJ/ EVOLUCIO INTERANUAL")) {
      jTextPeticionari.setEnabled(false);
      jTextAny.setEnabled(true);
      jTextAnyFinal.setEnabled(true);
      jComboMes.setEnabled(false);
      jComboOfisams.setEnabled(false);
    }    
    if (report.equals("AJ/ EVOLUCIO INTERANUAL / OFISAM")) {
      jTextPeticionari.setEnabled(false);
      jTextAny.setEnabled(true);
      jTextAnyFinal.setEnabled(true);
      jComboMes.setEnabled(false);
      jComboOfisams.setEnabled(false);
    }        
    if (report.equals("AT/ MES-OFICINA")) {
      jTextPeticionari.setEnabled(false);
      jTextAny.setEnabled(true);
      jTextAnyFinal.setEnabled(false);            
      jComboMes.setEnabled(true);
      jComboOfisams.setEnabled(true);
    }
    if (report.equals("AT/ RESUM MENSUAL PER MATERIES")) {
      jTextPeticionari.setEnabled(false);
      jTextAny.setEnabled(true);
      jTextAnyFinal.setEnabled(false);            
      jComboMes.setEnabled(true);
      jComboOfisams.setEnabled(false);
    }
    if (report.equals("AT/ RESUM MENSUAL PER TIPUS ASSISTENCIA")) {
      jTextPeticionari.setEnabled(false);
      jTextAny.setEnabled(true);
      jTextAnyFinal.setEnabled(false);            
      jComboMes.setEnabled(true);
      jComboOfisams.setEnabled(false);
    }
    if (report.equals("AT/ RESUM MENSUAL PER DINERS")) {
      jTextPeticionari.setEnabled(false);
      jTextAny.setEnabled(true);
      jTextAnyFinal.setEnabled(false);            
      jComboMes.setEnabled(true);
      jComboOfisams.setEnabled(false);
    }
    if (report.equals("AT/ RESUM ANUAL PER MATERIES")) {
      jTextPeticionari.setEnabled(false);
      jTextAny.setEnabled(true);
      jTextAnyFinal.setEnabled(false);            
      jComboMes.setEnabled(false);
      jComboOfisams.setEnabled(false);
    }
    if (report.equals("AT/ RESUM ANUAL PER TIPUS ASSISTENCIA")) {
      jTextPeticionari.setEnabled(false);
      jTextAny.setEnabled(true);
      jTextAnyFinal.setEnabled(false);            
      jComboMes.setEnabled(false);
      jComboOfisams.setEnabled(false);
    }
    if (report.equals("AT/ RESUM ANUAL PER DINERS")) {
      jTextPeticionari.setEnabled(false);
      jTextAny.setEnabled(true);
      jTextAnyFinal.setEnabled(false);            
      jComboMes.setEnabled(false);
      jComboOfisams.setEnabled(false);
    }
    if (report.equals("AT/ ASSISTENCIES A AJUNTAMENT (ANUAL)")) {
      jTextPeticionari.setEnabled(true);
      jTextAny.setEnabled(true);
      jTextAnyFinal.setEnabled(false);            
      jComboMes.setEnabled(false);
      jComboOfisams.setEnabled(false);
    }
    if (report.equals("AT/ ASSISTENCIES A AJUNTAMENT (RESUM)")) {
      jTextPeticionari.setEnabled(true);
      jTextAny.setEnabled(true);
      jTextAnyFinal.setEnabled(false);            
      jComboMes.setEnabled(false);
      jComboOfisams.setEnabled(false);
    }
    if (report.equals("AT/ EVOLUCIO INTERANUAL")) {
      jTextPeticionari.setEnabled(false);
      jTextAny.setEnabled(true);
      jTextAnyFinal.setEnabled(true);
      jComboMes.setEnabled(false);
      jComboOfisams.setEnabled(false);
    }     
    if (report.equals("AT/ EVOLUCIO INTERANUAL / OFISAM")) {
      jTextPeticionari.setEnabled(false);
      jTextAny.setEnabled(true);
      jTextAnyFinal.setEnabled(true);
      jComboMes.setEnabled(false);
      jComboOfisams.setEnabled(false);
    }       
  }


}