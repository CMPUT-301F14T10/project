package ca.ualberta.cs.queueunderflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import android.util.Log;
import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.Reply;

// implement generic buffer class?
// This deals with all the actions that users do when they are offline
public class NetworkBuffer {

	ArrayList<Question> questionBuffer;
	Map<String, Answer> answerBuffer;
	Map<String, Reply> questionReplyBuffer;
	Map<String, String> qaIDBuffer;
	Map<String, Reply> answerReplyBuffer;
	ArrayList<String> upvoteQBuffer;
	Map<String, String> upvoteABuffer;
	
	public NetworkBuffer() {
		questionBuffer = new ArrayList<Question>();
		answerBuffer = new Hashtable<String, Answer>();
		questionReplyBuffer = new Hashtable<String, Reply>();
		qaIDBuffer = new Hashtable<String, String>();
		answerReplyBuffer = new Hashtable<String, Reply>();
		upvoteQBuffer = new ArrayList<String>();
		upvoteABuffer = new Hashtable<String, String>();
	}
	
	public void addQuestion(Question question) {
		questionBuffer.add(question);
		Log.d("network", "adding question to buffer: " + question.getName());
	}
	
	// Problem : What is you add more than one answer while offline - dictionaries don't work anymore - Need to fix later
	public void addAnswer(String questionID, Answer answer) {
		answerBuffer.put(questionID, answer);
		Log.d("network", "adding answer to buffer: " + answer.getName());
	}
	
	public void addQReply(String questionID, Reply reply) {
		questionReplyBuffer.put(questionID, reply);
	}
	
	public void addAReply(String questionID, String answerID, Reply reply) {
		qaIDBuffer.put(questionID, answerID);
		answerReplyBuffer.put(answerID, reply);
	}
	
	public void addQUpvote(String questionID) {
		upvoteQBuffer.add(questionID);
	}
	
	public void addAUpvote(String questionID, String answerID) {
		upvoteABuffer.put(questionID, answerID);
	}
	
	private void clearAll() {
		questionBuffer.clear();
		answerBuffer.clear();
		questionReplyBuffer.clear();
		qaIDBuffer.clear();
		answerReplyBuffer.clear();
		upvoteQBuffer.clear();
		upvoteABuffer.clear();
	}
	
	public void flushAll() {
		
		NetworkController networkController = new NetworkController();
		
		// Push add questions in buffer online
		for (Question question : questionBuffer) {
			networkController.addQuestion(question);
			Log.d("flush buffer", question.getName());
		}
		
		// Push all answers to question in buffer online
		Set<String> answerBufferKeys = answerBuffer.keySet();
		for(String id : answerBufferKeys) {
			Answer answer = answerBuffer.get(id);
			networkController.addAnswer(id, answer);
			Log.d("flush buffer", answer.getName());
		}
		
		// Push all replies to a question in buffer online
		Set<String> questionReplyBufferKeys = questionReplyBuffer.keySet();
		for(String id : questionReplyBufferKeys) {
			Reply reply = questionReplyBuffer.get(id);
			networkController.addQReply(id, reply);
		}
		
		// Push all replies to an answer in buffer online
		Set<String> qaIDBufferKeys = qaIDBuffer.keySet();
		for(String questionID : qaIDBufferKeys) {
			String answerID = qaIDBuffer.get(questionID);
			Reply reply = answerReplyBuffer.get(answerID);
			networkController.addAReply(questionID, answerID, reply);
		}
		
		// Push all question upvotes in buffer online
		for( String id : upvoteQBuffer) {
			networkController.upvoteQuestion(id);
		}
		
		// Push all answer upvotes in buffer online
		Set<String> upvoteABufferKeys = upvoteABuffer.keySet();
		for (String questionID : upvoteABufferKeys) {
			String answerID = upvoteABuffer.get(questionID);
			networkController.upvoteAnswer(questionID, answerID);
		}
		
		clearAll();
	
	}

}