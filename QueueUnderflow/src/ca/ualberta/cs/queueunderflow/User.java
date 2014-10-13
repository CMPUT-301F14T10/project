package ca.ualberta.cs.queueunderflow;

import java.util.ArrayList;

public class User {

	protected String username;
	protected ArrayList <Question> upvotedQuestions;
	protected ArrayList <Answer> upvotedAnswers;
	protected ArrayList<Reply> upvotedReplies;
	
	public User() {
		this.username=null;
	}
	public void setUserName(String userName) {
		this.username=userName;
		this.upvotedQuestions= new ArrayList<Question>();
		this.upvotedAnswers= new ArrayList<Answer>();
	}
	public String getUserName() {
		return this.username;
	}
	
	public void addUpvotedQuestion(Question question) {
		upvotedQuestions.add(question);
	}
	public void addUpvotedAnswer(Answer answer) {
		upvotedAnswers.add(answer);
	}
	
	public boolean alreadyUpvotedQuestion(Question question){
		return this.upvotedQuestions.contains(question);
	}
	public boolean alreadyUpvotedAnswer(Answer answer){
		return this.upvotedAnswers.contains(answer);
	}
	
}
