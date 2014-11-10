package ca.ualberta.cs.queueunderflow.test.controllers;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import ca.ualberta.cs.queueunderflow.ListHandler;
import ca.ualberta.cs.queueunderflow.User;
import ca.ualberta.cs.queueunderflow.controllers.AskAnswerController;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.views.AddAnAnswerActivity;
import junit.framework.TestCase;

public class AskAnswerContorllerTestB extends ActivityInstrumentationTestCase2<AddAnAnswerActivity>{
	//test for adding an answer using contorller
	public AskAnswerContorllerTestB() {
		super(AddAnAnswerActivity.class);
	}


	public void testAddAnswer(){
		ListHandler.getMasterQList().getQuestionList().clear();
		Question question = new Question("A question", "Me");
		User user;
		AddAnAnswerActivity activity = (AddAnAnswerActivity) getActivity();	
		AskAnswerController acc = new AskAnswerController(activity);
		ListHandler.getMasterQList().add(question);
		EditText answertext = (EditText) activity.findViewById(ca.ualberta.cs.queueunderflow.R.id.AddAnswerEditText);
		answertext.setText("an answer");
		acc.addAnswer(0, 0, answertext);
		assertTrue("anaswer not added", ListHandler.getMasterQList().get(0).getAnswerListSize()==1);
	}

}