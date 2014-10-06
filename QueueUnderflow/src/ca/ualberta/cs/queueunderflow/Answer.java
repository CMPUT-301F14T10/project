package ca.ualberta.cs.queueunderflow;

import java.util.ArrayList;

public class Answer {
	protected String answerName;
	protected ArrayList<Reply> answerReplies;
	protected String author;
	protected int upvote;
	protected boolean hasPicture;
	protected Picture image;
	
	
	public Answer(String answerName, ArrayList<Reply> answer_replies, String author, int upvote,
			boolean hasPicture, Picture image) {
		this.answerName=answerName;
		this.answerReplies=new ArrayList<Reply>();
		this.author=author;
		this.upvote=upvote;
		this.hasPicture=hasPicture;
		this.image=image;
	}
	
	public void setAuthor(String author) {
		this.author=author;
	}
	
	public String getAuthor() {
		return this.author;
	}
	
	public String getAnswer() {
		return this.answerName;
	}
	
	
	public void addReply(Reply reply) {
		answerReplies.add(reply);
	}
	
	public ArrayList<Reply> getReplies () {
		return this.answerReplies;
	}
	
	public int getSizeReplies() {
		return answerReplies.size();
	}
	
	public void upvoteAnswer() {
		upvote+=1;
	}
	public int getUpvotes() {
		return upvote;
	}
	
	public boolean hasPicture() {
		return this.hasPicture;
	}
	
	public void setPicture (Picture pic) {
		this.image= pic;
	}
	
	public Picture getPicture() {
		return this.image;
	}
	
	

	

}
