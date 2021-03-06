package ca.ualberta.cs.queueunderflow.test.views;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import ca.ualberta.cs.queueunderflow.R;
import ca.ualberta.cs.queueunderflow.adapters.QuestionListAdapter;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.singletons.ListHandler;
import ca.ualberta.cs.queueunderflow.singletons.NetworkManager;
import ca.ualberta.cs.queueunderflow.views.MainActivity;

public class MainActivityTest8 extends ActivityInstrumentationTestCase2<MainActivity> {

	public MainActivityTest8() {
		super(MainActivity.class);
	}
	

	public void setUp() {
		// Set network connectivity to false to show favs can be accessed offline
		NetworkManager networkManager = NetworkManager.getInstance();
		networkManager.setOnline(false);
		
		ListHandler.getFavsList().clear();
	}
	
	// Test : Check that a question which is preset to be favorited is in fact in there & visible in the FavoritesFragment view
	public void testFavoritesDisplay1() {
		
		// Setting up the question to be included in the reading list
		Question question1 = new Question("Hello", "Me");
		question1.setIsFav(true);
		ListHandler.getFavsList().add(question1);
		
		// Start the activity
		Intent intent1 = new Intent();
		intent1.putExtra("returnFragment", MainActivity.FAVORITES_FRAGMENT);
		setActivityIntent(intent1);
		MainActivity activity1 = (MainActivity) getActivity();
		
		// Get the listview & adapter
		ListView listView1 = (ListView) activity1.findViewById(R.id.homeListView);
		QuestionListAdapter adapter1 = (QuestionListAdapter) listView1.getAdapter();
		
		// Check that the question is visible in the FavoritesFragment
		assertEquals(question1, adapter1.getItem(0));
		
		View view1 = listView1.getChildAt(0);
		TextView questionText = (TextView) view1.findViewById(R.id.questionTextView);
		TextView authorUsername = (TextView) view1.findViewById(R.id.authorTextView);
		TextView upvoteDisplay = (TextView) view1.findViewById(R.id.upvoteDisplay);
		CheckBox favBox1 = (CheckBox) view1.findViewById(R.id.favBtn);
		
		assertEquals("Hello", questionText.getText().toString());
		assertEquals("Me", authorUsername.getText().toString());
		assertEquals("0", upvoteDisplay.getText().toString());
		assertEquals(true, favBox1.isChecked());
		
		
	}
	
	// Test : Favorite a question by clicking on the button in the listview & checking that its in the Favorites Fragment view
	public void testFavoritesDisplay2() throws Throwable {
		ListHandler.getMasterQList().getQuestionList().clear();
		ListHandler.getFavsList().getQuestionList().clear();
		
		// Reading List is set to not check by default for a new question
		Question question1 = new Question("Hello", "Me");
		ListHandler.getMasterQList().add(question1);
		
		getActivity();
		
		runTestOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// Start the activity
				Intent intent = new Intent();
				intent.putExtra("returnFragment", MainActivity.HOME_SCREEN_FRAGMENT);
				setActivityIntent(intent);
				MainActivity activity = (MainActivity) getActivity();
				
				// Get the listview & adapter
				ListView listView = (ListView) activity.findViewById(R.id.homeListView);
				QuestionListAdapter adapter = (QuestionListAdapter) listView.getAdapter();
				
				// Confirm that the question is not currently marked as favorited
				View view = listView.getChildAt(0);
				CheckBox favBox = (CheckBox) view.findViewById(R.id.favBtn);
				assertEquals(false, favBox.isChecked());
				assertTrue(ListHandler.getFavsList().size() == 0);
				
				favBox.performClick();	// CLICK TO ADD TO FAVORITES
				
				// Check that a question was favorited when the button was clicked
				assertTrue(ListHandler.getFavsList().size() == 1);
			}
		});
		
		// Start the activity
		Intent intent1 = new Intent();
		intent1.putExtra("returnFragment", MainActivity.FAVORITES_FRAGMENT);
		setActivityIntent(intent1);
		MainActivity activity1 = (MainActivity) getActivity();
		
		ListView listView1 = (ListView) activity1.findViewById(R.id.homeListView);
		QuestionListAdapter adapter1 = (QuestionListAdapter) listView1.getAdapter();
		assertEquals(1, adapter1.getCount());
		assertEquals(question1, adapter1.getItem(0));
		
		// Check that the question is correctly displayed & is visible in the favorites fragment view
		View view1 = listView1.getChildAt(0);
		TextView questionText = (TextView) view1.findViewById(R.id.questionTextView);
		TextView authorUsername = (TextView) view1.findViewById(R.id.authorTextView);
		TextView upvoteDisplay = (TextView) view1.findViewById(R.id.upvoteDisplay);
		CheckBox favBox1 = (CheckBox) view1.findViewById(R.id.favBtn);
		
		assertEquals("Hello", questionText.getText().toString());
		assertEquals("Me", authorUsername.getText().toString());
		assertEquals("0", upvoteDisplay.getText().toString());
		assertEquals(true, favBox1.isChecked());
		
	}

}
