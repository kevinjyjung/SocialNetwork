package interview.SocialNetwork;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.PriorityQueue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

/**
 * Represents the social network
 * @author Kevin Jung
 *
 */
public class Network {
	
	private HashMap<Integer,Node> networkMap;
	
	private Network(HashMap<Integer,Node> network) {
		this.networkMap = network;
	}

	/**
	 * Creates the network using the data file
	 * @param filePath Path to the JSON Lines file containing network info
	 * @return Network instance or null if failure
	 */
	public static Network create(String filePath) {
		BufferedReader reader = null;
		HashMap<Integer,Node> networkMap = new HashMap<Integer,Node>();
		try {
			String currentLine;
			reader = new BufferedReader(new FileReader(filePath));
			try {
				while ((currentLine = reader.readLine()) != null) {
					Gson gson = new GsonBuilder().create();
					Node node = NodeValidator.validate(
							gson.fromJson(currentLine, NodeValidator.class));
					networkMap.put(node.getUser(), node);
				}
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (reader != null) {
					reader.close();
				} 
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return new Network(networkMap);
	}
	
	/**
	 * Resets distances of all nodes in the network
	 */
	public void resetDistances() {
		for (int user: this.networkMap.keySet()) {
			this.networkMap.get(user).dist = Double.MAX_VALUE;
		}
	}
	
	/**
	 * Calculates the shortest path with the highest skilled programmers
	 * @param user1 Starting point
	 * @param user2 Target point
	 * @return Result object
	 */
	public Result getShortestPath(int user1, int user2) {
		// Reset distances
		resetDistances();
		
		// Initialise data structures
		HashMap<Integer,Integer> pathsFrom = new HashMap<Integer,Integer>();
		PriorityQueue<Node> unsettled = 
				new PriorityQueue<Node>(networkMap.size()/2, new NodeDistanceComparator());
		
		// Endpoint nodes
		Node user1Node = networkMap.get(user1);
		Node user2Node = networkMap.get(user2);
		
		// Check that users are distinct values
		if (user1 == user2) {
			pathsFrom.put(user1, user1);
			return new Result(user1, networkMap, pathsFrom, 0);
		}
		
		// Check if the two endpoint users exist in the network
		if ((user1Node == null) && (user2Node == null)) {
			return new Result(Status.FAIL_NO_USERS);
		} else if (user1Node == null) {
			return new Result(Status.FAIL_NO_USER1);
		} else if (user2Node == null) {
			return new Result(Status.FAIL_NO_USER2);
		}
		
		// Set initial distance to zero
		networkMap.get(user1).dist = 0;
		
		// Add user1 to unsettled nodes
		unsettled.add(networkMap.get(user1));
		
		// Main loop
		while (!unsettled.isEmpty()) {
			// Remove head
			Node node = unsettled.remove();
			int user = node.getUser();
			
			// Iterate through friends
			for (int friend: node.getFriends()) {
				Node friendNode = networkMap.get(friend);
				// Check if already discovered
				if (friendNode.dist == Double.MAX_VALUE) {
					// Update distance and add to unsettled
					friendNode.dist = node.dist + friendNode.getWeight();
					pathsFrom.put(friend, user);
					// Check if target is reached
					if (friend == user2) {
						return new Result(user2, networkMap, pathsFrom, friendNode.dist);
					}
					unsettled.add(friendNode);
				}
			}
		}
		return new Result(Status.FAIL_NO_PATH);
	}
	
	public HashMap<Integer,Node> getMap() {
		return this.networkMap;
	}
}
