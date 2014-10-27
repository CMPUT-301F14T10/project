package ca.ualberta.cs.queueunderflow;

import java.util.ArrayList;
//Unknown purpose
public class UserList {
protected ArrayList <User> userList;
	
	public UserList() {
		userList= new ArrayList<User>();
	}
	
	public void add (User user) {
		// Add at position 0, so that freshest A is always first
		userList.add(user);	
	}
	public int size() {
		return userList.size();
	}
	
	public User getUser(int i) {
		return userList.get(i);
	}
	
	public ArrayList <User> getUserList() {
		return userList;
	}
}
