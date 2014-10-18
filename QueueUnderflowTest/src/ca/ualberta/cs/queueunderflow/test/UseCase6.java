package ca.ualberta.cs.queueunderflow.test;

import ca.ualberta.cs.queueunderflow.Answer;
import ca.ualberta.cs.queueunderflow.Question;
import ca.ualberta.cs.queueunderflow.QuestionList;
import ca.ualberta.cs.queueunderflow.Reply;
import ca.ualberta.cs.queueunderflow.User;
import junit.framework.TestCase;


public class UseCase6 extends TestCase
{
	//Use CASE 6 (incorporates user story 6)
	public void testAddReply() {
		User me= new User();
		me.setUserName("Me");
		
		QuestionList questionList= new QuestionList();
		String questionName= "A question?";

		String author= "Some other user";
		
		//Add question_replies
		Question questionTest= new Question(questionName,author);
		
		//Exception: Reply is empty or whitespace characters only
		String emptyReply= "          ";
		String empty= emptyReply.trim();
		String result;
		if (empty.length()==0) {
			result="Reenter an valid reply";
		}
		else {
			result=null;
		}
		assertTrue("emptyAnswer is empty", result!=null);
		
		Reply question_reply=new Reply("What are you talking about", me.getUserName());
		questionTest.addQuestionReply(question_reply);
		assertTrue("Reply list isnt empty", questionTest.getSizeReplies()==1);
		
		//Add a answer and reply to the answer
		String author2="Another user is answering";
		String answerName= "An answer";
		Answer testAnswer= new Answer(answerName,author2);
		
		Reply answer_reply= new Reply("Please clarify what you're asking", me.getUserName());
		testAnswer.addReply(answer_reply);
		assertTrue("Reply list isn't empty", testAnswer.getSizeReplies()==1);
		
		questionTest.addAnswer(testAnswer);
		questionList.add(questionTest);
		
		//Exception: Where there is no online connectivity
		assertFalse("No network connectivity, push online later.",questionList.pushOnline());
	
	}
}
