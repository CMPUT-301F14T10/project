package ca.ualberta.cs.queueunderflow;

import java.util.ArrayList;

import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.Reply;

// TODO: Auto-generated Javadoc
/**
 * The Class User.
 */
public class User {

	//Changing all these fields to static too
	/** The username. */
	protected static String username;
	
	/** The upvoted questions. */
	protected ArrayList <Question> upvotedQuestions;
	
	/** The upvoted answers. */
	protected ArrayList <Answer> upvotedAnswers;
	
	/** The upvoted replies. */
	protected ArrayList<Reply> upvotedReplies;
	
	/**
	 * Instantiates a new user.
	 */
	public User() {
		this.username="Anonymous";
		this.upvotedQuestions= new ArrayList<Question>();
		this.upvotedAnswers= new ArrayList<Answer>();
	}
	
	/**
	 * Sets the user name.
	 *
	 * @param userName the new user name
	 */
	public void setUserName(String userName) {
		String tempUserName = userName.trim();
		if (tempUserName.length() == 0) {
			this.username = "Anonymous";
			throw new IllegalArgumentException("Invalid username. Username is set to Anonymous.");
		}
		this.username=tempUserName;
		this.upvotedQuestions= new ArrayList<Question>();
		this.upvotedAnswers= new ArrayList<Answer>();
	}
	
	
	/**
	 * Gets the user name.
	 *
	 * @return the user name
	 */
	public static String getUserName() {
		if (username == null) {
			username = "Anonymous";
		}
		return username;
	}
	
	//Adding static to these 4 methods
	/**
	 * Adds the upvoted question.
	 *
	 * @param question the question
	 */
	public void addUpvotedQuestion(Question question) {
		upvotedQuestions.add(question);
	}
	
	/**
	 * Adds the upvoted answer.
	 *
	 * @param answer the answer
	 */
	public void addUpvotedAnswer(Answer answer) {
		upvotedAnswers.add(answer);
	}
	
	/**
	 * Already upvoted question.
	 *
	 * @param question the question
	 * @return true, if successful
	 */
	public boolean alreadyUpvotedQuestion(Question question){
		//return this.upvotedQuestions.contains(question);
		return this.upvotedQuestions.contains(question);
	}
	
	/**
	 * Already upvoted answer.
	 *
	 * @param answer the answer
	 * @return true, if successful
	 */
	public boolean alreadyUpvotedAnswer(Answer answer){
		//return this.upvotedAnswers.contains(answer);
		return this.upvotedAnswers.contains(answer);

	}
	
}
