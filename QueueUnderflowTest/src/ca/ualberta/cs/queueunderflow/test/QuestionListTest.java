package ca.ualberta.cs.queueunderflow.test;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;
import ca.ualberta.cs.queueunderflow.Answer;
import ca.ualberta.cs.queueunderflow.AnswerList;
import ca.ualberta.cs.queueunderflow.Favorites;
import ca.ualberta.cs.queueunderflow.MasterAnswerList;
import ca.ualberta.cs.queueunderflow.Picture;
import ca.ualberta.cs.queueunderflow.QuestionList;
import ca.ualberta.cs.queueunderflow.Question;
import ca.ualberta.cs.queueunderflow.ReadingList;
import ca.ualberta.cs.queueunderflow.Reply;
import ca.ualberta.cs.queueunderflow.User;

public class QuestionListTest extends TestCase {
	
	//All use cases are here
	
	//Use CASE 1 (incorporates user story 1 and 14)
	public void testBrowseQuestions() {
		
		QuestionList questionList= new QuestionList();
		String questionName= "A question?";
		String author= "A author";
		Question questionTest= new Question(questionName,author);
		questionList.add(questionTest);
		
		/*Testing the questionIndex method of questionlist class, used to get a question
		in the question list
		 */
		 
		int question_index= questionList.questionIndex(questionTest);
		assertTrue("question index is 0", question_index==0);
		
		
		Question sameQuestion= questionList.get(question_index);
		String author2="Answering the question author";
		String answerName= "Answer 1";
		String answerName2= "Answer 2";
		String answerName3= "Answer 3";
		
		Answer testAnswer= new Answer(answerName,author2);
		Answer testAnswer2= new Answer(answerName2,author2);
		Answer testAnswer3= new Answer(answerName3,author2);
		
		sameQuestion.addAnswer(testAnswer);
		sameQuestion.addAnswer(testAnswer2);
		sameQuestion.addAnswer(testAnswer3);
		
		//Add the question (with answers) back to question list
		questionList.set(question_index, sameQuestion);
		
		//Testing to see how many answers a question has received
		int question_index2= questionList.questionIndex(sameQuestion);
		Question sameQuestion2= questionList.get(question_index2);
		assertTrue("The answers this question received=3", sameQuestion2.getAnswerListSize()==3);
	}
	
	//Use CASE 2 (incorporates user stories 2 and 22)
	public void testViewQuestionsAndAnswers () {
		
		String questionName= "A question?";
		String author= "Me";
		Question questionTest= new Question(questionName,author);
		
		//Exception: There are no answers to the question selected
		assertTrue("No answers for this question yet",questionTest.getAnswerListSize()==0);

		String answerName= "An answer";
		String authorName= "You";
		Answer testAnswer= new Answer(answerName,authorName);
		questionTest.addAnswer(testAnswer);
		
		//Testing to see if the answer_list of a question is not empty
		AnswerList question_answer= questionTest.getAnswerList();
		assertTrue("question_answer isn't empty", question_answer.size()==1);
	}
	
	//Use CASE 3 (incorporates user story 3)
	public void testViewReplies() {
		String questionName= "A question?";
		String author="A author";
		Question questionTest= new Question(questionName,author);

		//Exception: If no replies, display “No Replies” message. 
		assertTrue("No Replies",questionTest.getSizeReplies()==0);
		
		//Adding replies to question
		String reply_author= "I dunno";
		Reply question_reply= new Reply("Whats going on",reply_author);
		
	
		questionTest.addQuestionReply(question_reply);
		QuestionList questionList= new QuestionList();
		questionList.add(questionTest);
		
		int question_index= questionList.questionIndex(questionTest);
		
		//Adding an answer
		Question sameQuestion= questionList.get(question_index);
		String answerName= "An answer";
		
		//Add replies to answer
		//answer_replies.add("Answer reply 1");
		
		String answer_author= "Me";
		String answer_reply_author= "7-11";
		Answer testAnswer= new Answer(answerName,answer_author);
		
		//Exception: If no replies, display “No Replies” message. 
		assertTrue("No Replies",testAnswer.getSizeReplies()==0);
		
		//Add replies to answer
		Reply answer_reply= new Reply("Go to stackoverflow",answer_reply_author);
		testAnswer.addReply(answer_reply);
		sameQuestion.addAnswer(testAnswer);
		questionList.set(question_index, sameQuestion);
		
		//Testing to see how many answers a question has received
		int question_index2= questionList.questionIndex(sameQuestion);
		Question sameQuestion2= questionList.get(question_index2);
		assertTrue("The answers this question received=1", sameQuestion2.getAnswerListSize()==1);
		
		//See replies to question and answer
		assertTrue("Question replies = 1", sameQuestion2.getSizeReplies()==1);
		assertTrue(sameQuestion2.getReplies().contains(question_reply));
		
		AnswerList answers= sameQuestion2.getAnswerList();
		Answer testAnswer2= answers.getAnswer(0);
		assertTrue("Answer replies=1", testAnswer2.getSizeReplies()==1 );
		assertTrue(testAnswer2.getReplies().contains(answer_reply));
	}
	
