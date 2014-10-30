package ca.ualberta.cs.queueunderflow.test;

import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.QuestionList;
import junit.framework.TestCase;


public class UseCase1 extends TestCase
{
	//Use CASE 1 (incorporates user story 1 and 14)
	public void testBrowseQuestions() {
		
		QuestionList questionList= new QuestionList();
		String questionName= "A question?";
		String author= "A author";
		Question questionTest= new Question(questionName,author);
		questionList.add(questionTest);
		assertTrue ("Question list isn't empty, can browse questions", questionList.size()==1);
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
}
