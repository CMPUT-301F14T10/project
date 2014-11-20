package ca.ualberta.cs.queueunderflow.test.views;

import ca.ualberta.cs.queueunderflow.ListHandler;
import ca.ualberta.cs.queueunderflow.R;
import ca.ualberta.cs.queueunderflow.adapters.AnswerListAdapter;
import ca.ualberta.cs.queueunderflow.adapters.SingleQuestionAdapter;
import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.Reply;
import ca.ualberta.cs.queueunderflow.views.QAViewActivity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class QAViewActivityTest2 extends ActivityInstrumentationTestCase2<QAViewActivity>{

	public QAViewActivityTest2() {
		super(QAViewActivity.class);
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

}
