package ca.ualberta.cs.queueunderflow;

import ca.ualberta.cs.queueunderflow.models.QuestionList;


/**
 * The Class ListHandler.
 * Singleton.  Main access point of the various lists for each fragment. Also holds the user.
 @author group 10
 * @version 0.5
 * 
 */
public class ListHandler {

	private static ListHandler instance = null;
	
	/** The q list. */
	private static QuestionList qList;
	
	/** The fav q list. */
	private static QuestionList favQList;
	
	/** The my q list. */
	private static QuestionList myQList;	
	
	/** The reading list. */
	private static QuestionList readingList;
	
	/** The user. */
	private static User user;
	
	private ListHandler() {
		qList = new QuestionList();
		favQList = new QuestionList();
		myQList = new QuestionList();
		readingList = new QuestionList();
		user = new User();
	}
	
	public ListHandler getInstance() {
		if (instance == null) {
			instance = new ListHandler();
		}
		return instance;
	}
	
	//Used to set master question list
	static public void setMasterQList(QuestionList questionList) {
		qList=questionList;
	}
	
	/**
	 * Gets the master q list.
	 *
	 * @return the master q list
	 */
	static public QuestionList getMasterQList() {
		if (qList == null) {
			qList = new QuestionList();
		}
		return qList;
	}
	
	/**
	 * Gets the my qs list.
	 *
	 * @return the my qs list
	 */
	static public QuestionList getMyQsList() {
		if (myQList == null) {
			myQList = new QuestionList();
		}
		return myQList;
	}
	
	/**
	 * Gets the favs list.
	 *
	 * @return the favs list
	 */
	static public QuestionList getFavsList() {
		if (favQList == null) {
			favQList = new QuestionList();
		}
		return favQList;
	}
	
	/**
	 * Gets the reading list.
	 *
	 * @return the reading list
	 */
	static public QuestionList getReadingList() {
		if (readingList == null) {
			readingList = new QuestionList();
		}
		return readingList;
	}
	
	
	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	static public User getUser(){
		if (user == null) {
			user = new User();
		}
		return user; 
	}
	
}
