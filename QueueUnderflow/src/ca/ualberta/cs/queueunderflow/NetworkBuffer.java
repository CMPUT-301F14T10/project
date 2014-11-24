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
	Map<String, ArrayList<Answer>> answerBuffer;
	Map<String, ArrayList<Reply>> questionReplyBuffer;
	Map<String, ArrayList<String>> qaIDBuffer;
	Map<String, ArrayList<Reply>> answerReplyBuffer;
	ArrayList<String> upvoteQBuffer;
	Map<String, String> upvoteABuffer;
	
	public NetworkBuffer() {
		questionBuffer = new ArrayList<Question>();
		answerBuffer = new Hashtable<String, ArrayList<Answer>>();
		questionReplyBuffer = new HashMap<String, ArrayList<Reply>>();
		qaIDBuffer = new Hashtable<String, ArrayList<String>>();
		answerReplyBuffer = new Hashtable<String, ArrayList<Reply>>();
		upvoteQBuffer = new ArrayList<String>();
		upvoteABuffer = new Hashtable<String, String>();
	}
	
	public void addQuestion(Question question) {
		questionBuffer.add(question);
		Log.d("network", "adding question to buffer: " + question.getName());
	}
	
	public void addAnswer(String questionID, Answer answer) {
		ArrayList<Answer> answersList = answerBuffer.get(questionID);
		if (answersList == null) {
			answersList = new ArrayList<Answer>();
		}
		answersList.add(answer);
		answerBuffer.put(questionID, answersList);
	}
	
	public void addQReply(String questionID, Reply reply) {
		ArrayList<Reply> repliesList = questionReplyBuffer.get(questionID);
		if (repliesList == null) {
			repliesList = new ArrayList<Reply>();
		}
		repliesList.add(reply);
		questionReplyBuffer.put(questionID, repliesList);
	}
	
	public void addAReply(String questionID, String answerID, Reply reply) {
		ArrayList<String> answersIDs = qaIDBuffer.get(questionID);
		if (answersIDs == null) {
			answersIDs = new ArrayList<String>();
		}
		answersIDs.add(answerID);
		qaIDBuffer.put(questionID, answersIDs);
		
		ArrayList<Reply> replies = answerReplyBuffer.get(answerID);
		if (replies == null) {
			replies = new ArrayList<Reply>();
		}
		replies.add(reply);
		answerReplyBuffer.put(answerID, replies);
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
			ArrayList<Answer> answers = answerBuffer.get(id);
			for (Answer a : answers) {
				networkController.addAnswer(id, a);
				Log.d("flush buffer", a.getName());
			}
		}
		
		// Push all replies to a question in buffer online
		Set<String> questionReplyBufferKeys = questionReplyBuffer.keySet();
		for(String id : questionReplyBufferKeys) {
			ArrayList<Reply> replies = questionReplyBuffer.get(id);
			for (Reply r : replies) {
				networkController.addQReply(id, r);
			}
		}
		
		// Push all replies to an answer in buffer online
		Set<String> qaIDBufferKeys = qaIDBuffer.keySet();
		for(String questionID : qaIDBufferKeys) {
			ArrayList<String> answerIDs = qaIDBuffer.get(questionID);
			for (String id : answerIDs) {
				ArrayList<Reply> replies = answerReplyBuffer.get(id);
				for (Reply r : replies) {
					networkController.addAReply(questionID, id, r);
				}
			}
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