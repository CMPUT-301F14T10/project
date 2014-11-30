package ca.ualberta.cs.queueunderflow.test.views;

import java.util.List;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import ca.ualberta.cs.queueunderflow.ListHandler;
import ca.ualberta.cs.queueunderflow.NetworkManager;
import ca.ualberta.cs.queueunderflow.R;
import ca.ualberta.cs.queueunderflow.adapters.QuestionListAdapter;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.QuestionList;
import ca.ualberta.cs.queueunderflow.views.MainActivity;

public class MainActivityTest1 extends ActivityInstrumentationTestCase2<MainActivity>{

	public MainActivityTest1() {
		super(MainActivity.class);
	}
	
	public void setUp() {
		// Set network connectivity to false, else it'll grab the list from the network
		NetworkManager networkManager = NetworkManager.getInstance();
		networkManager.setOnline(false);
		
		ListHandler.getMasterQList().clear();
	}
	
	public void testBrowseQuestionsDisplay() {
		Question question1 = new Question("First Q", "me");
		Question question2 = new Question("Second Q", "you");
		Question question3 = new Question("Third Q", "him");
		
		QuestionList questionList = ListHandler.getMasterQList();
		questionList.add(question1);
		questionList.add(question2);
		questionList.add(question3);
		
		// Start activity & get the listview & adapter
		MainActivity activity = getActivity();
		ListView listView = (ListView) activity.findViewById(R.id.homeListView);
		QuestionListAdapter adapter = (QuestionListAdapter) listView.getAdapter();
		
		// Check that the number if items in the adapter is correct
		assertEquals(3, adapter.getCount());
		
		// Check that the view for each Question / List Item is correctly displayed
		View view1 = adapter.getView(0, null, null);
		TextView questionText1 = (TextView) view1.findViewById(R.id.questionTextView);
		TextView authorUsername1 = (TextView) view1.findViewById(R.id.authorTextView);
		assertEquals(question3.getName(), questionText1.getText().toString());
		assertEquals(question3.getAuthor(), authorUsername1.getText().toString());
		
		View view2 = adapter.getView(1, null, null);
		TextView questionText2 = (TextView) view2.findViewById(R.id.questionTextView);
		TextView authorUsername2 = (TextView) view2.findViewById(R.id.authorTextView);
		assertEquals(question2.getName(), questionText2.getText().toString());
		assertEquals(question2.getAuthor(), authorUsername2.getText().toString());
		
		View view3 = adapter.getView(2, null, null);
		TextView questionText3 = (TextView) view3.findViewById(R.id.questionTextView);
		TextView authorUsername3 = (TextView) view3.findViewById(R.id.authorTextView);
		assertEquals(question1.getName(), questionText3.getText().toString());
		assertEquals(question1.getAuthor(), authorUsername3.getText().toString());

	}

}
