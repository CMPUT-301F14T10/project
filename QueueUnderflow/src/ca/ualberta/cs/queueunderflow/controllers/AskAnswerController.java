package ca.ualberta.cs.queueunderflow.controllers;

import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import ca.ualberta.cs.queueunderflow.NetworkBuffer;
import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.GenericResponse;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.QuestionList;
import ca.ualberta.cs.queueunderflow.singletons.ListHandler;
import ca.ualberta.cs.queueunderflow.singletons.NetworkManager;
import ca.ualberta.cs.queueunderflow.singletons.User;


/**
 * The Class AskAnswerController.
 * The controller that handles adding questions and answers.
 * @author group 10
 * @version 1.0
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
	
	/** The Constant ATTACH_PHOTO_TRY_AGAIN. */
	public static final int ATTACH_PHOTO_TRY_AGAIN = 0;
	
	/** The Constant SETUP_SUCCESS. */
	public static final int SETUP_SUCCESS = 1;
	
	/** The activity. */
	private Activity activity;	// This is so we can make Toast messages
	
	/** The image path. */
	private String imagePath; 
	
	/** The image encoded in base64. */
	private String encodedImage;
	
	/** The network manager. */
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
	 * @param questionName the question name
	 * @param username the username
	 * @param imagePreviewBtn the has picture
	 */
	public void askQuestion(String questionName, String username, ImageButton imagePreviewBtn) {
		try {
			Question newQuestion = new Question(questionName, username);
			int setUpStatus = setUpResponse(newQuestion, imagePreviewBtn);
			if (setUpStatus == ATTACH_PHOTO_TRY_AGAIN) {
				return;
			}
			
			// For mimicking fake view
			ListHandler.getMasterQList().add(newQuestion);
			ListHandler.getMyQsList().add(newQuestion);
			
			// If not online / not connected : add it to the Network Buffer to push online later when connected
			System.out.println("NETWORK CONNECTION --- " + Boolean.toString(networkManager.isOnline(activity.getApplicationContext())));
			if ( !networkManager.isOnline(activity.getApplicationContext()) ) {
				NetworkBuffer networkBuffer = networkManager.getNetworkBuffer();
				networkBuffer.addQuestion(newQuestion);
				Toast.makeText(activity.getApplicationContext(), "Not connected to the network. Question will automatically be pushed online when connected.", Toast.LENGTH_LONG).show();
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
    
    /**
     * Sets the encoded image.
     *
     * @param encoded the new encoded image
     */
    public void setEncodedImage(String encoded) {
    	this.encodedImage=encoded;
    }

	/**
	 * Adds the answer.
	 *
	 * @param fromFragment the from fragment
	 * @param position the position
	 * @param answerName the answer name
	 * @param username the username
	 * @param imagePreviewBtn the image preview btn
	 */
	public void addAnswer(int fromFragment, int position, String answerName, String username, ImageButton imagePreviewBtn) {
		try {
			Answer newAnswer = new Answer(answerName, User.getUserName());
			int setUpStatus = setUpResponse(newAnswer, imagePreviewBtn);
			if (setUpStatus == ATTACH_PHOTO_TRY_AGAIN) {
				return;
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
				Toast.makeText(activity.getApplicationContext(), "Not connected to the network. Answer will automatically be pushed online when connected.", Toast.LENGTH_LONG).show();
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
	
	
	/**
	 * Sets the up response.
	 *
	 * @param response the response
	 * @param imagePreviewBtn the image preview btn
	 * @return the int
	 */
	private int setUpResponse(GenericResponse response, ImageButton imagePreviewBtn) {
		//If the user wants to display location, add the location to the question
		if (User.getUseLocation()) {
			String city= User.getCity();
			String country= User.getCountry();
			String location=city+", "+country;
			response.setLocation(location);
		}
		if (imagePreviewBtn.getVisibility() == View.VISIBLE) {
			//Exception check: check if image >64kb
			try {
				response.setImagePath(imagePath);
			} catch (IllegalArgumentException e){
				Toast.makeText(activity.getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
				imagePreviewBtn.setVisibility(View.INVISIBLE);
				return ATTACH_PHOTO_TRY_AGAIN;
			}
			response.setHasPicture(true);
			response.setEncodedImage(encodedImage);
		}
		
		return SETUP_SUCCESS;
		
	}

}


