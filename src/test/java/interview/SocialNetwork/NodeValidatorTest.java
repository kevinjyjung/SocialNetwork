package interview.SocialNetwork;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import com.google.gson.JsonParseException;

public class NodeValidatorTest {

	@Test
	public void testValidateSucess() {
		NodeValidator nodeVal = new NodeValidator();
		nodeVal.user = 1;
		nodeVal.skill = 1;
		nodeVal.friends = new ArrayList<Integer>();
		Node node = NodeValidator.validate(nodeVal);
		assertEquals(1, node.getUser());
		assertEquals(1, node.getSkill());
		assertEquals(0, node.getFriends().size());
	}
	
	@Test(expected = JsonParseException.class)
	public void testValidateFail() {
		NodeValidator nodeVal = new NodeValidator();
		NodeValidator.validate(nodeVal);
	}

}
