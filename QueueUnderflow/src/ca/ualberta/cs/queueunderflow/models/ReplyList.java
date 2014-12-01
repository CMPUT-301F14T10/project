package ca.ualberta.cs.queueunderflow.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ca.ualberta.cs.queueunderflow.singletons.User;

/**
 * The Class ReplyList.
 */
public class ReplyList {

	/** The reply list. */
	protected ArrayList <Reply> replyList;
	
	/**
	 * Instantiates a new reply list.
	 */
	public ReplyList() {
		this.replyList= new ArrayList <Reply> ();
	}
	
	/**
	 * Adds the.
	 *
	 * @param reply the reply
	 */
	public void add(Reply reply) {
		replyList.add(0, reply);
	}
	
	/**
	 * Gets the.
	 *
	 * @param i the i
	 * @return the reply
	 */
	public Reply get(int i) {
		return replyList.get(i);
	}
	
	/**
	 * Gets the reply list.
	 *
	 * @return the reply list
	 */
	public ArrayList <Reply> getReplyList() {
		return this.replyList;
	}
	
	/**
	 * Equals.
	 *
	 * @param replyList2 the reply list2
	 * @return true, if successful
	 */
	public boolean equals(ReplyList replyList2) {
		if (replyList2.size()!=replyList.size()) {
			return false;
		}
		for (int i = 0; i < replyList.size(); i++) {
			if (!replyList2.get(i).equals(replyList.get(i))) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Sets the reply list.
	 *
	 * @param replyList the new reply list
	 */
	public void setReplyList(ArrayList<Reply> replyList) {
		this.replyList=replyList;
	}
	
	/**
	 * Size.
	 *
	 * @return the int
	 */
	public int size() {
		return replyList.size();
	}
	
	/**
	 * Contains.
	 *
	 * @param reply the reply
	 * @return true, if successful
	 */
	public boolean contains(Reply reply) {
		return replyList.contains(reply);
	}
	
	/**
	 * Sort by.
	 *
	 * @param method the method
	 */
	public void sortBy(String method) {
		
		// Sort by date
		Comparator<Reply> leastRecentComparator = new Comparator<Reply>() {

			@Override
			public int compare(Reply lhs, Reply rhs) {
				return (lhs.getDate().compareTo(rhs.getDate()));
			
			}
		};
		
		Comparator<Reply> mostRecentComparator = new Comparator<Reply>() {
			@Override
			public int compare(Reply lhs, Reply rhs) {
				return (lhs.getDate().compareTo(rhs.getDate())) * -1;	
				
				}
		};
		
		// Sort by nearby location, Replys with nearby location appear at the top
		Comparator<Reply> nearbyComparator = new Comparator<Reply>() {

			@Override
			public int compare(Reply lhs, Reply rhs) {
				String city= User.getCity();
				String country= User.getCountry();
				String location= city+", "+country;
				if (lhs.getLocation().equals(location) && !rhs.getLocation().equals(location)) {
					return -1;
				}
				else if (!lhs.getLocation().equals(location) && rhs.getLocation().equals(location)) {
					return 1;
				}
				return 0;
			}
		};
		
		if (method.equals("most recent")) {
			Collections.sort(replyList, mostRecentComparator);
		}
		else if (method.equals("least recent")) {
			Collections.sort(replyList, leastRecentComparator);
		}
		else if (method.equals("nearby replies")) {
			Collections.sort(replyList,nearbyComparator);
		}
	}
	
}
