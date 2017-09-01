package interview.SocialNetwork;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Main Program
 * @author Kevin Jung
 *
 */
public class App {
	
	/**
	 * Main method
	 * @param args First argument is the path to JSON Lines file containing network info
	 */
    public static void main(String[] args) {
    	// Validate arguments
    	if (args.length < 1) {
    		System.err.println("You must provide the JSON Lines file to read.");
    		return;
    	}
    	String fileName = args[0];
    	
    	// Create network
    	System.out.println("Creating network...");
    	Network network = Network.create(fileName);
    	if (network == null) {
    		System.err.println("Could not create network.");
    		return;
    	}
    	System.out.print("Network created. Users to connect:\n>> ");
    	Scanner in = new Scanner(System.in);
    	while (in.hasNext()) {
    		try {
    			// Read input
    			
    			int user1 = in.nextInt();
    			int user2 = in.nextInt();
    			
    			// Run algorithm
    	    	System.out.println("Running shortest path algorithm...");
    	    	Result result = network.getShortestPath(user1, user2);
    	    	
    	    	// Print result
    	    	System.out.println(result.toString());
    	    	
    	    	// Next prompt
    	    	System.out.print("Users to connect:\n>> ");
    		} catch (InputMismatchException e) {
    			System.out.print("Invalid input. Please try again with integer user ID values.\n>> ");
    			in.nextLine();
    		}
    	}
    	in.close();
    }
}
