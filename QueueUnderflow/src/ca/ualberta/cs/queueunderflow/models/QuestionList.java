package ca.ualberta.cs.queueunderflow.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ca.ualberta.cs.queueunderflow.TModel;
import ca.ualberta.cs.queueunderflow.TView;

// TODO: Auto-generated Javadoc
/**
 * The Class QuestionList.
 * Holds a collection of questions
 */
public class QuestionList extends TModel<TView>{
	
	/** The question list. */
	protected ArrayList <Question> questionList;
	//QuestionList 2 is used in sort by pictures, questionlist 3 is used in the other sort by categories
	//Is subject to change
	/** The question list2. */
	protected ArrayList <Question> questionList2;
	
	/** The question list3. */
	protected ArrayList <Question> questionList3;
	
	/** The online. */
	protected boolean online;
	
	/**
	 * Instantiates a new question list.
	 */
	public QuestionList () {
		questionList= new ArrayList<Question>();
		this.online=false;
	}
	
	/**
	 * Gets the online indicator.
	 *
	 * @return true if the questionlist is online, false otherwise
	 */
	public boolean getOnline() {
		return this.online;
	}
	
	/**
	 * Toggles the online indicator
	 */
	public void setOnline() {
		if (this.online==false) {
			this.online=true;
		}
		else {
			this.online=false;
		}
	}
	
	/**
	 * Push online.
	 *
	 * @return true, if successful
	 */
	public boolean pushOnline() {
		if (this.online) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Adds the specified question to the beginning of the list
	 *
	 * @param question the question to add
	 */
	public void add (Question question) {
		// Add at position 0, so that freshest Q is always first
		questionList.add(0, question);
		notifyViews();
		
	}
	
	/**
	 * Gets the question list.
	 *
	 * @return the question list
	 */
	public ArrayList <Question> getQuestionList() {
		return questionList;
	}
	
	/**
	 * Size.
	 *
	 * @return the number of questions in the list
	 */
	public int size() {
		return questionList.size();
	}
	
	/**
	 * Question index. Searches the list for the specified question and returns the index of the first occurrence
	 *
	 * @param question the question
	 * @return the index of the first occurrence
	 */
	public int questionIndex(Question question) {
		return questionList.indexOf(question);
	}
	
	/**
	 * Gets the question at the specified location in the list
	 *
	 * @param index the index of the question to return
	 * @return the question at the specified location
	 */
	public Question get(int index){
		return questionList.get(index);
	}
	
	// Everytime we update anything within a questionlist (ie add an answer to a question, add a reply to a question, add a reply to an answer), we use SET
	// Although b/c of references in java, it isn't necessary. this is the only way that the list knows to call the notify views method to refresh the view/screen right now
	/**
	 * Sets the question at the specified location with the specified question
	 *
	 * @param index the index to put the specified question
	 * @param question the question to add
	 */
	public void set(int index, Question question) {
		questionList.set(index, question);
		notifyViews();
	}
	
	/**
	 * Search.
	 *
	 * @param question the question
	 * @return the question
	 */
	public Question search(String question) {
		boolean test= false;
		Question q=questionList.get(0);
		for (int i=0; i<questionList.size(); i++) {
			q=questionList.get(i);
			String q_name=q.getName();
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
	
	/**
	 * Sort by pictures.
	 *
	 * @return the array list
	 */
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
	
	/**
	 * Sorts the list of questions by the specified method
	 *
	 * @param method the method to sort by
	 */
	public void sortBy(String method) {
		
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
		
		if (method.equals("most recent")) {
			Collections.sort(questionList, mostRecentComparator);
		}
		else if (method.equals("least recent")) {
			Collections.sort(questionList, leastRecentComparator);
		}
		else if (method == "most upvotes") {
			Collections.sort(questionList, mostUpvotesComparator);
		}
	}
	
	/**
	 * Removes the first occurrence of the specified question in the list
	 *
	 * @param question the question to remove
	 */
	public void remove(Question question) {
		questionList.remove(question);
	}

	/**
	 * Sets the question list.
	 *
	 * @param newQuestions the new question list
	 */
	public void setQuestionList(ArrayList<Question> newQuestions) {
		this.questionList=newQuestions;
		
	}
}
