package ca.ualberta.cs.queueunderflow;

import java.util.ArrayList;

public class Answer {
	protected String answerName;
	protected ArrayList<String> replies;
	
	
	public Answer(String answerName, ArrayList<String> replies) {
		this.answerName=answerName;
		this.replies=replies;
	}
	
	public String getAnswer() {
		return this.answerName;
	}
	
	public ArrayList<String> getReplies () {
		return this.replies;
	}

}
