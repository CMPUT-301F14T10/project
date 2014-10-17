package ca.ualberta.cs.queueunderflow.test;

import ca.ualberta.cs.queueunderflow.Answer;
import ca.ualberta.cs.queueunderflow.Question;
import ca.ualberta.cs.queueunderflow.User;
import junit.framework.TestCase;

public class UseCase11 extends TestCase {
	
<<<<<<< HEAD
	public void testUpvote() {
		User user=new User();
		user.setUserName("A user");
=======
	//Use CASE 11 (incorporates user stories 9 and 10)
	public void testUpvote() {
		User user=new User();
		user.setUserName("Paul");
>>>>>>> b276022bec7b228e126fe6f42c8febf3b2091006
		
		String userName= user.getUserName();
		String questionName= "A question?";
		String author= userName;
		
		//Add question_replies
		Question questionTest= new Question(questionName, author);
		questionTest.upvoteQuestion();
		user.addUpvotedQuestion(questionTest);
		assertTrue("Question upvoted by 1", questionTest.getUpvotes()==1);
		
		//Adding an answer

		String author2="Author 2";
		String answerName= "An answer";

		
		Answer testAnswer= new Answer(answerName,author2);
		testAnswer.upvoteAnswer();
		user.addUpvotedAnswer(testAnswer);
		assertTrue("Answer upvoted by 1", testAnswer.getUpvotes()==1);
		
		//Exception: User already upvoted question/answer
		assertTrue("Question already upvoted", user.alreadyUpvotedQuestion(questionTest));
		assertTrue("Answer already upvoted", user.alreadyUpvotedAnswer(testAnswer));
	}
<<<<<<< HEAD
	
=======
>>>>>>> b276022bec7b228e126fe6f42c8febf3b2091006
}
