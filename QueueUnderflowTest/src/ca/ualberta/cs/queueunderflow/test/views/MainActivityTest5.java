package ca.ualberta.cs.queueunderflow.test.views;

import ca.ualberta.cs.queueunderflow.ListHandler;
import ca.ualberta.cs.queueunderflow.NetworkManager;
import ca.ualberta.cs.queueunderflow.R;
import ca.ualberta.cs.queueunderflow.adapters.QuestionListAdapter;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.views.MainActivity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

//Testing sort by most upvoted QUESTIONS display

public class MainActivityTest5 extends ActivityInstrumentationTestCase2<MainActivity>{

	public MainActivityTest5() {
		super(MainActivity.class);
	}
	
	public void setUp() {
		// Set network connectivity to false, else it'll grab the list from the network
		NetworkManager networkManager = NetworkManager.getInstance();
		networkManager.setOnline(false);
	}
	
	public void testMostUpvotedQuestionsDisplay() throws Throwable {
		final Question question1 = new Question("One Upvote Q", "Me");
		question1.setUpvotes(1);
		final Question question2 = new Question("Two Upvotes Q", "You");
		question2.setUpvotes(2);
		final Question question3 = new Question("Zero Upvotes Q", "Him");	// Default upvote is set to zero for every new question
		
		ListHandler.getMasterQList().add(question1);
		ListHandler.getMasterQList().add(question2);
		ListHandler.getMasterQList().add(question3);
		
		MainActivity activity = (MainActivity) getActivity();
		getInstrumentation().invokeMenuActionSync(activity, R.id.mostUpvotesMenu, 0);	// Perform click on the sortby most upvotes menu item to sort questions
		runTestOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// Start activity
				Intent intent = new Intent();
				intent.putExtra("returnFragment", MainActivity.HOME_SCREEN_FRAGMENT);
				setActivityIntent(intent);
				MainActivity activity = (MainActivity) getActivity();
				
				
				// Get listview & adapter
				ListView listView = (ListView) activity.findViewById(R.id.homeListView);
				QuestionListAdapter adapter = (QuestionListAdapter) listView.getAdapter();
				
				// Check that the questions are displayed in sorted order of most upvotes
				
				// Check that the 1st question from the top is the question2 with 2 (the most) upvotes
				assertEquals(question2, adapter.getItem(0));
				View view1 = listView.getChildAt(0);
				TextView questionText1 = (TextView) view1.findViewById(R.id.questionTextView);
				TextView authorUsername1 = (TextView) view1.findViewById(R.id.authorTextView);
				TextView upvoteDisplay1 = (TextView) view1.findViewById(R.id.upvoteDisplay);
				
				assertEquals("Two Upvotes Q", questionText1.getText().toString());
				assertEquals("You", authorUsername1.getText().toString());
				assertEquals("2", upvoteDisplay1.getText().toString());
				
				// Check that the 2nd question from the top is the question1 with 1 (the 2nd most) upvotes
				assertEquals(question1, adapter.getItem(1));
				View view2 = listView.getChildAt(1);
				TextView questionText2 = (TextView) view2.findViewById(R.id.questionTextView);
				TextView authorUsername2 = (TextView) view2.findViewById(R.id.authorTextView);
				TextView upvoteDisplay2 = (TextView) view2.findViewById(R.id.upvoteDisplay);
				
				assertEquals("One Upvote Q", questionText2.getText().toString());
				assertEquals("Me", authorUsername2.getText().toString());
				assertEquals("1", upvoteDisplay2.getText().toString());
				
				// Check that the 3rd question from the top is the question3 with 0 (the 3rd most / least) upvotes
				assertEquals(question3, adapter.getItem(2));
				View view3 = listView.getChildAt(2);
				TextView questionText3 = (TextView) view3.findViewById(R.id.questionTextView);
				TextView authorUsername3 = (TextView) view3.findViewById(R.id.authorTextView);
				TextView upvoteDisplay3 = (TextView) view3.findViewById(R.id.upvoteDisplay);
				
				assertEquals("Zero Upvotes Q", questionText3.getText().toString());
				assertEquals("Him", authorUsername3.getText().toString());
				assertEquals("0", upvoteDisplay3.getText().toString());
			};
		
		});

		
	}

}
