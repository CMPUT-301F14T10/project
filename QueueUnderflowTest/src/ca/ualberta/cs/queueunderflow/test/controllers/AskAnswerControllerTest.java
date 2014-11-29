package ca.ualberta.cs.queueunderflow.test.controllers;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ImageButton;
import ca.ualberta.cs.queueunderflow.ListHandler;
import ca.ualberta.cs.queueunderflow.R;
import ca.ualberta.cs.queueunderflow.User;
import ca.ualberta.cs.queueunderflow.controllers.AskAnswerController;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.views.AskAQuestionActivity;

public class AskAnswerControllerTest extends ActivityInstrumentationTestCase2<AskAQuestionActivity>{
	
	public AskAnswerControllerTest() {
		super(AskAQuestionActivity.class);
		}

	//test for adding a question w/o a picture using contorller
	public void testAskQuestion() throws Throwable{
		String question = "A question";
		String name = "personA";
		ListHandler.getMasterQList().getQuestionList().clear();

		// Start the AskAQuestionActivity
		AskAQuestionActivity activity = (AskAQuestionActivity) getActivity();
		
		// No picture attached
		ImageButton imagePreviewBtn = (ImageButton) activity.findViewById(R.id.imagePreviewBtn);
		imagePreviewBtn.setVisibility(View.INVISIBLE);
		
		//init contorller
		AskAnswerController acc = new AskAnswerController(activity);
		//add the question through controlelr
		acc.askQuestion(question, name, imagePreviewBtn);
		assertTrue("quesiton not added", ListHandler.getMasterQList().size() == 1);
		assertEquals("question not the same", ListHandler.getMasterQList().get(0).getAuthor(), name );
		
	}
	
	//test for adding an answer w/o a picture to a quesiton
	public void testAddAnswer(){
		//start activity
		AskAQuestionActivity activity = (AskAQuestionActivity) getActivity();	
		//clear list
		ListHandler.getMasterQList().getQuestionList().clear();
		assertTrue("list cleared", ListHandler.getMasterQList().getQuestionList().size()==0);
		//add a new question to the list
		Question question = new Question("A question", "Me");
		AskAnswerController acc = new AskAnswerController(activity);
		ListHandler.getMasterQList().add(question);
		assertTrue("question added", ListHandler.getMasterQList().getQuestionList().size()==1);
		//adding an answer to the quesiton using a controller
		
		// No picture attached
		ImageButton imagePreviewBtn = (ImageButton) activity.findViewById(R.id.imagePreviewBtn);
		imagePreviewBtn.setVisibility(View.INVISIBLE);
		
		acc.addAnswer(1, 0, "an answer", User.getUserName(), imagePreviewBtn);
		assertTrue("anaswer not added", ListHandler.getMasterQList().get(0).getAnswerListSize()==1);
}
}
