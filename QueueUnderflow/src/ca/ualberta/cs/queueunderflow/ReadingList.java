package ca.ualberta.cs.queueunderflow;

import java.util.ArrayList;

import ca.ualberta.cs.queueunderflow.models.Question;

// TODO: Auto-generated Javadoc
/**
 * The Class ReadingList.
 * Not used yet - May not use
 */
public class ReadingList {
	
	/** The reading list. */
	protected ArrayList<Question> readingList;
	
	/**
	 * Instantiates a new reading list.
	 */
	public ReadingList() {
		this.readingList= new ArrayList <Question> ();
	}
	
	/**
	 * Adds the specified question
	 *
	 * @param question the question
	 */
	public void add(Question question) {
		readingList.add(question);
	}
	
	/**
	 * Gets the question.
	 *
	 * @param i the index of the question
	 * @return the question
	 */
	public Question getQuestion(int i) {
		return readingList.get(i);
	}
	
	/**
	 * Size.
	 *
	 * @return the size of the list
	 */
	public int size() {
		return readingList.size();
	}
}
