package ca.ualberta.cs.queueunderflow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AskAQuestionActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ask_aquestion);

		// Display the up caret to go back to the MainActivity
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		final EditText authorUsername = (EditText) findViewById(R.id.authorBox);
		final EditText questionInput = (EditText) findViewById(R.id.questionInput);
		Button askBtn = (Button) findViewById(R.id.askBtn);
		
		/* Need to put some of this in a controller later */
		authorUsername.setText(User.getUserName());
		askBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					Question newQuestion = new Question(questionInput.getText().toString(), authorUsername.getText().toString());
					QuestionList homeScreenList = ListHandler.getMasterQList();
					homeScreenList.add(newQuestion);
					
					QuestionList myQuestionsList = ListHandler.getMyQsList();
					myQuestionsList.add(newQuestion);
					
					finish();
				} catch (IllegalArgumentException e) {
					Toast.makeText(getApplicationContext(), "Invalid question. Please re-enter a question.", Toast.LENGTH_SHORT).show();
				}

			}
		});
		
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ask_aquestion, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		// This goes back to the MainActivity when the up caret is clicked
		if (id == android.R.id.home) {
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
