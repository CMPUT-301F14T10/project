package ca.ualberta.cs.queueunderflow;

import java.util.ArrayList;

public class Answer {
	protected String answerName;
	protected ArrayList<Reply> answerReplies;
	protected String author;
	
	
	public Answer(String answerName, ArrayList<Reply> answer_replies, String author) {
		this.answerName=answerName;
		this.answerReplies=new ArrayList<Reply>();
		this.author=author;
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

}
