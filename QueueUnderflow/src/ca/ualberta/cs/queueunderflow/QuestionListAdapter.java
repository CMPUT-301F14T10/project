package ca.ualberta.cs.queueunderflow;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
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
					Toast.makeText(getContext(), "Question was already upvoted", Toast.LENGTH_SHORT).show();
				}
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
		
		CheckBox favBtn = (CheckBox) view.findViewById(R.id.favBtn);
		favBtn.setChecked(questionArray.get(position).getIsFav());
		favBtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Question question = questionArray.get(position);
				
				if (questionArray == ListHandler.getFavsList().getQuestionList()) {
					Buffer buffer = new Buffer();	// Buffer hold list of questions to be REMOVED from the ListHandler.getFavsList()
					if (isChecked == false) {	// unfavorited a question from the FavFragment
						buffer.addToFavBuffer(position);
					}
					else {	// re-favorited a question after unfavoriting from the FavFragment
						buffer.removeFromFavBuffer(position);
					}
				}
				
				else if (isChecked == true) {
					if (!ListHandler.getFavsList().getQuestionList().contains(question)) {
						ListHandler.getFavsList().add(question);
					}
				}
				else if (isChecked == false) {
					ListHandler.getFavsList().remove(question);
				}
				
				questionArray.get(position).setIsFav(isChecked);
			}
		});
		
		
		CheckBox readingListBtn = (CheckBox) view.findViewById(R.id.offlineBtn);
		readingListBtn.setChecked(questionArray.get(position).getIsInReadingList());
		readingListBtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Question question = questionArray.get(position);
				
				if (questionArray == ListHandler.getReadingList().getQuestionList()) {
					Buffer buffer = new Buffer();	// Buffer hold list of questions to be REMOVED from the ListHandler.getReadingList()
					if (isChecked == false) {	// un-marking a question from the ReadingListFragment
						buffer.addToReadingListBuffer(position);
					}
					else {	// re-marking for offline reading after un-marking it from the ReadingListFragment
						buffer.removeFromReadingListBuffer(position);
					}
				}
				
				else if (isChecked == true) {
					if (!ListHandler.getReadingList().getQuestionList().contains(question)) {
						ListHandler.getReadingList().add(question);
					}
				}
				else if (isChecked == false) {
					ListHandler.getReadingList().remove(question);
				}
				
				questionArray.get(position).setIsInReadingList(isChecked);
			}
		});
		return view;
	}
}
