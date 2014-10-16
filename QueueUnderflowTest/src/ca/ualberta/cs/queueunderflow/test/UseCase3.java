package ca.ualberta.cs.queueunderflow.test;

import ca.ualberta.cs.queueunderflow.Answer;
import ca.ualberta.cs.queueunderflow.AnswerList;
import ca.ualberta.cs.queueunderflow.Question;
import ca.ualberta.cs.queueunderflow.QuestionList;
import ca.ualberta.cs.queueunderflow.Reply;
import junit.framework.TestCase;


public class UseCase3 extends TestCase
{
	//Use CASE 3 (incorporates user story 3)
	public void testViewReplies() {
		String questionName= "A question?";
		String author="A author";
		Question questionTest= new Question(questionName,author);

		//Exception: If no replies to the question, display No Replies message. 
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
		String answer_author= "Me";
		String answer_reply_author= "7-11";
		Answer testAnswer= new Answer(answerName,answer_author);
		
		//Exception: If no replies to the answer, display No Replies message. 
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
}
