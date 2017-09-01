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
    	// JSON Lines file input
    	Scanner in = new Scanner(System.in);
    	System.out.println("\nPath to JSON Lines file:");
    	String fileName = in.next();
    	
    	// Create network
    	System.out.println("Creating network...");
    	Network network = Network.create(fileName);
    	if (network == null) {
    		System.err.println("Could not create network.");
    		in.close();
    		return;
    	}
    	System.out.println("Network created. Users to connect:");
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
    	    	System.out.println("Run again with new users?[y/N]");
    	    	String again = in.next();
    	    	if (again.equals("y") || again.equals("Y")) {
    	    		System.out.println("Users to connect:");
    	    	} else {
    	    		break;
    	    	}
    		} catch (InputMismatchException e) {
    			System.out.print("Invalid input. Please try again with integer user ID values.\n>> ");
    			in.nextLine();
    		}
    	}
    	in.close();
    }
}
