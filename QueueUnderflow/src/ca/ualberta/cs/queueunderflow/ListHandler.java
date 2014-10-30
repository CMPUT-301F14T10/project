package ca.ualberta.cs.queueunderflow;

import ca.ualberta.cs.queueunderflow.models.QuestionList;

public class ListHandler {

	// Need to change this to a singleton pattern instead of static QuestionLists

	private static QuestionList qList;
	private static QuestionList readingList;
	private static QuestionList favQList;
	private static QuestionList myQList;
	private static User user;
	//private static UserList myUList;
	
	static public QuestionList getMasterQList() {
		if (qList == null) {
			qList = new QuestionList();
		}
		return qList;
	}
	
	static public QuestionList getMyQsList() {
		if (myQList == null) {
			myQList = new QuestionList();
		}
		return myQList;
	}
	
	static public QuestionList getFavsList() {
		if (favQList == null) {
			favQList = new QuestionList();
		}
		return favQList;
	}
	
	static public QuestionList getReadingList() {
		if (readingList == null) {
			readingList = new QuestionList();
		}
		return readingList;
	}
	
	
	static public User getUser(){
		if (user == null) {
			user = new User();
		}
		return user; 
	}
	
}
