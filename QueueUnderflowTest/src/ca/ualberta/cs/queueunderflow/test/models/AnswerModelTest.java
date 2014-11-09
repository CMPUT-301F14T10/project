package ca.ualberta.cs.queueunderflow.test.models;

import ca.ualberta.cs.queueunderflow.models.Answer;
import junit.framework.TestCase;


//Not really much to test here; that's why its 5 lines long

public class AnswerModelTest extends TestCase{

	Answer testAnswer= new Answer("An answer", "Paul");
	
	public void testAnswerTest() {
		assertTrue("Answer content is 'An answer'", testAnswer.getName().equals("An answer"));
		assertTrue("Answer author is Paul", testAnswer.getAuthor().equals("Paul"));
	}
}
