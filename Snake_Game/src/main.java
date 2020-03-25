import java.awt.Color;
import javax.swing.JFrame;

public class main {
	public static void main(String[] args) {
		int width = 700;
		int height = 700;
		
		//create a JFrame object and the game we are using
		JFrame frame = new JFrame("Snake");
		Gameplay game = new Gameplay();
		
		frame.setBounds(10,10,width, height);
		frame.setBackground(Color.GRAY);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(game);
	}

}
