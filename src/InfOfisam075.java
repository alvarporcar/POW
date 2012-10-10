//package infofisam075;

import javax.swing.UIManager;
import java.awt.*;

/**
 * <p>Title: Informes Ofisam 075</p>
 * <p>Description: Explotaci� de la base de dades creada per POW-PHP per a imprimir informes</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Alvar Porcar</p>
 * @author unascribedfile:/usr/local/eclipse/workspace/jPOW
 * @version 1.0
 */ 
public class InfOfisam075 {
    boolean packFrame = false;
    
    //Construct the application
    InfOfisam075() {
        MarcInfOfisam frame = new MarcInfOfisam();
        //Validate frames that have preset sizes
        //Pack frames that have useful preferred size info, e.g. from their layout
        if (packFrame) {
            frame.pack();
        }
        else {
            frame.validate();
        }
        //Center the window
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        frame.setVisible(true);
    }
    //Main method
    public static void main(String[] args) {
        try {
            //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        new InfOfisam075();
    }
}