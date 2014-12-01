package ca.ualberta.cs.queueunderflow.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import ca.ualberta.cs.queueunderflow.elasticsearch.ESManager;
import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.QuestionList;
import ca.ualberta.cs.queueunderflow.models.Reply;
import ca.ualberta.cs.queueunderflow.singletons.ListHandler;


/**
 * The Class NetworkController.
 */
public class NetworkController {

	/** The es manager. */
	private ESManager esManager;
	
	// For retrieving a list or a question
	/** The temp list. */
	private List<Question> tempList;
	
	/** The temp question. */
	private Question tempQuestion;
	
	 /*	Index Location - What it tracks
	  * 0 - populateListThread
	  * 1 - addQuestionThread - not used
	  * 2 - getQuestionThread
	  * 3 - addAnswerThread
	  * 4 - addQReplyThread
	  * 5 - addAReplyThread
	  * 6 - upvoteQuestionThread - not used
	  * 7 - upvoteAnswerThread
	  * 
	  * Note : Some are not used but are in the vector in case it may be needed in the future & to keep the logical groupings
	  */
	/** The is thread finished. */
 	private Vector<Boolean> isThreadFinished;
	
	/**
	 * Instantiates a new network controller.
	 */
	public NetworkController() {
		esManager = new ESManager();
		tempList = new ArrayList<Question>();
		
		isThreadFinished = new Vector<Boolean>(8);
		for (int i = 0; i < 8; i++) {
			isThreadFinished.add(false);
		}
	}
	
	/**
	 * Adds the question.
	 *
	 * @param newQuestion the new question
	 */
	public void addQuestion(Question newQuestion) {
		Thread thread = new AddQThread(newQuestion);
		thread.start();
	}

	/**
	 * Gets the question.
	 *
	 * @param questionID the question id
	 * @return the question
	 */
	public Question getQuestion(String questionID) {
		Thread thread = new GetQuestionThread(questionID);
		thread.start();
		
    	// Make sure populateThread is done and we retrieved the results from network before we continue to ensure we're not returning null
    	while (isThreadFinished.get(2) != true) {
    	}
    	
    	isThreadFinished.set(2, false);
    	
    	
		return tempQuestion;
	}
	
	/**
	 * The Class GetQuestionThread.
	 */
	class GetQuestionThread extends Thread {
		
		/** The question id. */
		private String questionID;
		
		/**
		 * Instantiates a new gets the question thread.
		 *
		 * @param questionID the question id
		 */
		public GetQuestionThread(String questionID) {
			this.questionID = questionID;
		}

		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			tempQuestion = esManager.getQuestion(questionID);
			isThreadFinished.set(2, true);
		}

	}
	
	/**
	 * Populate list.
	 *
	 * @param list the list
	 * @param search the search
	 */
	public void populateList(QuestionList list, String search) {
		Thread thread = new PopulateListThread(search);
		thread.start();
		fillQuestionList(list);
	}
    
    /**
     * Fill question list.
     *
     * @param questionList the question list
     */
    private void fillQuestionList(QuestionList questionList) {
    	System.out.println("INSIDE fillQuestionList");
    	
    	// Make sure populateThread is done and we retrieved the results from network before we continue
    	while (isThreadFinished.get(0) != true) {
    	}
    	isThreadFinished.set(0, false);
		
    	if (tempList.size() == 0) {
    		return;
    	}
    	
		ArrayList<Question> list = questionList.getQuestionList();
		list.clear();
		
		for (Question q : tempList) {
			if (ListHandler.isInFavs(q.getStringID())) {
				q.setIsFav(true);
			}
			else {
				q.setIsFav(false);
			}
			if (ListHandler.isInReadingList(q.getStringID())) {
				q.setIsInReadingList(true);
			}
			else {
				q.setIsInReadingList(false);
			}
			list.add(q);
		}
		
		// Sort list by most recent
		questionList.sortBy("most recent");
		questionList.notifyViews();
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
	 * The Class PopulateListThread.
	 */
	class PopulateListThread extends Thread {
		
		/** The search. */
		private String search;
	
		/**
		 * Instantiates a new populate list thread.
		 *
		 * @param s the s
		 */
		public PopulateListThread(String s) {
			search = s;
		}

		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			tempList = esManager.searchQuestions(search, null);
			isThreadFinished.set(0, true);
		}

	}
	
	/**
 * Adds the answer.
 *
 * @param questionID the question id
 * @param newAnswer the new answer
 */
