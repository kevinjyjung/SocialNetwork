package interview.SocialNetwork;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

public class NodeTest {
	
	public static Node node;
	
	@BeforeClass
	public static void beforeClass() {
		node = new Node(1, 4);
		node.addFriend(2);
		ArrayList<Integer> friends = new ArrayList<Integer>();
		friends.add(3);
		friends.add(4);
		node.addFriends(friends);
	}

	@Test
	public void testGetUser() {
		assertEquals(1, node.getUser());
	}

	@Test
	public void testGetSkill() {
		assertEquals(4, node.getSkill());
	}

	@Test
	public void testGetWeight() {
		assertEquals(0.2d, node.getWeight(), 1e-6);
	}

	@Test
	public void testGetFriends() {
		int nFriends = 0;
		int sumFriends = 0;
		for (int friend: node.getFriends()) {
			nFriends++;
			sumFriends += friend;
		}
		assertEquals(9, sumFriends);
		assertEquals(3, nFriends);
	}
}
