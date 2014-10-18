package ca.ualberta.cs.queueunderflow.test;

import ca.ualberta.cs.queueunderflow.Picture;
import ca.ualberta.cs.queueunderflow.Question;
import ca.ualberta.cs.queueunderflow.QuestionList;
import ca.ualberta.cs.queueunderflow.User;
import junit.framework.TestCase;

public class UseCase14 extends TestCase {
	public void testQuestionsAsked() {
		User me= new User();
		me.setUserName("Me");
		
		QuestionList questionList= new QuestionList();
		String questionName= "How does this work?";

		Question questionTest= new Question(questionName,me.getUserName());
		Picture pic= new Picture(32);
		questionTest.setPicture(pic);
		
		questionList.add(questionTest);
		assertTrue("Question List isn't empty", questionList.size()==1);
	}
}
