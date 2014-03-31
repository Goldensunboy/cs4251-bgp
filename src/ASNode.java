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

	public static String PrintAS(List<ASNode> l) {
		String s = "";
		boolean b = false;
		for(ASNode a : l) {
			if(b) {
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

	public void connect(ASNode node) {
		ArrayList<ASNode> newPath1 = new ArrayList<ASNode>();
		ArrayList<ASNode> newPath2 = new ArrayList<ASNode>();
		newPath1.add(node);
		newPath2.add(this);
		Map<Integer, ArrayList<ASNode>> map1 = addNodeToTable(node, paths);
		Map<Integer, ArrayList<ASNode>> map2 = addNodeToTable(node,
				node.getPaths());
		paths.put(node.getASNum(), newPath1);
		map1.put(this.ASNum, newPath2);
		node.setPathsCombine(map1);
		setPathsCombine(map2);
		neighbors.add(node);
		node.getNeighbors().add(this);

	}

	private Map<Integer, ArrayList<ASNode>> addNodeToTable(ASNode node,
			Map<Integer, ArrayList<ASNode>> paths) {
		Map<Integer, ArrayList<ASNode>> map = new HashMap<Integer, ArrayList<ASNode>>();
		for (int currentASnum : paths.keySet()) {
			ArrayList<ASNode> list = new ArrayList<ASNode>();
			list.addAll(paths.get(currentASnum));
			list.add(0, node);
			map.put(currentASnum, list);
		}

		return map;
	}

	public void setPathsCombine(Map<Integer, ArrayList<ASNode>> paths) {
		for (int currASnum : paths.keySet()) {
			if (this.paths.containsKey(currASnum)) {
				if (paths.get(currASnum).size() < this.paths.get(currASnum)
						.size()) {
					this.paths.put(currASnum, paths.get(currASnum));

				}
			} else {
				this.paths.put(currASnum, paths.get(currASnum));
			}
		}

	}

	public void announce(ASNode node, ArrayList<ASNode> path) {
		if (neighbors.size() == 0) {
			return;
		} else {
			ArrayList<ASNode> list = new ArrayList<ASNode>();
			list.addAll(path);
			list.add(0, this);
			for (ASNode tempNode : neighbors) {
				if (tempNode.getASNum() != node.getASNum()) {
					if (tempNode.getPaths().containsKey(node.getASNum())) {
						if (tempNode.getPaths().get(node.getASNum()).size() > path
								.size()) {
							tempNode.getPaths().put(node.getASNum(), list);
							tempNode.announce(node, list);
						}
					} else {
						tempNode.getPaths().put(node.getASNum(), list);
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

		System.out.println("Connect AS1 to AS2");

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

		System.out.println("");
		System.out.println("");
		System.out.println("");

		System.out.println("Connect AS4 to AS1");
		ASNode node4 = new ASNode(4);
		node4.connect(node1);
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
		System.out.println("PATHS of AS4");
		node4.pathStrings();
		System.out.println("Neighbors of AS4");
		node4.neighborsStrings();

		System.out.println("");
		System.out.println("");
		System.out.println("");

		System.out.println("ANNOUNCE AS4 to AS1");
		ArrayList<ASNode> list = new ArrayList<ASNode>();
		list.add(node4);
		node1.announce(node4, list);
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
		System.out.println("PATHS of AS4");
		node4.pathStrings();
		System.out.println("Neighbors of AS4");
		node4.neighborsStrings();

	}

}
