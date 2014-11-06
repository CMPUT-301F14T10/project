package ca.ualberta.cs.queueunderflow;

import java.util.ArrayList;
// TODO: Auto-generated Javadoc
//Unknown purpose
/**
 * The Class UserList.
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
		// Add at position 0, so that freshest A is always first
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
