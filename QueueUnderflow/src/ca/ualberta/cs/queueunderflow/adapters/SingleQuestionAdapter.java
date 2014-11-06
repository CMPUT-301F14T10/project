package ca.ualberta.cs.queueunderflow.adapters;

import java.util.ArrayList;

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
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import ca.ualberta.cs.queueunderflow.ListHandler;
import ca.ualberta.cs.queueunderflow.LoadSave;
import ca.ualberta.cs.queueunderflow.R;
import ca.ualberta.cs.queueunderflow.User;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.Reply;
import ca.ualberta.cs.queueunderflow.views.AddAnAnswerActivity;
import ca.ualberta.cs.queueunderflow.views.WriteReplyDialogFragment;

// TODO: Auto-generated Javadoc
/**
 * The Class SingleQuestionAdapter.
 */
public class SingleQuestionAdapter extends BaseExpandableListAdapter {
	
	/** The type question. */
	private static int TYPE_QUESTION = 0;
	
    /** The activity. */
    private Activity activity;
    
    /** The group layout id. */
    private int groupLayoutID;
    
    /** The child layout id. */
    private int childLayoutID;
    
    /** The single question array. */
    private ArrayList<Question> singleQuestionArray;
    
    /** The from fragment. */
    private int fromFragment;
    
    /** The question position. */
    private int questionPosition;
    
    /**
     * Instantiates a new single question adapter.
     *
     * @param activity the activity
     * @param questionArray the question array
     * @param fromFragment the from fragment
     * @param questionPosition the question position
     */
    public SingleQuestionAdapter(Activity activity, ArrayList<Question> questionArray, int fromFragment, int questionPosition) {
        super();
        this.activity = activity;
        this.groupLayoutID = R.layout.list_item_question;
        this.childLayoutID = R.layout.exp_list_item_reply;
		singleQuestionArray = new ArrayList<Question>(1);
		singleQuestionArray.add(questionArray.get(questionPosition));
        this.fromFragment = fromFragment;
        this.questionPosition = questionPosition;
    }

    //grabs the reply from the child of the group.
    /* (non-Javadoc)
     * @see android.widget.ExpandableListAdapter#getChild(int, int)
     */
    @Override
    public Reply getChild(int groupPosition, int childPosition) {
        return singleQuestionArray.get(groupPosition).getReplyAt(childPosition);
    }
 
    //grabs ChildID. Still needs to be done.
    /* (non-Javadoc)
     * @see android.widget.ExpandableListAdapter#getChildId(int, int)
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return 0;
    }
 
    /* (non-Javadoc)
     * @see android.widget.ExpandableListAdapter#getChildView(int, int, boolean, android.view.View, android.view.ViewGroup)
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(childLayoutID, parent, false);
        
         
        TextView replyDisplay = (TextView) view.findViewById(R.id.replyTextView);
        replyDisplay.setText(singleQuestionArray.get(groupPosition).getReplyAt(childPosition).getReply());
         
        TextView authorDisplay = (TextView) view.findViewById(R.id.authorTextView);
        authorDisplay.setText(singleQuestionArray.get(groupPosition).getReplyAt(childPosition).getAuthor());
         
        // Should Replies have a date displayed? Or is it not necessary?
        //TextView dateDisplay = (TextView) view.findViewById(R.id.dateTextView);
        //dateDisplay.setText(DateFormat.format("MMM dd, yyyy", singleQuestionArray.get(groupPosition).getReplyAt(childPosition).getDate()));
         
        return view;
    }
 
    /* (non-Javadoc)
     * @see android.widget.ExpandableListAdapter#getChildrenCount(int)
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return singleQuestionArray.get(groupPosition).getSizeReplies();
    }
 
    /* (non-Javadoc)
     * @see android.widget.ExpandableListAdapter#getGroup(int)
     */
    @Override
    public Question getGroup(int groupPosition) {
        return singleQuestionArray.get(groupPosition);
    }
 
    /* (non-Javadoc)
     * @see android.widget.ExpandableListAdapter#getGroupCount()
     */
    @Override
    public int getGroupCount() {
        return singleQuestionArray.size();
    }
 
