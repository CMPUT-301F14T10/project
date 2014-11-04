package ca.ualberta.cs.queueunderflow.models;

import java.util.ArrayList;
import java.util.Date;

import android.text.format.DateFormat;

import ca.ualberta.cs.queueunderflow.Picture;

/* This is the class to set Answers.
 It grabs the user and the list of answers from a question. It also is what is
 used for grabbing upvotes for certain questions.
 */
public class Answer {
	protected String answerName;
	protected ArrayList<Reply> answerReplies;
	protected String author;
	protected int upvote;
	protected boolean hasPicture;
	protected Picture image;
	protected Date date;
	
	//error checking for the answers in the question.
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
	public void setAuthor(String author) {
		this.author=author;
	}
	
	//grabs author of the reply.
	public String getAuthor() {
		return this.author;
	}
	
	// grabs the answername
	public String getAnswer() {
		return this.answerName;
	}
	
	//sets reply at correct position
    public Reply getReplyAt(int position) {
        return answerReplies.get(position);
    }
     
    //adds replies.
    public void addReply(Reply reply) {
        answerReplies.add(0, reply);
    }
	
    //grabs the list of replies for the current question.
	public ArrayList<Reply> getReplies () {
		return this.answerReplies;
	}
	
	//grabs the number of replies.
	public int getSizeReplies() {
		return answerReplies.size();
	}
	
	//upvotes an answer by 1.
	public void upvoteAnswer() {
		upvote+=1;
	}
	
	//grabs the upvotes from the current answers.
	public int getUpvotes() {
		return upvote;
	}
	
	//sets the upvotes of an answer.
	public void setUpvotes(int number) {
		this.upvote=number;
	}
	
	//simple boolean to check if there is a picture
	public boolean hasPicture() {
		return this.hasPicture;
	}
	
	public void sethasPicture(boolean value) {
		this.hasPicture=value;
	}
	
	//it sets the image as pic.
	public void setPicture (Picture pic) {
		this.image= pic;
	}
	
	//gets the picture and returns it as an image
	public Picture getPicture() {
		return this.image;
	}
	
	// returns the current date
	public Date getDate() {
		return this.date;
	}
	
	public void setDate(Date newDate) {
		this.date=newDate;
	}
	
	public void setReplyArray(ArrayList<Reply> newReplies) {
		this.answerReplies=newReplies;
	}
	
	
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
