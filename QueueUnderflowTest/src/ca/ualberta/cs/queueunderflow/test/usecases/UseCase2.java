package ca.ualberta.cs.queueunderflow.test.usecases;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import ca.ualberta.cs.queueunderflow.R;
import ca.ualberta.cs.queueunderflow.adapters.AnswerListAdapter;
import ca.ualberta.cs.queueunderflow.adapters.SingleQuestionAdapter;
import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.AnswerList;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.singletons.ListHandler;
import ca.ualberta.cs.queueunderflow.singletons.NetworkManager;
import ca.ualberta.cs.queueunderflow.views.QAViewActivity;

public class UseCase2 extends ActivityInstrumentationTestCase2<QAViewActivity> {

	public UseCase2() {
		super(QAViewActivity.class);
	}
	
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
	
}
