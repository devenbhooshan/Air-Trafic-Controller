import java.io.IOException;
import java.net.ServerSocket;

import javax.swing.JFrame;


public class Main extends JFrame {
	
	public Main(){
		
		setTitle("ATC");
		setSize(1300,700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(new GUI());
		setVisible(true);
		
	}

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		new Main();
		
		
	}

}
