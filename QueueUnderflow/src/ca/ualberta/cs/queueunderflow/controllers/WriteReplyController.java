package ca.ualberta.cs.queueunderflow.controllers;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import ca.ualberta.cs.queueunderflow.NetworkBuffer;
import ca.ualberta.cs.queueunderflow.R;
import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.QuestionList;
import ca.ualberta.cs.queueunderflow.models.Reply;
import ca.ualberta.cs.queueunderflow.singletons.ListHandler;
import ca.ualberta.cs.queueunderflow.singletons.NetworkManager;
import ca.ualberta.cs.queueunderflow.singletons.User;

/**
 * The Class WriteReplyController.
 * The controller that handles adding replies to questions and answers.
 * @author group 10
 * @version 1.0
 */
public class WriteReplyController {

	/** The Constant HOME_SCREEN_FRAGMENT. */
	public static final int HOME_SCREEN_FRAGMENT = 1;
	
	/** The Constant FAVORITES_FRAGMENT. */
	public static final int FAVORITES_FRAGMENT = 2;
	
	/** The Constant MY_QUESTIONS_FRAGMENT. */
	public static final int MY_QUESTIONS_FRAGMENT = 3;
	
	/** The Constant READING_LIST_FRAGMENT. */
	public static final int READING_LIST_FRAGMENT = 4;
	
	/** The type question. */
	private static int TYPE_QUESTION = 0;
	
	/** The type answer. */
	private static int TYPE_ANSWER = 1;
	
	/** The activity. */
	private Activity activity;
	
	/** The view. */
	private View view;
	
	/** The network manager. */
	private NetworkManager networkManager = NetworkManager.getInstance();
	
	/**
	 * Instantiates a new write reply controller.
	 *
	 * @param activity the activity
	 * @param view the view
	 */
	public WriteReplyController(Activity activity, View view) {
		this.activity = activity;
		this.view = view;
	}
	
	/**
	 * Adds the reply.
	 *
	 * @param arguments the arguments
	 */
	public void addReply(Bundle arguments) {
		
		// Retrieves info
		int fromFragment = arguments.getInt("fromFragment");
		int questionPosition = arguments.getInt("questionPosition");
		int type = arguments.getInt("type");									// whether we're adding a reply to a question or an answer
		
		QuestionList questionList = findQuestionList(fromFragment);
		Question question = questionList.get(questionPosition);
		
		// Creating the reply
		EditText replyText = (EditText) view.findViewById(R.id.replyText);
		Reply newReply;
		try {
			newReply = new Reply(replyText.getText().toString(), User.getUserName());
			if (User.getUseLocation()) {
				newReply.setLocation(User.getCity() + ", " + User.getCountry());
			}
		} catch (IllegalArgumentException e) {
			Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
			return;
		}
		
		
		
		// To mimic
		if (type == TYPE_QUESTION) {
			question.addReply(newReply);
			questionList.set(questionPosition, question);
		}
		else if (type == TYPE_ANSWER) {
			int answerPosition = arguments.getInt("answerPosition");
			Answer answer = question.getAnswerList().getAnswer(answerPosition);
			answer.addReply(newReply);
			questionList.set(questionPosition, question);
		}
		
		
		if ( !networkManager.isOnline(activity.getApplicationContext()) ) {
			NetworkBuffer networkBuffer = networkManager.getNetworkBuffer();
			
			if (type == TYPE_QUESTION) {
				networkBuffer.addQReply(question.getStringID(), newReply);
			}
			else if (type == TYPE_ANSWER) {
				int answerPosition = arguments.getInt("answerPosition");
				Answer answer = question.getAnswerList().getAnswer(answerPosition);
				networkBuffer.addAReply(question.getStringID(), answer.getStringID(), newReply);
			}
			
			Toast.makeText(activity.getApplicationContext(), "Not connected to the network. Reply will automatically be pushed online when connected.", Toast.LENGTH_LONG).show();
			return;
		}
		else { // if online
			// Adding the reply to the proper Question or Answer
			NetworkController  networkController = new NetworkController();
			if (type == TYPE_QUESTION) {
				networkController.addQReply(question.getStringID(), newReply);
			}
			else if (type == TYPE_ANSWER) {
				int answerPosition = arguments.getInt("answerPosition");
				Answer answer = question.getAnswerList().getAnswer(answerPosition);
				networkController.addAReply(question.getStringID(), answer.getStringID(), newReply);
			}
		}
		
	}

	
	// Retrieves the question from the fragment where it was selected from
    /**
	 * Find question list.
	 *
	 * @param fromFragment the from fragment
	 * @return the question list
	 */
	private QuestionList findQuestionList(int fromFragment) {
		switch (fromFragment) {
		case (HOME_SCREEN_FRAGMENT):
			return ListHandler.getMasterQList();
		case (FAVORITES_FRAGMENT):
			return ListHandler.getFavsList();
		case (READING_LIST_FRAGMENT):
			return ListHandler.getReadingList();
		case (MY_QUESTIONS_FRAGMENT):
			return ListHandler.getMyQsList();
		}
		
		// Just to satisfy Java
		return ListHandler.getMasterQList();
    }
	
}