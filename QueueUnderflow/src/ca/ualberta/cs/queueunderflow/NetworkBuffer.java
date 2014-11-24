package ca.ualberta.cs.queueunderflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import android.util.Log;
import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.Reply;

// implement generic buffer class?
// This deals with all the actions that users do when they are offline
/**
 * The Class NetworkBuffer.
 */
public class NetworkBuffer {

	/** The question buffer. */
	ArrayList<Question> questionBuffer;
	
	/** The answer buffer. */
	Map<UUID, Answer> answerBuffer;
	
	/** The question reply buffer. */
	Map<UUID, Reply> questionReplyBuffer;
	
	/** The qa id buffer. */
	Map<UUID, UUID> qaIDBuffer;
	
	/** The answer reply buffer. */
	Map<UUID, Reply> answerReplyBuffer;
	
	/** The upvote q buffer. */
	ArrayList<UUID> upvoteQBuffer;
	
	/** The upvote a buffer. */
	Map<UUID, UUID> upvoteABuffer;
	
	/**
	 * Instantiates a new network buffer.
	 */
	public NetworkBuffer() {
		questionBuffer = new ArrayList<Question>();
		answerBuffer = new Hashtable<UUID, Answer>();
		questionReplyBuffer = new Hashtable<UUID, Reply>();
		qaIDBuffer = new Hashtable<UUID, UUID>();
		answerReplyBuffer = new Hashtable<UUID, Reply>();
		upvoteQBuffer = new ArrayList<UUID>();
		upvoteABuffer = new Hashtable<UUID, UUID>();
	}
	
	/**
	 * Adds the question.
	 *
	 * @param question the question
	 */
	public void addQuestion(Question question) {
		questionBuffer.add(question);
		Log.d("network", "adding question to buffer: " + question.getName());
	}
	
	// Problem : What is you add more than one answer while offline - dictionaries don't work anymore - Need to fix later
	/**
	 * Adds the answer.
	 *
	 * @param questionID the question id
	 * @param answer the answer
	 */
	public void addAnswer(UUID questionID, Answer answer) {
		answerBuffer.put(questionID, answer);
		Log.d("network", "adding answer to buffer: " + answer.getName());
	}
	
	/**
	 * Adds the q reply.
	 *
	 * @param questionID the question id
	 * @param reply the reply
	 */
	public void addQReply(UUID questionID, Reply reply) {
		questionReplyBuffer.put(questionID, reply);
	}
	
	/**
	 * Adds the a reply.
	 *
	 * @param questionID the question id
	 * @param answerID the answer id
	 * @param reply the reply
	 */
	public void addAReply(UUID questionID, UUID answerID, Reply reply) {
		qaIDBuffer.put(questionID, answerID);
		answerReplyBuffer.put(answerID, reply);
	}
	
	/**
	 * Adds the q upvote.
	 *
	 * @param questionID the question id
	 */
	public void addQUpvote(UUID questionID) {
		upvoteQBuffer.add(questionID);
	}
	
	/**
	 * Adds the a upvote.
	 *
	 * @param questionID the question id
	 * @param answerID the answer id
	 */
	public void addAUpvote(UUID questionID, UUID answerID) {
		upvoteABuffer.put(questionID, answerID);
	}
	
	/**
	 * Clear all.
	 */
	private void clearAll() {
		questionBuffer.clear();
		answerBuffer.clear();
		questionReplyBuffer.clear();
		qaIDBuffer.clear();
		answerReplyBuffer.clear();
		upvoteQBuffer.clear();
		upvoteABuffer.clear();
	}
	
	/**
	 * Flush all.
	 */
	public void flushAll() {
		
		NetworkController networkController = new NetworkController();
		
		// Push add questions in buffer online
		for (Question question : questionBuffer) {
			networkController.addQuestion(question);
			Log.d("flush buffer", question.getName());
		}
		
		// Push all answers to question in buffer online
		Set<UUID> answerBufferKeys = answerBuffer.keySet();
		for(UUID id : answerBufferKeys) {
			Answer answer = answerBuffer.get(id);
			networkController.addAnswer(id, answer);
			Log.d("flush buffer", answer.getName());
		}
		
		// Push all replies to a question in buffer online
		Set<UUID> questionReplyBufferKeys = questionReplyBuffer.keySet();
		for(UUID id : questionReplyBufferKeys) {
			Reply reply = questionReplyBuffer.get(id);
			networkController.addQReply(id, reply);
		}
		
		// Push all replies to an answer in buffer online
		Set<UUID> qaIDBufferKeys = qaIDBuffer.keySet();
		for(UUID questionID : qaIDBufferKeys) {
			UUID answerID = qaIDBuffer.get(questionID);
			Reply reply = answerReplyBuffer.get(answerID);
			networkController.addAReply(questionID, answerID, reply);
		}
		
		// Push all question upvotes in buffer online
		for( UUID id : upvoteQBuffer) {
			networkController.upvoteQuestion(id);
		}
		
		// Push all answer upvotes in buffer online
		Set<UUID> upvoteABufferKeys = upvoteABuffer.keySet();
		for (UUID questionID : upvoteABufferKeys) {
			UUID answerID = upvoteABuffer.get(questionID);
			networkController.upvoteAnswer(questionID, answerID);
		}
		
		clearAll();
	
	}

}
