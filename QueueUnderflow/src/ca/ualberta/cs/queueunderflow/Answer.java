package ca.ualberta.cs.queueunderflow;

import java.util.ArrayList;

public class Answer {
	protected String answerName;
	protected ArrayList<String> answerReplies;
	
	
	public Answer(String answerName, ArrayList<String> answer_replies) {
		this.answerName=answerName;
		this.answerReplies=new ArrayList<String>();
	}
	
	public String getAnswer() {
		return this.answerName;
	}
	
	
	public void addReply(String reply) {
		answerReplies.add(reply);
	}
	
	public ArrayList<String> getReplies () {
		return this.answerReplies;
	}
	public int getSizeReplies() {
		return answerReplies.size();
	}

}
