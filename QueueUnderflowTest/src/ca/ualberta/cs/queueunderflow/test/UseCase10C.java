package ca.ualberta.cs.queueunderflow.test;

import java.util.Calendar;
import java.util.Date;

import ca.ualberta.cs.queueunderflow.ListHandler;
import ca.ualberta.cs.queueunderflow.R;
import ca.ualberta.cs.queueunderflow.adapters.QuestionListAdapter;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.views.MainActivity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

//Use CASE 10 (incorporates user stories 10) - Testing sort by LEAST RECENT display
public class UseCase10C extends ActivityInstrumentationTestCase2<MainActivity> {
	
	Question question1;
	Question question2;
	Question question3;
	
	public UseCase10C() {
		super(MainActivity.class);
	}
	
	public void setUp() {
		ListHandler.getMasterQList().getQuestionList().clear();
		
		String questionName= "Oldest question";
		String author= "long time ago";
		question1= new Question(questionName,author);
		Calendar cal = Calendar.getInstance();
		cal.setTime(question1.getDate());
		//Make the date 5 seconds behind current date
		cal.add(Calendar.SECOND,-5);
		Date question1date = cal.getTime();
		question1.setDate(question1date);
		
		String questionName2= "Middle date question";
		String author2= "middle";
		question2= new Question(questionName2,author2);
		Calendar cal2 = Calendar.getInstance();  
		cal2.setTime(question2.getDate());
		//Make the date 3 seconds behind current date
		cal2.add(Calendar.SECOND,-3);
		Date question2date = cal2.getTime();
		question2.setDate(question2date);
		
		String questionName3= "Freshest question";
		String author3 = "Now";
		question3 = new Question(questionName3,author3);
		assertTrue(question3 != null);
		//Set the upvotes each question has
		question1.setUpvotes(1);
		question2.setUpvotes(10);
		question3.setUpvotes(5);
		
		ListHandler.getMasterQList().add(question2);
		ListHandler.getMasterQList().add(question1);
		ListHandler.getMasterQList().add(question3);

	}
	
	public void testSortByLeastRecentDisplay() throws Throwable {
		getInstrumentation().invokeMenuActionSync(getActivity(), R.id.leastRecentMenu, 0);	// Perform click on the sortby least recent menu item to sort questions
		
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
				assertEquals(3, adapter.getCount());
				
				// Check that the questions are displayed in sorted order of least recent
				// Check that the 1st question from the top is the question1 the least recent
				assertEquals(question1, adapter.getItem(0));
				View view1 = listView.getChildAt(0);
				TextView questionText1 = (TextView) view1.findViewById(R.id.questionTextView);
				TextView authorUsername1 = (TextView) view1.findViewById(R.id.authorTextView);
				TextView dateDisplay1 = (TextView) view1.findViewById(R.id.dateTextView);
				
				assertEquals(question1.getQuestion(), questionText1.getText().toString());
				assertEquals(question1.getAuthor(), authorUsername1.getText().toString());
				assertEquals(DateFormat.format("MMM dd, yyyy", question1.getDate()), dateDisplay1.getText().toString());
				
				// Check that the 2nd question from the top is the question2
				assertEquals(question2, adapter.getItem(1));
				View view2 = listView.getChildAt(1);
				TextView questionText2 = (TextView) view2.findViewById(R.id.questionTextView);
				TextView authorUsername2 = (TextView) view2.findViewById(R.id.authorTextView);
				TextView dateDisplay2 = (TextView) view2.findViewById(R.id.dateTextView);
				
				assertEquals(question2.getQuestion(), questionText2.getText().toString());
				assertEquals(question2.getAuthor(), authorUsername2.getText().toString());
				assertEquals(DateFormat.format("MMM dd, yyyy", question2.getDate()), dateDisplay2.getText().toString());
				
				// Check that the 3rd question from the top is the question3 with 0 (the 3rd least / most) recent
				assertEquals(question3, adapter.getItem(2));
				View view3 = listView.getChildAt(2);
				TextView questionText3 = (TextView) view3.findViewById(R.id.questionTextView);
				TextView authorUsername3 = (TextView) view3.findViewById(R.id.authorTextView);
				TextView dateDisplay3 = (TextView) view3.findViewById(R.id.dateTextView);
				
				assertEquals(question3.getQuestion(), questionText3.getText().toString());
				assertEquals(question3.getAuthor(), authorUsername3.getText().toString());
				assertEquals(DateFormat.format("MMM dd, yyyy", question3.getDate()), dateDisplay3.getText().toString());
			}
		});
		
	}
}
