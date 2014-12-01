package ca.ualberta.cs.queueunderflow.views;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import ca.ualberta.cs.queueunderflow.R;
import ca.ualberta.cs.queueunderflow.controllers.AskAnswerController;
import ca.ualberta.cs.queueunderflow.singletons.User;

/**
 * The Class AskAQuestionActivity.
 * Allows the user to input a question and attach a photo.
 * @author group 10
 * @version 0.5
 */
public class AskAQuestionActivity extends GenericAddActivity{
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ask_aquestion);

		controller = new AskAnswerController(AskAQuestionActivity.this);
		
		setUpActionBar();
		
		TextView authorUsername = (TextView) findViewById(R.id.authorBox);
		authorUsername.setText(User.getUserName());
		
		final EditText questionInput = (EditText) findViewById(R.id.questionInput);
		final ImageButton imagePreviewBtn = (ImageButton) findViewById(R.id.imagePreviewBtn);
		
		Button askBtn = (Button) findViewById(R.id.askBtn);
		askBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				controller.askQuestion(questionInput.getText().toString(), User.getUserName(), imagePreviewBtn);
			}
		});
		
		Button addFromCameraBtn = (Button) findViewById(R.id.fromCamera);
		addFromCameraBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				setUpCameraPath();
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriFile);
				
				if (intent.resolveActivity(getPackageManager()) != null) {
					startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
				}
			}
		});
		
		//SOURCE: http://stackoverflow.com/questions/2169649/get-pick-an-image-from-androids-built-in-gallery-app-programmatically
		Button addFromGalleryBtn= (Button) findViewById(R.id.fromGallery);
		addFromGalleryBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent= new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(intent, SELECT_PICTURE);
			}
			
		});
	}

}
