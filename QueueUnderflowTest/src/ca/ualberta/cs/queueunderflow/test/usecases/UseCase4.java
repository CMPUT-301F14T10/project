package ca.ualberta.cs.queueunderflow.test.usecases;

import java.util.ArrayList;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.text.format.DateFormat;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import ca.ualberta.cs.queueunderflow.ListHandler;
import ca.ualberta.cs.queueunderflow.Picture;
import ca.ualberta.cs.queueunderflow.User;
import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.AnswerList;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.QuestionList;
import ca.ualberta.cs.queueunderflow.models.Reply;
import ca.ualberta.cs.queueunderflow.views.AskAQuestionActivity;
import ca.ualberta.cs.queueunderflow.views.MainActivity;
import junit.framework.TestCase;

//Use CASE 4 (incorporates user stories 4 and 7)
public class UseCase4 extends ActivityInstrumentationTestCase2<AskAQuestionActivity> {
	
	public UseCase4() {
		super(AskAQuestionActivity.class);
	}
	
	public void testAskQuestionDisplay() throws Throwable {
		
		// Set Username
		User user = new User();
		user.setUserName("Me");
		
		// Start the AskAQuestionActivity
		AskAQuestionActivity activity = (AskAQuestionActivity) getActivity();
		
		// Check that the author username is displaying the the User username
		TextView authorUsername = (TextView) activity.findViewById(ca.ualberta.cs.queueunderflow.R.id.authorBox);
		assertEquals("Me", authorUsername.getText().toString());
		
		// Enter a question & click ask
		runTestOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				
				// Start the AskAQuestionActivity
				AskAQuestionActivity activity1 = (AskAQuestionActivity) getActivity();
				
				// Enter a question in the editText
				EditText questionInput = (EditText) activity1.findViewById(ca.ualberta.cs.queueunderflow.R.id.questionInput);
				questionInput.setText("This is my question.");
				
				assertEquals("This is my question.", questionInput.getText().toString());
				
				// Perform a click to submit/ask the question
				Button askBtn = (Button) activity1.findViewById(ca.ualberta.cs.queueunderflow.R.id.askBtn);
				askBtn.performClick();	
				
			}
		});
		
		// Check that the question asked is in the masterlist of questions & in the myQuestions asked list & that it's at the top since its the most recent question
		Question question = new Question("This is my question.", "Me");
		
		assertEquals(question, ListHandler.getMasterQList().get(0));
		assertEquals(question, ListHandler.getMyQsList().get(0));
	}
	
	public void testAskQuestion() {
		User me= new User();
		me.setUserName("Me");
		
		//Add a question to the list
		QuestionList questionList= new QuestionList();
		String questionName= "How does this work?";
		Question questionTest= new Question(questionName,me.getUserName());
		Picture pic= new Picture(32);
		questionTest.setPicture(pic);
		
		questionList.add(questionTest);
		assertTrue("Question List isn't empty", questionList.size()==1);
		
		//Exception: Where the question entered is whitespaces only
		String questionName2= "        ";
		int flag = 0; // indicates if the whitespace exception is caught & handled
		try {
			Question anotherQuestion = new Question(questionName2, me.getUserName());
			questionList.add(anotherQuestion);
		} catch (IllegalArgumentException e) {
			flag = 1;
		}
		assertTrue("Whitespace question exception caught & handled", flag == 1);
		assertTrue("Whitespace only question is not added to the questionList", questionList.size() == 1);
		
		/*Exception: Where there is no online connectivity
		 * 
		 By default, there is no network connectivity (questionList connectivity is initialized to be 0)
		 because this is a mock test, not the actual implementation.
		 */
		assertFalse("No network connectivity, push online later.",questionList.pushOnline());
		
		
	}
}
