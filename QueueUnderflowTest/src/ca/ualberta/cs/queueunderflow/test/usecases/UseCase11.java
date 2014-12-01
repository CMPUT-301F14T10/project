package ca.ualberta.cs.queueunderflow.test.usecases;

import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.singletons.User;
import junit.framework.TestCase;

public class UseCase11 extends TestCase {
	
	//Use CASE 11 (incorporates user stories 9 and 10)
	public void testUpvote() {	
		User user=new User();
		user.setUserName("A user");
		String userName= user.getUserName();
		String questionName= "A question?";
		String author= userName;
		
		//Add question_replies
		Question questionTest= new Question(questionName, author);
		questionTest.upvoteResponse();
		user.addUpvotedQuestion(questionTest.getID());
		assertTrue("Question upvoted by 1", questionTest.getUpvotes()==1);
		
		//Adding an answer

		String author2="Author 2";
		String answerName= "An answer";

		
		Answer testAnswer= new Answer(answerName,author2);
		testAnswer.upvoteResponse();
		user.addUpvotedAnswer(testAnswer.getID());
		assertTrue("Answer upvoted by 1", testAnswer.getUpvotes()==1);
		
		//Exception: User already upvoted question/answer
		assertTrue("Question already upvoted", user.alreadyUpvotedQuestion(questionTest.getID()));
		assertTrue("Answer already upvoted", user.alreadyUpvotedAnswer(testAnswer.getID()));
	}

}
