package interview.SocialNetwork;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.BeforeClass;
import org.junit.Test;

public class NetworkTest {

	public static final String DIR = "testdata";
	public static final String DATA = "test.json";
	public static final int N_TRIALS = 1000;
	
	public static Network network;
	
	@BeforeClass
	public static void beforeClass() {
		network = Network.create(DIR + "\\" + DATA);
	}
	
	/**
	 * Gets possible paths for the test network
	 * @return List of paths
	 */
	private ArrayList<int[]> getPaths() {
		ArrayList<int[]> paths = new ArrayList<int[]>();
		paths.add(new int[] {1, 2, 4, 6, 9});
		paths.add(new int[] {1, 3, 6, 9});
		paths.add(new int[] {1, 3, 7, 9});
		paths.add(new int[] {1, 3, 5, 8, 9});
		return paths;
	}
	
	/**
	 * Gets the minimum cost of the test network from user 1 to user 9
	 * @param paths Possible paths to test
	 * @return Minimum cost
	 */
	private double getMinCost(ArrayList<int[]> paths) {
		double minCost = Double.MAX_VALUE;
		for (int i = 0; i < paths.size(); i++) {
			int[] path = paths.get(i);
			double cost = 0;
			for (int j = 1; j < path.length; j++) {
				cost += network.getMap().get(path[j]).getWeight();
			}
			if (minCost > cost) {
				minCost = cost;
			}
		}
		return minCost;
	}
	
	/**
	 * Gets to minimum cost path of the test network from user 1 to user 9
	 * @param paths Possible paths to test
	 * @return Minimum cost path
	 */
	private int[] getMinCostPath(ArrayList<int[]> paths) {
		double minCost = Double.MAX_VALUE;
		int[] minCostPath = null;
		for (int i = 0; i < paths.size(); i++) {
			int[] path = paths.get(i);
			double cost = 0;
			for (int user: path) {
				cost += network.getMap().get(user).getWeight();
			}
			if (minCost > cost) {
				minCost = cost;
				minCostPath = path;
			}
		}
		return minCostPath;
	}
	
	/**
	 * Randomises each node's skill
	 */
	public void randomiseSkills() {
		HashMap<Integer,Node> networkMap = network.getMap();
		for (int user: networkMap.keySet()) {
			Node node = networkMap.get(user);
			int skill = ThreadLocalRandom.current().nextInt(0, 11);
			if (skill == 10) {
				skill = ThreadLocalRandom.current().nextInt(10, 101);
				if (skill == 100) {
					skill = ThreadLocalRandom.current().nextInt(100, 10000);
				}
			}
			node.setSkill(skill);
		}
	}
	
	@Test
	public void testGetShortestPath() {
		ArrayList<int[]> paths = getPaths();
		
		// check path with default skill values
		Result result = network.getShortestPath(1, 9);
		int[] minCostPath = getMinCostPath(paths);
		for (int i = 0; i < minCostPath.length; i++) {
			assertEquals(minCostPath[i], result.getPath().get(i).getUser());
		}
		
		// check cost with randomised skill values
		for (int n = 0; n < N_TRIALS; n++) {
			randomiseSkills();
			result = network.getShortestPath(1, 9);
			double minCost = getMinCost(paths);
			assertEquals(minCost, result.getCost(), 1e-6);
		}
	}
	
	@Test
	public void testCalculateShortestPathFail() {
		assertEquals(Status.FAIL_NO_PATH, network.getShortestPath(1, 10).getStatus());
		assertEquals(Status.FAIL_NO_USER1, network.getShortestPath(11, 9).getStatus());
		assertEquals(Status.FAIL_NO_USER2, network.getShortestPath(1, 11).getStatus());
		assertEquals(Status.FAIL_NO_USERS, network.getShortestPath(11, 12).getStatus());
	}

}
