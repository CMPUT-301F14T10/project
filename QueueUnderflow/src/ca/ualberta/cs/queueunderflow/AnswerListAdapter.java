package ca.ualberta.cs.queueunderflow;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;


public class AnswerListAdapter extends ArrayAdapter<Answer>{
	
	private LayoutInflater inflater;
	private int layoutID;
	private ArrayList<Answer> answerArray;
	private Activity activity;

	public AnswerListAdapter(Activity activity, int layoutID, ArrayList <Answer> answerArray) {

		super(activity,layoutID,answerArray);
		// TODO Auto-generated constructor stub
		// this line is taken from http://www.survivingwithandroid.com/2012/10/android-listview-custom-adapter-and.html
		this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		this.activity = activity;
		this.layoutID = layoutID;
		this.answerArray = answerArray;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final View view;

		view = inflater.inflate(layoutID, parent, false);
		
		TextView questionDisplay = (TextView) view.findViewById(R.id.answerTextView);
		questionDisplay.setText(answerArray.get(position).getAnswer());
		
		TextView authorDisplay = (TextView) view.findViewById(R.id.authorTextView);
		authorDisplay.setText(answerArray.get(position).getAuthor());
		
		TextView dateDisplay = (TextView) view.findViewById(R.id.dateTextView);
		dateDisplay.setText(DateFormat.format("MMM dd, yyyy", answerArray.get(position).getDate()));
		
		TextView upvoteDisplay = (TextView) view.findViewById(R.id.upvoteDisplay);
		upvoteDisplay.setText(Integer.toString(answerArray.get(position).getUpvotes()));
		
		ImageButton upvoteBtn = (ImageButton) view.findViewById(R.id.upvoteBtn);
		upvoteBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				answerArray.get(position).upvoteAnswer();
				TextView upvoteDisplay = (TextView) view.findViewById(R.id.upvoteDisplay);
				upvoteDisplay.setText(Integer.toString(answerArray.get(position).getUpvotes()));
				
			}
		});
		
		if (answerArray.get(position).hasPicture() == true) {
			ImageButton hasPictureIcon = (ImageButton) view.findViewById(R.id.hasPictureIcon);
			hasPictureIcon.setVisibility(View.VISIBLE);
		}
		
		return view;
	}
	
	
}
	





