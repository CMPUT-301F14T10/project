package ca.ualberta.cs.queueunderflow.test.usecases;

import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.QuestionList;
import ca.ualberta.cs.queueunderflow.singletons.User;
import junit.framework.TestCase;

public class UseCase12 extends TestCase {
	public void testSeeMostUpvoted() {
		User user=new User();
		user.setUserName("A user");
		QuestionList questionList= new QuestionList();

		String userName= user.getUserName();
		String questionName= "A question?";
		String author= userName;
		
		//Exception: No questions
		assertTrue("No questions currently", questionList.size()==0);
		
		Question question1= new Question(questionName, author);
		
		//Exception: No upvoted questions
		assertTrue("No upvoted questions", question1.getUpvotes()==0);
		
		//Exception: No answers to a questions
		assertTrue("No answers currently", question1.getAnswerListSize()==0);
		
		
		
		String answer_test= "Answer1";
		String answer_test2= "Answer2";
		
		Answer answer1= new Answer(answer_test,author);
		Answer answer2= new Answer(answer_test2, author);
		
		//Exception: No upvoted answers
		assertTrue("No upvoted answers", answer1.getUpvotes()==0);
		assertTrue("No upvoted answers", answer2.getUpvotes()==0);
		
		answer1.setUpvotes(5);
		answer2.setUpvotes(10);
		
		question1.addAnswer(answer1);
		question1.addAnswer(answer2);
		
		assertTrue("Question has two answers", question1.getAnswerListSize()==2);
		assertTrue("Most upvoted answer is answer2", question1.getAnswerList().getAnswer(0) ==answer2);

	}
}