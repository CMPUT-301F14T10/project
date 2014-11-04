package ca.ualberta.cs.queueunderflow.models;

import java.util.ArrayList;
import java.util.Date;

import android.text.format.DateFormat;

import ca.ualberta.cs.queueunderflow.Picture;



public class Question {
	protected String questionName;
	protected AnswerList answerList;
	protected ArrayList <Reply> questionReplies;
	protected String author;
	protected int upvote;
	protected boolean hasPicture;
	protected Picture image;
	protected Date date;

	protected boolean isFav;
	protected boolean isInReadingList;
	
	public Question (String questionName, String author) {
		questionName = questionName.trim();
		if (questionName.length() == 0) {
			throw new IllegalArgumentException("Not a valid question. Please enter another question.");
		}
		this.questionName=questionName;
		this.answerList= new AnswerList();
		this.questionReplies= new ArrayList<Reply> ();
		this.author=author;
		this.upvote=0;
		this.hasPicture=false;
		this.image=null;
		this.date = new Date();
		
		this.isFav = false;
		this.isInReadingList = false;
	}
	
	public void setAuthor(String author) {
		this.author=author;
	}
	
	public String getAuthor() {
		return this.author;
	}
	
	public String getQuestion() {
		return this.questionName;
	}
	
	public AnswerList getAnswerList () {
		return this.answerList;
	}
	
	public void addAnswer(Answer answer) {
		answerList.add(answer);
	}
	
	public int getAnswerListSize() {
		return answerList.size();
	}
	
    public Reply getReplyAt(int position) {
        return questionReplies.get(position);
    }
    
	public void addQuestionReply(Reply reply) {
		questionReplies.add(0, reply);
	}
	
	public ArrayList<Reply> getReplies() {
		return this.questionReplies;
		
	}
	
	public int getSizeReplies() {
		return questionReplies.size();
	}
	
	public void upvoteQuestion() {
		upvote+=1;
	}
	
	
	public void setUpvotes(int number) {
		this.upvote=number;
	}
	
	public int getUpvotes() {
		return upvote;
	}
	public boolean hasPicture() {
		return this.hasPicture;
	}
	
	public void setPicture (Picture pic) {
		this.image= pic;
		this.hasPicture=true;
	}
	
	public Picture getPicture() {
		return this.image;
	}
	
	public Date getDate() {
		return this.date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public boolean getIsFav() {
		return this.isFav;
	}
	
	public void setIsFav(boolean isChecked) {
		this.isFav = isChecked;
	}

	public boolean getIsInReadingList() {
		return this.isInReadingList;
	}

	public void setIsInReadingList(boolean isChecked) {
		this.isInReadingList = isChecked;
	}

	public void setQuestion(String newQuestion) {
		this.questionName=newQuestion;
		
	}

	public void setReplyList(ArrayList<Reply> replyList) {
		this.questionReplies= replyList;
		
	}

	public void setAnswerList(AnswerList newAnswerList) {
		this.answerList=newAnswerList;
		
	}

	public void setHasPicture(boolean hasPicture) {
		this.hasPicture = hasPicture;
	}

	@Override
	public boolean equals(Object o) {
		Question question = (Question) o;
		
		if (!question.getQuestion().equals(this.questionName) || !question.getAuthor().equals(this.author)) {
			return false;
		}
		else if (!DateFormat.format("MMM dd, yyyy", question.getDate()).equals(DateFormat.format("MMM dd, yyyy", this.date))) {
			return false;
		}
		else if (question.getUpvotes() != this.upvote || question.hasPicture() != this.hasPicture) {
			return false;
		}
		else if (question.getIsFav() != this.isFav || question.getIsInReadingList() != this.isInReadingList) {
			return false;
		}
		else if (!question.getAnswerList().equals(this.answerList) || !question.getReplies().equals(this.questionReplies)) {
			return false;
		}
		return true;
	}
	
	
	
}