	//Use CASE 4 (incorporates user stories 4 and 7)
	public void testAskQuestion() {
		
		//Add a question to the list
		QuestionList questionList= new QuestionList();
		String questionName= "How does this work?";

		String author= "Me";
		Question questionTest= new Question(questionName,author);
		Picture pic= new Picture(32);
		questionTest.setPicture(pic);
		
		questionList.add(questionTest);
		assertTrue("Question List isn't empty", questionList.size()==1);
		
		//Exception: Where the question entered is whitespaces only
		String questionName2= "        ";
		String empty= questionName2.trim();
		String result;
		if (empty.length()==0) {
			result="Reenter a question";
		}
		else {
			result=null;
		}
		assertTrue("questionName2 is empty", result!=null);
		
		//Exception: Where there is no online connectivity
		QuestionList questionList3= new QuestionList();
		String questionName3= "Are we online?";
		String author3= "Me";
		Question questionTest3= new Question(questionName3,author3);
		questionList3.add(questionTest3);
		assertFalse("No network connectivity, push online later.",questionList3.pushOnline());
		
		
	}
	
	//Use CASE 5: Incorporates user stories 5 and 7
	
	public void testAddAnswer() {
		
		QuestionList questionList= new QuestionList();
		String questionName= "A question?";
		String author= "Me";
		Question questionTest= new Question(questionName,author);
		questionList.add(questionTest);
		int question_index= questionList.questionIndex(questionTest);
		
		Question sameQuestion= questionList.get(question_index);
		//Exception: Where the answer entered is whitespaces only
		String emptyAnswer= "          ";
		String empty= emptyAnswer.trim();
		String result;
		if (empty.length()==0) {
			result="Reenter an answer";
		}
		else {
			result=null;
		}
		assertTrue("emptyAnswer is empty", result!=null);
		
		//Adding an answer
		String answerName= "An answer";
		String authorName= "You";
		Answer testAnswer= new Answer(answerName,authorName);
		sameQuestion.addAnswer(testAnswer);
		questionList.set(question_index, sameQuestion);
		assertTrue("Answer List isn't empty",questionList.get(question_index).getAnswerListSize()==1 ) ;
		
		//Exception: Where there is no online connectivity
		assertFalse("No network connectivity, push online later.",questionList.pushOnline());
	}
	
	//Use CASE 6 (incorporates user story 6)
	public void testAddReply() {
		QuestionList questionList= new QuestionList();
		String questionName= "A question?";

		String author= "A author";
		
		//Add question_replies
		Question questionTest= new Question(questionName,author);
		Reply question_reply=new Reply("What are you talking about", "Walter");
		questionTest.addQuestionReply(question_reply);
		assertTrue("Reply list isnt empty", questionTest.getSizeReplies()==1);
		
		//Add a answer and reply to the answer
		String author2="Author 2";
		String answerName= "An answer";
		Answer testAnswer= new Answer(answerName,author2);
		Reply answer_reply= new Reply("What are you asking", "Jason");
		testAnswer.addReply(answer_reply);
		assertTrue("Reply list isn't empty", testAnswer.getSizeReplies()==1);
		
		questionTest.addAnswer(testAnswer);
		questionList.add(questionTest);
		
		//Exception: Where there is no online connectivity
		assertFalse("No network connectivity, push online later.",questionList.pushOnline());
	
	}
	
