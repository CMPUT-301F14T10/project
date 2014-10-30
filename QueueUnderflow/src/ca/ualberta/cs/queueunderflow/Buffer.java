package ca.ualberta.cs.queueunderflow;

import java.util.ArrayList;
import java.util.Vector;

import ca.ualberta.cs.queueunderflow.models.QuestionList;

public class Buffer {

	private static ArrayList<Integer> favBuffer;
	private static ArrayList<Integer> readingListBuffer;
	
	public Buffer() {
		if (favBuffer == null) {
			favBuffer = new ArrayList<Integer>();
		}
		if (readingListBuffer == null) {
			readingListBuffer = new ArrayList<Integer>();
		}
	}
	
	// FavBuffer Methods
	public void addToFavBuffer(int position) {
		favBuffer.add(position);
	}
	
	public void removeFromFavBuffer(int position) {
		favBuffer.remove(position);
	}
	
	// ReadingListBuffer Methods
	public void addToReadingListBuffer(int position) {
		readingListBuffer.add(position);
	}
	
	public void removeFromReadingListBuffer(int position) {
		readingListBuffer.remove(position);
	}
	
	// Checks if buffer is empty
	public boolean isFavBufferEmpty() {
		return favBuffer.isEmpty();
	}
	
	public boolean isReadingListBufferEmpty() {
		return readingListBuffer.isEmpty();
	}

	// Flushes the buffer - sets each question in the corresponding list to null then removes all nulls
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
