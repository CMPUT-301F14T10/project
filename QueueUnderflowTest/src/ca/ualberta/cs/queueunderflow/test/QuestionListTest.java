package ca.ualberta.cs.queueunderflow.test;



import java.util.ArrayList;

import junit.framework.TestCase;
import ca.ualberta.cs.queueunderflow.Answer;
import ca.ualberta.cs.queueunderflow.AnswerList;
import ca.ualberta.cs.queueunderflow.Favorites;
import ca.ualberta.cs.queueunderflow.MasterAnswerList;
import ca.ualberta.cs.queueunderflow.QuestionList;
import ca.ualberta.cs.queueunderflow.Question;
import ca.ualberta.cs.queueunderflow.Reply;

public class QuestionListTest extends TestCase {
	
	//Use case 1
	public void testBrowseQuestions() {
		QuestionList questionList= new QuestionList();
		String questionName= "How does this work?";
		AnswerList answerList=new AnswerList ();
		ArrayList <Reply> question_replies= new ArrayList <Reply >();
		String author= "Me";
		Question questionTest= new Question(questionName,answerList, question_replies,author,0,false);
		questionList.add(questionTest);
		
		assertTrue("Question List isn't empty", questionList.getQuestionList().contains(questionTest));
	}
	
	
	
	//Use case 4
	public void testAddQuestion() {
		QuestionList questionList= new QuestionList();
		String questionName= "How does this work?";
		AnswerList answerList=new AnswerList ();
		ArrayList <Reply> question_replies= new ArrayList <Reply >();
		String author= "Me";
		Question questionTest= new Question(questionName,answerList, question_replies,author,0,false);
		questionList.add(questionTest);
		
		assertTrue("Question List isn't empty", questionList.size()==1);
	}
	
	
	//Use case 5
	public void testAnswerQuestion() {
		QuestionList questionList= new QuestionList();
		String questionName= "A question?";
		AnswerList answerList= new AnswerList();
		ArrayList <Reply> question_replies= new ArrayList <Reply >();
		String author= "Me";
		Question questionTest= new Question(questionName,answerList, question_replies,author,0,false);
		questionList.add(questionTest);
		
		//Testing the questionIndex method of questionlist class
		int question_index= questionList.questionIndex(questionTest);
		assertTrue("question index is 0", question_index==0);
		
		//Adding an answer
		Question sameQuestion= questionList.get(question_index);
		ArrayList<Reply> answer_replies= new ArrayList<Reply>();
		String answerName= "An answer";
		String authorName= "You";
		Answer testAnswer= new Answer(answerName, answer_replies,authorName,0);
		
		sameQuestion.addAnswer(testAnswer);
		
		//Testing to see if the answer_list of a question is not empty
		questionList.set(question_index, sameQuestion);
		assertTrue("Answer List isn't empty",questionList.get(question_index).getAnswerListSize()==1 ) ;
		
	}
	
	//use case 14
	public void testAnswersQuestionRec() {
		QuestionList questionList= new QuestionList();
		String questionName= "A question?";
		AnswerList answerList= new AnswerList();
		ArrayList <Reply> question_replies= new ArrayList <Reply >();
		String author= "A author";
		//Add question_replies
		Question questionTest= new Question(questionName, answerList,question_replies,author,0,false);
		questionList.add(questionTest);
		
		//Testing the questionIndex method of questionlist class
		int question_index= questionList.questionIndex(questionTest);
		assertTrue("question index is 0", question_index==0);
		
		//Adding an answer
		Question sameQuestion= questionList.get(question_index);
		ArrayList<Reply> answer_replies= new ArrayList<Reply>();
		String author2="Author 2";
		String answerName= "An answer";
		String answerName2= "Answer 2";
		String answerName3= "Answer 3";
		
		Answer testAnswer= new Answer(answerName, answer_replies,author2,0);
		Answer testAnswer2= new Answer(answerName2,answer_replies,author2,0);
		Answer testAnswer3= new Answer(answerName3, answer_replies,author2,0);
		
		sameQuestion.addAnswer(testAnswer);
		sameQuestion.addAnswer(testAnswer2);
		sameQuestion.addAnswer(testAnswer3);
		
		
		questionList.set(question_index, sameQuestion);
		
		//Testing to see how many answers a question has received
		int question_index2= questionList.questionIndex(sameQuestion);
		Question sameQuestion2= questionList.get(question_index2);
		assertTrue("The answers this question received=3", sameQuestion2.getAnswerListSize()==3);
	}
	
