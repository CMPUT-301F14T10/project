package ca.ualberta.cs.queueunderflow.views;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import ca.ualberta.cs.queueunderflow.R;
import ca.ualberta.cs.queueunderflow.User;
import ca.ualberta.cs.queueunderflow.controllers.AskAnswerController;

public class AskAQuestionActivity extends Activity{

	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private Uri imageUriFile;
	private AskAnswerController controller;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ask_aquestion);

		controller = new AskAnswerController(AskAQuestionActivity.this);
		
		// Display the up caret to go back to the MainActivity
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		TextView authorUsername = (TextView) findViewById(R.id.authorBox);
		authorUsername.setText(User.getUserName());
		
		final EditText questionInput = (EditText) findViewById(R.id.questionInput);
		
		Button askBtn = (Button) findViewById(R.id.askBtn);
		askBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				controller.askQuestion();
			}
		});
		
		Button addFromCameraBtn = (Button) findViewById(R.id.fromCamera);
		addFromCameraBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	
				// Modified from BogoPicGen / CameraTest Lab
				
				// Create folder if one doesn't exist already
				String folderName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/tmp";
				File folder = new File(folderName);
				if (!folder.exists()) {
					folder.mkdir();
				}
				
				// Create a URI for the image's file
				String imageFilePath = folderName + "/" + String.valueOf(System.currentTimeMillis()) + ".jpg";
				File imageFile = new File(imageFilePath);
				imageUriFile = Uri.fromFile(imageFile);
				
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriFile);
				
				if (intent.resolveActivity(getPackageManager()) != null) {
					startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
				}
			}
		});
	
	}
	
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if ((requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) && (resultCode == RESULT_OK)) {
			ImageButton imagePreviewBtn = (ImageButton) findViewById(R.id.imagePreviewBtn);
			imagePreviewBtn.setImageDrawable(Drawable.createFromPath(imageUriFile.getPath()));
			imagePreviewBtn.setVisibility(View.VISIBLE);
			controller.setImagePath(imageUriFile.getPath());
		}
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
