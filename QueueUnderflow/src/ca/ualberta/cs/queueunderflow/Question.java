package ca.ualberta.cs.queueunderflow;

public class Question {
	protected String questionName;
	protected AnswerList answerList;
	
	public Question (String questionName, AnswerList answerList) {
		this.questionName=questionName;
		this.answerList= answerList;
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
	
}
