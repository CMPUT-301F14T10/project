package ca.ualberta.cs.queueunderflow;

import ca.ualberta.cs.queueunderflow.models.QuestionList;

// TODO: Auto-generated Javadoc
/**
 * The Class ListHandler.
 */
public class ListHandler {

	// Need to change this to a singleton pattern instead of static QuestionLists

	/** The q list. */
	private static QuestionList qList;
	
	/** The reading list. */
	private static QuestionList readingList;
	
	/** The fav q list. */
	private static QuestionList favQList;
	
	/** The my q list. */
	private static QuestionList myQList;
	
	/** The user. */
	private static User user;
	//private static UserList myUList;
	
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
