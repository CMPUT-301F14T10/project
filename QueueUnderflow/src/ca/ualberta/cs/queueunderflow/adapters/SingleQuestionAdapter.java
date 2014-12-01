package ca.ualberta.cs.queueunderflow.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Base64;
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
import ca.ualberta.cs.queueunderflow.NetworkBuffer;
import ca.ualberta.cs.queueunderflow.R;
import ca.ualberta.cs.queueunderflow.controllers.NetworkController;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.Reply;
import ca.ualberta.cs.queueunderflow.singletons.Buffer;
import ca.ualberta.cs.queueunderflow.singletons.ListHandler;
import ca.ualberta.cs.queueunderflow.singletons.LoadSave;
import ca.ualberta.cs.queueunderflow.singletons.NetworkManager;
import ca.ualberta.cs.queueunderflow.singletons.User;
import ca.ualberta.cs.queueunderflow.views.AddAnAnswerActivity;
import ca.ualberta.cs.queueunderflow.views.MainActivity;
import ca.ualberta.cs.queueunderflow.views.WriteReplyDialogFragment;

/**
 * The Class SingleQuestionAdapter.
 * Connects a single question(& its replies) to the QAView
 * Handles favoriting & adding the question to the reading list
 * Handles switching to the AddAAnswerActivity & calling the WriteReplyDialogFragment to be displayed
 * @author group 10
 * @version 0.5
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
     * @param question the question
     * @param fromFragment the from fragment
     * @param position the position
     */
    public SingleQuestionAdapter(Activity activity, Question question, int fromFragment, int position) {
        super();
        this.activity = activity;
        this.groupLayoutID = R.layout.list_item_question;
        this.childLayoutID = R.layout.exp_list_item_reply;
		singleQuestionArray = new ArrayList<Question>(1);
		singleQuestionArray.add(question);
		this.fromFragment = fromFragment;
		this.questionPosition = position;
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
        
        TextView locationDisplay = (TextView) view.findViewById(R.id.locationTextView);
        TextView nearbyDisplay= (TextView) view.findViewById(R.id.nearbyTextView);
        nearbyDisplay.setTextColor(Color.BLUE);

		//If the user decides to use location services, then display location info else do not
        String location= singleQuestionArray.get(groupPosition).getReplyAt(childPosition).getLocation();
        String nearby= User.getCity()+", " +User.getCountry();
        
        if (User.getUseLocation()) {
        	locationDisplay.setText(singleQuestionArray.get(groupPosition).getReplyAt(childPosition).getLocation());
        	if (location.equals(nearby)) {
        		nearbyDisplay.setText("Nearby");
        	}
        	else {
        		nearbyDisplay.setText("");
        	}
        }
        else {
        	locationDisplay.setText("");
        	nearbyDisplay.setText("");
        }
        
        

        
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
        questionDisplay.setText(singleQuestionArray.get(groupPosition).getName());
         
        TextView authorDisplay = (TextView) view.findViewById(R.id.authorTextView);
        authorDisplay.setText(singleQuestionArray.get(groupPosition).getAuthor());
         
        TextView dateDisplay = (TextView) view.findViewById(R.id.dateTextView);
        dateDisplay.setText(DateFormat.format("MMM dd, yyyy", singleQuestionArray.get(groupPosition).getDate()));
        
		TextView locationDisplay = (TextView) view.findViewById(R.id.locationTextView);
		TextView nearbyDisplay= (TextView) view.findViewById(R.id.nearbyTextView);
		nearbyDisplay.setTextColor(Color.BLUE);
		String location= singleQuestionArray.get(groupPosition).getLocation();
		String nearby= User.getCity()+", "+User.getCountry();

		//If user decides to use location services, display location data, else do not
		if (User.getUseLocation()) {
			locationDisplay.setText(singleQuestionArray.get(groupPosition).getLocation());
			if (location.equals(nearby)) {
				nearbyDisplay.setText("Nearby");
			}
			else {
				nearbyDisplay.setText("");
			}
		}
		else {
			locationDisplay.setText("");
			nearbyDisplay.setText("");
		}

        TextView upvoteDisplay = (TextView) view.findViewById(R.id.upvoteDisplay);
        upvoteDisplay.setText(Integer.toString(singleQuestionArray.get(groupPosition).getUpvotes()));

        ImageButton upvoteBtn = (ImageButton) view.findViewById(R.id.upvoteBtn);
        upvoteBtn.setOnClickListener(new OnClickListener() {
             
            @Override
            public void onClick(View v) {
            	//User user= ListHandler.getUser();
            	Question question= singleQuestionArray.get(groupPosition);
    			if (question.hasUserUpvoted()) {
					Toast.makeText(activity.getApplicationContext(), "Question was already upvoted", Toast.LENGTH_SHORT).show();			// This should be in the model?
				}
				else {
				        boolean isInFavorites = false;
				        if(ListHandler.getFavsList().getQuestionList().contains(singleQuestionArray.get(groupPosition))) isInFavorites = true;
				    
					User.addUpvotedQuestion(singleQuestionArray.get(groupPosition).getID());
					
					// To mimic fake view updates
					question.upvoteResponse();
					TextView upvoteDisplay = (TextView) view.findViewById(R.id.upvoteDisplay);
					upvoteDisplay.setText(Integer.toString(singleQuestionArray.get(groupPosition).getUpvotes()));
					
					
					NetworkManager networkManager = NetworkManager.getInstance();
					if ( !networkManager.isOnline(activity.getApplicationContext()) ) {
						NetworkBuffer networkBuffer = networkManager.getNetworkBuffer();			
						networkBuffer.addQUpvote(question.getStringID());				
						return;
					}
					else {
						NetworkController  networkController = new NetworkController();
						networkController.upvoteQuestion(singleQuestionArray.get(groupPosition).getStringID());
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
			//String imagePath= singleQuestionArray.get(groupPosition).getImagePath();
			//imagePreview.setImageURI(Uri.parse(imagePath));
			
            //Changed this because using setting the image to be displayed as bitmap, not by uri 
			String encodedImage= singleQuestionArray.get(groupPosition).getEncodedImage();
			byte [] imageBytes= Base64.decode(encodedImage.getBytes(), Base64.DEFAULT);
			imagePreview.setImageBitmap(BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length));
        }
        
        ImageButton replyBtn = (ImageButton) view.findViewById(R.id.replyBtn);
        replyBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				// Passing info about which questionList it's from, questionPosition & questionAnswer & the type (we're replying to an Answer) so we know where to add the reply to
				Bundle args = new Bundle();
				args.putInt("fromFragment", fromFragment);
				args.putInt("questionPosition", questionPosition);
				//args.putString("questionID", singleQuestionArray.get(groupPosition).getStringID());
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
				intent.putExtra("question_position", questionPosition);
				intent.putExtra("fromFragment", fromFragment);
				intent.putExtra("questionID", singleQuestionArray.get(groupPosition).getStringID());
				activity.startActivity(intent);
				//update(null);
			}
		});

		CheckBox favBtn = (CheckBox) view.findViewById(R.id.favBtn);
		favBtn.setChecked(singleQuestionArray.get(groupPosition).getIsFav());
		favBtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Question question = singleQuestionArray.get(groupPosition);
				question.setIsFav(isChecked);
				
				// To fix the bug where Q's favorited are not being shown as favorited in myQs fragment. - not the best way to handle it but due to limited time this will work
				int index = ListHandler.getMyQsList().getIndexFromID(question.getID());
				if (index != -1) {
					ListHandler.getMyQsList().get(index).setIsFav(isChecked);
					ListHandler.getMyQsList().notifyViews();
					System.out.println(Boolean.toString(ListHandler.getMyQsList().getFromStringID(question.getStringID()).getIsFav()));
				}
				
				if (isChecked == true) {
					System.out.println("favoriting normal");
					ListHandler.getFavsList().add(question);
					
					//
					System.out.println("FAVLIST CONTENT");
					ArrayList<String> favIDs = new ArrayList<String>();
					for (Question q : ListHandler.getFavsList().getQuestionList()) {
						favIDs.add(q.getStringID());
					}
					System.out.println(favIDs);
					//
				}
				else if (isChecked == false) {
					System.out.println("unfavoriting normal");
					ListHandler.getFavsList().remove(question);
					
					//
					System.out.println("FAVLIST CONTENT");
					ArrayList<String> favIDs = new ArrayList<String>();
					for (Question q : ListHandler.getFavsList().getQuestionList()) {
						favIDs.add(q.getStringID());
					}
					System.out.println(favIDs);
					//
				}
				
				//Mark as unsaved changes.
				LoadSave.unsavedChanges = true;
			}
		});
		
		
		CheckBox readingListBtn = (CheckBox) view.findViewById(R.id.offlineBtn);
		readingListBtn.setChecked(singleQuestionArray.get(groupPosition).getIsInReadingList());
		readingListBtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Question question = singleQuestionArray.get(groupPosition);
				question.setIsInReadingList(isChecked);
				
				// To fix the bug where Q's marked as in reading list are not being shown as marked in myQs fragment. - not the best way to handle it but due to limited time this will work
				int index = ListHandler.getMyQsList().getIndexFromID(question.getID());
				if (index != -1) {
					ListHandler.getMyQsList().get(index).setIsInReadingList(isChecked);
					ListHandler.getMyQsList().notifyViews();
					System.out.println(Boolean.toString(ListHandler.getMyQsList().getFromStringID(question.getStringID()).getIsInReadingList()));
				}
				
				if (isChecked == true) {
					ListHandler.getReadingList().add(question);
				}
				else if (isChecked == false) {
					ListHandler.getReadingList().remove(question);
				}
				
				
				//Mark as unsaved changes.
				LoadSave.unsavedChanges = true;
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
