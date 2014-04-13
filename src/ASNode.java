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
	public int IPV4;
	public int slash_x;
	public Map<Integer, Pair> IPTable;

	public ASNode(int ASNum, Map<Integer, ArrayList<ASNode>> paths,
			List<ASNode> neighbors) {
		this.ASNum = ASNum;
		this.paths = paths;
		this.neighbors = neighbors;
		IPV4 = 143 << 24 | 128 << 16 | ASNum << 8;
		slash_x = 16;
		IPTable = new HashMap<Integer, Pair>();
		IPTable.put(IPV4, new Pair(0, this));
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

		Map<Integer, Pair> map3 = addIPToTable(this, IPTable);

		Map<Integer, Pair> map4 = addIPToTable(node, node.IPTable);

		node.setIPCombine(map3, this);
		setIPCombine(map4, node);

		/* put new path into this node */
		paths.put(node.getASNum(), newPath1);
		map1.put(this.ASNum, newPath2);
		/* put new path into other node */
		//announceIP(node);
		//node.announceIP(this);
		node.setPathsCombine(map1);
		/* exchange maps and see if any path if shorter, if shorter than adjust */
		setPathsCombine(map2);
		/* Add each other as neighbors */
		neighbors.add(node);
		node.getNeighbors().add(this);
		/* Announce each other's paths */
		node.announce(this);
		announce(node);
		// node.announceIP(this);
		// announceIP(node);
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

	protected static Map<Integer, Pair> addIPToTable(ASNode node,
			Map<Integer, Pair> ips) {

		Map<Integer, Pair> table = new HashMap<Integer, Pair>();
		for (int currentIP : ips.keySet()) {
			table.put(currentIP,
					new Pair(ips.get(currentIP).length + 1,
							ips.get(currentIP).node));
		}

		return table;
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

	public void setIPCombine(Map<Integer, Pair> IPTable, ASNode node) {
		for (int currIP : IPTable.keySet()) {
			if (this.IPTable.containsKey(currIP)) {
				if (IPTable.get(currIP).length < this.IPTable.get(currIP).length) {
					this.IPTable.put(currIP, new Pair(
							IPTable.get(currIP).length, node));
				}
			} else {
				if (currIP != this.IPV4) {
					this.IPTable.put(currIP, new Pair(
							IPTable.get(currIP).length, node));
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
			ASNode temp = list.get(list.size() - 1);
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
		for (int currAS : node.getPaths().keySet()) {
			ArrayList<ASNode> list2 = new ArrayList<ASNode>();
			list2.addAll(node.paths.get(currAS));
			/* Add current node to front of list */
			list2.add(0, node);
			announce(node, list2);
		}

	}

	public void announceIP(ASNode node, int ip, int length) {
		if (neighbors.size() == 0) {
			return;
		}
		for (ASNode temp : neighbors) {
			if (temp.IPTable.containsKey(ip)) {
				if (temp.IPTable.get(ip).length > length && length > 0) {
						temp.IPTable.put(ip, new Pair(length, this));
						temp.announceIP(node, ip, length++);
					
				}
			} else {
				if (ip != this.IPV4) {
					temp.IPTable.put(ip, new Pair(length, this));
					temp.announceIP(node, ip, length++);
				}
			}
		}
	}

	public void announceIP(ASNode node) {
		announceIP(node, node.IPV4, 2);
		for (int tempIP : node.IPTable.keySet()) {
			if (tempIP != node.IPV4) {
				announceIP(node, tempIP, node.IPTable.get(tempIP).length++);
			}
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
	
	public boolean equals(Object o) {
		if(o instanceof ASNode) {
			return ASNum == ((ASNode)o).ASNum;
		} else {
			return false;
		}
	}

	public static void main(String[] args) {
		System.out.println("START");
		ASNode node1 = new ASNode(1);
		System.out.println(Integer.toHexString((node1.IPV4)));
	}

}
