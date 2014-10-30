package ca.ualberta.cs.queueunderflow;

import java.util.ArrayList;

import ca.ualberta.cs.queueunderflow.models.Question;

public class ReadingList {
	protected ArrayList<Question> readingList;
	
	public ReadingList() {
		this.readingList= new ArrayList <Question> ();
	}
	
	public void add(Question question) {
		readingList.add(question);
	}
	
	public Question getQuestion(int i) {
		return readingList.get(i);
	}
	
	public int size() {
		return readingList.size();
	}
}
