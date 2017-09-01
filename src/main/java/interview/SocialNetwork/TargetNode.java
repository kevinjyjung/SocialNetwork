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
	
	@Override
	public double getWeight() {
		return 0;
	}

}
