package ca.ualberta.cs.queueunderflow;



/**
 * The Class Picture. Suppose to hold an image.
 * @author group 10
 * @version 0.5
 */
public class Picture
{
	/** The picture. */
	protected int picture;
	
	/**
	 * Instantiates a new picture.
	 *
	 * @param picture the picture
	 */
	public Picture(int picture) {
		this.picture=picture;
	}
	
	/**
	 * Gets the size.
	 *
	 * @return the size
	 */
	public int getSize() {
		return this.picture;
	}
	

}
