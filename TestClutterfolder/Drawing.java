import java.awt.*;
import javax.swing.*;

public class Drawing extends JFrame {
	private ImageIcon hextile;
	private ImageIcon anotherhex;
	private JLabel somelabel;
	private JLabel anotherlabel;

	Drawing() {
		setLayout(new FlowLayout());

		hextile = new ImageIcon(getClass().getResource("hex.png"));
		anotherhex = new ImageIcon(getClass().getResource("hex.png"));
		somelabel = new JLabel(hextile);
		anotherlabel = new JLabel(anotherhex);
		add(somelabel);
		add(anotherlabel);

	}

	public static void main (String args[]) {
		System.out.println(args[0]);
		Drawing gui = new Drawing();
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setVisible(true);
		gui.setSize(200,200);
		gui.setTitle("Image stuff");


	}
}