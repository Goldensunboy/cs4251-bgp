/* Andrew Wilder *
 * Ilyssa Widen  */

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class BGPTablePanel extends JPanel {

	private static final long serialVersionUID = 2906749678740103380L;
	
	// Link to the simulation panel for using node data
	private BGPSimPanel bsp;
	
	public BGPTablePanel(BGPSimPanel new_bsp) {
		bsp = new_bsp;
	}
	
	// Set the background for this panel to red
	public void paintComponent(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
}
