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
	public ArrayList <Question> getQuestionList() {
		return questionList;
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
	public Question search(String question) {
		boolean test= false;
		Question q=questionList.get(0);
		for (int i=0; i<questionList.size(); i++) {
			q=questionList.get(i);
			String q_name=q.getQuestion();
			if (q_name.equals(question)) {
				test=true;
				break;
			}
			
		}
		if (test==true) {
			return q;
		}
		else {
			return null;
		}
		
	}
} 
