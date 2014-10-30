package ca.ualberta.cs.queueunderflow.models;

import java.util.ArrayList;

/*
 Sets the Answerlist so we can have answers to questions.
 */

public class AnswerList {
protected ArrayList <Answer> answerList;
    
    // sets a new arraylist
	public AnswerList () {
		answerList= new ArrayList<Answer>();
	}
	
	public void add (Answer answer) {
		// Add at position 0, so that freshest A is always first
		answerList.add(0, answer);	
	}
	
	//returns the size of answerlist. So you can keep track of number of answers
	public int size() {
		return answerList.size();
	}
	
	//grabs the answer
	public Answer getAnswer(int i) {
		return answerList.get(i);
	}
	
	// returns the arraylist of answers
	public ArrayList <Answer> getAnswerList() {
		return answerList;
	}
}
