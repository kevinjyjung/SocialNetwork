package interview.SocialNetwork;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 * Represents result object from shortest path algorithm
 * @author Kevin Jung
 *
 */
public class Result {
	
	private ArrayList<Node> path;
	private double cost;
	private Status status;
	
	/**
	 * Constructor used for success
	 * @param user2 Target point
	 * @param networkMap HashMap of userID to Node
	 * @param pathsFrom Optimal path information
	 * @param cost Total cost of optimal solution
	 */
	public Result(int user2, HashMap<Integer,Node> networkMap, 
			HashMap<Integer,Integer> pathsFrom, double cost) {
		// Failures should use the second constructor
		if (pathsFrom.get(user2) == null) {
			this.status = Status.FAIL_UNEXPECTED;
			return;
		} 
		
		this.status = Status.SUCCESS;
		this.cost = cost;
		path = new ArrayList<Node>();
		
		// Check if same user
		if (pathsFrom.get(user2) == user2) {
			path.add(networkMap.get(user2));
			return;
		}
		
		// Use a stack to obtain correct order for the optimal path
		Stack<Integer> userStack = new Stack<Integer>();
		Integer user = user2;
		while (user != null) {
			userStack.push(user);
			user = pathsFrom.get(user);
		}
		while (!userStack.isEmpty()) {
			this.path.add(networkMap.get(userStack.pop()));
		}
 	}
	
	/**
	 * Alternate constructor used for failures
	 * @param status
	 */
	public Result(Status status) {
		this.path = null;
		this.cost = 0;
		
		// First constructor should be used for success
		if (status == Status.SUCCESS) {
			this.status = Status.FAIL_UNEXPECTED;
		} else {
			this.status = status;
		}
	}
	
	/**
	 * Optimal path
	 * @return Path Optimal path as an ArrayList of Nodes
	 */
	public ArrayList<Node> getPath() {
		if (this.path == null) {
			return null;
		}
		return new ArrayList<Node>(this.path);
	}
	
	/**
	 * Optimal cost
	 * @return Optimal cost
	 */
	public double getCost() {
		return this.cost;
	}
	
	/**
	 * Represents failure or success
	 * @return One of SUCCESS | FAIL_NO_PATH | FAIL_NO_USERS | FAIL_NO_USER1
	 * | FAIL_NO_USER2 | FAIL_UNEXPECTED
	 */
	public Status getStatus() {
		return this.status;
	}
	
	/**
	 * Convert result to a human readable string
	 * @return Result string
	 */
	@Override
	public String toString() {
		switch (this.status) {
		case SUCCESS:
			StringBuilder builder = new StringBuilder();
			builder.append(String.format("%10s %10s%n", "user", "skill"));
			for (Node node: this.path) {
				builder.append(node.toString());
				builder.append("\n");
			}
			builder.append(String.format("%nTotal Cost: %.4f%n", this.cost));
			return builder.toString();	 
		case FAIL_NO_PATH:
    		return "Path doesn't exist.";
    	case FAIL_NO_USERS:
    		return "Users don't exist.";
    	case FAIL_NO_USER1:
    		return "User 1 doesn't exist.";
    	case FAIL_NO_USER2:
    		return "User 2 doesn't exist.";
    	default:
    		return "Unexpected error.";
		}
		
	}
}
