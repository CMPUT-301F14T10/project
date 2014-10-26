package ca.ualberta.cs.queueunderflow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class QAViewActivity extends Activity implements TView<QuestionList>{

	public static final int HOME_SCREEN_FRAGMENT = 1;
	public static final int FAVORITES_FRAGMENT = 2;
	public static final int READING_LIST_FRAGMENT = 3;
	public static final int MY_QUESTIONS_FRAGMENT = 4;
	AnswerListAdapter adapter;

	
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
		int fromFragment = intent.getIntExtra("fromFragment", -1);
		
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
		final Question question = questionList.get(position);
		
		// Set up the display
		TextView questionDisplay = (TextView) findViewById(R.id.questionTextView);
		questionDisplay.setText(question.getQuestion());
		
		TextView authorDisplay = (TextView) findViewById(R.id.authorTextView);
		authorDisplay.setText(questionList.get(0).getAuthor());
		
		TextView dateDisplay = (TextView) findViewById(R.id.dateTextView);
		dateDisplay.setText(DateFormat.format("MMM dd, yyyy", question.getDate()));
		
		final TextView upvoteDisplay = (TextView) findViewById(R.id.upvoteDisplay);
		upvoteDisplay.setText(Integer.toString(question.getUpvotes()));
		
		ImageButton upvoteBtn = (ImageButton) findViewById(R.id.upvoteBtn);
		upvoteBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				question.upvoteQuestion();
				upvoteDisplay.setText(Integer.toString(question.getUpvotes()));
			}
		});
		
		if (question.hasPicture() == true) {
			ImageButton hasPictureIcon = (ImageButton) findViewById(R.id.hasPictureIcon);
			hasPictureIcon.setVisibility(0);
		}
		
        Button addAnswer = (Button) findViewById(R.id.addAnAnswerBtn);
        addAnswer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), AddAnAnswerActivity.class);
				//Pass the position of the question to the new activity
				intent.putExtra("question_position",position );
				startActivity(intent);

			}
		});
        
        ExpandableListView answersExpListView = (ExpandableListView) findViewById(R.id.answersExpListView);
        adapter = new AnswerListAdapter(this, R.layout.list_item_answer, R.layout.exp_list_item_reply, question.getAnswerList().getAnswerList());
        answersExpListView.setAdapter(adapter);
        
        // This hides that expand arrow on the answer item
        answersExpListView.setGroupIndicator(null);
	}


	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.qaview, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		// Go back to MainActivity when the up caret is clicked
		if (id == android.R.id.home) {
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	// Not complete yet
	@Override
	public void update(QuestionList model) {
		ExpandableListView answersExpListView = (ExpandableListView) findViewById(R.id.answersExpListView);
		adapter.notifyDataSetChanged();
	}
}