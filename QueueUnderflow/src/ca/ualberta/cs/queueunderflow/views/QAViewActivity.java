package ca.ualberta.cs.queueunderflow.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.TextView;
import android.widget.Toast;
import ca.ualberta.cs.queueunderflow.ListHandler;
import ca.ualberta.cs.queueunderflow.R;
import ca.ualberta.cs.queueunderflow.TView;
import ca.ualberta.cs.queueunderflow.adapters.AnswerListAdapter;
import ca.ualberta.cs.queueunderflow.adapters.SingleQuestionAdapter;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.QuestionList;

/**
 * The Class QAViewActivity.
 * Displays a single question with it's replies, answers, it's replies.
 * @author group 10
 * @version 0.5
 */
public class QAViewActivity extends Activity implements TView<QuestionList>{

	/** The Constant HOME_SCREEN_FRAGMENT. */
	public static final int HOME_SCREEN_FRAGMENT = 1;
	
	/** The Constant FAVORITES_FRAGMENT. */
	public static final int FAVORITES_FRAGMENT = 2;
	
	/** The Constant MY_QUESTIONS_FRAGMENT. */
	public static final int MY_QUESTIONS_FRAGMENT = 3;
	
	/** The Constant READING_LIST_FRAGMENT. */
	public static final int READING_LIST_FRAGMENT = 4;

	/** The single q adapter. */
	SingleQuestionAdapter singleQAdapter;
	
	/** The adapter. */
	AnswerListAdapter adapter;

	/** The from fragment. */
	private int fromFragment;
	
	/** The question. */
	private Question question;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qaview);
		
		// Display the up caret to go back to the MainActivity
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		// Retrieve the position of the question selected & the fragment where it was selected from
		// The fragment where it was selected from informs us of which list to use from the ListHandler to inflate the view
		
		Intent intent = getIntent();
		final int position = intent.getIntExtra("position", -1); 				// -1 is the default value if nothing was retrieved
		fromFragment = intent.getIntExtra("fromFragment", -1);
		
		// If position or fragment was not successfully retrieved, finish & go back
		if ((position == -1) || (fromFragment == -1)) {
			finish();
		}
		
		// Put some of this in a controller - BELOW
		
		// Determine which fragment we came from / where the question was selected
		QuestionList questionList = null;
		switch (fromFragment) {
		case (HOME_SCREEN_FRAGMENT):
			questionList = ListHandler.getMasterQList();
			questionList.addView(this);
			break;
		case (FAVORITES_FRAGMENT):
			questionList = ListHandler.getFavsList();
			questionList.addView(this);
			break;
		case (READING_LIST_FRAGMENT):
			questionList = ListHandler.getReadingList();
			questionList.addView(this);
			break;
		case (MY_QUESTIONS_FRAGMENT):
			questionList = ListHandler.getMyQsList();
			questionList.addView(this);
			break;
		}
		
		// Get the question selected
		question = questionList.get(position);
		
		// Set up the display
		ExpandableListView singleQuestionExpListView = (ExpandableListView) findViewById(R.id.questionExpListView);
		singleQAdapter = new SingleQuestionAdapter(this, questionList.getQuestionList() , fromFragment, position);
		singleQuestionExpListView.setAdapter(singleQAdapter);
		
		// Display a "no replies" toast if question is clicked on but has no replies
		singleQuestionExpListView.setOnGroupExpandListener(new OnGroupExpandListener() {
			
			@Override
			public void onGroupExpand(int groupPosition) {
				if (question.getSizeReplies() == 0) {
					Toast.makeText(getApplicationContext(), "This question has no replies", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
        // This hides that expand arrow on the question item
        singleQuestionExpListView.setGroupIndicator(null);
        
        
        Button addAnswer = (Button) findViewById(R.id.addAnAnswerBtn);
        addAnswer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), AddAnAnswerActivity.class);
				//Pass the position of the question to the new activity
				intent.putExtra("question_position", position);
				intent.putExtra("fromFragment", fromFragment);
				
				startActivity(intent);

			}
		});
        
        TextView answersCount = (TextView) findViewById(R.id.answersCount);
        answersCount.setText(Integer.toString(question.getAnswerListSize()));
        
        ExpandableListView answersExpListView = (ExpandableListView) findViewById(R.id.answersExpListView);
        adapter = new AnswerListAdapter(this, question.getAnswerList().getAnswerList(), fromFragment, position);
        answersExpListView.setAdapter(adapter);
        
     // Display a "no replies" toast if answer is clicked on but has no replies
        answersExpListView.setOnGroupExpandListener(new OnGroupExpandListener() {
			
			@Override
			public void onGroupExpand(int groupPosition) {
				if (question.getAnswerList().getAnswer(groupPosition).getSizeReplies() == 0) {
					Toast.makeText(getApplicationContext(), "This answer has no replies", Toast.LENGTH_SHORT).show();
				}
			}
		});
        
        // This hides that expand arrow on the answer item
        answersExpListView.setGroupIndicator(null);
	}


	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.qaview, menu);
		return true;
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		// Go back to MainActivity when the up caret is clicked
		if (id == android.R.id.home) {
			Intent intent = NavUtils.getParentActivityIntent(this);
			// This is so we go back to the proper fragment that we came from in the main activity
			intent.putExtra("returnFragment", fromFragment);
			NavUtils.navigateUpTo(this, intent);
			return true;
		}
		
		// Put in controller?
        switch(id) {
        case R.id.mostRecentMenu:
        	question.getAnswerList().sortBy("most recent");
        	adapter.notifyDataSetChanged();
        	return true;
        case R.id.leastRecentMenu:
        	question.getAnswerList().sortBy("least recent");
        	adapter.notifyDataSetChanged();
        	return true;
        case R.id.mostUpvotesMenu:
        	question.getAnswerList().sortBy("most upvotes");
        	adapter.notifyDataSetChanged();
        	return true;
        case R.id.hasPictureMenu:
        	question.getAnswerList().sortBy("has pictures");
        	adapter.notifyDataSetChanged();
        	return true;
        case R.id.noPictureMenu:
        	question.getAnswerList().sortBy("no pictures");
        	adapter.notifyDataSetChanged();
        	return true;
        }
		return super.onOptionsItemSelected(item);
	}

	// Not complete yet
	/* (non-Javadoc)
	 * @see ca.ualberta.cs.queueunderflow.TView#update(java.lang.Object)
	 */
	@Override
	public void update(QuestionList model) {
		singleQAdapter.notifyDataSetChanged();
		adapter.notifyDataSetChanged();
		
        TextView answersCount = (TextView) findViewById(R.id.answersCount);
        answersCount.setText(Integer.toString(question.getAnswerListSize()));
	}
}
