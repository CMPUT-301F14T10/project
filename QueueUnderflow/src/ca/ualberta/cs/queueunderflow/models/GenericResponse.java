package ca.ualberta.cs.queueunderflow.models;

import java.util.ArrayList;
import java.util.Date;

import android.text.format.DateFormat;

import ca.ualberta.cs.queueunderflow.Picture;

//TODO: Auto-generated Javadoc
/**
* The Class GenericResponse.
*/
public class GenericResponse {

	
	/** The response name. */
	protected String name;
	
	/** The response replies. */
	protected ArrayList <Reply> replies;
	
	/** The author. */
	protected String author;
	
	/** The upvote. */
	protected int upvote;
	
	/** The has picture. */
	protected boolean hasPicture;
	
	/** The image. */
	protected Picture image;
	
	/** The date. */
	protected Date date;

	/** The is fav. */
	protected boolean isFav;
	
	/** The is in reading list. */
	protected boolean isInReadingList;
	
	public GenericResponse (String name, String author) {
		name = name.trim();
		if (name.length() == 0) {
			throw new IllegalArgumentException("Not a valid repsonse. Please enter another response.");
		}
		this.name=name;
		this.replies= new ArrayList<Reply> ();
		this.author=author;
		this.upvote=0;
		this.hasPicture=false;
		this.image=null;
		this.date = new Date();
		
		this.isFav = false;
		this.isInReadingList = false;
	}
	
	
	/**
	 * Sets the author.
	 *
	 * @param author the new author
	 */
	public void setAuthor(String author) {
		this.author=author;
	}
	
	/**
	 * Gets the author.
	 *
	 * @return the author
	 */
	public String getAuthor() {
		return this.author;
	}
	
	/**
	 * Gets the response.
	 *
	 * @return the response
	 */
	public String getName() {
		return this.name;
	}
	
	
    /**
     * Gets the reply at.
     *
     * @param position the position
     * @return the reply at
     */
    public Reply getReplyAt(int position) {
        return replies.get(position);
    }
    
	/**
	 * Adds the reply.
	 *
	 * @param reply the reply
	 */
	public void addReply(Reply reply) {
		replies.add(0, reply);
	}
	
	/**
	 * Gets the replies.
	 *
	 * @return the replies
	 */
	public ArrayList<Reply> getReplies() {
		return this.replies;
		
	}
	
	/**
	 * Gets the size replies.
	 *
	 * @return the size replies
	 */
	public int getSizeReplies() {
		return replies.size();
	}
	
	/**
	 * Upvote response.
	 */
	public void upvoteResponse() {
		upvote+=1;
	}
	
	
	/**
	 * Sets the upvotes.
	 *
	 * @param number the new upvotes
	 */
	public void setUpvotes(int number) {
		this.upvote=number;
	}
	
	/**
	 * Gets the upvotes.
	 *
	 * @return the upvotes
	 */
	public int getUpvotes() {
		return upvote;
	}
	
	/**
	 * Checks for picture.
	 *
	 * @return true, if successful
	 */
	public boolean hasPicture() {
		return this.hasPicture;
	}
	
	/**
	 * Sets the picture.
	 *
	 * @param pic the new picture
	 */
	public void setPicture (Picture pic) {
		this.image= pic;
		this.hasPicture=true;
	}
	
	/**
	 * Gets the picture.
	 *
	 * @return the picture
	 */
	public Picture getPicture() {
		return this.image;
	}
	
	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public Date getDate() {
		return this.date;
	}
	
	/**
	 * Sets the date.
	 *
	 * @param date the new date
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	
	/**
	 * Gets the checks if is fav.
	 *
	 * @return the checks if is fav
	 */
	public boolean getIsFav() {
		return this.isFav;
	}
	
	/**
	 * Sets the checks if is fav.
	 *
	 * @param isChecked the new checks if is fav
	 */
	public void setIsFav(boolean isChecked) {
		this.isFav = isChecked;
	}

	/**
	 * Gets the checks if is in reading list.
	 *
	 * @return the checks if is in reading list
	 */
	public boolean getIsInReadingList() {
		return this.isInReadingList;
	}

	/**
	 * Sets the checks if is in reading list.
	 *
	 * @param isChecked the new checks if is in reading list
	 */
	public void setIsInReadingList(boolean isChecked) {
		this.isInReadingList = isChecked;
	}

	/**
	 * Sets the response.
	 *
	 * @param newReponse the new response
	 */
	public void setName(String newName) {
		this.name=newName;
		
	}

	/**
	 * Sets the reply list.
	 *
	 * @param replyList the new reply list
	 */
	public void setReplyList(ArrayList<Reply> replyList) {
		this.replies= replyList;
		
	}

	/**
	 * Sets the checks for picture.
	 *
	 * @param hasPicture the new checks for picture
	 */
	public void setHasPicture(boolean hasPicture) {
		this.hasPicture = hasPicture;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		GenericResponse response = (GenericResponse) o;
		
		if (!response.getName().equals(this.name) || !response.getAuthor().equals(this.author)) {
			return false;
		}
		else if (!DateFormat.format("MMM dd, yyyy", response.getDate()).equals(DateFormat.format("MMM dd, yyyy", this.date))) {
			return false;
		}
		else if (response.getUpvotes() != this.upvote || response.hasPicture() != this.hasPicture) {
			return false;
		}
		else if (response.getIsFav() != this.isFav || response.getIsInReadingList() != this.isInReadingList) {
			return false;
		}
		else if (!response.getReplies().equals(this.replies)) {
			return false;
		}
		return true;
	}
}