package ca.ualberta.cs.queueunderflow.test.models;

import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.AnswerList;
import ca.ualberta.cs.queueunderflow.models.Question;
import junit.framework.TestCase;

public class QuestionModelTest extends TestCase{

	Question question= new Question("A question", "Paul");
	Question question2= new Question("A question", "Paul");

	public void testAddAnswer_and_GetAnswerListSize_Methods() {
		Answer answer= new Answer("An answer", "Paul");
		question.addAnswer(answer);
		
		assertTrue("An answer was added and the size of answerlist is 1", question.getAnswerListSize()==1);
	}
	public void testGetAnswerListMethod() {
		Answer answer= new Answer("An answer", "Paul");
		question.addAnswer(answer);
		
		AnswerList answers= question.getAnswerList();
		assertTrue("Answerlist has an answer", answers.size()==1);
	}
	
	public void testSetAnswerListMethod() {
		AnswerList answers= new AnswerList();
		Answer answer= new Answer("An answer", "Paul");

		answers.add(answer);
		answers.add(answer);
		
		question.setAnswerList(answers);
		assertTrue("AnswerList of question has a size of two because it was set by setter", question.getAnswerList().size()==2);

	}
	
	public void testEqualsMethod() {
		Answer answer= new Answer("An answer", "Paul");

		question.setUpvotes(10);
		question2.setUpvotes(10);
		question.addAnswer(answer);
		question2.addAnswer(answer);
		
		assertTrue("Both questions are the same", question.equals(question2));
	}
}
