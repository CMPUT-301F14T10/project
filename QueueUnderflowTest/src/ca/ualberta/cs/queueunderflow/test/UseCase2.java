package ca.ualberta.cs.queueunderflow.test;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;
import ca.ualberta.cs.queueunderflow.ListHandler;
import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.AnswerList;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.views.QAViewActivity;


public class UseCase2 extends ActivityInstrumentationTestCase2<QAViewActivity>
{
	public UseCase2() {
		super(QAViewActivity.class);
	}

	//Use CASE 2 (incorporates user stories 2, 14 and 22)
	
	public void testViewNumberOfAnswers() {
		Question question = new Question("First Question", "Me");
		Answer answer1 = new Answer("Answer #1", "You");
		Answer answer2 = new Answer("Answer #2", "Him");
		question.addAnswer(answer1);
		question.addAnswer(answer2);
		ListHandler.getMasterQList().add(question);
		
		Intent intent = new Intent();
		intent.putExtra("fromFragment", QAViewActivity.HOME_SCREEN_FRAGMENT);
		intent.putExtra("position", 0);
		setActivityIntent(intent);
		QAViewActivity activity = (QAViewActivity) getActivity();
		
		TextView answersCount = (TextView) activity.findViewById(ca.ualberta.cs.queueunderflow.R.id.answersCount);
		assertTrue("answer count is correct", Integer.toString(2) == answersCount.getText().toString());
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
