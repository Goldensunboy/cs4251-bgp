import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class ASNodeTests {

	@Test
	public void test1() {
		ASNode node1 = new ASNode(1);
		ASNode node2 = new ASNode(2);
		node1.connect(node2);
		Map<Integer, List<ASNode>> map1 = new HashMap<Integer, List<ASNode>>();
		Map<Integer, List<ASNode>> map2 = new HashMap<Integer, List<ASNode>>();
		List<ASNode> list1 = new ArrayList<ASNode>();
		List<ASNode> list2 = new ArrayList<ASNode>();
		list2.add(node1);
		list1.add(node2);
		map1.put(2, list1);
		map2.put(1, list2);
		Assert.assertTrue(mapCompare(node1.getPaths(), map1));
		Assert.assertTrue(mapCompare(node2.getPaths(), map2));
	}

	@Test
	public void test2() {
		ASNode node1 = new ASNode(1);
		ASNode node2 = new ASNode(2);
		node2.connect(node1);
		Map<Integer, List<ASNode>> map1 = new HashMap<Integer, List<ASNode>>();
		Map<Integer, List<ASNode>> map2 = new HashMap<Integer, List<ASNode>>();
		List<ASNode> list1 = new ArrayList<ASNode>();
		List<ASNode> list2 = new ArrayList<ASNode>();
		list2.add(node1);
		list1.add(node2);
		map1.put(2, list1);
		map2.put(1, list2);
		Assert.assertTrue(mapCompare(node1.getPaths(), map1));
		Assert.assertTrue(mapCompare(node2.getPaths(), map2));
	}

	@Test
	public void test3() {
		ASNode node1 = new ASNode(1);
		ASNode node2 = new ASNode(2);
		ASNode node3 = new ASNode(3);

		node1.connect(node2);
		node3.connect(node1);
		Map<Integer, List<ASNode>> map1 = new HashMap<Integer, List<ASNode>>();
		Map<Integer, List<ASNode>> map2 = new HashMap<Integer, List<ASNode>>();
		Map<Integer, List<ASNode>> map3 = new HashMap<Integer, List<ASNode>>();
		List<ASNode> list1 = new ArrayList<ASNode>();
		List<ASNode> list2 = new ArrayList<ASNode>();
		List<ASNode> list3 = new ArrayList<ASNode>();
		List<ASNode> list4 = new ArrayList<ASNode>();
		List<ASNode> list5 = new ArrayList<ASNode>();
		list5.add(node1);
		list5.add(node2);
		list2.add(node1);
		list1.add(node2);
		list4.add(node1);

		list3.add(node3);
		map1.put(3, list3);
		map1.put(2, list1);
		map2.put(1, list2);
		map3.put(1, list4);
		map3.put(2, list5);
		node3.pathStrings();

		Assert.assertTrue(mapCompare(node1.getPaths(), map1));
		Assert.assertTrue(mapCompare(node2.getPaths(), map2));
		Assert.assertTrue(mapCompare(node3.getPaths(), map3));

	}

	@Test
	public void test4() {
		ASNode node1 = new ASNode(1);
		ASNode node2 = new ASNode(2);
		ASNode node3 = new ASNode(3);

		node1.connect(node2);
		node1.connect(node3);
		Map<Integer, List<ASNode>> map1 = new HashMap<Integer, List<ASNode>>();
		Map<Integer, List<ASNode>> map2 = new HashMap<Integer, List<ASNode>>();
		Map<Integer, List<ASNode>> map3 = new HashMap<Integer, List<ASNode>>();
		List<ASNode> list1 = new ArrayList<ASNode>();
		List<ASNode> list2 = new ArrayList<ASNode>();
		List<ASNode> list3 = new ArrayList<ASNode>();
		List<ASNode> list4 = new ArrayList<ASNode>();
		List<ASNode> list5 = new ArrayList<ASNode>();
		list5.add(node1);
		list5.add(node2);
		list2.add(node1);
		list1.add(node2);
		list4.add(node1);

		list3.add(node3);
		map1.put(3, list3);
		map1.put(2, list1);
		map2.put(1, list2);
		map3.put(1, list4);
		map3.put(2, list5);
		node3.pathStrings();

		Assert.assertTrue(mapCompare(node1.getPaths(), map1));
		Assert.assertTrue(mapCompare(node2.getPaths(), map2));
		Assert.assertTrue(mapCompare(node3.getPaths(), map3));
	}
	
	@Test
	public void test5(){
		ASNode node1 = new ASNode(1);
		ASNode node2 = new ASNode(2);
		ASNode node3 = new ASNode(3);

		node2.connect(node1);
		node1.connect(node3);
		Map<Integer, List<ASNode>> map1 = new HashMap<Integer, List<ASNode>>();
		Map<Integer, List<ASNode>> map2 = new HashMap<Integer, List<ASNode>>();
		Map<Integer, List<ASNode>> map3 = new HashMap<Integer, List<ASNode>>();
		List<ASNode> list1 = new ArrayList<ASNode>();
		List<ASNode> list2 = new ArrayList<ASNode>();
		List<ASNode> list3 = new ArrayList<ASNode>();
		List<ASNode> list4 = new ArrayList<ASNode>();
		List<ASNode> list5 = new ArrayList<ASNode>();
		list5.add(node1);
		list5.add(node2);
		list2.add(node1);
		list1.add(node2);
		list4.add(node1);

		list3.add(node3);
		map1.put(3, list3);
		map1.put(2, list1);
		map2.put(1, list2);
		map3.put(1, list4);
		map3.put(2, list5);
		node3.pathStrings();

		Assert.assertTrue(mapCompare(node1.getPaths(), map1));
		Assert.assertTrue(mapCompare(node2.getPaths(), map2));
		Assert.assertTrue(mapCompare(node3.getPaths(), map3));
		
		
	}

	public boolean listCompare(List<ASNode> list1, List<ASNode> list2) {
		if (list1.size() == list2.size()) {
			for (int i = 0; i < list1.size(); i++) {
				if (list1.get(i).getASNum() != list2.get(i).getASNum()) {
					return false;
				}
			}
			return true;
		}

		return false;
	}

	public boolean mapCompare(Map<Integer, ArrayList<ASNode>> map,
			Map<Integer, List<ASNode>> map2) {
		for (Integer key : map.keySet()) {
			if (!map2.containsKey(key)) {
				return false;
			}
			if (!listCompare(map.get(key), map2.get(key))) {
				return false;
			}

		}

		return true;
	}

}
