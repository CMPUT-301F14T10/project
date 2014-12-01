package ca.ualberta.cs.queueunderflow.test.views;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ExpandableListView;
import ca.ualberta.cs.queueunderflow.R;
import ca.ualberta.cs.queueunderflow.adapters.AnswerListAdapter;
import ca.ualberta.cs.queueunderflow.adapters.SingleQuestionAdapter;
import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.Reply;
import ca.ualberta.cs.queueunderflow.singletons.ListHandler;
import ca.ualberta.cs.queueunderflow.singletons.NetworkManager;
import ca.ualberta.cs.queueunderflow.views.QAViewActivity;

public class QAViewActivityTest2 extends ActivityInstrumentationTestCase2<QAViewActivity>{

	Reply qReply;
	Reply aReply;
	
	public QAViewActivityTest2() {
		super(QAViewActivity.class);
	}

	public void setUp() {
		// Set network connectivity to false, else it'll grab the list from the network
		NetworkManager networkManager = NetworkManager.getInstance();
		networkManager.setOnline(false);
		
		ListHandler.getMasterQList().clear();
		addTestQuestion();
	}
	
	public void testReplyDisplay() {
		Intent intent = new Intent();
		intent.putExtra("fromFragment", QAViewActivity.HOME_SCREEN_FRAGMENT);
		intent.putExtra("position", 0);
		setActivityIntent(intent);
		QAViewActivity activity = (QAViewActivity) getActivity();
		
		ExpandableListView questionListView = (ExpandableListView) activity.findViewById(R.id.questionExpListView);
		ExpandableListView answersListView = (ExpandableListView) activity.findViewById(R.id.answersExpListView);
		
		assertTrue(questionListView.getCount() == 1);
		assertTrue(answersListView.getCount() == 1);
	
		SingleQuestionAdapter singleQAdapter = (SingleQuestionAdapter) questionListView.getExpandableListAdapter();
		Question question = singleQAdapter.getGroup(0);
		assertTrue(singleQAdapter.getChildrenCount(0) == 1);
		Reply resultQReply = singleQAdapter.getChild(0, 0);
		assertEquals(resultQReply, qReply);
		
		AnswerListAdapter answersAdapter = (AnswerListAdapter) answersListView.getExpandableListAdapter();
		Answer answer = answersAdapter.getGroup(0);
		assertTrue(answersAdapter.getChildrenCount(0) == 1);
		Reply resultAReply = answersAdapter.getChild(0, 0);
		assertEquals(resultAReply, aReply);
	}
	
	public void tearDown() {
		ListHandler.getMasterQList().clear();
	}
	
	private void addTestQuestion() {
		Question question = new Question("Main Question", "UserA");
		qReply = new Reply("A question reply", "UserB");
		Answer answer = new Answer("An answer", "UserC");
		aReply = new Reply("An answer reply", "UserD");
		
		question.addReply(qReply);
		answer.addReply(aReply);
		question.addAnswer(answer);
		
		ListHandler.getMasterQList().add(question);
		
		assertTrue(ListHandler.getMasterQList().size() == 1);
	}

}
