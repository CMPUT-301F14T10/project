package ca.ualberta.cs.queueunderflow;

import java.util.ArrayList;
import java.util.Date;

public class Answer {
	protected String answerName;
	protected ArrayList<Reply> answerReplies;
	protected String author;
	protected int upvote;
	protected boolean hasPicture;
	protected Picture image;
	protected Date date;
	
	
	public Answer(String answerName,String author) {
		this.answerName=answerName;
		this.answerReplies=new ArrayList<Reply>();
		this.author=author;
		this.upvote=0;
		this.hasPicture=false;;
		this.image=null;
		this.date= new Date();
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
	
	
    public Reply getReplyAt(int position) {
        return answerReplies.get(position);
    }
     
    public void addReply(Reply reply) {
        answerReplies.add(0, reply);
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
	
	public void setUpvotes(int number) {
		this.upvote=number;
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
	public Date getDate() {
		return this.date;
	}
	
	

	

}
