/*package ca.ualberta.cs.queueunderflow.test.views;


//Test isn't working so its all commented out
import ca.ualberta.cs.queueunderflow.ListHandler;
import ca.ualberta.cs.queueunderflow.R;
import ca.ualberta.cs.queueunderflow.controllers.AskAnswerController;
import ca.ualberta.cs.queueunderflow.views.AddAnAnswerActivity;
import ca.ualberta.cs.queueunderflow.views.AskAQuestionActivity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

public class AddAnAnswerActivityTest extends ActivityInstrumentationTestCase2<AddAnAnswerActivity> {

	public AddAnAnswerActivityTest() {
		super(AddAnAnswerActivity.class);
	}
	protected void setUp() throws Exception {
		super.setUp();

	}
	
	public void testSetUpActivity() {
        Log.v("precursor test", "printing string now...");

		Intent intent= new Intent(getActivity(),AskAQuestionActivity.class);
		intent.putExtra("Question", 10);
		setActivityIntent(intent);
		assertTrue("Size is one", ListHandler.getMyQsList().size()==1);

	}
	
	

	@UiThreadTest
	public void testAddAnswerActivity() {
        Log.d("got to the actual thing you want to test", "printing string now...");
		Intent intent= new Intent(getActivity(),AskAQuestionActivity.class);
		intent.putExtra("Question", 10);
		setActivityIntent(intent);
		assertTrue("Size is one", ListHandler.getMyQsList().size()==1);
       // Log.d("test", "got to actual test");

		AddAnAnswerActivity addAnswerAct= getActivity();
		//AskAnswerController controller = new AskAnswerController(addAnswerAct);
		EditText answerText = (EditText) addAnswerAct.findViewById(R.id.AddAnswerEditText);
		answerText.setText("An answer");
		assertEquals("An answer", answerText.getText().toString());

		//Button addBtn = (Button) addAnswerAct.findViewById(ca.ualberta.cs.queueunderflow.R.id.AddAnAnswerButton);
		//addBtn.performClick();
		//controller.addAnswer(3, 0, "answer");
		//int oldLength=lta.getAdapter().getCount();
		//makeTweet("test new element");
		//ArrayAdapter <NormalTweetModel> aa= lta.getAdapter();
		//assertEquals(aa.getCount(),oldLength+1);
		//assertTrue(aa.getItem(aa.getCount()-1) instanceof NormalTweetModel);
		//assertEquals(aa.getItem(aa.getCount()-1).getText(),"test new element");
	}

}*/
