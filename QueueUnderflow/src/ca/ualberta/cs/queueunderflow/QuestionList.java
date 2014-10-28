package ca.ualberta.cs.queueunderflow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class QuestionList extends TModel<TView>{
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
		notifyViews();
		
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
		notifyViews();
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
	
	public ArrayList<Question> sortBy(String method) {
		questionList3 = new ArrayList<Question>();
		
		// Sort by date
		Comparator<Question> leastRecentComparator = new Comparator<Question>() {

			@Override
			public int compare(Question lhs, Question rhs) {
				return (lhs.getDate().compareTo(rhs.getDate()));
			
			}
		};
		
		Comparator<Question> mostRecentComparator = new Comparator<Question>() {
			@Override
			public int compare(Question lhs, Question rhs) {
				return (lhs.getDate().compareTo(rhs.getDate())) * -1;	
				
				}
		};
		
		// Sort by upvote
		Comparator <Question> mostUpvotesComparator = new Comparator<Question>() {
			@Override
			public int compare(Question lhs, Question rhs) {
				return (lhs.getUpvotes() - rhs.getUpvotes())*-1;
			}
		};
		
		Comparator <Question> leastUpvotesComparator = new Comparator<Question>() {
			@Override
			public int compare(Question lhs, Question rhs) {
				return lhs.getUpvotes() - rhs.getUpvotes();
			}
		};
		
		questionList3 = (ArrayList<Question>) questionList.clone();
		
		if (method.equals("most recent")) {
			Collections.sort(questionList3, mostRecentComparator);
		}
		else if (method.equals("least recent")) {
			Collections.sort(questionList3, leastRecentComparator);
		}
		else if (method == "most upvotes") {
			Collections.sort(questionList3, mostUpvotesComparator);
		}
		else if (method == "least upvotes") {
			Collections.sort(questionList3, leastUpvotesComparator);
		}
		
		return questionList3;
	}
	
	public void remove(Question question) {
		questionList.remove(question);
	}
}