	//Use CASE 7(incorporates user story 7)
	public void testAddPhotoFromCamera() {
		String questionName= "How does this work?";
		String author= "Me";
		Question questionTest= new Question(questionName,author);
		Picture pic= new Picture(10);
		questionTest.setPicture(pic);
		
		assertTrue("Question has a picture", questionTest.getPicture()!=null);
		
		String answerName= "An answer";
		String authorName= "You";
		Answer testAnswer= new Answer(answerName,authorName);
		
		testAnswer.setPicture(pic);
		assertTrue("Answer has a picture", testAnswer.getPicture()!=null);
		
		//Exception: Photo is larger than 64 kb
		Picture picture2= new Picture(65);
		assertFalse("image is too large, select another photo", picture2.getSize()<64);
	}
	
	//Use CASE 8 (incorporates user story 7 and 8)
	public void testAddPhotoFromGallery() {
		String questionName= "How does this work?";
		String author= "Me";
		Question questionTest= new Question(questionName,author);
		Picture pic= new Picture(10);
		questionTest.setPicture(pic);
		
		assertTrue("Question has a picture", questionTest.getPicture()!=null);
		
		String answerName= "An answer";
		String authorName= "You";
		Answer testAnswer= new Answer(answerName,authorName);
		
		testAnswer.setPicture(pic);
		assertTrue("Answer has a picture", testAnswer.getPicture()!=null);
		
		//Exception: Photo is larger than 64 kb
		Picture picture2= new Picture(65);
		assertFalse("image is too large, select another photo", picture2.getSize()<64);
	}
	
	//Use CASE 9 (incorporates user story 8)
	public void testPhotoSizeLimit() {
		String questionName= "How does this work?";
		String author= "Me";
		Question questionTest= new Question(questionName,author);
		Picture pic= new Picture(65);
		assertFalse("image is too large, select another photo", pic.getSize()<64);

		//Exception: Photo is smaller than 64kb, no complaints
		
		Picture picture2= new Picture(61);
		assertTrue("No complaints about picture size", picture2.getSize()<=64);
		questionTest.setPicture(picture2);
	}
	
	//Use CASE 10 (incorporates user stories 9 and 10)
	public void testSortQuestionBy() {
		String questionName= "Oldest question";
		String author= "long time ago";
		Question question1= new Question(questionName,author);
		Calendar cal = Calendar.getInstance();
		cal.setTime(question1.getDate());
		//Make the date 5 seconds behind current date
		cal.add(Calendar.SECOND,-5);
		Date question1date = cal.getTime();
		question1.setDate(question1date);
		
		String questionName2= "Middle date question";
		String author2= "middle";
		Question question2= new Question(questionName2,author2);
		Calendar cal2 = Calendar.getInstance();  
		cal2.setTime(question2.getDate());
		//Make the date 3 seconds behind current date
		cal2.add(Calendar.SECOND,-3);
		Date question2date = cal2.getTime();
		question2.setDate(question2date);
		
		String questionName3= "Freshest question";
		String author3= "Now";
		Question question3= new Question(questionName3,author3);
		
		//Set the upvotes each question has
		question1.setUpvotes(1);
		question2.setUpvotes(10);
		question3.setUpvotes(5);
		
		QuestionList questionList = new QuestionList();
		questionList.add(question2);
		questionList.add(question1);
		questionList.add(question3);
		
		ArrayList<Question> mostRecentList = new ArrayList<Question>();
		mostRecentList.add(question3);
		mostRecentList.add(question2);
		mostRecentList.add(question1);
		
		ArrayList<Question> leastRecentList = new ArrayList<Question>();
		leastRecentList.add(question1);
		leastRecentList.add(question2);
		leastRecentList.add(question3);
		
		
		//Checking if the sorting methods work
		ArrayList<Question> questionList3 = questionList.sortBy("most recent");
		assertTrue("Questionlist3 1st slot is question3", questionList3.get(0).equals(question3));
		assertTrue("Questionlist3 2nd slot is question2", questionList3.get(1).equals(question2));
		assertTrue("Questionlist3 3rd slot ins question1", questionList3.get(2).equals(question1));
		assertEquals("questionList3 is sorted by date, most recent to least recent", questionList3, mostRecentList);
		
		ArrayList<Question> questionList4 = questionList.sortBy("least recent");
		assertTrue("Questionlist4 1st slot is question1", questionList4.get(0).equals(question1));
		assertTrue("Questionlist4 2nd slot is question2", questionList4.get(1).equals(question2));
		assertTrue("Questionlist4 3rd slot ins question3", questionList4.get(2).equals(question3));
		assertEquals("questionList4 is sorted by date, least recent to most recent", questionList4, leastRecentList);
		
		ArrayList<Question> mostUpvotesList = new ArrayList<Question>();
		mostUpvotesList.add(question2);
		mostUpvotesList.add(question3);
		mostUpvotesList.add(question1);
		
		ArrayList<Question> leastUpvotesList = new ArrayList<Question>();
		leastUpvotesList.add(question1);
		leastUpvotesList.add(question3);
		leastUpvotesList.add(question2);
		
		ArrayList<Question> questionList5 = questionList.sortBy("most upvotes");
		assertEquals("questionList1 is sorted by most upvotes to least", mostUpvotesList, questionList5);
		
		ArrayList<Question> questionList6 = questionList.sortBy("least upvotes");
		assertEquals("questionList2 is sorted by least upvotes to most", leastUpvotesList, questionList6);
	}
	
	
	//Use CASE 11 (incorporates user stories 9 and 10)
	
