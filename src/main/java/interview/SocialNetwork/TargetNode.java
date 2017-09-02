package interview.SocialNetwork;

/**
 * Target Node overrides the getWeight method from a normal Node
 * @author Kevin Jung
 *
 */
public class TargetNode extends Node {

	public TargetNode(int user, int skill) {
		super(user, skill);
	}
	
	public TargetNode(Node node) {
		super(node.getUser(), node.getSkill());
		addFriends(node.getFriends());
	}
	
	/**
	 * Weight of target node is set to zero since all edges coming in
	 * to the target node have the same weight, it shouldn't affect
	 * the final path obtained from running the algorithm
	 */
	@Override
	public double getWeight() {
		return 0;
	}

}
