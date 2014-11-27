package ca.ualberta.cs.queueunderflow.controllers;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;
import ca.ualberta.cs.queueunderflow.ListHandler;
import ca.ualberta.cs.queueunderflow.NetworkBuffer;
import ca.ualberta.cs.queueunderflow.NetworkController;
import ca.ualberta.cs.queueunderflow.NetworkManager;
import ca.ualberta.cs.queueunderflow.User;
import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.QuestionList;


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
	
	private NetworkManager networkManager = NetworkManager.getInstance();

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
			//If the user wants to display location, add the location to the question
			if (User.getUseLocation()) {
				String city= User.getCity();
				String country= User.getCountry();
				String location=city+","+country;
				newQuestion.setLocation(location);
			}
			if (hasPicture == View.VISIBLE) {
				try {
					newQuestion.setImagePath(imagePath);
				} catch (IllegalArgumentException e){
					Toast.makeText(activity.getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
					return;
				}
				
				newQuestion.setHasPicture(true);
				//New here
				//newQuestion.setImagePath(imagePath);
				newQuestion.setEncodedImage(encodedImage);
			}
			
			
			// For mimicking fake view
			ListHandler.getMasterQList().add(newQuestion);
			ListHandler.getMyQsList().add(newQuestion);
			
			// If not online / not connected : add it to the Network Buffer to push online later when connected
			System.out.println("NETWORK CONNECTION --- " + Boolean.toString(networkManager.isOnline(activity.getApplicationContext())));
			if ( !networkManager.isOnline(activity.getApplicationContext()) ) {
				NetworkBuffer networkBuffer = networkManager.getNetworkBuffer();
				networkBuffer.addQuestion(newQuestion);
				Toast.makeText(activity.getApplicationContext(), "Not connected to the network. Question will automatically be pushed online when connected.", Toast.LENGTH_SHORT).show();
				activity.finish();
				return; // add to buffer
			}
			else {
				// New - If it get's here, it's connected
				NetworkController networkController = new NetworkController();
				networkController.addQuestion(newQuestion);
			}
			

			
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

	public void addAnswer(int fromFragment, int position, String answerName, String username, int hasPicture) {
		try {
			Answer newAnswer = new Answer(answerName, User.getUserName());
			if (hasPicture == View.VISIBLE) {
				//Exception check: check if image >64kb
				try {
					newAnswer.setImagePath(imagePath);
				} catch (IllegalArgumentException e){
					Toast.makeText(activity.getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
					return;
				}
				
				
				newAnswer.setHasPicture(true);
				//New here
				//newAnswer.setImagePath(imagePath);
				newAnswer.setEncodedImage(encodedImage);
			}
			
			// For mimicking fake view
			QuestionList questionList = findQuestionList(fromFragment);
			Question question = questionList.get(position);
			String questionID = question.getStringID();
			question.addAnswer(newAnswer);
			System.out.println("AddAnAnswerActivity ; fromFragment = " + fromFragment + " | position = " + position + " | questionID = " + questionID);
			questionList.set(position, question);
			
			// If not online / not connected : add it to the Network Buffer to push online later when connected
			System.out.println("NETWORK CONNECTION --- " + Boolean.toString(networkManager.isOnline(activity.getApplicationContext())));
			if ( !networkManager.isOnline(activity.getApplicationContext()) ) {
				NetworkBuffer networkBuffer = networkManager.getNetworkBuffer();
				networkBuffer.addAnswer(question.getStringID(), newAnswer);
				Toast.makeText(activity.getApplicationContext(), "Not connected to the network. Answer will automatically be pushed online when connected.", Toast.LENGTH_SHORT).show();
				activity.finish();
				return;
			}
			else {
				// New - If it get's here, it's connected
				NetworkController networkController = new NetworkController();
				networkController.addAnswer(questionID, newAnswer);
			}
			
			activity.finish();
		} catch (IllegalArgumentException e) {
			Toast.makeText(activity.getApplicationContext(), "Invalid answer. Please re-enter an answer.", Toast.LENGTH_SHORT).show();
		}
	}

}


