import java.util.Calendar;

import javax.swing.JTextField;


public class Proves {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Calendar c1 = Calendar.getInstance();
		
		String dia = Integer.toString(c1.get(Calendar.DATE));
		String mes = Integer.toString(c1.get(Calendar.MONTH));
		String annio = Integer.toString(c1.get(Calendar.YEAR));
		
		System.out.println(annio + " " + mes+ " "+ dia);
		
		JTextField texte = new JTextField("caca de la vaca");
		System.out.println(texte.getText());

	}

}
