package ca.ualberta.cs.queueunderflow.adapters;

import java.util.ArrayList;

import ca.ualberta.cs.queueunderflow.Buffer;
import ca.ualberta.cs.queueunderflow.ListHandler;
import ca.ualberta.cs.queueunderflow.LoadSave;
import ca.ualberta.cs.queueunderflow.R;
import ca.ualberta.cs.queueunderflow.User;
import ca.ualberta.cs.queueunderflow.R.id;
import ca.ualberta.cs.queueunderflow.R.layout;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.views.AddAnAnswerActivity;
import ca.ualberta.cs.queueunderflow.views.WriteReplyDialogFragment;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

// TODO: Auto-generated Javadoc
// Controller?
/**
 * The Class QuestionListAdapter.
 */
public class QuestionListAdapter extends ArrayAdapter<Question> {
	
	/** The type question. */
	private static int TYPE_QUESTION = 0;
	
	/** The inflater. */
	private LayoutInflater inflater;
	
	/** The layout id. */
	private int layoutID;
	
	/** The question array. */
	private ArrayList<Question> questionArray;
	
	/** The activity. */
	private Activity activity;
	
	/** The from fragment. */
	private int fromFragment;
	
	/**
	 * Instantiates a new question list adapter.
	 *
	 * @param activity the activity
	 * @param layoutID the layout id
	 * @param questionArray the question array
	 * @param fromFragment the from fragment
	 */
	public QuestionListAdapter(Activity activity, int layoutID, ArrayList<Question> questionArray, int fromFragment) {
		super(activity, layoutID, questionArray);
		
		// this line is taken from http://www.survivingwithandroid.com/2012/10/android-listview-custom-adapter-and.html
		this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		this.activity = activity;
		this.layoutID = layoutID;
		this.questionArray = questionArray;
		this.fromFragment = fromFragment;
	}

	/* (non-Javadoc)
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
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
		questionDisplay.setText(questionArray.get(position).getName());
		
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
                                        boolean isInFavorites = false;
                                        if(ListHandler.getFavsList().getQuestionList().contains(questionArray.get(position))) isInFavorites = true;
				    
					user.addUpvotedQuestion(questionArray.get(position));
					questionArray.get(position).upvoteResponse();
					TextView upvoteDisplay = (TextView) view.findViewById(R.id.upvoteDisplay);
					upvoteDisplay.setText(Integer.toString(questionArray.get(position).getUpvotes()));
					
	                                //Save favorites if applicable
                                        if (isInFavorites)
                                        {
                                            Log.d("test", "Item is in favorites.");
                                            //Mark as unsaved changes.
                                            LoadSave.unsavedChanges = true;
                                        }else{
                                            Log.d("testitem", "Item is NOT in favorites.");
                                        }
				}

				
				//upvoteBtn.setEnabled(false);
				}	
			}
		);		
		
		if (questionArray.get(position).hasPicture() == true) {
			ImageButton hasPictureIcon = (ImageButton) view.findViewById(R.id.hasPictureIcon);
			hasPictureIcon.setVisibility(View.VISIBLE);
			
			ImageView imagePreview = (ImageView) view.findViewById(R.id.imagePreview);
			// TODO set imagePreview to the photo
		}
		
		ImageButton answerBtn = (ImageButton) view.findViewById(R.id.answerBtn);
		answerBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), AddAnAnswerActivity.class);
				//Pass the position of the question to the new activity
				intent.putExtra("fromFragment", fromFragment);
				intent.putExtra("question_position",position);
				activity.startActivity(intent);
			}
		});
		
        ImageButton replyBtn = (ImageButton) view.findViewById(R.id.replyBtn);
        replyBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				// Passing info about which questionList it's from, questionPosition & questionAnswer & the type (we're replying to an Answer) so we know where to add the reply to
				Bundle args = new Bundle();
				args.putInt("fromFragment", fromFragment);
				args.putInt("questionPosition", position);
				args.putInt("type", TYPE_QUESTION);
				
				// Create & display reply dialog + attach arguments
				DialogFragment replyDialog = new WriteReplyDialogFragment();
				replyDialog.setArguments(args);
				replyDialog.show(activity.getFragmentManager(), null);
				
			}
		});
		
		CheckBox favBtn = (CheckBox) view.findViewById(R.id.favBtn);
		favBtn.setChecked(questionArray.get(position).getIsFav());
		favBtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Question question = questionArray.get(position);
				
				if (questionArray == ListHandler.getFavsList().getQuestionList()) {
					Buffer buffer = Buffer.getInstance();	// Buffer hold list of questions to be REMOVED from the ListHandler.getFavsList()
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
				
				//Mark as unsaved changes,
				LoadSave.unsavedChanges = true;
			}
		});
		
		
		CheckBox readingListBtn = (CheckBox) view.findViewById(R.id.offlineBtn);
		readingListBtn.setChecked(questionArray.get(position).getIsInReadingList());
		readingListBtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Question question = questionArray.get(position);
				
				if (questionArray == ListHandler.getReadingList().getQuestionList()) {
					Buffer buffer = Buffer.getInstance();	// Buffer hold list of questions to be REMOVED from the ListHandler.getReadingList()
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
				
				//Mark as unsaved changes.
				LoadSave.unsavedChanges = true;
			}
		});
		return view;
	}
}
