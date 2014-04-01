/* Andrew Wilder *
 * Ilyssa Widen  */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ASNode {
	public int ASNum, x, y;
	public Map<Integer, ArrayList<ASNode>> paths;
	public List<ASNode> neighbors;

	public static String PrintAS(List<ASNode> nodes) {
		String s = "";
		boolean b = false;
		for (ASNode a : nodes) {
			if (b) {
				s += " ";
			} else {
				b = true;
			}
			s += a.ASNum;
		}
		return s;
	}

	public ASNode(int ASNum, Map<Integer, ArrayList<ASNode>> paths,
			List<ASNode> neighbors) {
		this.ASNum = ASNum;
		this.paths = paths;
		this.neighbors = neighbors;
	}

	public ASNode(int ASNum) {
		this(ASNum, new HashMap<Integer, ArrayList<ASNode>>(),
				new ArrayList<ASNode>());
	}

	public ASNode(int ASNum, int new_x, int new_y) {
		this(ASNum, new HashMap<Integer, ArrayList<ASNode>>(),
				new ArrayList<ASNode>());
		x = new_x;
		y = new_y;
	}

	/*
	 * Method to connect this node to node
	 */

	public void connect(ASNode node) {

		/* Create new paths for this node and other node */
		ArrayList<ASNode> newPath1 = new ArrayList<ASNode>();
		ArrayList<ASNode> newPath2 = new ArrayList<ASNode>();
		newPath1.add(node);
		newPath2.add(this);
		/* Adds the Node to each others maps to get ready for exchange */
		Map<Integer, ArrayList<ASNode>> map1 = addNodeToTable(this, paths);
		Map<Integer, ArrayList<ASNode>> map2 = addNodeToTable(node,
				node.getPaths());
		/* put new path into this node */
		paths.put(node.getASNum(), newPath1);
		map1.put(this.ASNum, newPath2);
		/* put new path into other node */
		node.setPathsCombine(map1);
		/* exchange maps and see if any path if shorter, if shorter than adjust */
		setPathsCombine(map2);
		/* Add each other as neighbors */
		neighbors.add(node);
		node.getNeighbors().add(this);

	}

	/*
	 * EXAMPE if node is equal to is 1 and map is 2:2,3:3, returns map that is
	 * equals to 2:1->2 , 3:1->3
	 */

	protected static Map<Integer, ArrayList<ASNode>> addNodeToTable(
			ASNode node, Map<Integer, ArrayList<ASNode>> paths) {
		Map<Integer, ArrayList<ASNode>> map = new HashMap<Integer, ArrayList<ASNode>>();
		for (int currentASnum : paths.keySet()) {
			ArrayList<ASNode> list = new ArrayList<ASNode>();
			list.addAll(paths.get(currentASnum));
			list.add(0, node);
			map.put(currentASnum, list);
		}

		return map;
	}

	/*
	 * Method takes in another Map and combines it for shortest paths
	 */

	public void setPathsCombine(Map<Integer, ArrayList<ASNode>> paths) {
		for (int currASnum : paths.keySet()) {
			if (this.paths.containsKey(currASnum)) {
				if (paths.get(currASnum).size() < this.paths.get(currASnum)
						.size()) {

					if (!paths.get(currASnum).contains(currASnum)) {
						this.paths.put(currASnum, paths.get(currASnum));
					}
				}
			} else {
				if (currASnum != ASNum) {
					this.paths.put(currASnum, paths.get(currASnum));
				}
			}
		}

	}

	/*
	 * Announce a path
	 */

	public void announce(ASNode node, ArrayList<ASNode> path) {
		if (neighbors.size() == 0) {
			return;
		} else {
			ArrayList<ASNode> list = new ArrayList<ASNode>();
			list.addAll(path);
			/* Add current node to front of list */
			list.add(0, this);
			ASNode temp = list.get(list.size()-1);
			for (ASNode tempNode : neighbors) {
				if (tempNode.getASNum() != temp.getASNum()) {
					if (tempNode.getPaths().containsKey(temp.getASNum())) {
						if (tempNode.getPaths().get(temp.getASNum()).size() > path
								.size()) {
							tempNode.getPaths().put(temp.getASNum(), list);
							tempNode.announce(node, list);
						}
						// tempNode.announce(node, list);

					} else {
						tempNode.getPaths().put(temp.getASNum(), list);
						tempNode.announce(node, list);

					}
				}

			}
		}

	}

	public void announce(ASNode node) {
		ArrayList<ASNode> list = new ArrayList<ASNode>();
		list.add(node);
		announce(node, list);
		for(int currAS : node.getPaths().keySet()){
			ArrayList<ASNode> list2 = new ArrayList<ASNode>();
			list2.addAll(node.paths.get(currAS));
			/* Add current node to front of list */
			list2.add(0, node);
			announce(node,list2);
		}

	}

	public int getASNum() {
		return ASNum;
	}

	public void setASNum(int aSNum) {
		ASNum = aSNum;
	}

	public Map<Integer, ArrayList<ASNode>> getPaths() {
		return paths;
	}

	public List<ASNode> getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(List<ASNode> neighbors) {
		this.neighbors = neighbors;
	}

	public void setPaths(Map<Integer, ArrayList<ASNode>> paths) {
		this.paths = paths;
	}

	public void pathStrings(Map<Integer, ArrayList<ASNode>> paths) {
		for (int num : paths.keySet()) {
			System.out.print(num + ":");
			for (ASNode node : paths.get(num)) {
				System.out.print(node.getASNum() + " ");
			}
		}
		System.out.println("");
	}

	public void pathStrings() {
		pathStrings(paths);
	}

	public void neighborsStrings() {
		for (ASNode node : neighbors) {
			System.out.print(node.getASNum() + " ");
		}
		System.out.println("");

	}

	public String toString() {
		return "" + ASNum;
	}

	public static void main(String[] args) {
		System.out.println("START");
		ASNode node1 = new ASNode(1);
		System.out.println("Create AS1");
		ASNode node5 = new ASNode(5);
		System.out.println("Connect AS1 to AS5");
		node1.connect(node5);
		System.out.println("PATHS of AS1");
		node1.pathStrings();
		System.out.println("Neighbors of AS1");
		node1.neighborsStrings();
		System.out.println("PATHS of AS5");
		node5.pathStrings();
		System.out.println("Neighbors of AS5");
		node5.neighborsStrings();
		System.out.println("");
		System.out.println("");
		System.out.println("");

		System.out.println("Connect AS1 to AS2 and announce");

		ASNode node2 = new ASNode(2);
		node1.connect(node2);
		ArrayList<ASNode> list2 = new ArrayList<ASNode>();
		list2.add(node2);
		node1.announce(node2, list2);

		System.out.println("PATHS of AS1");
		node1.pathStrings();
		System.out.println("Neighbors of AS1");
		node1.neighborsStrings();
		System.out.println("PATHS of AS5");
		node5.pathStrings();
		System.out.println("Neighbors of AS5");
		node5.neighborsStrings();
		System.out.println("PATHS of AS2");
		node2.pathStrings();
		System.out.println("Neighbors of AS2");
		node2.neighborsStrings();
		/*
		 * System.out.println(""); System.out.println("");
		 * System.out.println("");
		 * 
		 * System.out.println("Connect AS4 to AS1"); ASNode node4 = new
		 * ASNode(4); node4.connect(node1); System.out.println("PATHS of AS1");
		 * node1.pathStrings(); System.out.println("Neighbors of AS1");
		 * node1.neighborsStrings(); System.out.println("PATHS of AS5");
		 * node5.pathStrings(); System.out.println("Neighbors of AS5");
		 * node5.neighborsStrings(); System.out.println("PATHS of AS2");
		 * node2.pathStrings(); System.out.println("Neighbors of AS2");
		 * node2.neighborsStrings(); System.out.println("PATHS of AS4");
		 * node4.pathStrings(); System.out.println("Neighbors of AS4");
		 * node4.neighborsStrings();
		 * 
		 * System.out.println(""); System.out.println("");
		 * System.out.println("");
		 * 
		 * System.out.println("ANNOUNCE AS4 to AS1"); ArrayList<ASNode> list =
		 * new ArrayList<ASNode>(); list.add(node4); node1.announce(node4,
		 * list); System.out.println("PATHS of AS1"); node1.pathStrings();
		 * System.out.println("Neighbors of AS1"); node1.neighborsStrings();
		 * System.out.println("PATHS of AS5"); node5.pathStrings();
		 * System.out.println("Neighbors of AS5"); node5.neighborsStrings();
		 * System.out.println("PATHS of AS2"); node2.pathStrings();
		 * System.out.println("Neighbors of AS2"); node2.neighborsStrings();
		 * System.out.println("PATHS of AS4"); node4.pathStrings();
		 * System.out.println("Neighbors of AS4"); node4.neighborsStrings();
		 * 
		 * }
		 * 
		 * // Used for backwards referencing nodes by integer public boolean
		 * equals(Object o) { if (o instanceof Integer) { return ASNum ==
		 * (Integer) o; } else if (o instanceof ASNode) { return ASNum ==
		 * ((ASNode) o).ASNum; } else { return false; } }
		 */
	}
}
