package classes;

import java.util.ArrayList;



public class QuestionList {
	protected ArrayList <Question> questionList;
	//QuestionList 2 is used in sort by pictures, questionlist 3 is used in the other sort by categories
	//Is subject to change
	protected ArrayList <Question> questionList2;
	protected ArrayList <Question> questionList3;
	
	protected boolean online;
	
	public QuestionList () {
		questionList= new ArrayList<Question>();
		this.online=false;
	}

	
	public boolean getOnline() {
		return this.online;
	}
	
	public void setOnline() {
		if (this.online==false) {
			this.online=true;
		}
		else {
			this.online=false;
		}
	}
	
	public boolean pushOnline() {
		if (this.online) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void add (Question question) {
		// Add at position 0, so that freshest Q is always first
		questionList.add(0, question);
		
	}
	public ArrayList <Question> getQuestionList() {
		return questionList;
	}
	public void setQuestionList(ArrayList <Question> newQuestions) {
		this.questionList=newQuestions;
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
	
	public ArrayList<Question> sortByPictures() {
		questionList2= new ArrayList <Question>();
		for (int i=0; i<questionList.size(); i++) {
			Question testQuestion= questionList.get(i);
			boolean result= testQuestion.hasPicture();
			if (result) {
				questionList2.add(testQuestion);
			}
		}
		return questionList2;
	}
	
	
	public void remove(Question question) {
		questionList.remove(question);
	}
}
