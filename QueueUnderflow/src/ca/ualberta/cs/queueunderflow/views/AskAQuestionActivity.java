package ca.ualberta.cs.queueunderflow.views;

import java.io.ByteArrayOutputStream;
import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import ca.ualberta.cs.queueunderflow.R;
import ca.ualberta.cs.queueunderflow.User;
import ca.ualberta.cs.queueunderflow.controllers.AskAnswerController;

// TODO: Auto-generated Javadoc
/**
 * The Class AskAQuestionActivity.
 * Allows the user to input a question and attach a photo.
 * @author group 10
 * @version 0.5
 */
public class AskAQuestionActivity extends Activity{

	/** The Constant CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE. */
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	
	/** The Constant SELECT_PICTURE CODE. */
	private static final int SELECT_PICTURE= 1;
    private String selectedImagePath;
    
	/** The image uri file. */
	private Uri imageUriFile;
	
	/** The controller. */
	private AskAnswerController controller;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
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
		final ImageButton imagePreviewBtn = (ImageButton) findViewById(R.id.imagePreviewBtn);
		
		Button askBtn = (Button) findViewById(R.id.askBtn);
		
		
		//For AddAnAnswer activity testing only, should never go here in normal circumstances
		Intent intent = getIntent();
		int question = intent.getIntExtra("Question", -1); // -1 is the default value if nothing was retrieved
		String test_question=null;
		if (question!=-1){
			test_question="This is a question";
		}
		if (test_question!=null) {
			controller.askQuestion(test_question,"test user",imagePreviewBtn.getVisibility());
		}

		///////////////////////////////////////////////////
		
		
		
		
		askBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				controller.askQuestion(questionInput.getText().toString(), User.getUserName(), imagePreviewBtn.getVisibility());
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
		
		//SOURCE: http://stackoverflow.com/questions/2169649/get-pick-an-image-from-androids-built-in-gallery-app-programmatically
		//Still need to set up the adapters(think its the adapters anyway) to display the actual image
		Button addFromGalleryBtn= (Button) findViewById(R.id.fromGallery);
		addFromGalleryBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent= new Intent();
				//Set the type of intent to be for images and start intent to go into default android gallery
				intent.setType("image/*");	
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(Intent.createChooser(intent, "Select from gallery"),SELECT_PICTURE);
			}
			
		});
	}
	
	

	/* (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//If clicked on add from camera button, end up here
		if ((requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) && (resultCode == RESULT_OK)) {
			ImageButton imagePreviewBtn = (ImageButton) findViewById(R.id.imagePreviewBtn);
			imagePreviewBtn.setImageDrawable(Drawable.createFromPath(imageUriFile.getPath()));
			imagePreviewBtn.setVisibility(View.VISIBLE);
			
			String imagePath= imageUriFile.getPath();
			//controller.setImagePath(imageUriFile.getPath());
			
	        //Convert the image to a bitmap and compress it to 30% of its original quality as well
	        Bitmap bitmap= BitmapFactory.decodeFile(imagePath);
	        ByteArrayOutputStream byteArray= new ByteArrayOutputStream();
	        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, byteArray);
	        byte [] bytes= byteArray.toByteArray();
	        
	        //Convert the image to a base64 encoded string in order to serialize it later
	        String encoded = Base64.encodeToString(bytes, Base64.DEFAULT);
	        controller.setEncodedImage(encoded);
		}
		else {
			//If clicked on the add from gallery button, end up here
			//SOURCES: http://stackoverflow.com/questions/5309190/android-pick-images-from-gallery
			// http://stackoverflow.com/questions/2169649/get-pick-an-image-from-androids-built-in-gallery-app-programmatically
		    if(requestCode == SELECT_PICTURE && resultCode==RESULT_OK) {
		        imageUriFile= data.getData();

		        //Get the image that the user selected with cursor
		        Cursor cursor = getContentResolver().query(imageUriFile, new String[] { android.provider.MediaStore.Images.ImageColumns.DATA }, null, null, null);
		        cursor.moveToFirst();

		        //Get the image path and close the cursor to prevent possible errors
		        String imagePath = cursor.getString(0);
		        cursor.close();
		        
		        //Convert the image to a bitmap and compress it to 30% of its original quality as well
		        Bitmap bitmap= BitmapFactory.decodeFile(imagePath);
		        ByteArrayOutputStream byteArray= new ByteArrayOutputStream();
		        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, byteArray);
		        byte [] bytes= byteArray.toByteArray();
		        
		        //Convert the image to a base64 encoded string in order to serialize it later
		        String encoded = Base64.encodeToString(bytes, Base64.DEFAULT);
		        
		        ImageButton imagePreviewBtn = (ImageButton) findViewById(R.id.imagePreviewBtn);
				imagePreviewBtn.setImageDrawable(Drawable.createFromPath(imagePath));
				imagePreviewBtn.setVisibility(View.VISIBLE);
				//controller.setImagePath(imagePath);
				controller.setEncodedImage(encoded);
		    }
		}
	}
	



	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ask_aquestion, menu);
		return true;
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
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
