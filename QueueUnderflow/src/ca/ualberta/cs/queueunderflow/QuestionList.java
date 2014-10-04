package ca.ualberta.cs.queueunderflow;

import java.util.ArrayList;

public class QuestionList {
	protected ArrayList <Question> questionList;
	
	public QuestionList () {
		questionList= new ArrayList<Question>();
		
	}
	
	public void add (Question question) {
		questionList.add(question);
		
	}
	
	public int size() {
		return questionList.size();
	}
	
	public int questionIndex(Question question) {
		return questionList.indexOf(question);
	}
	
	public Question get(int index){
		return questionList.get(index);
	}
	
	public void set(int index, Question question) {
		questionList.set(index, question);
	}
} 
