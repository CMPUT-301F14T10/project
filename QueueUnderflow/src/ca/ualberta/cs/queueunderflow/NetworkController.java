package ca.ualberta.cs.queueunderflow;

import java.util.ArrayList;
import java.util.UUID;

import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.QuestionList;
import ca.ualberta.cs.queueunderflow.models.Reply;

// Implement an interface?
// NOTE : This assumes that the MasterList in ListHandler is always completely online, thus we add to that list
// to ensure that there are no conflicts
/**
 * The Class NetworkController.
 */
public class NetworkController {

	/** The es manager. */
	private ESManager esManager;
	
	//private QuestionList result= null;
	
	/**
	 * Instantiates a new network controller.
	 */
	public NetworkController() {
		esManager = new ESManager();
	}
	
	//Trial method to get QuestionList from server, commenting out, trying without threads
	
/*	public void getQList() {
		Thread thread = new GetQuestionListThread();
		thread.start();
		
		boolean proceed=false;
		//if there is more stuff on the server, pull that stuff from the server
		if (result.size()> ListHandler.getMasterQList().size()) {
			proceed=true;
		}
		
		if (proceed) {
			ListHandler.setMasterQList(result);
		}
	}*/
	
	//Trial Method to add QuestionList to server
	
/*	public void addQuestionList(QuestionList newQuestions) {
		Thread thread= new AddQuestionListThread(newQuestions);
		thread.start();
		
	}*/
	
	/**
	 * Adds the question id list.
	 *
	 * @param strings the strings
	 */
	public void addQuestionIDList(ArrayList <String> strings) {
		Thread thread= new AddQuestionIDListThread(strings);
		thread.start();
	}
		
	/**
	 * Adds the question.
	 *
	 * @param newQuestion the new question
	 */
	public void addQuestion(Question newQuestion) {
		Thread thread = new AddQThread(newQuestion);
		thread.start();
		
		ListHandler.getMasterQList().add(newQuestion);
		ListHandler.getMyQsList().add(newQuestion);
	}
	
	/**
	 * Adds the answer.
	 *
	 * @param questionID the question id
	 * @param newAnswer the new answer
	 */
	public void addAnswer(UUID questionID, Answer newAnswer) {
		Thread thread = new AddAThread(questionID, newAnswer);
		thread.start();
		
		finish(questionID);
	}
	
	
	/**
	 * Adds the q reply.
	 *
	 * @param questionID the question id
	 * @param newReply the new reply
	 */
	public void addQReply(UUID questionID, Reply newReply) {
		Thread thread = new AddQReplyThread(questionID, newReply);
		thread.start();
		
		finish(questionID);
	}
	
	/**
	 * Upvote question.
	 *
	 * @param questionID the question id
	 */
	public void upvoteQuestion(UUID questionID) {
		Thread thread = new UpvoteQuestionThread(questionID);
		thread.start();
		
		finish(questionID);
		
	}
	
	/**
	 * Upvote answer.
	 *
	 * @param questionID the question id
	 * @param answerID the answer id
	 */
	public void upvoteAnswer(UUID questionID, UUID answerID) {
		Thread thread = new UpvoteAnswerThread(questionID, answerID);
		thread.start();

		finish(questionID);
	}

	/**
	 * Adds the a reply.
	 *
	 * @param questionID the question id
	 * @param answerID the answer id
	 * @param reply the reply
	 */
	public void addAReply(UUID questionID, UUID answerID, Reply reply) {
		Thread thread = new AddAReplyThread(questionID, answerID, reply);
		thread.start();
		
		finish(questionID);
	}
    
	/**
	 * Finish.
	 *
	 * @param questionID the question id
	 */
	private void finish(UUID questionID) {
		// So it updates the view b/c we use .set
		int questionIndex = ListHandler.getMasterQList().getIndexFromID(questionID);
		Question question = ListHandler.getMasterQList().get(questionIndex);
		ListHandler.getMasterQList().set(questionIndex, question);
	}
    
    
    // BELOW - Maybe move this somewhere else later
	// This is modified from https://github.com/dfserrano/AndroidElasticSearch 11-15-2014
	/**
     * The Class AddQThread.
     */
    class AddQThread extends Thread {

