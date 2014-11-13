package ca.ualberta.cs.queueunderflow.controllers;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import ca.ualberta.cs.queueunderflow.ListHandler;
import ca.ualberta.cs.queueunderflow.LoadSave;
import ca.ualberta.cs.queueunderflow.R;
import ca.ualberta.cs.queueunderflow.User;
import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.QuestionList;

// TODO: Auto-generated Javadoc
/**
 * The Class AskAnswerController.
 * The controller that handles adding questions and answers.
 * @author group 10
 * @version 0.5
 * 
 */
public class AskAnswerController {

	/** The Constant HOME_SCREEN_FRAGMENT. */
	public static final int HOME_SCREEN_FRAGMENT = 1;
	
	/** The Constant FAVORITES_FRAGMENT. */
	public static final int FAVORITES_FRAGMENT = 2;
	
	/** The Constant MY_QUESTIONS_FRAGMENT. */
	public static final int MY_QUESTIONS_FRAGMENT = 3;
	
	/** The Constant READING_LIST_FRAGMENT. */
	public static final int READING_LIST_FRAGMENT = 4;
	
	/** The activity. */
	private Activity activity;	// This is so we can make Toast messages
	
	/** The image path. */
	private String imagePath; // Not used yet
	
	/** The image encoded in base64. */
	private String encodedImage;
	
	/**
	 * Instantiates a new ask answer controller.
	 *
	 * @param activity the activity
	 */
	public AskAnswerController(Activity activity) {
		this.activity = activity;
	}

	/**
	 * Ask question.
	 *
	 * @param question the question name
	 * @param username the username
	 * @param hasPicture the has picture
	 */
	public void askQuestion(String questionName, String username, int hasPicture) {
		try {
			Question newQuestion = new Question(questionName, username);
			if (hasPicture == View.VISIBLE) {
				newQuestion.setHasPicture(true);
				//New here
				//newQuestion.setImagePath(imagePath);
				newQuestion.setEncodedImage(encodedImage);
			}
			QuestionList homeScreenList = ListHandler.getMasterQList();
			homeScreenList.add(newQuestion);
			
			QuestionList myQuestionsList = ListHandler.getMyQsList();
			myQuestionsList.add(newQuestion);
			
			//Mark as unsaved data.
			LoadSave.unsavedChanges = true;
			
			activity.finish();
		} catch (IllegalArgumentException e) {
			Toast.makeText(activity.getApplicationContext(), "Invalid answer. Please re-enter an answer.", Toast.LENGTH_SHORT).show();
		}
	}

	/**
 * Adds the answer.
 *
 * @param fromFragment the from fragment
 * @param position the position
 * @param answerInput the answer input
 */
public void addAnswer(int fromFragment, int position, String answerName, String username, int hasPicture) {
		try {
			Answer newAnswer = new Answer(answerName, User.getUserName());
			if (hasPicture == View.VISIBLE) {
				newAnswer.setHasPicture(true);
				//New here
				//newAnswer.setImagePath(imagePath);
				newAnswer.setEncodedImage(encodedImage);
			}
			QuestionList questionList = findQuestionList(fromFragment);
			Question question = questionList.get(position);
			question.addAnswer(newAnswer);
			questionList.set(position, question);
			
			activity.finish();
		} catch (IllegalArgumentException e) {
			Toast.makeText(activity.getApplicationContext(), "Invalid question. Please re-enter a question.", Toast.LENGTH_SHORT).show();
		}
	}
	
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
		default:
			return ListHandler.getMasterQList();
		}
    }

    /**
     * Sets the image path.
     *
     * @param path the new image path
     */
    public void setImagePath(String path) {
    	this.imagePath = path;
    }
    public void setEncodedImage(String encoded) {
    	this.encodedImage=encoded;
    }
}
