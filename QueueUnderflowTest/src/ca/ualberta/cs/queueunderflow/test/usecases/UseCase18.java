package ca.ualberta.cs.queueunderflow.test.usecases;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.QuestionList;
import ca.ualberta.cs.queueunderflow.models.Reply;
import ca.ualberta.cs.queueunderflow.singletons.User;
import ca.ualberta.cs.queueunderflow.views.MainActivity;

//Use CASE 18(incorporates user story 23)
public class UseCase18 extends ActivityInstrumentationTestCase2<MainActivity>
{
	
	public UseCase18() {
		super(MainActivity.class);
	}

	public void testSetUserName() {
		User me= new User();
		
		QuestionList questionList= new QuestionList();
		String questionName= "How does this work?";
		String author= "Paul";
		
		// Checks if username is correctly set when making a question/answer/reply - valid username
		me.setUserName(author);
		Question questionTest= new Question(questionName,me.getUserName());
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
		
		
		
		//Exception: No username is typed in or only whitespace is typed in
		String no_author= "         ";
		int flag = 0; // indicates if the whitespace exception is caught & handled
		try {
			me.setUserName(no_author);
		} catch (IllegalArgumentException e) {
			flag = 1;
		}
		assertTrue("Whitespace username exception caught & handled", flag == 1);
		assertEquals("No author specified, author is set to Anonymous", "Anonymous", me.getUserName());
		
		// Checks if username is correctly set when making a question - invalid username entered, set to Anonymous
		Question questionTest1= new Question(questionName,me.getUserName());
		questionList.add(questionTest1);
		assertTrue("Author name is Paul or Anonymous", questionTest1.getAuthor().equals("Anonymous"));

		

	}
}