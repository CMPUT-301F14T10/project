package ca.ualberta.cs.queueunderflow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class QuestionList {
	protected ArrayList <Question> questionList;
	protected ArrayList <Question> questionList2;
	protected ArrayList <Question> questionList3;
	
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
	
	public ArrayList<Question> sortByDate(String method) {
		questionList3 = new ArrayList<Question>();
		
		Comparator<Question> leastRecentComparator = new Comparator<Question>() {
			
			@Override
			public int compare(Question lhs, Question rhs) {
				return lhs.getDate().compareTo(rhs.getDate());
			}
		};
		
		Comparator<Question> mostRecentComparator = new Comparator<Question>() {
			
			@Override
			public int compare(Question lhs, Question rhs) {
				return (lhs.getDate().compareTo(rhs.getDate())) * -1;
			}
		};
		questionList3 = (ArrayList<Question>) questionList.clone();
		
		if (method == "most recent") {
			Collections.sort(questionList3, mostRecentComparator);
		}
		else if (method == "least recent") {
			Collections.sort(questionList3, leastRecentComparator);
		}

		return questionList3;
	}
} 
