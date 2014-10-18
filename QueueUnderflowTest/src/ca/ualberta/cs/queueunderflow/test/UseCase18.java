package ca.ualberta.cs.queueunderflow.test;

import ca.ualberta.cs.queueunderflow.Answer;
import ca.ualberta.cs.queueunderflow.Question;
import ca.ualberta.cs.queueunderflow.QuestionList;
import ca.ualberta.cs.queueunderflow.Reply;
import ca.ualberta.cs.queueunderflow.User;
import junit.framework.TestCase;

//Use CASE 18(incorporates user story 23)
public class UseCase18 extends TestCase
{
	public void testSetUserName() {
		User me= new User();
		
		QuestionList questionList= new QuestionList();
		String questionName= "How does this work?";
		String author= "Paul";
		
		me.setUserName(author);
		Question questionTest= new Question(questionName,me.getUserName());
		
		//Exception: No username is typed in or only whitespace is typed in
		String no_author= "         ";
		String author_exception= no_author.trim();
		assertTrue("No author specified, setting author to Anonymous",author_exception.length()==0);
		
		questionList.add(questionTest);
		assertTrue("Author name is Paul or Anonymous", questionTest.getAuthor().equals("Paul"));
		
		int question_index= questionList.questionIndex(questionTest);
		assertTrue("question index is 0", question_index==0);
		String answerName= "An answer";
		Answer testAnswer= new Answer(answerName,author);

		assertTrue("Answer author is Paul",testAnswer.getAuthor().equals(author));
		Reply a_reply= new Reply("Go to stackoverflow",author);
		assertTrue ("reply author is Paul", a_reply.getAuthor().equals("Paul"));
		testAnswer.addReply(a_reply);
	}
}