	//use case 3
	public void testRepliesReceived() {
		QuestionList questionList= new QuestionList();
		String questionName= "A question?";
		AnswerList answerList= new AnswerList();
		ArrayList <Reply> question_replies= new ArrayList <Reply >();
		String author="A author";
		//Adding replies to question
		String reply_author= "I dunno";
		Reply q_reply= new Reply("Whats going on",reply_author);
		//question_replies.add(q_reply);
		
		Question questionTest= new Question(questionName, answerList,question_replies,author,0,false);
		questionTest.addQuestionReply(q_reply);
		questionList.add(questionTest);
		
		//Testing the questionIndex method of questionlist class
		int question_index= questionList.questionIndex(questionTest);
		assertTrue("question index is 0", question_index==0);
		
		//Adding an answer
		Question sameQuestion= questionList.get(question_index);
		ArrayList<Reply> answer_replies= new ArrayList<Reply>();
		String answerName= "An answer";
		
		//Add replies to answer
		//answer_replies.add("Answer reply 1");
		
		String answer_author= "Me";
		String answer_r_author= "7-11";
		Answer testAnswer= new Answer(answerName, answer_replies, answer_author,0);
		Reply a_reply= new Reply("Go to stackoverflow",answer_r_author);
		testAnswer.addReply(a_reply);
		
		sameQuestion.addAnswer(testAnswer);
		
		
		questionList.set(question_index, sameQuestion);
		
		//Testing to see how many answers a question has received
		int question_index2= questionList.questionIndex(sameQuestion);
		Question sameQuestion2= questionList.get(question_index2);
		assertTrue("The answers this question received=1", sameQuestion2.getAnswerListSize()==1);
		
		//See replies to question and answer
		
		assertTrue("Question replies = 1", sameQuestion2.getSizeReplies()==1);
		assertTrue(sameQuestion2.getReplies().contains(q_reply));
		
		AnswerList answers= sameQuestion2.getAnswerList();
		Answer testAnswer2= answers.getAnswer(0);
		assertTrue("Answer replies=1", testAnswer2.getSizeReplies()==1 );
		assertTrue(testAnswer2.getReplies().contains(a_reply));
	}
	
	//Use case 23
	
	public void testSetUserName() {
		QuestionList questionList= new QuestionList();
		String questionName= "How does this work?";
		AnswerList answerList=new AnswerList ();
		ArrayList <Reply> question_replies= new ArrayList <Reply >();
		String author= "Me";
		Question questionTest= new Question(questionName,answerList, question_replies,author,0,false);
		questionTest.setAuthor("Paul");
		//Testing set author name for question
		
		questionList.add(questionTest);
		String name= questionTest.getAuthor();
		assertTrue("Author name is Paul", questionTest.getAuthor().equals(name));
		

		int question_index= questionList.questionIndex(questionTest);
		assertTrue("question index is 0", question_index==0);
		
		//Adding an answer
		//Question sameQuestion= questionList.get(question_index);
		ArrayList<Reply> answer_replies= new ArrayList<Reply>();
		String answerName= "An answer";
		
		//Add replies to answer
		//answer_replies.add("Answer reply 1");
		
		String answer_author= "Me";
		String answer_r_author= "7-11";
		Answer testAnswer= new Answer(answerName, answer_replies, answer_author,0);
		
		String name2="Jack";
		testAnswer.setAuthor(name2);
		
		assertTrue("Answer author is jack",testAnswer.getAuthor().equals(name2));
		
		
		Reply a_reply= new Reply("Go to stackoverflow",answer_r_author);
		
		a_reply.setAuthor("Will");
		assertTrue ("reply author is Will", a_reply.getAuthor().equals("Will"));
		testAnswer.addReply(a_reply);
	}
	
	//Use case 6
	public void testReplyClarify() {
		QuestionList questionList= new QuestionList();
		String questionName= "A question?";
		AnswerList answerList= new AnswerList();
		ArrayList <Reply> question_replies= new ArrayList <Reply >();
		String author= "A author";
		
		//Add question_replies
		Question questionTest= new Question(questionName, answerList,question_replies,author,0,false);
		Reply a_reply=new Reply("What are you talking about", "Walter");
		questionTest.addQuestionReply(a_reply);
		
		assertTrue("Reply list isnt empty", questionTest.getSizeReplies()==1);
		
		questionList.add(questionTest);
		
		//Adding an answer

		ArrayList<Reply> answer_replies= new ArrayList<Reply>();
		String author2="Author 2";
		String answerName= "An answer";

		
		Answer testAnswer= new Answer(answerName, answer_replies,author2,0);
		Reply answer_reply= new Reply("What are you asking", "Jason");
		testAnswer.addReply(answer_reply);
		assertTrue("Reply list isn't empty", testAnswer.getSizeReplies()==1);
	
	}
	
