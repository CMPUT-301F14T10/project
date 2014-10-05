package ca.ualberta.cs.queueunderflow;

import java.util.ArrayList;

public class Question {
	protected String questionName;
	protected AnswerList answerList;
	protected ArrayList <Reply> questionReplies;
	protected String author;
	protected int upvote;
	
	public Question (String questionName, AnswerList answerList, ArrayList<Reply> question_replies, String author, int upvote) {
		this.questionName=questionName;
		this.answerList= answerList;
		this.questionReplies= new ArrayList<Reply> ();
		this.author=author;
		this.upvote=upvote;
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
	
	public void addQuestionReply(Reply reply) {
		questionReplies.add(reply);
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
	public int getUpvotes() {
		return upvote;
	}
	
}
