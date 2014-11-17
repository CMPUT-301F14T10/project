package ca.ualberta.cs.queueunderflow;

import java.util.UUID;

import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.QuestionList;
import ca.ualberta.cs.queueunderflow.models.Reply;

// Implement an interface?
// NOTE : This assumes that the MasterList in ListHandler is always completely online, thus we add to that list
// to ensure that there are no conflicts
public class NetworkController {

	private ESManager esManager;
	
	public NetworkController() {
		esManager = new ESManager();
	}
	
	public void addQuestion(Question newQuestion) {
		Thread thread = new AddQThread(newQuestion);
		thread.start();
		
		ListHandler.getMasterQList().add(newQuestion);
		ListHandler.getMyQsList().add(newQuestion);
	}
	
	public void addAnswer(UUID questionID, Answer newAnswer) {
		Thread thread = new AddAThread(questionID, newAnswer);
		thread.start();
		
		finish(questionID);
	}
	
	
	public void addQReply(UUID questionID, Reply newReply) {
		Thread thread = new AddQReplyThread(questionID, newReply);
		thread.start();
		
		finish(questionID);
	}
	
	public void upvoteQuestion(UUID questionID) {
		Thread thread = new UpvoteQuestionThread(questionID);
		thread.start();
		
		finish(questionID);
		
	}
	
	public void upvoteAnswer(UUID questionID, UUID answerID) {
		Thread thread = new UpvoteAnswerThread(questionID, answerID);
		thread.start();

		finish(questionID);
	}

	public void addAReply(UUID questionID, UUID answerID, Reply reply) {
		Thread thread = new AddAReplyThread(questionID, answerID, reply);
		thread.start();
		
		finish(questionID);
	}
    
	private void finish(UUID questionID) {
		// So it updates the view b/c we use .set
		int questionIndex = ListHandler.getMasterQList().getIndexFromID(questionID);
		Question question = ListHandler.getMasterQList().get(questionIndex);
		ListHandler.getMasterQList().set(questionIndex, question);
	}
    
    
    // BELOW - Maybe move this somewhere else later
	// This is modified from https://github.com/dfserrano/AndroidElasticSearch 11-15-2014
	class AddQThread extends Thread {

		private Question question;
		
		public AddQThread(Question question) {
			this.question = question;
		}
		
		@Override
		public void run() {
			esManager.addQuestion(question);
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	class AddAThread extends Thread {

		private UUID questionID;
		private Answer answer;
		
		public AddAThread(UUID questionID, Answer answer) {
			this.questionID = questionID;
			this.answer = answer;
		}
		
		@Override
		public void run() {
			esManager.addAnswer(questionID, answer);
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

	class AddQReplyThread extends Thread {
		
		private UUID questionID;
		private Reply reply;
		
		public AddQReplyThread(UUID questionID, Reply reply) {
			this.questionID = questionID;
			this.reply = reply;
		}

		@Override
		public void run() {
			esManager.addQReply(questionID, reply);

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	class AddAReplyThread extends Thread {
		
		private UUID questionID;
		private UUID answerID;
		private Reply reply;
		
		public AddAReplyThread(UUID questionID, UUID answerID, Reply reply) {
			this.questionID = questionID;
			this.answerID = answerID;
			this.reply = reply;
		}

		@Override
		public void run() {
			esManager.addAReply(questionID, answerID, reply);

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	class UpvoteQuestionThread extends Thread {
		
		private UUID questionID;
		
		public UpvoteQuestionThread(UUID questionID) {
			this.questionID = questionID;
		}

		@Override
		public void run() {
			esManager.upvoteQuestion(questionID);

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	class UpvoteAnswerThread extends Thread {
		
		private UUID questionID;
		private UUID answerID;
		
		public UpvoteAnswerThread(UUID questionID, UUID answerID) {
			this.questionID = questionID;
			this.answerID = answerID;
		}

		@Override
		public void run() {
			esManager.upvoteAnswer(questionID, answerID);

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


}
