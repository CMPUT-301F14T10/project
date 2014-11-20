package ca.ualberta.cs.queueunderflow.test.usecases;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import ca.ualberta.cs.queueunderflow.User;
import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.QuestionList;
import ca.ualberta.cs.queueunderflow.models.Reply;
import ca.ualberta.cs.queueunderflow.views.MainActivity;

//Use CASE 18(incorporates user story 23)
public class UseCase18 extends ActivityInstrumentationTestCase2<MainActivity>
{
	
	public UseCase18() {
		super(MainActivity.class);
	}
	
	public void testSetUsernameDisplay() throws Throwable {

		// Start the SetUsernameFragment
		Intent intent = new Intent();
		intent.putExtra("returnFragment", MainActivity.SET_USERNAME_FRAGMENT);
		setActivityIntent(intent);
		MainActivity activity = (MainActivity) getActivity();
		
		// Check that the default username is set to Anonymous upon the initial start
		TextView currentUsername = (TextView) activity.findViewById(ca.ualberta.cs.queueunderflow.R.id.currentUsername);
		assertEquals("Anonymous", currentUsername.getText().toString());
		assertEquals("Anonymous", User.getUserName());	// For this to work, you have to clear the data for the app on the phone first to mimic initial startup of the app for the first time
		
		// Change the username & click submit
		runTestOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				MainActivity activity = (MainActivity) getActivity();
				
				EditText newUsername = (EditText) activity.findViewById(ca.ualberta.cs.queueunderflow.R.id.newUsername);
				newUsername.setText("Geneva");
				
				assertEquals("Geneva", newUsername.getText().toString());
				Button submitBtn = (Button) activity.findViewById(ca.ualberta.cs.queueunderflow.R.id.submitBtn);
				submitBtn.performClick();	
				
			}
		});
		
		// Check that the username is has been set on the screen & in User
		assertEquals("Geneva", currentUsername.getText().toString());
		assertEquals("Geneva", User.getUserName());
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