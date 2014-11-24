package ca.ualberta.cs.queueunderflow;

import java.util.ArrayList;

import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.Reply;


/**
 * The Class User.
 * @author group 10
 * @version 0.5
 */
public class User {

	/** The username. */
	protected static String username;
	
	/** Should we use the user's location data */
	protected static boolean use_location;
	
	/** User's city */
	protected static String city;
	
	/** User's country */
	protected static String country;
	
	/** The upvoted questions. */
	protected ArrayList <Question> upvotedQuestions;
	
	/** The upvoted answers. */
	protected ArrayList <Answer> upvotedAnswers;
	
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
	 * Set the user's city
	 * @param sCity User's city
	 */
	public static void setCity(String sCity) {
		User.city = sCity;
	}
	
	/**
	 * Set the user's country
	 * @param sCountry User's country
	 */
	public static void setCountry(String sCountry) {
		User.country = sCountry;
	}
	
	/**
	 * Get the user's country.
	 * @return User's country
	 */
	public static String getCountry() {
		return User.country;
	}
	
	/**
	 * Get the user's city
	 * @return User's city
	 */
	public static String getCity() {
		return User.city;
	}
	
	/**
	 * Get whether the user wants to use location data
	 * @return true or false
	 */
	public static boolean getUseLocation() {
		return User.use_location;
	}
	
	/**
	 * Set whether or not to use location data
	 * @param value true or false
	 */
	public static void setUseLocation(boolean value) {
		User.use_location = value;
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
		
		return this.upvotedQuestions.contains(question);
	}
	
	/**
	 * Already upvoted answer.
	 *
	 * @param answer the answer
	 * @return true, if successful
	 */
	public boolean alreadyUpvotedAnswer(Answer answer){
	
		return this.upvotedAnswers.contains(answer);

	}
	
}
