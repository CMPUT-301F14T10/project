package ca.ualberta.cs.queueunderflow;

public class ListHandler {

	// Need to change this to a singleton pattern instead of static QuestionLists
	static QuestionList qList;
	static QuestionList readingList;
	static QuestionList favQList;
	static QuestionList myQList;
	
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
}
