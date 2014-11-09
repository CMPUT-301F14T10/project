package ca.ualberta.cs.queueunderflow.models;

import java.util.ArrayList;
import java.util.Date;

import android.text.format.DateFormat;

import ca.ualberta.cs.queueunderflow.Picture;

// TODO: Auto-generated Javadoc
/* This is the class to set Answers.
 It grabs the user and the list of answers from a question. It also is what is
 used for grabbing upvotes for certain questions.
 */
/**
 * The Class Answer.
 */
public class Answer {
	
	/** The answer name. */
	protected String answerName;
	
	/** The answer replies. */
	protected ArrayList<Reply> answerReplies;
	
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
	
	//error checking for the answers in the question.
	/**
	 * Instantiates a new answer.
	 *
	 * @param answerName the answer name
	 * @param author the author
	 */
	public Answer(String answerName,String author) {
		answerName = answerName.trim();
		if (answerName.length() == 0) {
			throw new IllegalArgumentException("Not a valid answer. Please re-enter a valid answer.");
		}
		this.answerName=answerName;
		this.answerReplies=new ArrayList<Reply>();
		this.author=author;
		this.upvote=0;
		this.hasPicture=false;;
		this.image=null;
		this.date= new Date();
	}
	
	//allows author to set a username.
	/**
	 * Sets the author.
	 *
	 * @param author the new author
	 */
	public void setAuthor(String author) {
		this.author=author;
	}
	
	//grabs author of the reply.
	/**
	 * Gets the author.
	 *
	 * @return the author
	 */
	public String getAuthor() {
		return this.author;
	}
	
	// grabs the answername
	/**
	 * Gets the answer.
	 *
	 * @return the answer
	 */
	public String getAnswer() {
		return this.answerName;
	}
	
	//sets reply at correct position
    /**
	 * Gets the reply at.
	 *
	 * @param position the position
	 * @return the reply at
	 */
	public Reply getReplyAt(int position) {
        return answerReplies.get(position);
    }
     
    //adds replies.
    /**
     * Adds the reply.
     *
     * @param reply the reply
     */
    public void addReply(Reply reply) {
        answerReplies.add(0, reply);
    }
	
    //grabs the list of replies for the current question.
	/**
     * Gets the replies.
     *
     * @return the replies
     */
    public ArrayList<Reply> getReplies () {
		return this.answerReplies;
	}
	
	//grabs the number of replies.
	/**
	 * Gets the size replies.
	 *
	 * @return the size replies
	 */
	public int getSizeReplies() {
		return answerReplies.size();
	}
	
	//upvotes an answer by 1.
	/**
	 * Upvote answer.
	 */
	public void upvoteAnswer() {
		upvote+=1;
	}
	
	//grabs the upvotes from the current answers.
	/**
	 * Gets the upvotes.
	 *
	 * @return the upvotes
	 */
	public int getUpvotes() {
		return upvote;
	}
	
	//sets the upvotes of an answer.
	/**
	 * Sets the upvotes.
	 *
	 * @param number the new upvotes
	 */
	public void setUpvotes(int number) {
		this.upvote=number;
	}
	
	//simple boolean to check if there is a picture
	/**
	 * Checks for picture.
	 *
	 * @return true, if successful
	 */
	public boolean hasPicture() {
		return this.hasPicture;
	}
	
	/**
	 * Sets the checks for picture.
	 *
	 * @param value the new checks for picture
	 */
	public void sethasPicture(boolean value) {
		this.hasPicture=value;
	}
	
	//it sets the image as pic.
	/**
	 * Sets the picture.
	 *
	 * @param pic the new picture
	 */
	public void setPicture (Picture pic) {
		this.image= pic;
	}
	
	//gets the picture and returns it as an image
	/**
	 * Gets the picture.
	 *
	 * @return the picture
	 */
	public Picture getPicture() {
		return this.image;
	}
	
	// returns the current date
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
	 * @param newDate the new date
	 */
	public void setDate(Date newDate) {
		this.date=newDate;
	}
	
	/**
	 * Sets the reply array.
	 *
	 * @param newReplies the new reply array
	 */
	public void setReplyArray(ArrayList<Reply> newReplies) {
		this.answerReplies=newReplies;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		Answer answer = (Answer) o;
		System.out.println(DateFormat.format("MMM dd, yyyy", answer.getDate()));
		System.out.println(DateFormat.format("MMM dd, yyyy", this.date));
		
		if (!answer.getAnswer().equals(this.answerName) || !answer.getAuthor().equals(this.author)) {
			return false;
		}
		else if (!DateFormat.format("MMM dd, yyyy", answer.getDate()).equals(DateFormat.format("MMM dd, yyyy", this.date))) {
			return false;
		}
		else if (answer.getUpvotes() != this.upvote || answer.hasPicture() != this.hasPicture) {
			return false;
		}
//		else if (answer.getIsFav() != this.isFav || answer.getIsInReadingList() != this.isInReadingList) {
//			return false;
//		}
		else if (!answer.getReplies().equals(this.answerReplies)) {
			return false;
		}
		return true;
	}
	

	

}
