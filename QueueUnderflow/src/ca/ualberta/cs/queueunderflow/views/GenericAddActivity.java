package ca.ualberta.cs.queueunderflow.views;

import java.io.ByteArrayOutputStream;
import java.io.File;

import ca.ualberta.cs.queueunderflow.R;
import ca.ualberta.cs.queueunderflow.controllers.AskAnswerController;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public abstract class GenericAddActivity extends Activity {

	/** The Constant HOME_SCREEN_FRAGMENT. */
	public static final int HOME_SCREEN_FRAGMENT = 1;
	
	/** The Constant FAVORITES_FRAGMENT. */
	public static final int FAVORITES_FRAGMENT = 2;
	
	/** The Constant MY_QUESTIONS_FRAGMENT. */
	public static final int MY_QUESTIONS_FRAGMENT = 3;
	
	/** The Constant READING_LIST_FRAGMENT. */
	public static final int READING_LIST_FRAGMENT = 4;

	/** The Constant CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE. */
	protected static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	
	/** The Constant SELECT_PICTURE CODE. */
	protected static final int SELECT_PICTURE= 1;
    
    /** The selected image path. */
    protected String selectedImagePath;
    
	/** The image uri file. */
	protected Uri imageUriFile;
	
	/** The controller. */
	protected AskAnswerController controller;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//If clicked on add from camera button, end up here
		if ((requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) && (resultCode == RESULT_OK)) {
			addFromCamera();
		}
		else {
			//If clicked on the add from gallery button, end up here
			//SOURCES: http://stackoverflow.com/questions/5309190/android-pick-images-from-gallery
			// http://stackoverflow.com/questions/2169649/get-pick-an-image-from-androids-built-in-gallery-app-programmatically
		    if(requestCode == SELECT_PICTURE && resultCode==RESULT_OK) {
		        addFromGallery(data);
		    }
		}
	}


	protected void setUpCameraPath()  {
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
		
	}

	/**
	 * Adds the from gallery.
	 *
	 * @param data the data
	 */
	protected void addFromGallery(Intent data) {

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
		
		//Set the image path only to check if image >64kb 
		controller.setImagePath(imagePath);
		controller.setEncodedImage(encoded);
	}



	/**
	 * Adds the from camera.
	 */
	protected void addFromCamera() {

		ImageButton imagePreviewBtn = (ImageButton) findViewById(R.id.imagePreviewBtn);
		imagePreviewBtn.setImageDrawable(Drawable.createFromPath(imageUriFile.getPath()));
		imagePreviewBtn.setVisibility(View.VISIBLE);
		
		String imagePath= imageUriFile.getPath();
		
		//Set the image path only to check if image >64kb when asking the question
		controller.setImagePath(imageUriFile.getPath());
		
		//Convert the image to a bitmap and compress it to 30% of its original quality as well
		Bitmap bitmap= BitmapFactory.decodeFile(imagePath);
		ByteArrayOutputStream byteArray= new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 30, byteArray);
		byte [] bytes= byteArray.toByteArray();
		
		//Convert the image to a base64 encoded string in order to serialize it later
		String encoded = Base64.encodeToString(bytes, Base64.DEFAULT);
		controller.setEncodedImage(encoded);
	}
	

	
	
	protected void setUpActionBar() {
		// Display the up caret to go back to the MainActivity
		getActionBar().setDisplayHomeAsUpEnabled(true);
		// Hide the name of the activity
		getActionBar().setDisplayShowTitleEnabled(false);
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
		if (id == android.R.id.home) {
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
}