	//Use case 12 and 13
	
	public void testUpvotes() {
		String questionName= "A question?";
		AnswerList answerList= new AnswerList();
		ArrayList <Reply> question_replies= new ArrayList <Reply >();
		String author= "A author";
		
		//Add question_replies
		Question questionTest= new Question(questionName, answerList,question_replies,author,0,false);
		questionTest.upvoteQuestion();
		assertTrue("Question upvoted by 1", questionTest.getUpvotes()==1);
		
		//Adding an answer

		ArrayList<Reply> answer_replies= new ArrayList<Reply>();
		String author2="Author 2";
		String answerName= "An answer";

		
		Answer testAnswer= new Answer(answerName, answer_replies,author2,0);
		testAnswer.upvoteAnswer();
		assertTrue("Answer upvoted by 1", testAnswer.getUpvotes()==1);
	}
	
	//Use case 2
	public void testViewQA() {
		String questionName= "A question?";
		AnswerList answerList= new AnswerList();
		ArrayList <Reply> question_replies= new ArrayList <Reply >();
		String author= "Me";
		Question questionTest= new Question(questionName,answerList, question_replies,author,0,false);
		

		
		//Adding an answer
		ArrayList<Reply> answer_replies= new ArrayList<Reply>();
		String answerName= "An answer";
		String authorName= "You";
		Answer testAnswer= new Answer(answerName, answer_replies,authorName,0);
		
		questionTest.addAnswer(testAnswer);
		
		//Testing to see if the answer_list of a question is not empty
		
		AnswerList question_answer= questionTest.getAnswerList();
		assertTrue("question_answer isn't empty", question_answer.size()==1);
	}
	
	//Use case 15
	
	public void testSearchQuestion() {
		QuestionList questionList= new QuestionList();
		MasterAnswerList master= new MasterAnswerList();
		String questionName= "A question?";
		AnswerList answerList= new AnswerList();
		ArrayList <Reply> question_replies= new ArrayList <Reply >();
		String author= "Me";
		Question questionTest= new Question(questionName,answerList, question_replies,author,0,false);
		questionList.add(questionTest);
		
		//Testing the questionIndex method of questionlist class
		int question_index= questionList.questionIndex(questionTest);
		assertTrue("question index is 0", question_index==0);
		
		//Adding an answer
		Question sameQuestion= questionList.get(question_index);
		ArrayList<Reply> answer_replies= new ArrayList<Reply>();
		String answerName= "An answer";
		String authorName= "You";
		Answer testAnswer= new Answer(answerName, answer_replies,authorName,0);
		
		sameQuestion.addAnswer(testAnswer);
		master.addAnswer(testAnswer);
		//Testing to see if the answer_list of a question is not empty
		questionList.set(question_index, sameQuestion);
		assertTrue("Answer List isn't empty",questionList.get(question_index).getAnswerListSize()==1 ) ;
		
		String search_question= "A question?";
		assertTrue("Search doesn't return null",questionList.search(search_question)!=null);
		
		String search_answer="An answer";
		assertTrue("Answer search doesn't return null",master.search(search_answer)!=null);
	}
	
	//Use case 18
	public void testSaveFavorites() {
		String questionName= "How does this work?";
		AnswerList answerList=new AnswerList ();
		ArrayList <Reply> question_replies= new ArrayList <Reply >();
		String author= "Me";
		Question questionTest= new Question(questionName,answerList, question_replies,author,0,false);
		
		Favorites favorite_questions= new Favorites();
		favorite_questions.add(questionTest);
		
		assertTrue("Favorites size is equal 1", favorite_questions.size()==1);
		
	}
	
	public void testhasPictures() {
		String questionName= "How does this work?";
		AnswerList answerList=new AnswerList ();
		ArrayList <Reply> question_replies= new ArrayList <Reply >();
		String author= "Me";
		Question questionTest= new Question(questionName,answerList, question_replies,author,0,false);
		//The question has a picture
		Question questionTest2= new Question(questionName,answerList, question_replies,author,0,true);
		QuestionList questionList= new QuestionList();
		questionList.add(questionTest);
		questionList.add(questionTest2);
		
		ArrayList<Question> questionlist2= questionList.sortByPictures();
		assertTrue("questionlist2 has pictures",questionlist2.size()==1 );
		

		
	}
	
	
	

}
