import java.awt.Dimension;

import javax.swing.JFrame;


public class BGPSimFrame extends JFrame {

	private static final Dimension screenSize = new Dimension(800, 600);
	
	public BGPSimFrame() {
		super("BGP Simulation");
	}
	
	public static void main(String[] args) {
		BGPSimFrame bsf = new BGPSimFrame();
	}
}
