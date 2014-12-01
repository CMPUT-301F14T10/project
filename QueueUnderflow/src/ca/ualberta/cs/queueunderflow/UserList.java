package ca.ualberta.cs.queueunderflow;

import java.util.ArrayList;

import ca.ualberta.cs.queueunderflow.singletons.User;


/**
 * The Class UserList.
 * @author group 10
 * @version 1.0
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
