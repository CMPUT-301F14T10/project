package ca.ualberta.cs.queueunderflow.test;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import ca.ualberta.cs.queueunderflow.ListHandler;
import ca.ualberta.cs.queueunderflow.R;
import ca.ualberta.cs.queueunderflow.adapters.AnswerListAdapter;
import ca.ualberta.cs.queueunderflow.adapters.SingleQuestionAdapter;
import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.views.QAViewActivity;

/* This test upvoting a question in the QAView  - the display with only 1 question & its answers */
public class UseCase11C extends ActivityInstrumentationTestCase2<QAViewActivity> {

	Question question;
	Answer answer1;
	Answer answer2;
	
	public UseCase11C() {
		super(QAViewActivity.class);
	}
	
	public void setUp() {
		ListHandler.getMasterQList().getQuestionList().clear();
		
		// new questions & answers always start with an upvote of 0
		question = new Question("A Question", "Me");
		answer1 = new Answer("Answer #1", "You");
		answer2 = new Answer("Answer #2", "Him");
		question.addAnswer(answer1);
		question.addAnswer(answer2);
		ListHandler.getMasterQList().add(question);
	}
	
	public void testUpvoteDisplay() {
		Intent intent = new Intent();
		intent.putExtra("fromFragment", QAViewActivity.HOME_SCREEN_FRAGMENT);
		intent.putExtra("position", 0);
		
		setActivityIntent(intent);
		QAViewActivity activity = (QAViewActivity) getActivity();
		
		// --------- TEST QUESTION UPVOTE DISPLAY --------- //
		// Retrieve single question list view & adapter
		ExpandableListView singleQListView = (ExpandableListView) activity.findViewById(R.id.questionExpListView);
		SingleQuestionAdapter adapter = (SingleQuestionAdapter) singleQListView.getExpandableListAdapter();
		
		// Get layout for the 1st & only question in the listview
		View view = adapter.getGroupView(0, false, null, null);
		assertEquals(question,adapter.getGroup(0));
		
		// Check that the upvote is initially at 0
		TextView upvoteCount = (TextView) view.findViewById(R.id.upvoteDisplay);
		assertEquals(Integer.toString(0), upvoteCount.getText().toString());
		
		// Perform a click which should increase the upvote by 1
		ImageButton upvoteBtn = (ImageButton) view.findViewById(R.id.upvoteBtn);
		upvoteBtn.performClick();
		
		// Check that the upvote has increased by 1
		assertEquals(Integer.toString(1), upvoteCount.getText().toString());

		
		
		// --------- TEST ANSWER UPVOTE DISPLAY --------- //
		// Retrieve answer list view & adapter
		ExpandableListView answerListView = (ExpandableListView) activity.findViewById(R.id.answersExpListView);
		AnswerListAdapter adapterA = (AnswerListAdapter) answerListView.getExpandableListAdapter();
		
		// Get layout for the 1st answer in the listview
		View viewA = adapterA.getGroupView(0, false, null, null);
		assertEquals(answer2,adapterA.getGroup(0));
		
		// Check that the upvote is initially at 0
		TextView upvoteCountA = (TextView) viewA.findViewById(R.id.upvoteDisplay);
		assertEquals(Integer.toString(0), upvoteCountA.getText().toString());
		
		// Perform a click which should increase the upvote by 1
		ImageButton upvoteBtnA = (ImageButton) viewA.findViewById(R.id.upvoteBtn);
		upvoteBtnA.performClick();
		
		// Check that the upvote has increased by 1
		assertEquals(Integer.toString(1), upvoteCountA.getText().toString());
		
		// Check that the other answer's upvote has not changed - ensuring that we're upvoting the correct answer
		View view1 = adapterA.getGroupView(1, false, null, null);
		assertEquals(answer1,adapterA.getGroup(1));
		
		// Check that the upvote is still at 0
		TextView upvoteCount1 = (TextView) view1.findViewById(R.id.upvoteDisplay);
		assertEquals(Integer.toString(0), upvoteCount1.getText().toString());
	}
	
	public void tearDown() {
		ListHandler.getMasterQList().getQuestionList().clear();
	}
}
