package ca.ualberta.cs.queueunderflow;

import java.util.ArrayList;

public class AnswerList {
protected ArrayList <Answer> answerList;
	
	public AnswerList () {
		answerList= new ArrayList<Answer>();
	}
	
	public void add (Answer answer) {
		answerList.add(answer);
		
	}
	public int size() {
		return answerList.size();
	}
	
	public Answer getAnswer(int i) {
		return answerList.get(i);
	}
}