		/** The question. */
		private Question question;
		
		/**
		 * Instantiates a new adds the q thread.
		 *
		 * @param question the question
		 */
		public AddQThread(Question question) {
			this.question = question;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
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
	
	/**
	 * The Class AddAThread.
	 */
	class AddAThread extends Thread {

		/** The question id. */
		private UUID questionID;
		
		/** The answer. */
		private Answer answer;
		
		/**
		 * Instantiates a new adds the a thread.
		 *
		 * @param questionID the question id
		 * @param answer the answer
		 */
		public AddAThread(UUID questionID, Answer answer) {
			this.questionID = questionID;
			this.answer = answer;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
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

	/**
	 * The Class AddQReplyThread.
	 */
	class AddQReplyThread extends Thread {
		
		/** The question id. */
		private UUID questionID;
		
		/** The reply. */
		private Reply reply;
		
		/**
		 * Instantiates a new adds the q reply thread.
		 *
		 * @param questionID the question id
		 * @param reply the reply
		 */
		public AddQReplyThread(UUID questionID, Reply reply) {
			this.questionID = questionID;
			this.reply = reply;
		}

		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
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
	
	/**
	 * The Class AddAReplyThread.
	 */
	class AddAReplyThread extends Thread {
		
		/** The question id. */
		private UUID questionID;
		
		/** The answer id. */
		private UUID answerID;
		
		/** The reply. */
		private Reply reply;
		
		/**
		 * Instantiates a new adds the a reply thread.
		 *
		 * @param questionID the question id
		 * @param answerID the answer id
		 * @param reply the reply
		 */
		public AddAReplyThread(UUID questionID, UUID answerID, Reply reply) {
			this.questionID = questionID;
			this.answerID = answerID;
			this.reply = reply;
		}

		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
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
	
	/**
	 * The Class UpvoteQuestionThread.
	 */
	class UpvoteQuestionThread extends Thread {
		
		/** The question id. */
		private UUID questionID;
		
		/**
		 * Instantiates a new upvote question thread.
		 *
		 * @param questionID the question id
		 */
		public UpvoteQuestionThread(UUID questionID) {
			this.questionID = questionID;
		}

		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
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
	
	/**
	 * The Class UpvoteAnswerThread.
	 */
	class UpvoteAnswerThread extends Thread {
		
		/** The question id. */
		private UUID questionID;
		
		/** The answer id. */
		private UUID answerID;
		
		/**
		 * Instantiates a new upvote answer thread.
		 *
		 * @param questionID the question id
		 * @param answerID the answer id
		 */
		public UpvoteAnswerThread(UUID questionID, UUID answerID) {
			this.questionID = questionID;
			this.answerID = answerID;
		}

		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
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
	
	
	//Trail thread to get questionlist from server
/*	class GetQuestionListThread extends Thread {
				
		public GetQuestionListThread() {
		}
		
		@Override
		public void run() {
			result= esManager.getQuestionList();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		}
		
	}*/
	
	//Trial thread to add questionlist to server
/*	class AddQuestionListThread extends Thread {
		
		private QuestionList questionList;
		public AddQuestionListThread(QuestionList newQuestions) {
			this.questionList=newQuestions;
		}

		@Override
		public void run() {
			esManager.addQuestionList(questionList);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		}*/
	
	//Thread to get question ids from the server
	/**
	 * The Class AddQuestionIDListThread.
	 */
	class AddQuestionIDListThread extends Thread {
		
		/** The Question ids. */
		private ArrayList <String> QuestionIDS;
		
		/**
		 * Instantiates a new adds the question id list thread.
		 *
		 * @param strings the strings
		 */
		public AddQuestionIDListThread(ArrayList<String> strings) {
			this.QuestionIDS=strings;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			esManager.addQuestionIDS(QuestionIDS);
			try {
				Thread.sleep(500);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


}
