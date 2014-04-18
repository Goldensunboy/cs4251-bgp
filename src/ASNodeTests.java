import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class ASNodeTests {

	/*
	 * 1->2
	 */

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

		Map<PrefixPair, NextPair> map3 = new HashMap<PrefixPair, NextPair>();
		map3.put(new PrefixPair(143 << 24 | 128 << 16 | 1 << 8, 24), new NextPair(node1, 1));
		map3.put(new PrefixPair(143 << 24 | 128 << 16 | 2 << 8, 24), new NextPair(node2, 0));

		Map<PrefixPair, NextPair> map4 = new HashMap<PrefixPair, NextPair>();
		map4.put(new PrefixPair(143 << 24 | 128 << 16 | 2 << 8, 24), new NextPair(node2, 1));
		map4.put(new PrefixPair(143 << 24 | 128 << 16 | 1 << 8, 24), new NextPair(node1, 0));

		Assert.assertTrue(mapCompare(node1.getPaths(), map1));
		Assert.assertTrue(mapCompare(node2.getPaths(), map2));
		Assert.assertTrue(mapCompareIP(node2.IPTable, map3));
		Assert.assertTrue(mapCompareIP(node1.IPTable, map4));

	}

	/*
	 * 2->1
	 */

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
		
		
		Map<PrefixPair, NextPair> map3 = new HashMap<PrefixPair, NextPair>();
		map3.put(new PrefixPair(143 << 24 | 128 << 16 | 1 << 8, 24), new NextPair(node1, 1));
		map3.put(new PrefixPair(143 << 24 | 128 << 16 | 2 << 8, 24), new NextPair(node2, 0));

		Map<PrefixPair, NextPair> map4 = new HashMap<PrefixPair, NextPair>();
		map4.put(new PrefixPair(143 << 24 | 128 << 16 | 2 << 8, 24), new NextPair(node2, 1));
		map4.put(new PrefixPair(143 << 24 | 128 << 16 | 1 << 8, 24), new NextPair(node1, 0));
		
		
		Assert.assertTrue(mapCompare(node1.getPaths(), map1));
		Assert.assertTrue(mapCompare(node2.getPaths(), map2));
		Assert.assertTrue(mapCompareIP(node2.IPTable, map3));
		Assert.assertTrue(mapCompareIP(node1.IPTable, map4));

	}

	/*
	 * 1->2 3->1
	 */
	@Test
	public void test3() {
		ASNode node1 = new ASNode(1);
		ASNode node2 = new ASNode(2);
		ASNode node3 = new ASNode(3);

		node1.connect(node2);
		node3.connect(node1);
		//node1.announceIP(node3);
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

		List<ASNode> list32 = new ArrayList<ASNode>();
		list32.add(node1);
		list32.add(node3);

		list3.add(node3);
		map1.put(3, list3);
		map1.put(2, list1);
		map2.put(1, list2);
		map2.put(3, list32);
		map3.put(1, list4);
		map3.put(2, list5);
		
		Map<PrefixPair, NextPair> map5 = new HashMap<PrefixPair, NextPair>();
		map5.put(new PrefixPair(143 << 24 | 128 << 16 | 1 << 8, 24), new NextPair(node1, 1));
		map5.put(new PrefixPair(143 << 24 | 128 << 16 | 2 << 8, 24), new NextPair(node2, 0));
		map5.put(new PrefixPair(143 << 24 | 128 << 16 | 3 << 8, 24), new NextPair(node1, 2));

		Map<PrefixPair, NextPair> map6 = new HashMap<PrefixPair, NextPair>();
		map6.put(new PrefixPair(143 << 24 | 128 << 16 | 2 << 8, 24), new NextPair(node2, 1));
		map6.put(new PrefixPair(143 << 24 | 128 << 16 | 1 << 8, 24), new NextPair(node1, 0));
		map6.put(new PrefixPair(143 << 24 | 128 << 16 | 3 << 8, 24), new NextPair(node3, 1));

		Map<PrefixPair, NextPair> map7 = new HashMap<PrefixPair, NextPair>();
		map7.put(new PrefixPair(143 << 24 | 128 << 16 | 1 << 8, 24), new NextPair(node1, 1));
		map7.put(new PrefixPair(143 << 24 | 128 << 16 | 3 << 8, 24), new NextPair(node3, 0));
		map7.put(new PrefixPair(143 << 24 | 128 << 16 | 2 << 8, 24), new NextPair(node1, 2));
		
		Assert.assertTrue(mapCompareIP(node1.IPTable, map6));
		Assert.assertTrue(mapCompareIP(node3.IPTable, map7));
		Assert.assertTrue(mapCompareIP(node2.IPTable, map5));

		Assert.assertTrue(mapCompare(node1.getPaths(), map1));
		Assert.assertTrue(mapCompare(node2.getPaths(), map2));
		Assert.assertTrue(mapCompare(node3.getPaths(), map3));

	}

	/*
	 * 1->2 1->3
	 */
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
		List<ASNode> list32 = new ArrayList<ASNode>();
		list32.add(node1);
		list32.add(node3);
		list3.add(node3);
		map1.put(3, list3);
		map1.put(2, list1);
		map2.put(1, list2);
		map2.put(3, list32);
		map3.put(1, list4);
		map3.put(2, list5);
		
		
		Map<PrefixPair, NextPair> map5 = new HashMap<PrefixPair, NextPair>();
		map5.put(new PrefixPair(143 << 24 | 128 << 16 | 1 << 8, 24), new NextPair(node1, 1));
		map5.put(new PrefixPair(143 << 24 | 128 << 16 | 2 << 8, 24), new NextPair(node2, 0));
		map5.put(new PrefixPair(143 << 24 | 128 << 16 | 3 << 8, 24), new NextPair(node1, 2));

		Map<PrefixPair, NextPair> map6 = new HashMap<PrefixPair, NextPair>();
		map6.put(new PrefixPair(143 << 24 | 128 << 16 | 2 << 8, 24), new NextPair(node2, 1));
		map6.put(new PrefixPair(143 << 24 | 128 << 16 | 1 << 8, 24), new NextPair(node1, 0));
		map6.put(new PrefixPair(143 << 24 | 128 << 16 | 3 << 8, 24), new NextPair(node3, 1));

		Map<PrefixPair, NextPair> map7 = new HashMap<PrefixPair, NextPair>();
		map7.put(new PrefixPair(143 << 24 | 128 << 16 | 1 << 8, 24), new NextPair(node1, 1));
		map7.put(new PrefixPair(143 << 24 | 128 << 16 | 3 << 8, 24), new NextPair(node3, 0));
		map7.put(new PrefixPair(143 << 24 | 128 << 16 | 2 << 8, 24), new NextPair(node1, 2));
		
		Assert.assertTrue(mapCompareIP(node1.IPTable, map6));
		Assert.assertTrue(mapCompareIP(node3.IPTable, map7));
		Assert.assertTrue(mapCompareIP(node2.IPTable, map5));

		Assert.assertTrue(mapCompare(node1.getPaths(), map1));
		Assert.assertTrue(mapCompare(node2.getPaths(), map2));
		Assert.assertTrue(mapCompare(node3.getPaths(), map3));
	}

	/*
	 * 2->1 1->3
	 */
	@Test
	public void test5() {
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

		List<ASNode> list32 = new ArrayList<ASNode>();
		list32.add(node1);
		list32.add(node3);

		map1.put(3, list3);
		map1.put(2, list1);
		map2.put(1, list2);
		map2.put(3, list32);
		map3.put(1, list4);
		map3.put(2, list5);

		Map<PrefixPair, NextPair> map5 = new HashMap<PrefixPair, NextPair>();
		map5.put(new PrefixPair(143 << 24 | 128 << 16 | 1 << 8, 24), new NextPair(node1, 1));
		map5.put(new PrefixPair(143 << 24 | 128 << 16 | 2 << 8, 24), new NextPair(node2, 0));
		map5.put(new PrefixPair(143 << 24 | 128 << 16 | 3 << 8, 24), new NextPair(node1, 2));

		Map<PrefixPair, NextPair> map6 = new HashMap<PrefixPair, NextPair>();
		map6.put(new PrefixPair(143 << 24 | 128 << 16 | 2 << 8, 24), new NextPair(node2, 1));
		map6.put(new PrefixPair(143 << 24 | 128 << 16 | 1 << 8, 24), new NextPair(node1, 0));
		map6.put(new PrefixPair(143 << 24 | 128 << 16 | 3 << 8, 24), new NextPair(node3, 1));

		Map<PrefixPair, NextPair> map7 = new HashMap<PrefixPair, NextPair>();
		map7.put(new PrefixPair(143 << 24 | 128 << 16 | 1 << 8, 24), new NextPair(node1, 1));
		map7.put(new PrefixPair(143 << 24 | 128 << 16 | 3 << 8, 24), new NextPair(node3, 0));
		map7.put(new PrefixPair(143 << 24 | 128 << 16 | 2 << 8, 24), new NextPair(node1, 2));
	
		Assert.assertTrue(mapCompareIP(node1.IPTable, map6));
		Assert.assertTrue(mapCompareIP(node3.IPTable, map7));
		Assert.assertTrue(mapCompareIP(node2.IPTable, map5));

		Assert.assertTrue(mapCompare(node1.getPaths(), map1));
		Assert.assertTrue(mapCompare(node2.getPaths(), map2));
		Assert.assertTrue(mapCompare(node3.getPaths(), map3));

	}

	/*
	 * 1->2 1->3 2->3
	 */

	@Test
	public void Test6() {
		ASNode node1 = new ASNode(1);
		ASNode node2 = new ASNode(2);
		ASNode node3 = new ASNode(3);

		node1.connect(node2);
		node1.connect(node3);
		node2.connect(node3);
		Map<Integer, List<ASNode>> map1 = new HashMap<Integer, List<ASNode>>();
		Map<Integer, List<ASNode>> map2 = new HashMap<Integer, List<ASNode>>();
		Map<Integer, List<ASNode>> map3 = new HashMap<Integer, List<ASNode>>();
		List<ASNode> list1 = new ArrayList<ASNode>();
		List<ASNode> list2 = new ArrayList<ASNode>();
		List<ASNode> list3 = new ArrayList<ASNode>();
		List<ASNode> list4 = new ArrayList<ASNode>();
		List<ASNode> list5 = new ArrayList<ASNode>();
		List<ASNode> list6 = new ArrayList<ASNode>();

		list1.add(node2);
		list2.add(node3);
		list3.add(node1);
		list4.add(node3);
		list5.add(node1);
		list6.add(node2);

		map1.put(2, list1);
		map1.put(3, list2);
		map2.put(1, list3);
		map2.put(3, list4);

		map3.put(1, list5);
		map3.put(2, list6);
		
		Map<PrefixPair, NextPair> map4 = new HashMap<PrefixPair, NextPair>();
		map4.put(new PrefixPair(143 << 24 | 128 << 16 | 1 << 8, 24), new NextPair(node1, 0));
		map4.put(new PrefixPair(143 << 24 | 128 << 16 | 2 << 8, 24), new NextPair(node2, 1));
		map4.put(new PrefixPair(143 << 24 | 128 << 16 | 3 << 8, 24), new NextPair(node3, 1));
		
		Map<PrefixPair, NextPair> map5 = new HashMap<PrefixPair, NextPair>();
		map5.put(new PrefixPair(143 << 24 | 128 << 16 | 1 << 8, 24), new NextPair(node1, 1));
		map5.put(new PrefixPair(143 << 24 | 128 << 16 | 2 << 8, 24), new NextPair(node2, 0));
		map5.put(new PrefixPair(143 << 24 | 128 << 16 | 3 << 8, 24), new NextPair(node3, 1));
		
		Map<PrefixPair, NextPair> map6 = new HashMap<PrefixPair, NextPair>();
		map6.put(new PrefixPair(143 << 24 | 128 << 16 | 1 << 8, 24), new NextPair(node1, 1));
		map6.put(new PrefixPair(143 << 24 | 128 << 16 | 2 << 8, 24), new NextPair(node2, 1));
		map6.put(new PrefixPair(143 << 24 | 128 << 16 | 3 << 8, 24), new NextPair(node3, 0));
		
		Assert.assertTrue(mapCompareIP(node1.IPTable, map4));
		Assert.assertTrue(mapCompareIP(node2.IPTable, map5));
		Assert.assertTrue(mapCompareIP(node3.IPTable, map6));

		Assert.assertTrue(mapCompare(node1.getPaths(), map1));
		Assert.assertTrue(mapCompare(node2.getPaths(), map2));
		Assert.assertTrue(mapCompare(node3.getPaths(), map3));
	}

	/*
	 * 1->2 2->3 3->1
	 */
	@Test
	public void Test7() {
		ASNode node1 = new ASNode(1);
		ASNode node2 = new ASNode(2);
		ASNode node3 = new ASNode(3);

		node1.connect(node2);
		node2.connect(node3);
		node3.connect(node1);
		Map<Integer, List<ASNode>> map1 = new HashMap<Integer, List<ASNode>>();
		Map<Integer, List<ASNode>> map2 = new HashMap<Integer, List<ASNode>>();
		Map<Integer, List<ASNode>> map3 = new HashMap<Integer, List<ASNode>>();
		List<ASNode> list1 = new ArrayList<ASNode>();
		List<ASNode> list2 = new ArrayList<ASNode>();
		List<ASNode> list3 = new ArrayList<ASNode>();
		List<ASNode> list4 = new ArrayList<ASNode>();
		List<ASNode> list5 = new ArrayList<ASNode>();
		List<ASNode> list6 = new ArrayList<ASNode>();

		list1.add(node2);
		list2.add(node3);
		list3.add(node1);
		list4.add(node3);
		list5.add(node1);
		list6.add(node2);

		map1.put(2, list1);
		map1.put(3, list2);
		map2.put(1, list3);
		map2.put(3, list4);

		map3.put(1, list5);
		map3.put(2, list6);
		
		Map<PrefixPair, NextPair> map4 = new HashMap<PrefixPair, NextPair>();
		map4.put(new PrefixPair(143 << 24 | 128 << 16 | 1 << 8, 24), new NextPair(node1, 0));
		map4.put(new PrefixPair(143 << 24 | 128 << 16 | 2 << 8, 24), new NextPair(node2, 1));
		map4.put(new PrefixPair(143 << 24 | 128 << 16 | 3 << 8, 24), new NextPair(node3, 1));
		
		Map<PrefixPair, NextPair> map5 = new HashMap<PrefixPair, NextPair>();
		map5.put(new PrefixPair(143 << 24 | 128 << 16 | 1 << 8, 24), new NextPair(node1, 1));
		map5.put(new PrefixPair(143 << 24 | 128 << 16 | 2 << 8, 24), new NextPair(node2, 0));
		map5.put(new PrefixPair(143 << 24 | 128 << 16 | 3 << 8, 24), new NextPair(node3, 1));
		
		Map<PrefixPair, NextPair> map6 = new HashMap<PrefixPair, NextPair>();
		map6.put(new PrefixPair(143 << 24 | 128 << 16 | 1 << 8, 24), new NextPair(node1, 1));
		map6.put(new PrefixPair(143 << 24 | 128 << 16 | 2 << 8, 24), new NextPair(node2, 1));
		map6.put(new PrefixPair(143 << 24 | 128 << 16 | 3 << 8, 24), new NextPair(node3, 0));
		
		Assert.assertTrue(mapCompareIP(node1.IPTable, map4));
		Assert.assertTrue(mapCompareIP(node2.IPTable, map5));
		Assert.assertTrue(mapCompareIP(node3.IPTable, map6));

		Assert.assertTrue(mapCompare(node1.getPaths(), map1));
		Assert.assertTrue(mapCompare(node2.getPaths(), map2));
		Assert.assertTrue(mapCompare(node3.getPaths(), map3));
	}

	/*
	 * 1->2->3->4 1->2 3->4 2->3
	 */

	@Test
	public void Test8() {
		ASNode node1 = new ASNode(1);
		ASNode node2 = new ASNode(2);
		ASNode node3 = new ASNode(3);
		ASNode node4 = new ASNode(4);

		node1.connect(node2);
		node3.connect(node4);
		node2.connect(node3);

		Map<Integer, List<ASNode>> map1 = new HashMap<Integer, List<ASNode>>();
		Map<Integer, List<ASNode>> map2 = new HashMap<Integer, List<ASNode>>();
		Map<Integer, List<ASNode>> map3 = new HashMap<Integer, List<ASNode>>();
		Map<Integer, List<ASNode>> map4 = new HashMap<Integer, List<ASNode>>();

		ArrayList<ASNode> list1_2 = new ArrayList<ASNode>();
		ArrayList<ASNode> list1_3 = new ArrayList<ASNode>();
		ArrayList<ASNode> list1_4 = new ArrayList<ASNode>();

		list1_2.add(node2);

		list1_3.add(node2);
		list1_3.add(node3);

		list1_4.add(node2);
		list1_4.add(node3);
		list1_4.add(node4);

		map1.put(2, list1_2);
		map1.put(3, list1_3);
		map1.put(4, list1_4);

		ArrayList<ASNode> list2_1 = new ArrayList<ASNode>();
		ArrayList<ASNode> list2_3 = new ArrayList<ASNode>();
		ArrayList<ASNode> list2_4 = new ArrayList<ASNode>();

		list2_1.add(node1);
		list2_3.add(node3);
		list2_4.add(node3);
		list2_4.add(node4);
		map2.put(1, list2_1);
		map2.put(3, list2_3);
		map2.put(4, list2_4);

		ArrayList<ASNode> list3_1 = new ArrayList<ASNode>();
		ArrayList<ASNode> list3_2 = new ArrayList<ASNode>();
		ArrayList<ASNode> list3_4 = new ArrayList<ASNode>();

		list3_1.add(node2);
		list3_1.add(node1);

		list3_2.add(node2);

		list3_4.add(node4);

		map3.put(1, list3_1);
		map3.put(2, list3_2);
		map3.put(4, list3_4);

		ArrayList<ASNode> list4_1 = new ArrayList<ASNode>();
		ArrayList<ASNode> list4_2 = new ArrayList<ASNode>();
		ArrayList<ASNode> list4_3 = new ArrayList<ASNode>();

		list4_1.add(node3);
		list4_1.add(node2);
		list4_1.add(node1);

		list4_2.add(node3);
		list4_2.add(node2);

		list4_3.add(node3);

		map4.put(1, list4_1);
		map4.put(2, list4_2);
		map4.put(3, list4_3);

		Map<PrefixPair, NextPair> map5 = new HashMap<PrefixPair, NextPair>();
		map5.put(new PrefixPair(143 << 24 | 128 << 16 | 1 << 8, 24), new NextPair(node1, 0));
		map5.put(new PrefixPair(143 << 24 | 128 << 16 | 2 << 8, 24), new NextPair(node2, 1));
		map5.put(new PrefixPair(143 << 24 | 128 << 16 | 3 << 8, 24), new NextPair(node2, 2));
		map5.put(new PrefixPair(143 << 24 | 128 << 16 | 4 << 8, 24), new NextPair(node2, 3));
		
		Map<PrefixPair, NextPair> map6 = new HashMap<PrefixPair, NextPair>();
		map6.put(new PrefixPair(143 << 24 | 128 << 16 | 1 << 8, 24), new NextPair(node1, 1));
		map6.put(new PrefixPair(143 << 24 | 128 << 16 | 2 << 8, 24), new NextPair(node2, 0));
		map6.put(new PrefixPair(143 << 24 | 128 << 16 | 3 << 8, 24), new NextPair(node3, 1));
		map6.put(new PrefixPair(143 << 24 | 128 << 16 | 4 << 8, 24), new NextPair(node3, 2));
		
		Map<PrefixPair, NextPair> map7 = new HashMap<PrefixPair, NextPair>();
		map7.put(new PrefixPair(143 << 24 | 128 << 16 | 1 << 8, 24), new NextPair(node2, 2));
		map7.put(new PrefixPair(143 << 24 | 128 << 16 | 2 << 8, 24), new NextPair(node2, 1));
		map7.put(new PrefixPair(143 << 24 | 128 << 16 | 3 << 8, 24), new NextPair(node3, 0));
		map7.put(new PrefixPair(143 << 24 | 128 << 16 | 4 << 8, 24), new NextPair(node4, 1));
		
		Map<PrefixPair, NextPair> map8 = new HashMap<PrefixPair, NextPair>();
		map8.put(new PrefixPair(143 << 24 | 128 << 16 | 1 << 8, 24), new NextPair(node3, 3));
		map8.put(new PrefixPair(143 << 24 | 128 << 16 | 2 << 8, 24), new NextPair(node3, 2));
		map8.put(new PrefixPair(143 << 24 | 128 << 16 | 3 << 8, 24), new NextPair(node3, 1));
		map8.put(new PrefixPair(143 << 24 | 128 << 16 | 4 << 8, 24), new NextPair(node4, 0));

		Assert.assertTrue(mapCompareIP(node1.IPTable, map5));
		
		Assert.assertTrue(mapCompare(node1.getPaths(), map1));

		Assert.assertTrue(mapCompare(node2.getPaths(), map2));
		Assert.assertTrue(mapCompare(node3.getPaths(), map3));
		Assert.assertTrue(mapCompare(node4.getPaths(), map4));

	}

	@Test
	public void Test9(){
		Map<PrefixPair, NextPair> IPTable = new HashMap<PrefixPair,NextPair>();
		int IPV41 = 172 << 24 | 21 << 16 | 10 << 8 | 237;
		int IPV42 = 172 << 24 | 21 << 16 | 10 << 8 | 238;
		int IPV43 = 172 << 24 | 21 << 16 | 10 << 8 | 246;

		IPTable.put(new PrefixPair(IPV42,32), new NextPair());
		IPTable.put(new PrefixPair(IPV43,32), new NextPair());
		
		Assert.assertEquals(IPV42, ASNode.longestPrefix(IPTable.keySet(), IPV41).IPV4);
	/*	System.out.println(Integer.toBinaryString((ASNode.path(IPTable, IPV41).IPV4)));*/

		
	}
	
	@Test
	public void Test10(){
		Map<PrefixPair, NextPair> IPTable = new HashMap<PrefixPair,NextPair>();
		int IPV41 = 192 << 24 | 168 << 16 | 50 << 8 | 37;
		int IPV42 = 192 << 24 | 168 << 16 | 52 << 8;
		int IPV43 = 192 << 24 | 168 << 16 | 50 << 8;
		int IPV44 = 192 << 24 | 168 << 16 | 48 << 8;

		//System.out.println("1: "+Integer.toBinaryString(IPV42));
	//	System.out.println(Integer.toBinaryString(IPV43));
		//System.out.println("3: "+Integer.toBinaryString(IPV44));

		IPTable.put(new PrefixPair(IPV42,22), new NextPair());
		IPTable.put(new PrefixPair(IPV43,24), new NextPair());
		IPTable.put(new PrefixPair(IPV43,22), new NextPair());

		Assert.assertEquals(IPV43, ASNode.longestPrefix(IPTable.keySet(), IPV41).IPV4);

		//System.out.println(Integer.toBinaryString((ASNode.path(IPTable, IPV41).IPV4)));

		
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

	public boolean mapCompareIP(Map<PrefixPair, NextPair> map, Map<PrefixPair, NextPair> map2) {
		for (PrefixPair key : map.keySet()) {
			if (!map2.containsKey(key)) {
				return false;
			}
			if (!map2.get(key).pairEquals(map.get(key))) {
				return false;
			}
		}
		return true;
	}
}
