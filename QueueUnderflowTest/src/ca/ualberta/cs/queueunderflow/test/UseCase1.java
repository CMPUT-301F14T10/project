package ca.ualberta.cs.queueunderflow.test;

import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import ca.ualberta.cs.queueunderflow.ListHandler;
import ca.ualberta.cs.queueunderflow.R;
import ca.ualberta.cs.queueunderflow.adapters.QuestionListAdapter;
import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.QuestionList;
import ca.ualberta.cs.queueunderflow.views.MainActivity;

public class UseCase1 extends ActivityInstrumentationTestCase2<MainActivity>
{
	public UseCase1() {
		super(MainActivity.class);
		// TODO Auto-generated constructor stub
	}
	
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
		
		//testing exception 1 when there is no network connection
		String exception="";
		assertTrue("internet connection not available", questionList.getOnline()==false);
		if (questionList.getOnline()==false){
			exception="internet connection not available";
		}
		assertEquals("internet connection not available", exception);
	}
	
	public void testBrowseQuestionsDisplay() {
		Question question1 = new Question("First Q", "me");
		Question question2 = new Question("Second Q", "you");
		Question question3 = new Question("Third Q", "him");
		
		QuestionList questionList = ListHandler.getMasterQList();
		questionList.add(question1);
		questionList.add(question2);
		questionList.add(question3);
		
		// Start activity & get the listview & adapter
		MainActivity activity = getActivity();
		ListView listView = (ListView) activity.findViewById(R.id.homeListView);
		QuestionListAdapter adapter = (QuestionListAdapter) listView.getAdapter();
		
		// Check that the number if items in the adapter is correct
		assertTrue(adapter.getCount() == 3);
		
		// Check that the view for each Question / List Item is correctly displayed
		View view1 = adapter.getView(0, null, null);
		TextView questionText1 = (TextView) view1.findViewById(R.id.questionTextView);
		TextView authorUsername1 = (TextView) view1.findViewById(R.id.authorTextView);
		assertEquals(question3.getQuestion(), questionText1.getText().toString());
		assertEquals(question3.getAuthor(), authorUsername1.getText().toString());
		
		View view2 = adapter.getView(1, null, null);
		TextView questionText2 = (TextView) view2.findViewById(R.id.questionTextView);
		TextView authorUsername2 = (TextView) view2.findViewById(R.id.authorTextView);
		assertEquals(question2.getQuestion(), questionText2.getText().toString());
		assertEquals(question2.getAuthor(), authorUsername2.getText().toString());
		
		View view3 = adapter.getView(2, null, null);
		TextView questionText3 = (TextView) view3.findViewById(R.id.questionTextView);
		TextView authorUsername3 = (TextView) view3.findViewById(R.id.authorTextView);
		assertEquals(question1.getQuestion(), questionText3.getText().toString());
		assertEquals(question1.getAuthor(), authorUsername3.getText().toString());

	}
}
