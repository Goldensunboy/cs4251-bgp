/* Andrew Wilder *
 * Ilyssa Widen  */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ASNode {
	public int ASNum, x, y;
	public Map<Integer, ArrayList<ASNode>> paths;
	public List<ASNode> neighbors;
	public Map<PrefixPair, NextPair> IPTable;

	public ASNode(int ASNum, Map<Integer, ArrayList<ASNode>> paths,
			List<ASNode> neighbors) {
		this.ASNum = ASNum;
		this.paths = paths;
		this.neighbors = neighbors;
		int IPV4 = 143 << 24 | 128 << 16 | ASNum << 8;
		int slash_x = 24;
		IPTable = new HashMap<PrefixPair, NextPair>();
		IPTable.put(new PrefixPair(IPV4, slash_x), new NextPair(this, 0));
	}

	
	public ASNode(int ASNum, Map<Integer, ArrayList<ASNode>> paths,
			List<ASNode> neighbors,PrefixPair pair) {
		this.ASNum = ASNum;
		this.paths = paths;
		this.neighbors = neighbors;
		IPTable = new HashMap<PrefixPair, NextPair>();
		IPTable.put(pair, new NextPair(this, 0));
	}

	public ASNode(int ASNum) {
		this(ASNum, new HashMap<Integer, ArrayList<ASNode>>(),
				new ArrayList<ASNode>());
	}


	public ASNode(int ASNum,PrefixPair pair) {
		this(ASNum, new HashMap<Integer, ArrayList<ASNode>>(),
				new ArrayList<ASNode>(),pair);
	}

	
	public static PrefixPair longestPrefix(Set<PrefixPair> set, int IPV4) {
		int length = 0;
		PrefixPair temp = null;
		String str = Integer.toBinaryString(IPV4);
		for (PrefixPair p : set) {
			String str_temp = Integer.toBinaryString(p.IPV4).substring(0,
					p.slash_x);
			int temp_length = 0;
			for (int i = 0; i < str_temp.length(); i++) {
				if (str_temp.charAt(i) == str.charAt(i)) {
					temp_length++;
				}
			}
			if (temp_length > length) {
				length = temp_length;
				temp = p;
			}
		}

		return temp;
	}

	public List<ASNode> path(Map<PrefixPair, NextPair> IPTable, int IPV4) {
		List<ASNode> list = new LinkedList<ASNode>();
		Map<PrefixPair, NextPair> temp = IPTable;
		while (true) {
			PrefixPair p = longestPrefix(temp.keySet(), IPV4);
			NextPair np=temp.get(p);
			list.add(np.node);
			if(np.length==0){
				break;
			}
			temp=np.node.IPTable;
		}
		return list;
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
		
		// Exchange IP tables
		for (PrefixPair p : IPTable.keySet()) {
			announceIP(p, IPTable.get(p));
		}
		for (PrefixPair p : node.IPTable.keySet()) {
			node.announceIP(p, node.IPTable.get(p));
		}
		
		/* Announce each other's paths */
		node.announce(this);
		announce(node);
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
			ASNode temp = list.get(list.size() - 1);
			for (ASNode tempNode : neighbors) {
				if (tempNode.getASNum() != temp.getASNum()) {
					if (tempNode.getPaths().containsKey(temp.getASNum())) {
						if (tempNode.getPaths().get(temp.getASNum()).size() > path
								.size()) {
							tempNode.getPaths().put(temp.getASNum(), list);
							tempNode.announce(node, list);
						}

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

	// Note: The path is announced as-is, and incremented when adding to the
	// neighbor's table
	public void announceIP(PrefixPair p, NextPair n) {
		for (ASNode a : neighbors) {
			if (a.IPTable.keySet().contains(p)) {
				NextPair newPair = new NextPair(n.node, n.length + 1);
				if (newPair.length < a.IPTable.get(p).length) {
					a.IPTable.put(p, newPair);
					for (ASNode a2 : a.neighbors) {
						a2.announceIP(p, newPair);
					}
				}
			} else {
				NextPair newPair = new NextPair(n.node, n.length + 1);
				a.IPTable.put(p, newPair);
				for (ASNode a2 : a.neighbors) {
					a2.announceIP(p, newPair);
				}
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
		if (o instanceof ASNode) {
			return ASNum == ((ASNode) o).ASNum;
		} else {
			return false;
		}
	}

	public static void main(String[] args) {
		System.out.println("START");
		ASNode node = new ASNode(0);
		for (PrefixPair i : node.IPTable.keySet()) {
			System.out.println(Integer.toBinaryString(i.IPV4)
					.substring(0, i.slash_x).length());

		}
	}
}
