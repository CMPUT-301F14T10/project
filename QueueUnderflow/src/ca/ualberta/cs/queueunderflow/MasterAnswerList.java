package ca.ualberta.cs.queueunderflow;

import java.util.ArrayList;

import ca.ualberta.cs.queueunderflow.models.Answer;

public class MasterAnswerList {
	protected ArrayList <Answer> masterAnswerList;
	
	public MasterAnswerList() {
		masterAnswerList=new ArrayList <Answer> ();
	}
	
	public void addAnswer(Answer answer) {
		masterAnswerList.add(answer);
	}
	public Answer search(String answer) {
		boolean test= false;
		Answer a=masterAnswerList.get(0);
		for (int i=0; i<masterAnswerList.size(); i++) {
			a=masterAnswerList.get(i);
			String a_name=a.getAnswer();
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
