package ca.ualberta.cs.queueunderflow.test.usecases;

import ca.ualberta.cs.queueunderflow.MasterAnswerList;
import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.QuestionList;
import junit.framework.TestCase;

public class UseCase13 extends TestCase {
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
}
