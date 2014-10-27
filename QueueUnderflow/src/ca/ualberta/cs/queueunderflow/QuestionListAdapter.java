package ca.ualberta.cs.queueunderflow;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.InputFilter.LengthFilter;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

// Controller?
public class QuestionListAdapter extends ArrayAdapter<Question> {
	
	private LayoutInflater inflater;
	private int layoutID;
	private ArrayList<Question> questionArray;
	private Activity activity;
	
	public QuestionListAdapter(Activity activity, int layoutID, ArrayList<Question> questionArray) {
		super(activity, layoutID, questionArray);
		
		// this line is taken from http://www.survivingwithandroid.com/2012/10/android-listview-custom-adapter-and.html
		this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		this.activity = activity;
		this.layoutID = layoutID;
		this.questionArray = questionArray;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final View view;
		if (questionArray.get(position).hasPicture()) {
			view = inflater.inflate(R.layout.list_item_question_picture, parent, false);
		}
		else {
			view = inflater.inflate(layoutID, parent, false);
		}
		
		TextView questionDisplay = (TextView) view.findViewById(R.id.questionTextView);
		questionDisplay.setText(questionArray.get(position).getQuestion());
		
		TextView authorDisplay = (TextView) view.findViewById(R.id.authorTextView);
		authorDisplay.setText(questionArray.get(position).getAuthor());
		
		TextView dateDisplay = (TextView) view.findViewById(R.id.dateTextView);
		dateDisplay.setText(DateFormat.format("MMM dd, yyyy", questionArray.get(position).getDate()));
		
		TextView upvoteDisplay = (TextView) view.findViewById(R.id.upvoteDisplay);
		upvoteDisplay.setText(Integer.toString(questionArray.get(position).getUpvotes()));
		
		
		final ImageButton upvoteBtn = (ImageButton) view.findViewById(R.id.upvoteBtn);
		upvoteBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				User user= ListHandler.getUser();
				Question question= questionArray.get(position);
				if (user.alreadyUpvotedQuestion(question)) {
					Toast.makeText(getContext(), "YOU ONLY NEED TO CLICK ONCE", Toast.LENGTH_SHORT).show();
				}
				//d/s/fds/af/as/f/as/fsafsa
				else {
					user.addUpvotedQuestion(questionArray.get(position));
					questionArray.get(position).upvoteQuestion();
					TextView upvoteDisplay = (TextView) view.findViewById(R.id.upvoteDisplay);
					upvoteDisplay.setText(Integer.toString(questionArray.get(position).getUpvotes()));
				}

				
				//upvoteBtn.setEnabled(false);
				}	
			}
		);		
		
		if (questionArray.get(position).hasPicture() == true) {
			ImageButton hasPictureIcon = (ImageButton) view.findViewById(R.id.hasPictureIcon);
			hasPictureIcon.setVisibility(View.VISIBLE);
		}
		
		ImageButton answerBtn = (ImageButton) view.findViewById(R.id.answerBtn);
		answerBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), AddAnAnswerActivity.class);
				//Pass the position of the question to the new activity
				intent.putExtra("question_position",position);
				activity.startActivity(intent);
			}
		});
		
		return view;
	}
}
