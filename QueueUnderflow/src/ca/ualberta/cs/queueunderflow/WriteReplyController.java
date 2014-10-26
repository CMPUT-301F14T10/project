package ca.ualberta.cs.queueunderflow;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;

public class WriteReplyController {

	private static int TYPE_QUESTION = 0;
	private static int TYPE_ANSWER = 1;
	
	private Activity activity;		// In case we want to add a toast message later
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
		Reply newReply = new Reply(replyText.getText().toString(), User.getUserName());
		
		if (type == TYPE_QUESTION) {
			question.addQuestionReply(newReply);
		}
		else if (type == TYPE_ANSWER) {
			answer.addReply(newReply);
		}
	}
	
}
