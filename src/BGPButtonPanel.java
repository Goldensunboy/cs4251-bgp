/* Andrew Wilder *
 * Ilyssa Widen  */

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class BGPButtonPanel extends JPanel {
	
	private static final long serialVersionUID = 6181530790053151237L;
	
	// Link to the simulation panel for changing the mode
	private BGPSimPanel bsp;
	private JButton editModeButton = new JButton("Edit");
	private JButton clearButton = new JButton("Clear");

	// Create a new BGP Simulation Panel
	public BGPButtonPanel(BGPSimPanel new_bsp) {
		
		// Set the panel properties
		bsp = new_bsp;
		setLayout(new GridLayout(1, 3));
		setPreferredSize(new Dimension(250, 25));
		setMaximumSize(new Dimension(250, 25));
		
		// Create the buttons for changing the mode
		editModeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bsp.mode = BGPSimPanel.BGPMode.EDIT;
				bsp.currSelected = null;
				bsp.repaint();
			}
		});
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bsp.currSelected = null;
				bsp.ascounter = 0;
				bsp.nodeList = new ArrayList<ASNode>();
				bsp.btp.populateTable(null);
				bsp.repaint();
			}
		});
	
		// Add the elements
		add(editModeButton);
		add(clearButton);
		add(bsp.viewComboBox);
	}
}
