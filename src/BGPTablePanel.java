/* Andrew Wilder *
 * Ilyssa Widen  */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BGPTablePanel extends JPanel {

	private static final long serialVersionUID = 2906749678740103380L;
	
	// Link to the simulation panel for using node data
	public BGPSimPanel bsp;
	
	public BGPTablePanel(BGPSimPanel new_bsp) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		bsp = new_bsp;
		bsp.set_btp(this);
	}
	
	public void populateTable(ASNode a) {
		removeAll();
		if(a != null) {
			for(Integer i : a.paths.keySet()) {
				JPanel contents = new JPanel() {
					private static final long serialVersionUID = 6276724221354522978L;
					public void paintComponent(Graphics g) {
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
					}
				};
				contents.addMouseListener(new PanelHoverListener(bsp, i));
				contents.add(new JLabel("" + i + ":"));
				JTextField t = new JTextField(ASNode.PrintAS(a.paths.get(i)));
				t.setColumns(15);
				contents.add(t);
				contents.add(Box.createHorizontalGlue());
				add(contents);
			}
			add(Box.createVerticalGlue());
		}
		revalidate();
	}
	
	private class PanelHoverListener implements MouseListener {
		private BGPSimPanel bsp;
		private Integer dest;
		public PanelHoverListener(BGPSimPanel bsp, Integer i) {
			this.bsp = bsp;
			dest = i;
		}
		public void mouseEntered(MouseEvent e) {
			bsp.currHover = dest;
			bsp.repaint();
		}
		public void mouseExited(MouseEvent e) {
			bsp.currHover = -1;
			bsp.repaint();
		}
		public void mouseClicked(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
	}
}
