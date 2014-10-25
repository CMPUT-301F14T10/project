package ca.ualberta.cs.queueunderflow;

import java.util.ArrayList;
 
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.provider.ContactsContract.CommonDataKinds.Relation;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
 
public class AnswerListAdapter extends BaseExpandableListAdapter {
 
    private Activity activity;
    private int groupLayoutID;
    private int childLayoutID;
    private ArrayList<Answer> answerArray;
 
    public AnswerListAdapter(Activity activity, int groupLayoutID, int childLayoutID, ArrayList<Answer> answerArray) {
        super();
        this.activity = activity;
        this.groupLayoutID = groupLayoutID;
        this.childLayoutID = childLayoutID;
        this.answerArray = answerArray;
    }
 
    @Override
    public Reply getChild(int groupPosition, int childPosition) {
        return answerArray.get(groupPosition).getReplyAt(childPosition);
    }
 
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return 0;
    }
 
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(childLayoutID, parent, false);
         
         
        TextView replyDisplay = (TextView) view.findViewById(R.id.replyTextView);
        replyDisplay.setText(answerArray.get(groupPosition).getReplyAt(childPosition).getReply());
         
        TextView authorDisplay = (TextView) view.findViewById(R.id.authorTextView);
        authorDisplay.setText(answerArray.get(groupPosition).getReplyAt(childPosition).getAuthor());
         
        // Should Replies have a date displayed? Or is it not necessary?
        //TextView dateDisplay = (TextView) view.findViewById(R.id.dateTextView);
        //dateDisplay.setText(DateFormat.format("MMM dd, yyyy", answerArray.get(groupPosition).getReplyAt(childPosition).getDate()));
         
        return view;
    }
 
    @Override
    public int getChildrenCount(int groupPosition) {
        return answerArray.get(groupPosition).getSizeReplies();
    }
 
    @Override
    public Answer getGroup(int groupPosition) {
        return answerArray.get(groupPosition);
    }
 
    @Override
    public int getGroupCount() {
        return answerArray.size();
    }
 
    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }
 
    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
            View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view;
        if (answerArray.get(groupPosition).hasPicture()) {
            view = inflater.inflate(R.layout.list_item_answer_picture, parent, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView1);
            imageView.setVisibility(View.VISIBLE);
        }
        else {
            view = inflater.inflate(groupLayoutID, parent, false);
        }
         
        TextView answerDisplay = (TextView) view.findViewById(R.id.answerTextView);
        answerDisplay.setText(answerArray.get(groupPosition).getAnswer());
         
        TextView authorDisplay = (TextView) view.findViewById(R.id.authorTextView);
        authorDisplay.setText(answerArray.get(groupPosition).getAuthor());
         
        TextView dateDisplay = (TextView) view.findViewById(R.id.dateTextView);
        dateDisplay.setText(DateFormat.format("MMM dd, yyyy", answerArray.get(groupPosition).getDate()));
         
        TextView upvoteDisplay = (TextView) view.findViewById(R.id.upvoteDisplay);
        upvoteDisplay.setText(Integer.toString(answerArray.get(groupPosition).getUpvotes()));
         
        ImageButton upvoteBtn = (ImageButton) view.findViewById(R.id.upvoteBtn);
        upvoteBtn.setOnClickListener(new OnClickListener() {
             
            @Override
            public void onClick(View v) {
                answerArray.get(groupPosition).upvoteAnswer();
                TextView upvoteDisplay = (TextView) view.findViewById(R.id.upvoteDisplay);
                upvoteDisplay.setText(Integer.toString(answerArray.get(groupPosition).getUpvotes()));
            }
        });
         
        if (answerArray.get(groupPosition).hasPicture() == true) {
            ImageButton hasPictureIcon = (ImageButton) view.findViewById(R.id.hasPictureIcon);
            hasPictureIcon.setVisibility(0);
        }
        
        return view;
    }
 
    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return false;
    }
 
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return false;
    }
 
}