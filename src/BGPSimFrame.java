/* Andrew Wilder *
 * Ilyssa Widen  */

import java.awt.Dimension;
import javax.swing.JFrame;

public class BGPSimFrame extends JFrame {

	private static final Dimension screenSize = new Dimension(800, 600);
	
	public BGPSimFrame() {

		// Set frame options
		super("BGP Simulation");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(2, 1));
		setVisible(true);
		setResizable(false);
		getContentPane().setPreferredSize(screenSize);
		
		// Add panels
		JPanel simFrame = new BGPSimFrame();
		add(simFrame);

		JPanel toolsContainer = new JPanel();
		toolsContainer.

		// Size frame to fit contents
		pack();
	}
	
	public static void main(String[] args) {
		BGPSimFrame bsf = new BGPSimFrame();
		System.out.printf("Test\n");
	}
}
