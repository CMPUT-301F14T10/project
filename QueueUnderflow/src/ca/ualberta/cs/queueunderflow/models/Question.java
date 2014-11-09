package ca.ualberta.cs.queueunderflow.models;

import java.util.ArrayList;
import java.util.Date;

import android.text.format.DateFormat;

import ca.ualberta.cs.queueunderflow.Picture;



// TODO: Auto-generated Javadoc
/**
 * The Class Question.
 */
public class Question {
	
	/** The question name. */
	protected String questionName;
	
	/** The answer list. */
	protected AnswerList answerList;
	
	/** The question replies. */
	protected ArrayList <Reply> questionReplies;
	
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
	
	/**
	 * Instantiates a new question.
	 *
	 * @param questionName the question name
	 * @param author the author
	 */
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
	 * Gets the question.
	 *
	 * @return the question
	 */
	public String getQuestion() {
		return this.questionName;
	}
	
	/**
	 * Gets the answer list.
	 *
	 * @return the answer list
	 */
	public AnswerList getAnswerList () {
		return this.answerList;
	}
	
	/**
	 * Adds the answer.
	 *
	 * @param answer the answer
	 */
	public void addAnswer(Answer answer) {
		answerList.add(answer);
	}
	
	/**
	 * Gets the answer list size.
	 *
	 * @return the answer list size
	 */
	public int getAnswerListSize() {
		return answerList.size();
	}
	
    /**
     * Gets the reply at.
     *
     * @param position the position
     * @return the reply at
     */
    public Reply getReplyAt(int position) {
        return questionReplies.get(position);
    }
    
	/**
	 * Adds the question reply.
	 *
	 * @param reply the reply
	 */
	public void addQuestionReply(Reply reply) {
		questionReplies.add(0, reply);
	}
	
	/**
	 * Gets the replies.
	 *
	 * @return the replies
	 */
	public ArrayList<Reply> getReplies() {
		return this.questionReplies;
		
	}
	
	/**
	 * Gets the size replies.
	 *
	 * @return the size replies
	 */
	public int getSizeReplies() {
		return questionReplies.size();
	}
	
	/**
	 * Upvote question.
	 */
	public void upvoteQuestion() {
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
	 * Sets the question.
	 *
	 * @param newQuestion the new question
	 */
	public void setQuestion(String newQuestion) {
		this.questionName=newQuestion;
		
	}

	/**
	 * Sets the reply list.
	 *
	 * @param replyList the new reply list
	 */
	public void setReplyList(ArrayList<Reply> replyList) {
		this.questionReplies= replyList;
		
	}

	/**
	 * Sets the answer list.
	 *
	 * @param newAnswerList the new answer list
	 */
	public void setAnswerList(AnswerList newAnswerList) {
		this.answerList=newAnswerList;
		
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
