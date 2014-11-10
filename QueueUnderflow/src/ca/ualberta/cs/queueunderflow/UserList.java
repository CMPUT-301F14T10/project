package ca.ualberta.cs.queueunderflow;

import java.util.ArrayList;
// TODO: Auto-generated Javadoc
// Store users in an arraylist to keep track of upvote, questions asked etc.
/**
 * The Class UserList.
 * @author group 10
 * @version 0.5
 */
public class UserList {

/** The user list. */
protected ArrayList <User> userList;
	
	/**
	 * Instantiates a new user list.
	 */
	public UserList() {
		userList= new ArrayList<User>();
	}
	
	/**
	 * Adds the.
	 *
	 * @param user the user
	 */
	public void add (User user) {
		// Add at end of the list
		userList.add(user);	
	}
	
	/**
	 * Size.
	 *
	 * @return the int
	 */
	public int size() {
		return userList.size();
	}
	
	/**
	 * Gets the user.
	 *
	 * @param i the i
	 * @return the user
	 */
	public User getUser(int i) {
		return userList.get(i);
	}
	
	/**
	 * Gets the user list.
	 *
	 * @return the user list
	 */
	public ArrayList <User> getUserList() {
		return userList;
	}
}
