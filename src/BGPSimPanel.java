/* Andrew Wilder *
 * Ilyssa Widen  */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JPanel;

public class BGPSimPanel extends JPanel implements MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1039121570149094556L;
	private static final double NodeSize = 20,
            LineSize = 5;
	public int ascounter;
	private static final double PATH_SIZE = 3;
	
	// Combo box for choosing how to view data
	public String[] comboChoices = {
		"AS table",
		"IP table"
	};
	public JComboBox<String> viewComboBox = new JComboBox<String>(comboChoices);
	
	// Link to the table panel for showing node info
	BGPTablePanel btp;

	// Enumeration of the mode types
	public static enum BGPMode {
		EDIT,
		SIMULATE;
	};
	public BGPMode mode;
	
	// List of ASNodes used in the simulator
	public ArrayList<ASNode> nodeList;
	public ASNode currSelected;
	public int currHover;
	private int selOffX, selOffY, mdx, mdy;
	
	// Create a new simulation panel with a fixed size
	public BGPSimPanel() {
		
		// Set up object variables
		mode = BGPMode.EDIT;
		nodeList = new ArrayList<ASNode>();
		currSelected = null;
		currHover = -1;
		ascounter = 0;
		selOffX = 0;
		selOffY = 0;
		
		// Set up object properties
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public void set_btp(BGPTablePanel btp) {
		this.btp = btp;
	}
	
	// Draw the components of the simulation area
	public void paintComponent(Graphics g) {
		
		// Clear the screen
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
		//g.drawString("Mode: " + (mode == BGPMode.EDIT ? "Edit" : "Simulate"), 5, 15);
		
		// Draw the lines
		Set<NodePair> adjList = new HashSet<NodePair>();
		for(ASNode node : nodeList) {
			for(ASNode node2 : node.neighbors) {
				adjList.add(new NodePair(node, node2));
			}
		}
		for(NodePair pair : adjList) {
			g.drawLine(pair.n1.x, pair.n1.y, pair.n2.x, pair.n2.y);
			int cx = (pair.n1.x + pair.n2.x) >> 1;
			int cy = (pair.n1.y + pair.n2.y) >> 1;
			g.setColor(Color.BLUE);
			g.drawOval((int)(cx - LineSize), (int)(cy - LineSize), (int)(LineSize * 2) + 1, (int)(LineSize * 2) + 1);
			g.setColor(Color.BLACK);
		}
		
		// Draw the path hovered in the table
		if(currHover != -1) {
			g.setColor(new Color(0x00, 0x7F, 0x00));
			Iterator<ASNode> itr = currSelected.paths.get(new Integer(currHover)).iterator();
			ASNode prev = currSelected;
			while(itr.hasNext()) {
				ASNode next = itr.next();
				double dist = Point.distance(next.x, next.y, prev.x, prev.y);
				double scale = PATH_SIZE / dist;
				int dx = -(int)(scale * (next.y - prev.y));
				int dy = (int)(scale * (next.x - prev.x));
				g.drawLine(prev.x + dx, prev.y + dy, next.x + dx, next.y + dy);
				g.drawLine(prev.x - dx, prev.y - dy, next.x - dx, next.y - dy);
				prev = next;
			}
			g.drawOval((int)(prev.x - NodeSize - PATH_SIZE), (int)(prev.y - NodeSize - PATH_SIZE), (int)((NodeSize + PATH_SIZE) * 2) + 1, (int)((NodeSize + PATH_SIZE) * 2) + 1);
		}
		
		// Draw the nodes
		for(ASNode node : nodeList) {
			g.setColor(Color.WHITE);
			g.fillOval((int)(node.x - NodeSize), (int)(node.y - NodeSize), (int)(NodeSize * 2) + 1, (int)(NodeSize * 2) + 1);
			g.setColor(node == currSelected ? Color.RED : Color.BLUE);
			g.drawOval((int)(node.x - NodeSize), (int)(node.y - NodeSize), (int)(NodeSize * 2) + 1, (int)(NodeSize * 2) + 1);
			g.setColor(Color.BLACK);
			g.drawString("" + node.ASNum, node.x - 3, node.y + 5);
		}
	}
	
	// Click on the simulation field
	public void mousePressed(MouseEvent e) {

		if(mode == BGPMode.EDIT) {
		
			// Determine if an existing element was clicked, and build an adjacency list
			Set<NodePair> adjList = new HashSet<NodePair>();
			ASNode clickedNode = null;
			NodePair clickedLine = null;
			for(ASNode node : nodeList) {
				if(Point.distance(node.x, node.y, e.getX(), e.getY()) < NodeSize) {
					clickedNode = node;
				}
				for(ASNode node2 : node.neighbors) {
					NodePair newPair = new NodePair(node, node2);
					adjList.add(newPair);
					if(clickedLine != null && Point.distance((node.x + node2.x) / 2, (node.y + node2.y) / 2, e.getX(), e.getY()) < LineSize) {
						clickedLine = newPair;
					}
				}
			}
			
			// Determine left or right click
			if(e.getButton() == MouseEvent.BUTTON1) {
				// Left click
				
				// If nothing was clicked, then create a new node
				if(clickedNode == null && clickedLine == null) {
					
					// If a node was already selected, deselect it
					if(currSelected == null) {
						nodeList.add(new ASNode(++ascounter, e.getX(), e.getY()));
					} else {
						currSelected = null;
					}
				} else if(clickedNode != null) {
					
					// Clicked a node
					selOffX = clickedNode.x - e.getX();
					selOffY = clickedNode.y - e.getY();
					mdx = e.getX();
					mdy = e.getY();
					if(clickedNode == currSelected) {
						currSelected = null;
					} else if(currSelected == null) {
						currSelected = clickedNode;
					} else {
	
						// Add a new edge, if they aren't already connected
						if(!clickedNode.neighbors.contains(currSelected)) {
							currSelected.connect(clickedNode);
						}
						currSelected = null;
					}
				} else {
					// Clicked an edge
					// TODO nothing yet
				}
				
			} else if(e.getButton() == MouseEvent.BUTTON3) {
				if(clickedNode != null) {
					
					// Remove the clicked node
					
				} else if(clickedLine != null) {
					
					// Remove the clicked line
					
				}
			}
			
			// Update UI
			mouseMoved(e);
		} else if(mode == BGPMode.SIMULATE) {
			
		}
		repaint();
	}
	
	public void mouseDragged(MouseEvent e) {
		if(currSelected != null) {
			currSelected.x = e.getX() + selOffX;
			currSelected.y = e.getY() + selOffY;
		}
		repaint();
	}
	
	public void mouseReleased(MouseEvent e) {
		if(Point.distance(e.getX(), e.getY(), mdx, mdy) > 5) {
			currSelected = null;
		}
		repaint();
	}
	
	private static class NodePair implements Comparable<NodePair> {
		public ASNode n1, n2;
		public NodePair(ASNode n1, ASNode n2) {
			this.n1 = n1;
			this.n2 = n2;
		}

		public int compareTo(NodePair pair) {
			return pair.n1 == n1 && pair.n2 == n2 || pair.n1 == n2 && pair.n2 == n1 ? 0 : -1;
		}
	}

	public void mouseMoved(MouseEvent e) {
		// When the mouse moves, display info for the node under it
		ASNode under = null;
		for(ASNode node : nodeList) {
			if(Point.distance(node.x, node.y, e.getX(), e.getY()) < NodeSize) {
				under = node;
				break;
			}
		}
		if(under != null && under.ASNum != currHover && currSelected == null) {
			btp.populateTable(under);
			btp.repaint();
		} else if(currSelected == null) {
			btp.populateTable(null);
			btp.repaint();
		}
	}
	
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}
