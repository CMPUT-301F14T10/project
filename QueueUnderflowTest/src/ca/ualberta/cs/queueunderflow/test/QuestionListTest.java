package ca.ualberta.cs.queueunderflow.test;



import java.util.ArrayList;

import junit.framework.TestCase;
import ca.ualberta.cs.queueunderflow.Answer;
import ca.ualberta.cs.queueunderflow.AnswerList;
import ca.ualberta.cs.queueunderflow.QuestionList;
import ca.ualberta.cs.queueunderflow.Question;

public class QuestionListTest extends TestCase {
	
	public void testAddQuestion() {
		QuestionList questionList= new QuestionList();
		String questionName= "How does this work?";
		AnswerList answerList=new AnswerList ();
		Question questionTest= new Question(questionName,answerList);
		questionList.add(questionTest);
		
		assertTrue("Question List isn't empty", questionList.size()==1);
	}
	
	public void testAnswerQuestion() {
		QuestionList questionList= new QuestionList();
		String questionName= "A question?";
		AnswerList answerList= new AnswerList();
		Question questionTest= new Question(questionName, answerList);
		questionList.add(questionTest);
		
		//Testing the questionIndex method of questionlist class
		int question_index= questionList.questionIndex(questionTest);
		assertTrue("question index is 0", question_index==0);
		
		Question sameQuestion= questionList.get(question_index);
		ArrayList<String> reply_list= new ArrayList<String>();
		String answerName= "An answer";
		Answer testAnswer= new Answer(answerName, reply_list);
		
		sameQuestion.addAnswer(testAnswer);
		
		//Testing to see if the answer_list of a question is not empty
		questionList.set(question_index, sameQuestion);
		assertTrue("Answer List isn't empty",questionList.get(question_index).getAnswerListSize()==1 ) ;
	
		
	}
	

}
