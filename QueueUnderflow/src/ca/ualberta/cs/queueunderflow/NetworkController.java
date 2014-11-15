package ca.ualberta.cs.queueunderflow;

import java.util.UUID;

import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.Reply;

// Implement an interface?
// NOTE : This assumes that the MasterList in ListHandler is always completely online, thus we add to that list
// to ensure that there are no conflicts
public class NetworkController {

	public NetworkController() {
		
	}
	
	public void addQuestion(Question question) {
		ListHandler.getMasterQList().add(question);
		ListHandler.getMyQsList().add(question);
	}
	
	public void addAnswer(UUID questionID, Answer answer) {
		int questionIndex = ListHandler.getMasterQList().getIndexFromID(questionID);
		Question question = ListHandler.getMasterQList().get(questionIndex);
		question.addAnswer(answer);
		ListHandler.getMasterQList().set(questionIndex, question);
	}
	
	public void addQReply(UUID questionID, Reply reply) {
		int questionIndex = ListHandler.getMasterQList().getIndexFromID(questionID);
		Question question = ListHandler.getMasterQList().get(questionIndex);
		question.addReply(reply);
		ListHandler.getMasterQList().set(questionIndex, question);
	}
	
	public void addAReply(UUID questionID, UUID answerID, Reply reply) {
		int questionIndex = ListHandler.getMasterQList().getIndexFromID(questionID);
		Question question = ListHandler.getMasterQList().get(questionIndex);
		
		int answerIndex = question.getAnswerList().getIndexFromID(answerID);
		Answer answer = question.getAnswerList().getAnswer(answerIndex);
		
		answer.addReply(reply);
		ListHandler.getMasterQList().set(questionIndex, question);
	}
}
