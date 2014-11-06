package ca.ualberta.cs.queueunderflow.models;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/*
 Sets the Answerlist so we can have answers to questions.
 */

/**
 * The Class AnswerList.
 */
public class AnswerList {

/** The answer list. */
protected ArrayList <Answer> answerList;
    
    // sets a new arraylist
	/**
     * Instantiates a new answer list.
     */
    public AnswerList () {
		answerList= new ArrayList<Answer>();
	}
	
	/**
	 * Adds the.
	 *
	 * @param answer the answer
	 */
	public void add (Answer answer) {
		// Add at position 0, so that freshest A is always first
		answerList.add(0, answer);	
	}
	
	//returns the size of answerlist. So you can keep track of number of answers
	/**
	 * Size.
	 *
	 * @return the int
	 */
	public int size() {
		return answerList.size();
	}
	
	//grabs the answer
	/**
	 * Gets the answer.
	 *
	 * @param i the i
	 * @return the answer
	 */
	public Answer getAnswer(int i) {
		return answerList.get(i);
	}
	
	// returns the arraylist of answers
	/**
	 * Gets the answer list.
	 *
	 * @return the answer list
	 */
	public ArrayList <Answer> getAnswerList() {
		return answerList;
	}

	/**
	 * Sets the answer list.
	 *
	 * @param newAnswers the new answer list
	 */
	public void setAnswerList(ArrayList<Answer> newAnswers) {
		this.answerList=newAnswers;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		AnswerList aList = (AnswerList) o;
		
		if (aList.size() != answerList.size()) {
			return false;
		}
		
		for (int i = 0; i < answerList.size(); i++) {
			if (!aList.getAnswer(i).equals(answerList.get(i))) {
				return false;
			}
		}
		
		return true;
	}
	
	
	
}
