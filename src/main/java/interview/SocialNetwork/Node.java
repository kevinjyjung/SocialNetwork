package interview.SocialNetwork;

import java.util.ArrayList;

/**
 * Represents nodes in the social network
 * @author Kevin Jung
 *
 */
public class Node {

	private int user;
	private int skill;
	private ArrayList<Integer> friends;
	public double dist = Double.MAX_VALUE;
	
	public Node(int user, int skill) {
		this.user = user;
		this.skill = skill;
		this.friends = new ArrayList<Integer>();
	}
	
	public int getUser() {
		return this.user;
	}
	
	public int getSkill() {
		return this.skill;
	}
	
	public void setSkill(int skill) {
		this.skill = skill;
	}
	
	public double getWeight() {
		return 1/((double)this.skill + 1.);
	}
	
	public ArrayList<Integer> getFriends() {
		return this.friends;
	}
	
	public void addFriends(ArrayList<Integer> friends) {
		this.friends.addAll(friends);
	}
	
	public void addFriend(int friend) {
		this.friends.add(friend);
	}
	
	@Override
	public String toString() {
		return String.format("%,10d %,10d", this.user, this.skill);
	}
}
