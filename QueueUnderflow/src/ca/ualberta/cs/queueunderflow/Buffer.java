package ca.ualberta.cs.queueunderflow;

import java.util.ArrayList;
import java.util.Vector;

import ca.ualberta.cs.queueunderflow.models.QuestionList;

// TODO: Auto-generated Javadoc
/**
 * The Class Buffer.
 */
public class Buffer {

	/** The fav buffer. */
	public static ArrayList<Integer> favBuffer;
	
	/** The reading list buffer. */
	public static ArrayList<Integer> readingListBuffer;
	
	/**
	 * Instantiates a new buffer.
	 */
	public Buffer() {
		if (favBuffer == null) {
			favBuffer = new ArrayList<Integer>();
		}
		if (readingListBuffer == null) {
			readingListBuffer = new ArrayList<Integer>();
		}
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
	 * Checks if is fav buffer empty.
	 *
	 * @return true, if is fav buffer empty
	 */
	public boolean isFavBufferEmpty() {
		return favBuffer.isEmpty();
	}
	
	/**
	 * Checks if is reading list buffer empty.
	 *
	 * @return true, if is reading list buffer empty
	 */
	public boolean isReadingListBufferEmpty() {
		return readingListBuffer.isEmpty();
	}

	// Flushes the buffer - sets each question in the corresponding list to null then removes all nulls
	/**
	 * Flush fav.
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
	 * Flush reading list.
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