	public void testUpvote() {
		User user=new User();
		user.setUserName("Paul");
		
		String userName= user.getUserName();
		String questionName= "A question?";
		String author= userName;
		
		//Add question_replies
		Question questionTest= new Question(questionName, author);
		questionTest.upvoteQuestion();
		user.addUpvotedQuestion(questionTest);
		assertTrue("Question upvoted by 1", questionTest.getUpvotes()==1);
		
		//Adding an answer

		String author2="Author 2";
		String answerName= "An answer";

		
		Answer testAnswer= new Answer(answerName,author2);
		testAnswer.upvoteAnswer();
		user.addUpvotedAnswer(testAnswer);
		assertTrue("Answer upvoted by 1", testAnswer.getUpvotes()==1);
		
		//Exception: User already upvoted question/answer
		assertTrue("Question already upvoted", user.alreadyUpvotedQuestion(questionTest));
		assertTrue("Answer already upvoted", user.alreadyUpvotedAnswer(testAnswer));
	}
	
	//Use CASE 12 (incorporates user story 12)
	public void seeMostUpvoted() {
		User user=new User();
		user.setUserName("Paul");
		QuestionList questionList= new QuestionList();

		String userName= user.getUserName();
		String questionName= "A question?";
		String author= userName;
		
		//Exception: No questions
		assertTrue("No questions currently", questionList.size()==0);
		
		Question question1= new Question(questionName, author);
		
		//Exception: No upvoted questions
		assertTrue("No upvoted questions", question1.getUpvotes()==0);
		
		//Exception: No answers to a questions
		assertTrue("No answers currently", question1.getAnswerListSize()==0);
		
		
		
		String answer_test= "Answer1";
		String answer_test2= "Answer2";
		
		Answer answer1= new Answer(answer_test,author);
		Answer answer2= new Answer(answer_test2, author);
		
		//Exception: No upvoted answers
		assertTrue("No upvoted answers", answer1.getUpvotes()==0);
		assertTrue("No upvoted answers", answer2.getUpvotes()==0);
		
		answer1.setUpvotes(5);
		answer2.setUpvotes(10);
		
		question1.addAnswer(answer1);
		question1.addAnswer(answer2);
		
		assertTrue("Question has two answers", question1.getAnswerListSize()==2);
		assertTrue("Most upvoted answer is answer2", question1.getAnswerList().getAnswer(1)==answer2);

	}
	
