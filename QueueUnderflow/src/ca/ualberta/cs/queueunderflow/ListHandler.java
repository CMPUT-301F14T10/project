package ca.ualberta.cs.queueunderflow;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ca.ualberta.cs.queueunderflow.models.Question;
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
	
	/** The search results list. */
	private static QuestionList resultsList;
	
	
	/** The user. */
	private static User user;
	
	private static ArrayList<String> favIDs;
	private static ArrayList<String> readingListIDs;
	private static ArrayList<String> myQIDs;
	
	private ListHandler() {
		qList = new QuestionList();
		favQList = new QuestionList();
		myQList = new QuestionList();
		readingList = new QuestionList();
		resultsList = new QuestionList();
		user = new User();
		
//		favIDs = new ArrayList<String>();
//		readingListIDs = new ArrayList<String>();
//		myQIDs = new ArrayList<String>();
		
	}
	
	public ListHandler getInstance() {
		if (instance == null) {
			instance = new ListHandler();
		}
		return instance;
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
//			myQIDs = new ArrayList<String>();
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
//			favIDs = new ArrayList<String>();
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
//			readingListIDs = new ArrayList<String>();
		}
		return readingList;
	}
	
	
	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	static public User getUser() {
		if (user == null) {
			user = new User();
		}
		return user; 
	}

	public static boolean isInFavs(String questionID) {
		favIDs = new ArrayList<String>();
		for (Question q : getFavsList().getQuestionList()) {
			favIDs.add(q.getStringID());
		}
		System.out.println("checking for --> " + questionID);
		System.out.println(favIDs);
		if (favIDs != null && favIDs.contains(questionID)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static boolean isInReadingList(String questionID) {
		readingListIDs = new ArrayList<String>();
		for (Question q : getReadingList().getQuestionList()) {
			readingListIDs.add(q.getStringID());
		}
		System.out.println("checking for --> " + questionID);
		System.out.println(readingListIDs);
		if (readingListIDs != null && readingListIDs.contains(questionID)) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Gets the results list.
	 *
	 * @return the results list
	 */
	static public QuestionList getResultsList() {
		if (resultsList == null) {
			resultsList = new QuestionList();
		}
		return resultsList;
	}
	
}
