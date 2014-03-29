/* Andrew Wilder *
 * Ilyssa Widen  */

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class BGPSimPanel extends JPanel {

	private static final long serialVersionUID = 1039121570149094556L;

	// Enumeration of the mode types
	public static enum BGPMode {
		EDIT,
		SIMULATE
	};
	public BGPMode mode;
	
	// Create a new simulation panel with a fixed size
	public BGPSimPanel() {
		mode = BGPMode.EDIT;
	}

	// Draw the components of the simulation area
	public void paintComponent(Graphics g) {
		
		// Clear the screen
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.BLUE);
		g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
		
		// Display mode
		g.setColor(Color.BLACK);
		g.drawString("Mode: " + (mode == BGPMode.EDIT ? "Edit" : "Simulate"), 5, 15);
		
		// Draw the nodes
		// TODO
	}
}
