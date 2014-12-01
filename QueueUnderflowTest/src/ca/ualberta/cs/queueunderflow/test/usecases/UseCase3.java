package ca.ualberta.cs.queueunderflow.test.usecases;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import ca.ualberta.cs.queueunderflow.ListHandler;
import ca.ualberta.cs.queueunderflow.NetworkManager;
import ca.ualberta.cs.queueunderflow.R;
import ca.ualberta.cs.queueunderflow.adapters.AnswerListAdapter;
import ca.ualberta.cs.queueunderflow.adapters.SingleQuestionAdapter;
import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.AnswerList;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.QuestionList;
import ca.ualberta.cs.queueunderflow.models.Reply;
import ca.ualberta.cs.queueunderflow.views.QAViewActivity;
import junit.framework.TestCase;

//Use CASE 3 (incorporates user story 3)
public class UseCase3 extends ActivityInstrumentationTestCase2<QAViewActivity> {
	
	public UseCase3() {
		super(QAViewActivity.class);
	}
	
	public void setUp() {
		// Set network connectivity to false, else it'll grab the list from the network
		NetworkManager networkManager = NetworkManager.getInstance();
		networkManager.setOnline(false);
	}
	
	public void testReplyDisplay() {
		ListHandler.getMasterQList().getQuestionList().clear();
		
		Question question = new Question("Another Question", "Me");
		Answer answer1 = new Answer("Answer #1", "You");
		Answer answer2 = new Answer("Answer #2", "Him");
		Reply aReply = new Reply("Answer Reply", "Her");
		Reply qReply = new Reply("Question Reply", "Them");
		answer1.addReply(aReply);
		question.addReply(qReply);
		question.addAnswer(answer1);
		question.addAnswer(answer2);
		ListHandler.getMasterQList().add(question);
		
		// Start the activity
		Intent intent = new Intent();
		intent.putExtra("fromFragment", QAViewActivity.HOME_SCREEN_FRAGMENT);
		intent.putExtra("position", 0);
		setActivityIntent(intent);
		QAViewActivity activity = (QAViewActivity) getActivity();
		
		// Check question reply display
		ExpandableListView qListView = (ExpandableListView) activity.findViewById(R.id.questionExpListView);
		SingleQuestionAdapter singleQAdapter = (SingleQuestionAdapter) qListView.getExpandableListAdapter();
		
		assertEquals(question, singleQAdapter.getGroup(0));
		assertEquals(1, singleQAdapter.getChildrenCount(0));
		
		View replyView = singleQAdapter.getChildView(0, 0, true, null, null);
		TextView replyText = (TextView) replyView.findViewById(R.id.replyTextView);
		TextView authorUsername = (TextView) replyView.findViewById(R.id.authorTextView);
		assertEquals("Question Reply", replyText.getText().toString());
		assertEquals("Them", authorUsername.getText().toString());
		
		
		// Check answer reply display
		ExpandableListView aListView = (ExpandableListView) activity.findViewById(R.id.answersExpListView);
		AnswerListAdapter aAdapter = (AnswerListAdapter) aListView.getExpandableListAdapter();
		
		assertEquals(answer1, aAdapter.getGroup(1));
		assertEquals(1, aAdapter.getChildrenCount(1));
		
		View replyView1 = aAdapter.getChildView(1, 0, true, null, null);
		TextView replyText1 = (TextView) replyView1.findViewById(R.id.replyTextView);
		TextView authorUsername1 = (TextView) replyView1.findViewById(R.id.authorTextView);
		assertEquals("Answer Reply", replyText1.getText().toString());
		assertEquals("Her", authorUsername1.getText().toString());
		
		activity.finish();
	}
	
	
	public void testViewReplies() {
		String questionName= "A question?";
		String author="A author";
		Question questionTest= new Question(questionName,author);

		//Exception: If no replies to the question, display No Replies message. 
		assertTrue("No Replies",questionTest.getSizeReplies()==0);
		
		//Adding replies to question
		String reply_author= "I dunno";
		Reply question_reply= new Reply("Whats going on",reply_author);
	
		questionTest.addReply(question_reply);
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
