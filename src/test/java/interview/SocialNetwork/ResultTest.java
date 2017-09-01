package interview.SocialNetwork;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

public class ResultTest {

	@Test
	public void testResultSuccess() {
		int user = 4;
		Node node1 = new Node(1, 1);
		Node node2 = new Node(2, 10);
		Node node3 = new Node(3, 100);
		Node node4 = new Node(4, 1000);
		HashMap<Integer,Node> networkMap = new HashMap<Integer,Node>();
		networkMap.put(1, node1);
		networkMap.put(2, node2);
		networkMap.put(3, node3);
		networkMap.put(4, node4);
		HashMap<Integer,Integer> pathsFrom = new HashMap<Integer,Integer>();
		pathsFrom.put(4, 3);
		pathsFrom.put(3, 2);
		pathsFrom.put(2, 1);
		double cost = 1.1111;
		Result result = new Result(user, networkMap, pathsFrom, cost);
		assertEquals(Status.SUCCESS, result.getStatus());
		assertEquals(cost, result.getCost(), 1e-6);
		for (int i = 0; i < 4; i++) {
			assertEquals(i+1, result.getPath().get(i).getUser());
		}
	}
	
	@Test
	public void testResultZero() {
		int user = 1;
		Node node = new Node(1, 1);
		HashMap<Integer,Node> networkMap = new HashMap<Integer,Node>();
		networkMap.put(1,  node);
		HashMap<Integer,Integer> pathsFrom = new HashMap<Integer,Integer>();
		pathsFrom.put(1, 1);
		double cost = 0;
		Result result = new Result(user, networkMap, pathsFrom, cost);
		assertEquals(Status.SUCCESS, result.getStatus());
		assertEquals(1, result.getPath().size());
		assertEquals(0, result.getCost(), 1e-6);
	}

	@Test
	public void testResultFailNoPath() {
		Result result = new Result(Status.FAIL_NO_PATH);
		assertEquals(Status.FAIL_NO_PATH, result.getStatus());
	}
	
	@Test
	public void testResultFailNoUsers() {
		Result result = new Result(Status.FAIL_NO_USERS);
		assertEquals(Status.FAIL_NO_USERS, result.getStatus());
		result = new Result(Status.FAIL_NO_USER1);
		assertEquals(Status.FAIL_NO_USER1, result.getStatus());
		result = new Result(Status.FAIL_NO_USER2);
		assertEquals(Status.FAIL_NO_USER2, result.getStatus());
	}
	
	@Test
	public void testResultFailUnexpected() {
		Result result = new Result(1, new HashMap<Integer,Node>(), new HashMap<Integer,Integer>(), 0);
		assertEquals(Status.FAIL_UNEXPECTED, result.getStatus());
		result = new Result(Status.SUCCESS);
		assertEquals(Status.FAIL_UNEXPECTED, result.getStatus());
	}

}
