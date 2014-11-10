package ca.ualberta.cs.queueunderflow;

import java.util.ArrayList;
import java.util.Vector;

import ca.ualberta.cs.queueunderflow.models.QuestionList;

// TODO: Auto-generated Javadoc
/**
 * The Class Buffer.
 * Singleton. This class handles when a question is favorited & unfavorited while the Favorites fragment is active, 
 * or when a questions marked or unmarked as added to the reading list while the ReadingList fragment is active.
 *  (b/c we can't remove(unfavorite) a question from a list that's currently being displayed on the current screen via an adapter)
 *  This is in case the user unfavorites then favorites the same question again while in the Favorites fragment (in this case the
 *  buffer does not remove the question from the list), analogous for the reading list.
 */
public class Buffer {
	
	private static Buffer instance = null;

	/** The fav buffer. */
	public static ArrayList<Integer> favBuffer;
	
	/** The reading list buffer. */
	public static ArrayList<Integer> readingListBuffer;
	
	/**
	 * Instantiates a new buffer.
	 */
	private Buffer() {
		if (favBuffer == null) {
			favBuffer = new ArrayList<Integer>();
		}
		if (readingListBuffer == null) {
			readingListBuffer = new ArrayList<Integer>();
		}
	}
	
	/**
	 * Gets the instance
	 *
	 * @return instance The singleton instance of Buffer
	 */
	public static Buffer getInstance() {
		if (instance == null) {
			instance = new Buffer();
		}
		return instance;
	}
	
	// FavBuffer Methods
	/**
	 * Adds the to fav buffer.
	 *
	 * @param position the position
	 */
	public void addToFavBuffer(int position) {
		favBuffer.add(position);
	}
	
	/**
	 * Removes the from fav buffer.
	 *
	 * @param position the position
	 */
	public void removeFromFavBuffer(int position) {
		favBuffer.remove(position);
	}
	
	// ReadingListBuffer Methods
	/**
	 * Adds the to reading list buffer.
	 *
	 * @param position the position
	 */
	public void addToReadingListBuffer(int position) {
		readingListBuffer.add(position);
	}
	
	/**
	 * Removes the from reading list buffer.
	 *
	 * @param position the position
	 */
	public void removeFromReadingListBuffer(int position) {
		readingListBuffer.remove(position);
	}
	
	// Checks if buffer is empty
	/**
	 * Checks if the favorites buffer empty.
	 *
	 * @return true, if the favorites buffer is empty
	 */
	public boolean isFavBufferEmpty() {
		return favBuffer.isEmpty();
	}
	
	/**
	 * Checks if the reading list buffer empty.
	 *
	 * @return true, if the reading list buffer is empty
	 */
	public boolean isReadingListBufferEmpty() {
		return readingListBuffer.isEmpty();
	}

	// Flushes the buffer - sets each question in the corresponding list to null then removes all nulls
	/**
	 * Flush favorites buffer - removes all questions in the positions given in the buffer from the favorites list
	 */
	public void flushFav() {
		QuestionList questionList = ListHandler.getFavsList();
		for(int i = 0; i < favBuffer.size(); i++) {
			int index = favBuffer.get(i);
			questionList.set(index, null);
		}
		
		Vector<String> v = new Vector<String>(1);
		v.add(null);
		questionList.getQuestionList().removeAll(v);
		favBuffer.clear();
	}

	/**
	 * Flush reading list buffer - removes all questions in the positions given in the buffer from the reading list
	 */
	public void flushReadingList() {
		QuestionList questionList = ListHandler.getReadingList();
		for(int i = 0; i < readingListBuffer.size(); i++) {
			int index = readingListBuffer.get(i);
			questionList.set(index, null);
		}
		
		Vector<String> v = new Vector<String>(1);
		v.add(null);
		questionList.getQuestionList().removeAll(v);
		readingListBuffer.clear();
	}
}
