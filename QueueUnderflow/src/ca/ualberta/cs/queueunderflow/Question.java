package ca.ualberta.cs.queueunderflow;

import java.util.ArrayList;

public class Question {
	protected String questionName;
	protected AnswerList answerList;
	protected ArrayList <String> questionReplies;
	
	public Question (String questionName, AnswerList answerList, ArrayList <String> questionReplies) {
		this.questionName=questionName;
		this.answerList= answerList;
		this.questionReplies= new ArrayList<String> ();
		
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
	
	public void addQuestionReply(String reply) {
		questionReplies.add(reply);
	}
	
	public ArrayList<String> getReplies() {
		return this.questionReplies;
		
	}
	
	public int getSizeReplies() {
		return questionReplies.size();
	}
	
}
