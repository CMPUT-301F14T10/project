package ca.ualberta.cs.queueunderflow;

import java.util.ArrayList;

public class AnswerList {
protected ArrayList <Answer> answerList;
	
	public AnswerList () {
		answerList= new ArrayList<Answer>();
	}
	
	public void add (Answer answer) {
		// Add at position 0, so that freshest A is always first
		answerList.add(0, answer);	
	}
	public int size() {
		return answerList.size();
	}
	
	public Answer getAnswer(int i) {
		return answerList.get(i);
	}
	
	public ArrayList <Answer> getAnswerList() {
		return answerList;
	}
}
