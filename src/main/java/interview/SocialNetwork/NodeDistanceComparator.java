package interview.SocialNetwork;

import java.util.Comparator;

/**
 * Distance comparator for nodes
 * @author Kevin Jung
 *
 */
public class NodeDistanceComparator implements Comparator<Node> {

	public int compare(Node node1, Node node2) {
		double diff = node1.dist - node2.dist;
		if (diff > 0) {
			return 1;
		} else if (diff < 0) {
			return -1;
		} else {
			return 0;
		}
	}
	
}
