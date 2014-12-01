package ca.ualberta.cs.queueunderflow.singletons;

import java.util.ArrayList;
import java.util.UUID;

import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.Reply;


/**
 * The Class User.
 * @author group 10
 * @version 0.5
 */
public class User {

	/** The display checkbox. */
	protected static boolean displayCheckbox;
	
	/** The username. */
	protected static String username;
	
	/**  Should we use the user's location data. */
	protected static boolean use_location;
	
	/**  User's city. */
	protected static String city="Unknown";
	
	/**  User's country. */
	protected static String country="Unknown";
	
	/** The upvoted questions. Depreciated*/
	//protected ArrayList <Question> upvotedQuestions;
	
	/** The upvoted answers. Depreciated*/
	//protected ArrayList <Answer> upvotedAnswers;
	
	//Applied static to these arraylist
	protected static ArrayList <UUID> upvotedQuestions;
	
	/** The upvoted answers. */
	protected static ArrayList <UUID> upvotedAnswers;
	
	/**
	 * Instantiates a new user.
	 */
	public User() {
		this.username="Anonymous";
		//this.upvotedQuestions= new ArrayList<Question>();
		//this.upvotedAnswers= new ArrayList<Answer>();
		
		this.upvotedQuestions= new ArrayList <UUID>();
		this.upvotedAnswers= new ArrayList<UUID>();
		//By default, the user's city and country is set to Unknown
		// in case the user clicks on the location checkbox but doesn't do anything else
		//this.country="Unknown";
		//this.city="Unknown";
		this.displayCheckbox=false;
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
		//this.upvotedQuestions= new ArrayList<Question>();
		//this.upvotedAnswers= new ArrayList<Answer>();
		this.upvotedQuestions= new ArrayList <UUID>();
		this.upvotedAnswers= new ArrayList<UUID>();
	}
	
	/**
	 * Set the user's city.
	 *
	 * @param sCity User's city
	 */
	public static void setCity(String sCity) {
		User.city = sCity;
	}
	
	/**
	 * Set the user's country.
	 *
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
	 * Get the user's city.
	 *
	 * @return User's city
	 */
	public static String getCity() {
		return User.city;
	}
	
	/**
	 * Get whether the user wants to use location data.
	 *
	 * @return true or false
	 */
	public static boolean getUseLocation() {
		return User.use_location;
	}
	
	/**
	 * Set whether or not to use location data.
	 *
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
	 * @param uuid the uuid
	 */
	
	
	public static void addUpvotedQuestion(UUID uuid) {
		//upvotedQuestions.add(question);
		upvotedQuestions.add(uuid);
	}
	
	/**
	 * Adds the upvoted answer.
	 *
	 * @param uuid the uuid
	 */
	public static void addUpvotedAnswer(UUID uuid) {
		upvotedAnswers.add(uuid);
	}
	
	/**
	 * Already upvoted question.
	 *
	 * @param uuid the uuid
	 * @return true, if successful
	 */
	public static boolean alreadyUpvotedQuestion(UUID uuid){
		
		return upvotedQuestions.contains(uuid);
	}
	
	/**
	 * Already upvoted answer.
	 *
	 * @param uuid the uuid
	 * @return true, if successful
	 */
	public static boolean alreadyUpvotedAnswer(UUID uuid){
	
		return upvotedAnswers.contains(uuid);

	}
	
	/**
	 * Gets the upvoted questions.
	 *
	 * @return the upvoted questions
	 */
	public ArrayList <UUID> getUpvotedQuestions() {
		return User.upvotedQuestions;
	}
	
	/**
	 * Sets the upvoted questions.
	 *
	 * @param questions the new upvoted questions
	 */
	public void setUpvotedQuestions(ArrayList <UUID> questions) {
		User.upvotedQuestions=questions;
	}
	
	/**
	 * Gets the upvoted answers.
	 *
	 * @return the upvoted answers
	 */
	public ArrayList <UUID> getUpvotedAnswers() {
		return User.upvotedAnswers;
	}
	
	/**
	 * Sets the upvoted answers.
	 *
	 * @param answers the new upvoted answers
	 */
	public void setUpvotedAnswers(ArrayList <UUID> answers) {
		User.upvotedAnswers=answers;
	}
	
	/**
	 * Display checkbox.
	 *
	 * @return true, if successful
	 */
	public static boolean displayCheckbox() {
		return User.displayCheckbox;
	}
	
	/**
	 * Sets the checkbox.
	 *
	 * @param value the new checkbox
	 */
	public static void setCheckbox(boolean value) {
		User.displayCheckbox=value;
	}
	
}
