package ca.ualberta.cs.queueunderflow.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ca.ualberta.cs.queueunderflow.User;

public class ReplyList {

	protected ArrayList <Reply> replyList;
	
	public ReplyList() {
		this.replyList= new ArrayList <Reply> ();
	}
	public void add(Reply reply) {
		replyList.add(0, reply);
	}
	public Reply get(int i) {
		return replyList.get(i);
	}
	
	public ArrayList <Reply> getReplyList() {
		return this.replyList;
	}
	
	public int size() {
		return replyList.size();
	}
	
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
