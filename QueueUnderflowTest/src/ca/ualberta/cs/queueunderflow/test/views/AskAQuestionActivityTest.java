package ca.ualberta.cs.queueunderflow.test.views;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import ca.ualberta.cs.queueunderflow.ListHandler;
import ca.ualberta.cs.queueunderflow.User;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.views.AskAQuestionActivity;


public class AskAQuestionActivityTest extends ActivityInstrumentationTestCase2<AskAQuestionActivity> {

	public AskAQuestionActivityTest() {
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
	
}
