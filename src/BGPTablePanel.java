/* Andrew Wilder *
 * Ilyssa Widen  */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
		bsp.viewComboBox.addActionListener(new RepaintListener(this));
	}
	
	public void populateTable(ASNode a) {
		
		// Repopulate the table by removing all current elements
		removeAll();
		
		// Determine if AS or IP table to show
		if("AS table".equals(bsp.viewComboBox.getSelectedItem())) {
			
			// Show AS table (if a node is hovered or selected)
			if(a != null) {
				
				// Add a button for pathing to an IP
				JPanel pathPanel = new JPanel();
				JButton pathButton = new JButton("Navigate");
				pathButton.addActionListener(new PanelPathButtonListener(bsp));
				pathPanel.add(pathButton);
				add(pathPanel);
				
				// Add panels for each node the selected one can path to
				for(Integer i : a.paths.keySet()) {
					
					// New panel to which the data is added
					ContentsPanel contents = new ContentsPanel();
					contents.addMouseListener(new PanelHoverListener(bsp, i, contents));
					
					// Add a label for the node
					contents.add(new JLabel("" + i + ":"));
					
					// Add a text field for the path
					JTextField t = new JTextField(ASNode.PrintAS(a.paths.get(i)));
					t.setColumns(10);
					contents.add(t);
					
					// Add a button to update the path
					JButton b = new JButton("Update");
					b.addActionListener(new PanelButtonListener(bsp, i, t));
					contents.add(b);
					
					//contents.add(Box.createHorizontalGlue());
					add(contents);
				}
			}
		} else {
			
			// Show IP table (if a node is hovered or selected)
			if(a != null) {
				
				// Add a button for adding to the IP table
				JPanel addPanel = new JPanel();
				JButton addButton = new JButton("Add entry");
				addButton.addActionListener(new AddButtonListener(bsp));
				addPanel.add(addButton);
				add(addPanel);
				
				// Add a panel for each entry in the IP table
				// TODO
			}
		}
		
		// Refresh the UI elements
		revalidate();
	}
	
	private class ContentsPanel extends JPanel {
		private static final long serialVersionUID = 7160322308847208940L;
		public Color c = Color.CYAN;
		public ContentsPanel() {
		}
		public void paintComponent(Graphics g) {
			g.setColor(Color.BLACK);
			g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
			g.setColor(c);
			g.fillRect(1, 1, getWidth() - 2, getHeight() - 2);
		}
	}
	
	private class PanelHoverListener implements MouseListener {
		private BGPSimPanel bsp;
		private Integer i;
		private ContentsPanel cp;
		public PanelHoverListener(BGPSimPanel bsp, Integer i, ContentsPanel cp) {
			this.bsp = bsp;
			this.i = i;
			this.cp = cp;
		}
		public void mouseEntered(MouseEvent e) {
			cp.c = Color.GREEN;
			cp.repaint();
			bsp.currHover = i;
			bsp.repaint();
		}
		public void mouseExited(MouseEvent e) {
			cp.c = Color.CYAN;
			cp.repaint();
			bsp.currHover = -1;
			bsp.repaint();
		}
		public void mouseClicked(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
	}
	
	private class AddButtonListener implements ActionListener {
		private BGPSimPanel bsp;
		public AddButtonListener(BGPSimPanel bsp) {
			this.bsp = bsp;
		}
		public void actionPerformed(ActionEvent e) {
			String s = JOptionPane.showInputDialog(null, "Prefix nexthop #hops:", "Add a new IP prefix", JOptionPane.PLAIN_MESSAGE);
			
			// Make sure the input is the correct format
			if(Pattern.matches("\\d+(\\.\\d+){3}/\\d+ \\d+ \\d+", s)) {
				String[] input = s.split("\\.|/| ");
				for(int i = 0; i < 7; ++i) {
					switch(i) {
					case 0:
					case 1:
					case 2:
					case 3:
						if(Integer.parseInt(input[i]) > 255) {
							JOptionPane.showMessageDialog(null, "Number out of ipv4 range: " + Integer.parseInt(input[i]), "Input Error", JOptionPane.ERROR_MESSAGE);
							return;
						}
						break;
					case 4:
						if(Integer.parseInt(input[4]) > 255) {
							JOptionPane.showMessageDialog(null, "Prefix out of ipv4 range: " + Integer.parseInt(input[4]), "Input Error", JOptionPane.ERROR_MESSAGE);
							return;
						}
						break;
					case 5:
						if(!bsp.currSelected.neighbors.contains(new ASNode(Integer.parseInt(input[5])))) {
							JOptionPane.showMessageDialog(null, "Next hop not a neighbor: " + Integer.parseInt(input[i]), "Input Error", JOptionPane.ERROR_MESSAGE);
							return;
						}
						break;
					default:
					}
				}
				
				// Passed validation!
				
				
			} else {
				JOptionPane.showMessageDialog(null, "Example format: 143.128.5.0/24 4 0", "Input Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private class PanelPathButtonListener implements ActionListener {
		private BGPSimPanel bsp;
		public PanelPathButtonListener(BGPSimPanel bsp) {
			this.bsp = bsp;
		}
		public void actionPerformed(ActionEvent e) {
			String s = JOptionPane.showInputDialog(null, "IP to path to:", "Where would you like to go?", JOptionPane.PLAIN_MESSAGE);
			
			// Make sure the input is the correct format
			if(Pattern.matches("\\d+(\\.\\d+){3}", s)) {
				String[] input = s.split("\\.");
				for(int i = 0; i < 4; ++i) {
					if(Integer.parseInt(input[i]) > 255) {
						JOptionPane.showMessageDialog(null, "Number out of ipv4 range: " + Integer.parseInt(input[i]), "Input Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				
				// Input passed validation!
				
			} else {
				JOptionPane.showMessageDialog(null, "Example format: 143.128.100.5", "Input Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private class PanelButtonListener implements ActionListener {
		private BGPSimPanel bsp;
		private Integer i;
		private JTextField t;
		public PanelButtonListener(BGPSimPanel bsp, Integer i, JTextField t) {
			this.bsp = bsp;
			this.i = i;
			this.t = t;
		}
		public void actionPerformed(ActionEvent e) {
			
			// Make sure the text field has the correct format for input
			if(Pattern.matches("\\d+( \\d+)*", t.getText())) {
				
				// Iterate over the numbers with a scanner
				Scanner nodeValidator = new Scanner(t.getText());
				ArrayList<ASNode> newList = new ArrayList<ASNode>();
				while(nodeValidator.hasNext()) {
					int num = nodeValidator.nextInt();
					
					// Find the ASNode with the corresponding number
					ASNode theNode = null;
					for(ASNode a : bsp.nodeList) {
						if(a.ASNum == num) {
							theNode = a;
						}
					}
					
					// If the node wasn't found, give an error
					if(theNode == null) {
						JOptionPane.showMessageDialog(null, "Node not found: " + num, "Input Error", JOptionPane.ERROR_MESSAGE);
						return;
					} else {
						newList.add(theNode);
					}
				}
				
				// Validate that the path is traversible
				ASNode prev = bsp.currSelected;
				Iterator<ASNode> itr = newList.iterator();
				while(itr.hasNext()) {
					ASNode a = itr.next();
					if(prev.neighbors.contains(a)) {
						prev = a;
					} else {
						JOptionPane.showMessageDialog(null, "No link between " + prev.ASNum + " and " + a.ASNum, "Input Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				
				// Replace the routing entry with the new list
				bsp.currSelected.paths.put(i, newList);
			} else {
				JOptionPane.showMessageDialog(null, "Invalid AS path string", "Input Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private class RepaintListener implements ActionListener {
		private BGPTablePanel btp;
		public RepaintListener(BGPTablePanel btp) {
			this.btp = btp;
		}
		public void actionPerformed(ActionEvent e) {
			btp.populateTable(bsp.currSelected);
		}
	}
}
