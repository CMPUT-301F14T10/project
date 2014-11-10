package ca.ualberta.cs.queueunderflow.test.models;

import java.util.ArrayList;

import android.test.ActivityInstrumentationTestCase2;
import junit.framework.TestCase;
import ca.ualberta.cs.queueunderflow.Buffer;
import ca.ualberta.cs.queueunderflow.Favorites;
import ca.ualberta.cs.queueunderflow.models.Question;


public class BufferTest extends TestCase
{
	Buffer testBuffer = Buffer.getInstance();
	ArrayList<Integer> testfavBuffer = testBuffer.favBuffer;
	ArrayList<Integer> testreadingListBuffer = testBuffer.readingListBuffer;
	public void testBuffer(){
		//Buffer initialization test
		assertTrue(testBuffer.favBuffer != null);
		assertTrue(testBuffer.readingListBuffer != null);
		
		//addToFavorites test
		testBuffer.addToFavBuffer(0);
		assertTrue(testBuffer.favBuffer.size() == 1);
		
		//removeFromFavorites test
		testBuffer.removeFromFavBuffer(0);
		assertTrue(testBuffer.favBuffer.size() == 0);
		
		//addToReadingList test
		testBuffer.addToReadingListBuffer(0);
		assertTrue(testBuffer.readingListBuffer.size() == 1);
		
		//removeFromReadingList test
		testBuffer.removeFromReadingListBuffer(0);
		assertTrue(testBuffer.readingListBuffer.size() == 0);
		
		//isFavBufferEmpty test
		assertTrue(testBuffer.isFavBufferEmpty() == true);
		
		//isReadingListBufferEmpty test
		assertTrue(testBuffer.isReadingListBufferEmpty() == true);
		
		//flushFav test
		testBuffer.flushFav();
		assertTrue(testBuffer.favBuffer.size() == 0);
		
		//flushReadingList test
		testBuffer.flushReadingList();
		assertTrue(testBuffer.readingListBuffer.size() == 0);
	}
	
}
