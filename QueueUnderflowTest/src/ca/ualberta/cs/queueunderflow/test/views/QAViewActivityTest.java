package ca.ualberta.cs.queueunderflow.test.views;

import ca.ualberta.cs.queueunderflow.ListHandler;
import ca.ualberta.cs.queueunderflow.R;
import ca.ualberta.cs.queueunderflow.adapters.AnswerListAdapter;
import ca.ualberta.cs.queueunderflow.adapters.SingleQuestionAdapter;
import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.views.QAViewActivity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class QAViewActivityTest extends ActivityInstrumentationTestCase2<QAViewActivity>{

	public QAViewActivityTest() {
		super(QAViewActivity.class);
	}
	public void testQuestionAndAnswersDisplay() {
		ListHandler.getMasterQList().getQuestionList().clear();
		
		Question question = new Question("Another Question", "Me");
		Answer answer1 = new Answer("Answer #1", "You");
		Answer answer2 = new Answer("Answer #2", "Him");
		question.addAnswer(answer1);
		question.addAnswer(answer2);
		ListHandler.getMasterQList().add(question);
		
		// Start the activity
		Intent intent = new Intent();
		intent.putExtra("fromFragment", QAViewActivity.HOME_SCREEN_FRAGMENT);
		intent.putExtra("position", 0);
		setActivityIntent(intent);
		QAViewActivity activity = (QAViewActivity) getActivity();
		
		// Check that the number of answers display is correct
		TextView answersCount = (TextView) activity.findViewById(R.id.answersCount);
		assertTrue("answer count display is incorrect", Integer.toString(2) == answersCount.getText().toString());
		
		// Check that the correct question is displayed
		ExpandableListView qListView = (ExpandableListView) activity.findViewById(R.id.questionExpListView);
		SingleQuestionAdapter singleQAdapter = (SingleQuestionAdapter) qListView.getExpandableListAdapter();
		assertTrue(1 == singleQAdapter.getGroupCount());
		TextView questionDisplay = (TextView) qListView.getChildAt(0).findViewById(R.id.questionTextView);
		assertEquals(question.getName(), questionDisplay.getText().toString());
		
		// Check that the answers to the question is shown correctly
		ExpandableListView aListView = (ExpandableListView) activity.findViewById(R.id.answersExpListView);
		AnswerListAdapter aAdapter = (AnswerListAdapter) aListView.getExpandableListAdapter();
		assertTrue(2 == aAdapter.getGroupCount());
		
		View view1 = aAdapter.getGroupView(0, false, null, null);
		TextView answerText1 = (TextView) view1.findViewById(R.id.answerTextView);
		TextView authorUsername1 = (TextView) view1.findViewById(R.id.authorTextView);
		assertEquals(answer2.getName(), answerText1.getText().toString());
		assertEquals(answer2.getAuthor(), authorUsername1.getText().toString());
		
		View view2 = aAdapter.getGroupView(1, false, null, null);
		TextView answerText2 = (TextView) view2.findViewById(R.id.answerTextView);
		TextView authorUsername2 = (TextView) view2.findViewById(R.id.authorTextView);
		assertEquals(answer1.getName(), answerText2.getText().toString());
		assertEquals(answer1.getAuthor(), authorUsername2.getText().toString());
		
		activity.finish();
	}
	

}
