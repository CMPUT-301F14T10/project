package ca.ualberta.cs.queueunderflow.singletons;

import java.util.ArrayList;
import java.util.UUID;
import java.util.Vector;

import ca.ualberta.cs.queueunderflow.models.QuestionList;


/**
 * The Class Buffer.
 * Singleton. This class handles when a question is favorited & unfavorited while the Favorites fragment is active, 
 * or when a questions marked or unmarked as added to the reading list while the ReadingList fragment is active.
 *  (b/c we can't remove(unfavorite) a question from a list that's currently being displayed on the current screen via an adapter)
 *  This is in case the user unfavorites then favorites the same question again while in the Favorites fragment (in this case the
 *  buffer does not remove the question from the list), analogous for the reading list.
 *@author Group 10
 *@version 1.0
 */
public class Buffer {
	
	/** The instance. */
	private static Buffer instance = null;

	/** The fav buffer. */
	public static ArrayList<String> favBuffer;
	
	/** The reading list buffer. */
	public static ArrayList<String> readingListBuffer;
	
	/**
	 * Instantiates a new buffer.
	 */
	private Buffer() {
		if (favBuffer == null) {
			favBuffer = new ArrayList<String>();
		}
		if (readingListBuffer == null) {
			readingListBuffer = new ArrayList<String>();
		}
	}
	
	/**
	 * Gets the instance.
	 *
	 * @return instance The singleton instance of Buffer
	 */
	public static Buffer getInstance() {
		if (instance == null) {
			instance = new Buffer();
		}
		return instance;
	}
	
	/**
	 * Adds the to fav buffer.
	 *
	 * @param position the position
	 */
	public void addToFavBuffer(String position) {
		favBuffer.add(position);
	}
	
	/**
	 * Removes the from fav buffer.
	 *
	 * @param position the position
	 */
	public void removeFromFavBuffer(String position) {
		favBuffer.remove(position);
	}
	
	/**
	 * Adds the to reading list buffer.
	 *
	 * @param position the position
	 */
	public void addToReadingListBuffer(String position) {
		readingListBuffer.add(position);
	}
	
	/**
	 * Removes the from reading list buffer.
	 *
	 * @param position the position
	 */
	public void removeFromReadingListBuffer(String position) {
		readingListBuffer.remove(position);
	}
	
	
	
	/**
	 * Flush all.
	 */
	public void flushAll() {
		flush(ListHandler.getFavsList(), favBuffer);
		flush(ListHandler.getReadingList(), readingListBuffer);
	}
	
	
	/**
	 * Flush list buffer - removes all questions with the ids given in the buffer from the given list.
	 *
	 * @param questionList the question list
	 * @param buffer the buffer
	 */
	private void flush(QuestionList questionList, ArrayList<String> buffer) {
		for(int i = 0; i < buffer.size(); i++) {
			String id = buffer.get(i);
			int index = questionList.getIndexFromID(UUID.fromString(id));
			if (index != -1) {
				questionList.set(index, null);
			}
		}
		
		Vector<String> v = new Vector<String>(1);
		v.add(null);
		questionList.getQuestionList().removeAll(v);
	}
	
	/**
	 * Clear all.
	 */
	private void clearAll() {
		favBuffer.clear();
		readingListBuffer.clear();
	}
}