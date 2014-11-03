package ca.ualberta.cs.queueunderflow.controllers;

import ca.ualberta.cs.queueunderflow.ListHandler;
import ca.ualberta.cs.queueunderflow.LoadSave;
import ca.ualberta.cs.queueunderflow.User;
import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.QuestionList;
import android.app.Activity;
import android.widget.EditText;
import android.widget.Toast;

public class AskAnswerController {

	public static final int HOME_SCREEN_FRAGMENT = 1;
	public static final int FAVORITES_FRAGMENT = 2;
	public static final int MY_QUESTIONS_FRAGMENT = 3;
	public static final int READING_LIST_FRAGMENT = 4;
	
	private Activity activity;	// This is so we can make Toast messages
	
	public AskAnswerController(Activity activity) {
		this.activity = activity;
	}

	public void askQuestion(EditText questionInput) {
		try {
			Question newQuestion = new Question(questionInput.getText().toString(), User.getUserName());
			QuestionList homeScreenList = ListHandler.getMasterQList();
			homeScreenList.add(newQuestion);
			
			QuestionList myQuestionsList = ListHandler.getMyQsList();
			myQuestionsList.add(newQuestion);
			
			//myQList has changed. Save to file.
			LoadSave lSave = new LoadSave();
			lSave.saveMyQuestions();
			
			activity.finish();
		} catch (IllegalArgumentException e) {
			Toast.makeText(activity.getApplicationContext(), "Invalid question. Please re-enter a question.", Toast.LENGTH_SHORT).show();
		}
	}

	public void addAnswer(int fromFragment, int position, EditText answerInput) {
		try {
			Answer newAnswer = new Answer(answerInput.getText().toString(), User.getUserName());
			QuestionList questionList = findQuestionList(fromFragment);
			Question question = questionList.get(position);
			question.addAnswer(newAnswer);
			questionList.set(position, question);
			activity.finish();
		} catch (IllegalArgumentException e) {
			Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}
	
    private QuestionList findQuestionList(int fromFragment) {
		switch (fromFragment) {
		case (HOME_SCREEN_FRAGMENT):
			return ListHandler.getMasterQList();
		case (FAVORITES_FRAGMENT):
			return ListHandler.getFavsList();
		case (READING_LIST_FRAGMENT):
			return ListHandler.getReadingList();
		case (MY_QUESTIONS_FRAGMENT):
			return ListHandler.getMyQsList();
		default:
			return ListHandler.getMasterQList();
		}
    }

}
