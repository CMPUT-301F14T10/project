package ca.ualberta.cs.queueunderflow;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class WriteReplyController {

	private static int TYPE_QUESTION = 0;
	private static int TYPE_ANSWER = 1;
	
	private Activity activity;
	private View view;
	private Answer answer;
	private Question question;
	
	// This is used if we're adding a reply to a question
	public WriteReplyController(Activity activity, View view, Answer answer) {
		this.activity = activity;
		this.view = view;
		this.answer = answer;
	}
	
	// This is used if we're adding a reply to an answer
	public WriteReplyController(Activity activity, View view, Question question) {
		this.activity = activity;
		this.view = view;
		this.question = question;
	}

	public void addReply(int type) {;
		
		EditText replyText = (EditText) view.findViewById(R.id.replyText);
		Reply newReply;
		
		try {
			newReply = new Reply(replyText.getText().toString(), User.getUserName());
		} catch (IllegalArgumentException e) {
			Toast.makeText(activity.getApplicationContext(), "Invalid question. Please re-enter a question.", Toast.LENGTH_SHORT).show();
			return;
		}
		
		if (type == TYPE_QUESTION) {
			question.addQuestionReply(newReply);
		}
		else if (type == TYPE_ANSWER) {
			answer.addReply(newReply);
		}
	}
	
}
