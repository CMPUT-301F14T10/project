package ca.ualberta.cs.queueunderflow;

import java.util.ArrayList;

import ca.ualberta.cs.queueunderflow.models.Answer;

// TODO: Auto-generated Javadoc
/**
 * The Class MasterAnswerList.
 */
public class MasterAnswerList {
	
	/** The master answer list. */
	protected ArrayList <Answer> masterAnswerList;
	
	/**
	 * Instantiates a new master answer list.
	 */
	public MasterAnswerList() {
		masterAnswerList=new ArrayList <Answer> ();
	}
	
	/**
	 * Adds the answer.
	 *
	 * @param answer the answer
	 */
	public void addAnswer(Answer answer) {
		masterAnswerList.add(answer);
	}
	
	/**
	 * Search.
	 *
	 * @param answer the answer
	 * @return the answer
	 */
	public Answer search(String answer) {
		boolean test= false;
		Answer a=masterAnswerList.get(0);
		for (int i=0; i<masterAnswerList.size(); i++) {
			a=masterAnswerList.get(i);
			String a_name=a.getName();
			if (a_name.equals(answer)) {
				test=true;
				break;
			}
			
		}
		if (test==true) {
			return a;
		}
		else {
			return null;
		}
		
	}
}
