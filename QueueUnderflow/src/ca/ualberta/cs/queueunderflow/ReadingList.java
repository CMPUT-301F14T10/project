package ca.ualberta.cs.queueunderflow;

import java.util.ArrayList;

import ca.ualberta.cs.queueunderflow.models.Question;

// TODO: Auto-generated Javadoc
/**
 * The Class ReadingList.
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
	 * Adds the.
	 *
	 * @param question the question
	 */
	public void add(Question question) {
		readingList.add(question);
	}
	
	/**
	 * Gets the question.
	 *
	 * @param i the i
	 * @return the question
	 */
	public Question getQuestion(int i) {
		return readingList.get(i);
	}
	
	/**
	 * Size.
	 *
	 * @return the int
	 */
	public int size() {
		return readingList.size();
	}
}