public void addAnswer(String questionID, Answer newAnswer) {
		System.out.println("in NetworkController - addAnswer - questionID --> " + questionID);
		Thread thread = new AddAThread(questionID, newAnswer);
		thread.start();
		
		while (isThreadFinished.get(3) != true) {
		}
		isThreadFinished.set(3, false);
	}

	/**
	 * The Class AddAThread.
	 */
	class AddAThread extends Thread {

		/** The question id. */
		private String questionID;
		
		/** The answer. */
		private Answer answer;
		
		/**
		 * Instantiates a new adds the a thread.
		 *
		 * @param questionID the question id
		 * @param answer the answer
		 */
		public AddAThread(String questionID, Answer answer) {
			this.questionID = questionID;
			this.answer = answer;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			esManager.addAnswer(questionID, answer);
			
			isThreadFinished.set(3, true);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	/**
	 * Upvote question.
	 *
	 * @param questionID the question id
	 */
	public void upvoteQuestion(String questionID) {
		Thread thread = new UpvoteQuestionThread(questionID);
		thread.start();
		
	}
	
	/**
	 * The Class UpvoteQuestionThread.
	 */
	class UpvoteQuestionThread extends Thread {
		
		/** The question id. */
		private String questionID;
		
		/**
		 * Instantiates a new upvote question thread.
		 *
		 * @param questionID the question id
		 */
		public UpvoteQuestionThread(String questionID) {
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
	
	// UPVOTE ANSWER
	/**
	 * Upvote answer.
	 *
	 * @param questionID the question id
	 * @param answerID the answer id
	 */
	public void upvoteAnswer(String questionID, String answerID) {
		Thread thread = new UpvoteAnswerThread(questionID, answerID);
		thread.start();
		
		while (isThreadFinished.get(7) != true) {
		}
		
		isThreadFinished.set(7, false);
	}
	
	/**
	 * The Class UpvoteAnswerThread.
	 */
	class UpvoteAnswerThread extends Thread {
		
		/** The question id. */
		private String questionID;
		
		/** The answer id. */
		private String answerID;
		
		/**
		 * Instantiates a new upvote answer thread.
		 *
		 * @param questionID the question id
		 * @param answerID the answer id
		 */
		public UpvoteAnswerThread(String questionID, String answerID) {
			this.questionID = questionID;
			this.answerID = answerID;
		}

		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			esManager.upvoteAnswer(questionID, answerID);

			isThreadFinished.set(7, true);
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	// ADD QUESTION REPLY
	/**
	 * Adds the q reply.
	 *
	 * @param questionID the question id
	 * @param newReply the new reply
	 */
	public void addQReply(String questionID, Reply newReply) {
		Thread thread = new AddQReplyThread(questionID, newReply);
		thread.start();
		
		while (isThreadFinished.get(4) != true) {
		}
		
		isThreadFinished.set(4, false);
	}
	
	/**
	 * The Class AddQReplyThread.
	 */
	class AddQReplyThread extends Thread {
		
		/** The question id. */
		private String questionID;
		
		/** The reply. */
		private Reply reply;
		
		/**
		 * Instantiates a new adds the q reply thread.
		 *
		 * @param questionID the question id
		 * @param reply the reply
		 */
		public AddQReplyThread(String questionID, Reply reply) {
			this.questionID = questionID;
			this.reply = reply;
		}

		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			esManager.addQReply(questionID, reply);

			isThreadFinished.set(4, true);
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	// ADD ANSWER REPLY
	/**
	 * Adds the a reply.
	 *
	 * @param questionID the question id
	 * @param answerID the answer id
	 * @param reply the reply
	 */
	public void addAReply(String questionID, String answerID, Reply reply) {
		Thread thread = new AddAReplyThread(questionID, answerID, reply);
		thread.start();
		
		while (isThreadFinished.get(5) != true) {
		}
		
		isThreadFinished.set(5,  false);
	}
    
	/**
	 * The Class AddAReplyThread.
	 */
	class AddAReplyThread extends Thread {
		
		/** The question id. */
		private String questionID;
		
		/** The answer id. */
		private String answerID;
		
		/** The reply. */
		private Reply reply;
		
		/**
		 * Instantiates a new adds the a reply thread.
		 *
		 * @param questionID the question id
		 * @param answerID the answer id
		 * @param reply the reply
		 */
		public AddAReplyThread(String questionID, String answerID, Reply reply) {
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

			isThreadFinished.set(5, true);
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
}
