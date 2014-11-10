package ca.ualberta.cs.queueunderflow.test.views;

import ca.ualberta.cs.queueunderflow.ListHandler;
import ca.ualberta.cs.queueunderflow.R;
import ca.ualberta.cs.queueunderflow.adapters.AnswerListAdapter;
import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.views.QAViewActivity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class QAViewActivityTest4 extends ActivityInstrumentationTestCase2<QAViewActivity>{

	public QAViewActivityTest4() {
		super(QAViewActivity.class);
	}
	public void testSeeMostUpvotedAnswersDisplay() throws Throwable {
		Question question = new Question("A question", "Geneva");
		
		final Answer answer1 = new Answer("One Upvote A", "Me");
		answer1.setUpvotes(1);
		final Answer answer2 = new Answer("Two Upvotes A", "You");
		answer2.setUpvotes(2);
		final Answer answer3 = new Answer("Zero Upvotes A", "Him");	// Default upvote is set to zero for every new question
		
		question.addAnswer(answer1);
		question.addAnswer(answer2);
		question.addAnswer(answer3);
		ListHandler.getMasterQList().add(question);
		
		Intent intent = new Intent();
		intent.putExtra("fromFragment", QAViewActivity.HOME_SCREEN_FRAGMENT);
		intent.putExtra("position", 0);
		setActivityIntent(intent);
		QAViewActivity activity = (QAViewActivity) getActivity();
		getInstrumentation().invokeMenuActionSync(activity, R.id.mostUpvotesMenu, 0);	// Perform click on the sortby most upvotes menu item to sort answers
		
		runTestOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// Start activity
				Intent intent = new Intent();
				intent.putExtra("fromFragment", QAViewActivity.HOME_SCREEN_FRAGMENT);
				intent.putExtra("position", 0);
				setActivityIntent(intent);
				QAViewActivity activity = (QAViewActivity) getActivity();
				
				
				// Get listview & adapter
				ExpandableListView listView = (ExpandableListView) activity.findViewById(R.id.answersExpListView);
				AnswerListAdapter adapter = (AnswerListAdapter) listView.getExpandableListAdapter();
				
				// Check that the answers are displayed in sorted order of most upvotes
				
				// Check that the 1st answer from the top is the answer2 with 2 (the most) upvotes
				assertEquals(answer2, adapter.getGroup(0));
				View view1 = listView.getChildAt(0);
				TextView answerText1 = (TextView) view1.findViewById(R.id.answerTextView);
				TextView authorUsername1 = (TextView) view1.findViewById(R.id.authorTextView);
				TextView upvoteDisplay1 = (TextView) view1.findViewById(R.id.upvoteDisplay);
				
				assertEquals("Two Upvotes A", answerText1.getText().toString());
				assertEquals("You", authorUsername1.getText().toString());
				assertEquals("2", upvoteDisplay1.getText().toString());
				
				// Check that the 2nd answer from the top is the answer1 with 1 (the 2nd most) upvotes
				assertEquals(answer1, adapter.getGroup(1));
				View view2 = listView.getChildAt(1);
				TextView answerText2 = (TextView) view2.findViewById(R.id.answerTextView);
				TextView authorUsername2 = (TextView) view2.findViewById(R.id.authorTextView);
				TextView upvoteDisplay2 = (TextView) view2.findViewById(R.id.upvoteDisplay);
				
				assertEquals("One Upvote A", answerText2.getText().toString());
				assertEquals("Me", authorUsername2.getText().toString());
				assertEquals("1", upvoteDisplay2.getText().toString());
				
				// Check that the 3rd answer from the top is the answer3 with 0 (the 3rd most / least) upvotes
				assertEquals(answer3, adapter.getGroup(2));
				View view3 = listView.getChildAt(2);
				TextView answerText3 = (TextView) view3.findViewById(R.id.answerTextView);
				TextView authorUsername3 = (TextView) view3.findViewById(R.id.authorTextView);
				TextView upvoteDisplay3 = (TextView) view3.findViewById(R.id.upvoteDisplay);
				
				assertEquals("Zero Upvotes A", answerText3.getText().toString());
				assertEquals("Him", authorUsername3.getText().toString());
				assertEquals("0", upvoteDisplay3.getText().toString());
			};
		
		});
	}
}
