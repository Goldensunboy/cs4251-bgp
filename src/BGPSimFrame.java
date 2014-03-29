/* Andrew Wilder *
 * Ilyssa Widen  */

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BGPSimFrame extends JFrame {

	private static final long serialVersionUID = -8060282881786300136L;
	private static final Dimension screenSize = new Dimension(800, 600);
	
	public BGPSimFrame() {

		// Set frame options
		super("BGP Simulation");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		setVisible(true);
		setResizable(false);
		getContentPane().setPreferredSize(screenSize);
		
		// Add panels
		BGPSimPanel simPanel = new BGPSimPanel(screenSize.width * 3 / 4, screenSize.height);
		add(simPanel);
		JPanel toolsContainer = new JPanel();
		toolsContainer.setLayout(new BoxLayout(toolsContainer, BoxLayout.Y_AXIS));
		toolsContainer.add(new BGPButtonPanel(simPanel));
		toolsContainer.add(new BGPTablePanel(simPanel));

		// Size frame to fit contents
		this.pack();
	}
	
	// Start the application
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		BGPSimFrame bsf = new BGPSimFrame();
	}
}
