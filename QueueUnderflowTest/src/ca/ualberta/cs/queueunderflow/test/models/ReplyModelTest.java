package ca.ualberta.cs.queueunderflow.test.models;

import ca.ualberta.cs.queueunderflow.models.Reply;
import junit.framework.TestCase;

public class ReplyModelTest extends TestCase {

	Reply reply= new Reply ("This is a reply", "Paul");
	
	public void testGetAuthor_and_SetAuthor_Methods() {
		assertTrue("Author is Paul", reply.getAuthor().equals("Paul"));
		
		reply.setAuthor("Not Paul");
		assertTrue("Author isn't Paul", reply.getAuthor().equals("Not Paul"));
	}
	
	public void testGetReply_and_SetReply_Methods() {
		assertTrue("Reply is the same as original one", reply.getReply().equals("This is a reply"));
		
		reply.setReply("New reply");
		assertTrue("The reply was set to new reply", reply.getReply().equals("New reply"));
	}
	
	public void testReplyEqualsMethod() {
		Reply reply2= new Reply ("This is a reply", "Paul");
		assertTrue("Both replies are equal", reply.equals(reply2));
	}
}