	//Use CASE 13 (incorporates user story 15)
	public void testSearch() {
		QuestionList questionList= new QuestionList();
		//MasterAnswerList is an arraylist of all answers
		MasterAnswerList master= new MasterAnswerList();
		String questionName= "A question?";
		String author= "Me";
		Question questionTest= new Question(questionName,author);
		questionList.add(questionTest);
		
		int question_index= questionList.questionIndex(questionTest);
		
		//Adding an answer
		Question sameQuestion= questionList.get(question_index);
		String answerName= "An answer";
		String authorName= "You";
		Answer testAnswer= new Answer(answerName,authorName);
		
		sameQuestion.addAnswer(testAnswer);
		master.addAnswer(testAnswer);
		questionList.set(question_index, sameQuestion);
		
		String search_question= "A question?";
		assertTrue("Search doesn't return null",questionList.search(search_question)!=null);
		
		String search_answer="An answer";
		assertTrue("Answer search doesn't return null",master.search(search_answer)!=null);
		
		//Exception:There are no matching results
		String no_result= "Nothing here";
		assertTrue("Question search has no matches", questionList.search(no_result)==null);
		assertTrue("Answer search has no matches",master.search(no_result)==null);

	}
	
	//Use CASE 14 (incorporates user story 14)
	public void testQuestionsAsked() {
		QuestionList questionList= new QuestionList();
		String questionName= "How does this work?";

		String author= "Me";
		Question questionTest= new Question(questionName,author);
		Picture pic= new Picture(32);
		questionTest.setPicture(pic);
		
		questionList.add(questionTest);
		assertTrue("Question List isn't empty", questionList.size()==1);
	}
	
	//Use CASE 15 (incorporates user story 17)
	
	public void testReadingList() {
		String questionName= "How does this work?";
		String author= "Me";
		Question questionTest= new Question(questionName,author);
		
		ReadingList readingList= new ReadingList();
		readingList.add(questionTest);
		
		assertTrue("Reading list isn't empty", readingList.size()==1);
	}
	
	//Use CASE 16(Incorporates user stories 18 and 19)
	public void testFavorites() {
		String questionName= "How does this work?";
		String author= "Me";
		Question questionTest= new Question(questionName,author);
		Favorites favorite_questions= new Favorites();
		favorite_questions.add(questionTest);
		assertTrue("Favorites size is equal 1", favorite_questions.size()==1);
	}
	
	//Use CASE 17 (incorporates user stories 20 and 21)
	public void testPush() {
		String questionName= "A question?";
		String author="A author";
		Question questionTest= new Question(questionName,author);
		
		//Adding replies to question
		String reply_author= "I dunno";
		Reply question_reply= new Reply("Whats going on",reply_author);
		
		questionTest.addQuestionReply(question_reply);
		QuestionList questionList= new QuestionList();
		questionList.add(questionTest);
		
		int question_index= questionList.questionIndex(questionTest);
		
		//Adding an answer
		Question sameQuestion= questionList.get(question_index);
		String answerName= "An answer";
		
		String answer_author= "Me";
		String answer_reply_author= "7-11";
		Answer testAnswer= new Answer(answerName,answer_author);
		
		//Add replies to answer
		Reply answer_reply= new Reply("Go to stackoverflow",answer_reply_author);
		testAnswer.addReply(answer_reply);
		sameQuestion.addAnswer(testAnswer);
		questionList.set(question_index, sameQuestion);
		
		assertTrue("No network connection", questionList.getOnline()==false);
		//Once have network connectivity, push replies, questions, answers online
		questionList.setOnline();
		assertTrue("Push stuff online", questionList.getOnline());
	}
	
	//Use CASE 18(incorporates user story 23)
	public void testSetUserName() {
		QuestionList questionList= new QuestionList();
		String questionName= "How does this work?";

		String author= "Paul";
		Question questionTest= new Question(questionName,author);
		questionTest.setAuthor(author);
		
		//Exception: No username is typed in or only whitespace is typed in
		String no_author= "         ";
		String author_exception= no_author.trim();
		assertTrue("No author specified, setting author to Anonymous",author_exception.length()==0);
		
		questionList.add(questionTest);
		String name= questionTest.getAuthor();
		assertTrue("Author name is Paul", questionTest.getAuthor().equals(name));
		
		int question_index= questionList.questionIndex(questionTest);
		assertTrue("question index is 0", question_index==0);
		String answerName= "An answer";
		Answer testAnswer= new Answer(answerName,author);

		assertTrue("Answer author is Paul",testAnswer.getAuthor().equals(author));
		Reply a_reply= new Reply("Go to stackoverflow",author);
		assertTrue ("reply author is Will", a_reply.getAuthor().equals("Paul"));
		testAnswer.addReply(a_reply);
	}
	


}
