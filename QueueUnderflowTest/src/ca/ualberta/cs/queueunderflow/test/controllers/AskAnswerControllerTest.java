package ca.ualberta.cs.queueunderflow.test.controllers;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.TextView;
import ca.ualberta.cs.queueunderflow.ListHandler;
import ca.ualberta.cs.queueunderflow.controllers.AskAnswerController;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.views.AddAnAnswerActivity;
import ca.ualberta.cs.queueunderflow.views.AskAQuestionActivity;

public class AskAnswerControllerTest extends ActivityInstrumentationTestCase2<AskAQuestionActivity>{
	
	public AskAnswerControllerTest() {
		super(AskAQuestionActivity.class);
		}

	//test for adding a question using contorller
	public void testAskQuestion() throws Throwable{
		String question = "A question";
		String name = "personA";
		int hasPicture = 0;
		ListHandler.getMasterQList().getQuestionList().clear();

		// Start the AskAQuestionActivity
		AskAQuestionActivity activity = (AskAQuestionActivity) getActivity();	
		//init contorller
		AskAnswerController acc = new AskAnswerController(activity);
		//add the question through controlelr
		acc.askQuestion(question, name, hasPicture);
		assertTrue("quesiton not added", ListHandler.getMasterQList().size() == 1);
		assertEquals("question not the same", ListHandler.getMasterQList().get(0).getAuthor(), name );
	}
}

