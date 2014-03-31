/* Andrew Wilder *
 * Ilyssa Widen  */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BGPSimFrame extends JFrame {

	private static final long serialVersionUID = -8060282881786300136L;
	private static final Dimension screenSize = new Dimension(1000, 600);
	
	public BGPSimFrame() {

		// Set frame options
		super("BGP Simulation");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		setLayout(new BorderLayout());
		// Add panels
		BGPSimPanel simPanel = new BGPSimPanel();
		add(simPanel, BorderLayout.CENTER);
		JPanel toolsContainer = new JPanel()/* {
			private static final long serialVersionUID = 1631408217357456691L;
			public void paintComponent(Graphics g) {
				g.setColor(Color.YELLOW);
				g.fillRect(0, 0, getWidth(), getHeight());
			}
		}*/;
		toolsContainer.setMaximumSize(new Dimension(300, screenSize.height));
		toolsContainer.setLayout(new BoxLayout(toolsContainer, BoxLayout.Y_AXIS));
		//toolsContainer.setLayout(new GridLayout(1, 2));
		BGPButtonPanel bbp = new BGPButtonPanel(simPanel);
		//bbp.setMaximumSize(new Dimension(300, 50));
		toolsContainer.add(bbp);
		BGPTablePanel btp = new BGPTablePanel(simPanel);
		toolsContainer.add(btp);
		add(toolsContainer, BorderLayout.EAST);

		// Size frame to fit contents
		getContentPane().setPreferredSize(screenSize);
		setResizable(false);
		this.pack();
		setVisible(true);
	}
	
	// Start the application
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		BGPSimFrame bsf = new BGPSimFrame();
	}
}
