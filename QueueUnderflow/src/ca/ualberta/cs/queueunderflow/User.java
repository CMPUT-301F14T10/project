package ca.ualberta.cs.queueunderflow;

import java.util.ArrayList;

import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.Reply;

public class User {

	//Changing all these fields to static too
	protected static String username;
	protected ArrayList <Question> upvotedQuestions;
	protected ArrayList <Answer> upvotedAnswers;
	protected ArrayList<Reply> upvotedReplies;
	
	public User() {
		this.username="Anonymous";
		this.upvotedQuestions= new ArrayList<Question>();
		this.upvotedAnswers= new ArrayList<Answer>();
	}
	
	public void setUserName(String userName) {
		String tempUserName = userName.trim();
		if (tempUserName.length() == 0) {
			this.username = "Anonymous";
			throw new IllegalArgumentException("Invalid username. Username is set to Anonymous.");
		}
		this.username=tempUserName;
		this.upvotedQuestions= new ArrayList<Question>();
		this.upvotedAnswers= new ArrayList<Answer>();
	}
	
	
	public static String getUserName() {
		if (username == null) {
			username = "Anonymous";
		}
		return username;
	}
	
	//Adding static to these 4 methods
	public void addUpvotedQuestion(Question question) {
		upvotedQuestions.add(question);
	}
	public void addUpvotedAnswer(Answer answer) {
		upvotedAnswers.add(answer);
	}
	
	public boolean alreadyUpvotedQuestion(Question question){
		//return this.upvotedQuestions.contains(question);
		return this.upvotedQuestions.contains(question);
	}
	public boolean alreadyUpvotedAnswer(Answer answer){
		//return this.upvotedAnswers.contains(answer);
		return this.upvotedAnswers.contains(answer);

	}
	
}
