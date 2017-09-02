package interview.SocialNetwork;

import java.util.ArrayList;

import com.google.gson.JsonParseException;

/**
 * Validates a node created from parsing a JSON line
 * @author jungk
 *
 */
public class NodeValidator {
	public Integer user;
	public Integer skill;
	public ArrayList<Integer> friends;
	
	public NodeValidator() {}
	
	public static Node validate(NodeValidator node) throws JsonParseException {
		if ((node.user != null) && (node.skill != null) && (node.friends != null)) {
			return new Node(node);
		} else {
			throw new JsonParseException("JSON Object doesn't match schema");
		}
	}
}
