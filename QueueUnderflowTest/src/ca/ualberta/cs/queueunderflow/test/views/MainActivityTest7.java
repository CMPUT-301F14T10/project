package ca.ualberta.cs.queueunderflow.test.views;

import ca.ualberta.cs.queueunderflow.ListHandler;
import ca.ualberta.cs.queueunderflow.R;
import ca.ualberta.cs.queueunderflow.adapters.QuestionListAdapter;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.views.MainActivity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivityTest7 extends ActivityInstrumentationTestCase2<MainActivity> {

	public MainActivityTest7() {
		super(MainActivity.class);
	}
	
	// Test : Check that a question which is preset to be in the reading list is in fact in there & visible in the ReadingList Fragment view
		public void testReadingListDisplay1() {
			
			// Setting up the question to be included in the reading list
			Question question1 = new Question("Hello", "Me");
			question1.setIsInReadingList(true);
			ListHandler.getReadingList().add(question1);
			
			// Start the activity
			Intent intent1 = new Intent();
			intent1.putExtra("returnFragment", MainActivity.READING_LIST_FRAGMENT);
			setActivityIntent(intent1);
			MainActivity activity1 = (MainActivity) getActivity();
			
			// Get the listview & adapter
			ListView listView1 = (ListView) activity1.findViewById(R.id.homeListView);
			QuestionListAdapter adapter1 = (QuestionListAdapter) listView1.getAdapter();
			
			// Check that the question is visible in the ReadingList Fragment
			assertEquals(question1, adapter1.getItem(0));
			
			View view1 = listView1.getChildAt(0);
			TextView questionText = (TextView) view1.findViewById(R.id.questionTextView);
			TextView authorUsername = (TextView) view1.findViewById(R.id.authorTextView);
			TextView upvoteDisplay = (TextView) view1.findViewById(R.id.upvoteDisplay);
			CheckBox readingListBox1 = (CheckBox) view1.findViewById(R.id.offlineBtn);
			
			assertEquals("Hello", questionText.getText().toString());
			assertEquals("Me", authorUsername.getText().toString());
			assertEquals("0", upvoteDisplay.getText().toString());
			assertEquals(true, readingListBox1.isChecked());
			
			
		}
		
		// Test : Add a question to the reading list by clicking on the button in the listview & checking that its in the ReadingList Fragment view
		public void testReadingListDisplay2() throws Throwable {
			ListHandler.getMasterQList().getQuestionList().clear();
			ListHandler.getReadingList().getQuestionList().clear();
			
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
					
					// Confirm that the question is not currently marked as on the reading list
					View view = listView.getChildAt(0);
					CheckBox readingListBox = (CheckBox) view.findViewById(R.id.offlineBtn);
					assertEquals(false, readingListBox.isChecked());
					assertTrue(ListHandler.getReadingList().size() == 0);
					
					readingListBox.performClick();	// CLICK TO ADD TO READING LIST
					
					// Check that a question was added to the reading list when the button was clicked
					assertTrue(ListHandler.getReadingList().size() == 1);
				}
			});
			
			// Start the activity
			Intent intent1 = new Intent();
			intent1.putExtra("returnFragment", MainActivity.READING_LIST_FRAGMENT);
			setActivityIntent(intent1);
			MainActivity activity1 = (MainActivity) getActivity();
			
			ListView listView1 = (ListView) activity1.findViewById(R.id.homeListView);
			QuestionListAdapter adapter1 = (QuestionListAdapter) listView1.getAdapter();
			assertEquals(1, adapter1.getCount());
			assertEquals(question1, adapter1.getItem(0));
			
			// Check that the question is correctly displayed & is visible in the readinglist fragment view
			View view1 = listView1.getChildAt(0);
			TextView questionText = (TextView) view1.findViewById(R.id.questionTextView);
			TextView authorUsername = (TextView) view1.findViewById(R.id.authorTextView);
			TextView upvoteDisplay = (TextView) view1.findViewById(R.id.upvoteDisplay);
			CheckBox readingListBox1 = (CheckBox) view1.findViewById(R.id.offlineBtn);
			
			assertEquals("Hello", questionText.getText().toString());
			assertEquals("Me", authorUsername.getText().toString());
			assertEquals("0", upvoteDisplay.getText().toString());
			assertEquals(true, readingListBox1.isChecked());
			
		}

}
