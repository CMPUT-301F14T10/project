package ca.ualberta.cs.queueunderflow;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter.AuthorityEntry;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddAnAnswerActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_an_answer);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		final EditText answerText = (EditText) findViewById(R.id.AddAnswerEditText);
		Button addBtn = (Button) findViewById(R.id.AddAnAnswerButton);
		
		Intent intent = getIntent();
		final int position = intent.getIntExtra("question_position", -1); 				// -1 is the default value if nothing was retrieved
		
		addBtn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{				
				try {
					Answer newAnswer = new Answer(answerText.getText().toString(),User.getUserName());
					QuestionList homeScreenList = ListHandler.getMasterQList();
					Question question= homeScreenList.get(position);
					question.addAnswer(newAnswer);
					
					homeScreenList.set(position, question);
					QuestionList myQuestionsList = ListHandler.getMyQsList();
					myQuestionsList.set(position,question);
									
					finish();
					} catch (IllegalArgumentException e) {
						
						Toast.makeText(getApplicationContext(), "Invalid question. Please re-enter a question.", Toast.LENGTH_SHORT).show();
					}
							
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_an_answer, menu);
		return true;
	}

}
