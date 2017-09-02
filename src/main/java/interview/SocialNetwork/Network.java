package interview.SocialNetwork;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
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
		HashSet<Integer> unsettled = new HashSet<Integer>();
		PriorityQueue<Node> unsettledPQ = 
				new PriorityQueue<Node>(networkMap.size()/2, new NodeDistanceComparator());
		HashSet<Integer> settled = new HashSet<Integer>();
		
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
		
		// Replace user2 node with target node which has weight = 0
		networkMap.put(user2, new TargetNode(user2Node));
		
		// Add user1 to unsettled nodes
		unsettled.add(user1);
		unsettledPQ.add(networkMap.get(user1));
		
		// Main loop
		while (!unsettled.isEmpty()) {
			// Remove head
			Node node = unsettledPQ.remove();
			int user = node.getUser();
			unsettled.remove(user);
			settled.add(user);
			
			// Display progress
			if (settled.size() % 10000 == 0) {
				System.out.println("Number of settled nodes: " + Integer.toString(settled.size()));
			}
			
			// Check if target is reached
			if (user == user2) {
				user2Node.dist = node.dist + user2Node.getWeight();
				networkMap.put(user2, user2Node);
				return new Result(user2, networkMap, pathsFrom, user2Node.dist);
			}
			
			// Iterate through friends
			for (int friend: node.getFriends()) {
				// Check if already settled
				if (!settled.contains(friend)) {
					Node friendNode = networkMap.get(friend);
					double newFriendDist = node.dist + friendNode.getWeight();
					// Update distance if path is shorter
					if (unsettled.contains(friend)) {
						if (friendNode.dist > newFriendDist) {
							// Remove and add to reorder priority queue
							unsettledPQ.remove(friendNode);
							friendNode.dist = newFriendDist;
							unsettledPQ.add(friendNode);
							pathsFrom.put(friend, user);
						}
					// If new, then add to unsettled nodes
					} else {
						friendNode.dist = newFriendDist;
						unsettledPQ.add(friendNode);
						unsettled.add(friend);
						pathsFrom.put(friend, user);
					}
				}
			}
		}
		networkMap.put(user2, user2Node);
		return new Result(Status.FAIL_NO_PATH);
	}
	
	public HashMap<Integer,Node> getMap() {
		return this.networkMap;
	}
}