    /* (non-Javadoc)
     * @see android.widget.ExpandableListAdapter#getGroupId(int)
     */
    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }
 
    /* (non-Javadoc)
     * @see android.widget.ExpandableListAdapter#getGroupView(int, boolean, android.view.View, android.view.ViewGroup)
     */
    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
            View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view;
        if (singleQuestionArray.get(groupPosition).hasPicture()) {
            view = inflater.inflate(R.layout.list_item_question_picture, parent, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.imagePreview);
            imageView.setVisibility(View.VISIBLE);
        }
        else {
            view = inflater.inflate(groupLayoutID, parent, false);
        }
         
        TextView questionDisplay = (TextView) view.findViewById(R.id.questionTextView);
        questionDisplay.setText(singleQuestionArray.get(groupPosition).getQuestion());
         
        TextView authorDisplay = (TextView) view.findViewById(R.id.authorTextView);
        authorDisplay.setText(singleQuestionArray.get(groupPosition).getAuthor());
         
        TextView dateDisplay = (TextView) view.findViewById(R.id.dateTextView);
        dateDisplay.setText(DateFormat.format("MMM dd, yyyy", singleQuestionArray.get(groupPosition).getDate()));
         
        TextView upvoteDisplay = (TextView) view.findViewById(R.id.upvoteDisplay);
        upvoteDisplay.setText(Integer.toString(singleQuestionArray.get(groupPosition).getUpvotes()));
         
        ImageButton upvoteBtn = (ImageButton) view.findViewById(R.id.upvoteBtn);
        upvoteBtn.setOnClickListener(new OnClickListener() {
             
            @Override
            public void onClick(View v) {
            	User user= ListHandler.getUser();
                //singleQuestionArray.get(groupPosition).upvotequestion();
            	Question question= singleQuestionArray.get(groupPosition);
    			if (user.alreadyUpvotedQuestion(question)) {
					Toast.makeText(activity.getApplicationContext(), "Question was already upvoted", Toast.LENGTH_SHORT).show();			// This should be in the model?
				}
				else {
				        boolean isInFavorites = false;
				        if(ListHandler.getFavsList().getQuestionList().contains(singleQuestionArray.get(groupPosition))) isInFavorites = true;
				    
					user.addUpvotedQuestion(singleQuestionArray.get(groupPosition));
					singleQuestionArray.get(groupPosition).upvoteQuestion();
					TextView upvoteDisplay = (TextView) view.findViewById(R.id.upvoteDisplay);
					upvoteDisplay.setText(Integer.toString(singleQuestionArray.get(groupPosition).getUpvotes()));
					
					
					
					//Save favorites if applicable
					if (isInFavorites)
					{
					    Log.d("test", "Item is in favorites.");
					    LoadSave sl = new LoadSave();
					    sl.SaveFavorites();
					}else{
					    Log.d("testitem", "Item is NOT in favorites.");
					}
				}
            	/*
                TextView upvoteDisplay = (TextView) view.findViewById(R.id.upvoteDisplay);
                upvoteDisplay.setText(Integer.toString(singleQuestionArray.get(groupPosition).getUpvotes()));
                */
            }
        });
         
        if (singleQuestionArray.get(groupPosition).hasPicture() == true) {
            ImageButton hasPictureIcon = (ImageButton) view.findViewById(R.id.hasPictureIcon);
            hasPictureIcon.setVisibility(0);
            
            ImageView imagePreview = (ImageView) view.findViewById(R.id.imagePreview);
            // TODO set imagePreview to the photo
        }
        
        ImageButton replyBtn = (ImageButton) view.findViewById(R.id.replyBtn);
        replyBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				// Passing info about which questionList it's from, questionPosition & questionAnswer & the type (we're replying to an Answer) so we know where to add the reply to
				Bundle args = new Bundle();
				args.putInt("fromFragment", fromFragment);
				args.putInt("questionPosition", questionPosition);
				args.putInt("type", TYPE_QUESTION);
				
				// Create & display reply dialog + attach arguments
				DialogFragment replyDialog = new WriteReplyDialogFragment();
				replyDialog.setArguments(args);
				replyDialog.show(activity.getFragmentManager(), null);
				
			}
		});
        
		ImageButton answerBtn = (ImageButton) view.findViewById(R.id.answerBtn);
		answerBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), AddAnAnswerActivity.class);
				//Pass the position of the question to the new activity
				intent.putExtra("fromFragment", fromFragment);
				intent.putExtra("question_position", groupPosition);
				activity.startActivity(intent);
			}
		});

		CheckBox favBtn = (CheckBox) view.findViewById(R.id.favBtn);
		favBtn.setChecked(singleQuestionArray.get(groupPosition).getIsFav());
		favBtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Question question = singleQuestionArray.get(groupPosition);

				if (isChecked == true) {
					if (!ListHandler.getFavsList().getQuestionList().contains(question)) {
						ListHandler.getFavsList().add(question);
						//Log.d("test", "Added to favorites...");
					}
				}
				else if (isChecked == false) {
					ListHandler.getFavsList().remove(question);
					//Log.d("test", "Removed from favorites...");
				}
				
				singleQuestionArray.get(groupPosition).setIsFav(isChecked);
				
				//Save new favorite list.
				LoadSave saver = new LoadSave();
				saver.SaveFavorites();
			}
		});
		
		
		CheckBox readingListBtn = (CheckBox) view.findViewById(R.id.offlineBtn);
		readingListBtn.setChecked(singleQuestionArray.get(groupPosition).getIsInReadingList());
		readingListBtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Question question = singleQuestionArray.get(groupPosition);
				
				if (isChecked == true) {
					if (!ListHandler.getReadingList().getQuestionList().contains(question)) {
						ListHandler.getReadingList().add(question);
					}
				}
				else if (isChecked == false) {
					ListHandler.getReadingList().remove(question);
				}
				
				singleQuestionArray.get(groupPosition).setIsInReadingList(isChecked);
			}
		});
        return view;
    }
 
    /* (non-Javadoc)
     * @see android.widget.ExpandableListAdapter#hasStableIds()
     */
    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return false;
    }
 
    /* (non-Javadoc)
     * @see android.widget.ExpandableListAdapter#isChildSelectable(int, int)
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return false;
    }
 

}
