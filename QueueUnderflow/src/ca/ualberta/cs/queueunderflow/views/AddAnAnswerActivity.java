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
 * The Class AddAnAnswerActivity.
 * Allows the user to input an answer to add and optionally attach a photo 
 * @author group 10
 * @version 1.0
 */
public class AddAnAnswerActivity extends GenericAddActivity {
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_an_answer);
		
		controller = new AskAnswerController(AddAnAnswerActivity.this);
		
		setUpActionBar();
		
		TextView authorUsername = (TextView) findViewById(R.id.authorBox);
		authorUsername.setText(User.getUserName());
		
		final EditText answerInput = (EditText) findViewById(R.id.answerInput);
		final ImageButton imagePreviewBtn = (ImageButton) findViewById(R.id.imagePreviewBtn);
		Button addABtn = (Button) findViewById(R.id.addABtn);
		
		/* Get the position of the question and the fragment we clicked from */
		Intent intent = getIntent();
		final int position = intent.getIntExtra("question_position", -1); 	// -1 is the default value if nothing was retrieved
		final int fromFragment = intent.getIntExtra("fromFragment", -1);
		
		
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
		
		addABtn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				controller.addAnswer(fromFragment, position, answerInput.getText().toString(), User.getUserName(), imagePreviewBtn);
			}
		});
	}
}
