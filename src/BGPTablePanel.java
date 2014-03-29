/* Andrew Wilder *
 * Ilyssa Widen  */

import javax.swing.JPanel;

public class BGPTablePanel extends JPanel {

	private static final long serialVersionUID = 2906749678740103380L;
	
	// Link to the simulation panel for using node data
	private BGPSimPanel bsp;
	
	public BGPTablePanel(BGPSimPanel new_bsp) {
		bsp = new_bsp;
	}
}
