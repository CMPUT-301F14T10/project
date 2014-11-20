package ca.ualberta.cs.queueunderflow.test.views;

import ca.ualberta.cs.queueunderflow.ListHandler;
import ca.ualberta.cs.queueunderflow.R;
import ca.ualberta.cs.queueunderflow.User;
import ca.ualberta.cs.queueunderflow.adapters.QuestionListAdapter;
import ca.ualberta.cs.queueunderflow.controllers.AskAnswerController;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.views.MainActivity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivityTest6 extends ActivityInstrumentationTestCase2<MainActivity> {

	public MainActivityTest6() {
		super(MainActivity.class);
	}
public void testQuestionsAskedDisplay() throws Throwable {
		
		getActivity();
		
		// Set Username
		User user = new User();
		user.setUserName("Me");
		
		runTestOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// Ask a new question in any fragment - HomeScreenFragment
				Intent intent = new Intent();
				intent.putExtra("returnFragment", MainActivity.HOME_SCREEN_FRAGMENT);
				setActivityIntent(intent);
				MainActivity activity = (MainActivity) getActivity();
				AskAnswerController controller = new AskAnswerController(activity);
				controller.askQuestion("This is a question", User.getUserName(), View.INVISIBLE);
			}
		});
		
		// Check that the new question is in Questions Asked Fragment
		Intent intent2 = new Intent();
		intent2.putExtra("returnFragment", MainActivity.MY_QUESTIONS_FRAGMENT);
		setActivityIntent(intent2);
		MainActivity activity = (MainActivity) getActivity();
		
		ListView listView = (ListView) activity.findViewById(R.id.homeListView);
		QuestionListAdapter adapter = (QuestionListAdapter) listView.getAdapter();
		
		// Check that the view for the Question / List Item is correctly displayed
		View view1 = adapter.getView(0, null, null);
		TextView questionText1 = (TextView) view1.findViewById(R.id.questionTextView);
		TextView authorUsername1 = (TextView) view1.findViewById(R.id.authorTextView);
		ImageButton hasPictureIndicator = (ImageButton) view1.findViewById(R.id.hasPictureIcon);
		
		assertEquals("This is a question", questionText1.getText().toString());
		assertEquals("Me", authorUsername1.getText().toString());
		assertEquals(View.INVISIBLE, hasPictureIndicator.getVisibility());
		
		Question question = new Question("This is a question", "Me");
		question.setHasPicture(false);
		
		assertEquals(question, ListHandler.getMasterQList().get(0));
		assertEquals(question, ListHandler.getMyQsList().get(0));
	}
}
