/* Andrew Wilder *
 * Ilyssa Widen  */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class BGPButtonPanel extends JPanel {
	
	private static final long serialVersionUID = 6181530790053151237L;
	
	// Link to the simulation panel for changing the mode
	private BGPSimPanel bsp;
	private JButton editModeButton = new JButton("Edit");
	private JButton simulateButton = new JButton("Simulate");

	// Create a new BGP Simulation Panel
	public BGPButtonPanel(BGPSimPanel new_bsp) {
		
		// Set the panel properties
		bsp = new_bsp;
		setLayout(new GridLayout(2, 1));
		this.setSize(getWidth(), 100);
		
		// Create the buttons for changing the mode
		editModeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				bsp.mode = BGPSimPanel.BGPMode.EDIT;
				bsp.repaint();
			}
		});
		simulateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				bsp.mode = BGPSimPanel.BGPMode.SIMULATE;
				bsp.repaint();
				// TODO start simulation timer
			}
		});
		
		// Add the buttons
		add(editModeButton);
		add(simulateButton);
	}
	
	// Set the background for this panel to green
	public void paintComponent(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
}