package ca.ualberta.cs.queueunderflow.test;

import ca.ualberta.cs.queueunderflow.Answer;
import ca.ualberta.cs.queueunderflow.AnswerList;
import ca.ualberta.cs.queueunderflow.Question;
import junit.framework.TestCase;


public class UseCase2 extends TestCase
{
	//Use CASE 2 (incorporates user stories 2 and 22)
	public void testViewQuestionsAndAnswers () {
		
		String questionName= "A question?";
		String author= "Me";
		Question questionTest= new Question(questionName,author);
		
		//Exception: There are no answers to the question selected
		assertTrue("No answers for this question yet",questionTest.getAnswerListSize()==0);

		String answerName= "An answer";
		String authorName= "You";
		Answer testAnswer= new Answer(answerName,authorName);
		questionTest.addAnswer(testAnswer);
		
		//Testing to see if the answer_list of a question is not empty
		AnswerList question_answer= questionTest.getAnswerList();
		assertTrue("question_answer isn't empty", question_answer.size()==1);
	}
}
