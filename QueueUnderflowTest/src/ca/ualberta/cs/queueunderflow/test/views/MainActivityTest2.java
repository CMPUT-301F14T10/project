package ca.ualberta.cs.queueunderflow.test.views;

import ca.ualberta.cs.queueunderflow.R;
import ca.ualberta.cs.queueunderflow.adapters.QuestionListAdapter;
import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.singletons.ListHandler;
import ca.ualberta.cs.queueunderflow.views.MainActivity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivityTest2 extends ActivityInstrumentationTestCase2<MainActivity> {
	/* This test upvoting a question in the MainActivity/SuperFragment  - the display with questions only */
	
	Question question;
	Answer answer1;
	Answer answer2;
	Question anotherQuestion;
	
	public MainActivityTest2(Class<MainActivity> activityClass) {
		super(activityClass);
	}
	public void setUp() {
		ListHandler.getMasterQList().getQuestionList().clear();
		
		// new questions & answers always start with an upvote of 0
		question = new Question("Question", "Me");
		answer1 = new Answer("Answer #1", "You");
		answer2 = new Answer("Answer #2", "Him");
		question.addAnswer(answer1);
		question.addAnswer(answer2);
		
		anotherQuestion = new Question("Another question", "You");
		
		ListHandler.getMasterQList().add(question);
		ListHandler.getMasterQList().add(anotherQuestion);
	}
	
	public void testUpvoteDisplay() throws Throwable {
		getActivity();
		
		runTestOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				Intent intent = new Intent();
				intent.putExtra("returnFragment", MainActivity.HOME_SCREEN_FRAGMENT);
				setActivityIntent(intent);
				MainActivity activity = (MainActivity) getActivity();
				
				// Get the listview & adapter
				ListView listView = (ListView) activity.findViewById(R.id.homeListView);
				QuestionListAdapter adapter = (QuestionListAdapter) listView.getAdapter();
				
				// Check that the number if items in the adapter is correct
				assertTrue(adapter.getCount() == 2);
				
				// Get layout for the 1st question from the top in the listview
				View view = adapter.getView(0, null, null);
				assertEquals(anotherQuestion,adapter.getItem(0));
				
				// Check that the upvote is initially at 0
				TextView upvoteCount = (TextView) view.findViewById(R.id.upvoteDisplay);
				assertEquals(Integer.toString(0), upvoteCount.getText().toString());
				
				// Perform a click which should increase the upvote by 1
				ImageButton upvoteBtn = (ImageButton) view.findViewById(R.id.upvoteBtn);
				upvoteBtn.performClick();
				
				// Check that the upvote has increased by 1
				assertEquals(Integer.toString(1), upvoteCount.getText().toString());
				
				// Check that the other question's upvote has not changed - ensuring that we're upvoting the correct question
				View view1 = adapter.getView(1, null, null);
				assertEquals(question,adapter.getItem(1));
				
				// Check that the upvote is still at 0
				TextView upvoteCount1 = (TextView) view1.findViewById(R.id.upvoteDisplay);
				assertEquals(Integer.toString(0), upvoteCount1.getText().toString());
				
				activity.finish();
			}
		});

	}
	
	public void tearDown() {
		ListHandler.getMasterQList().getQuestionList().clear();
	}

}
