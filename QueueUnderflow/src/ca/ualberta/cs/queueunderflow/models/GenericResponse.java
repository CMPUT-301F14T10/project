package ca.ualberta.cs.queueunderflow.models;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import android.text.format.DateFormat;

import ca.ualberta.cs.queueunderflow.TModel;
import ca.ualberta.cs.queueunderflow.TView;
import ca.ualberta.cs.queueunderflow.legacy_code.Picture;


// TODO: Auto-generated Javadoc
/**
* The Class GenericResponse.
* Holds all the basic attributes of a response
* @author group 10
 * @version 0.5
*/
public class GenericResponse {

	
	/** The response name. */
	protected String name;
	
	/** The response replies. */
	//protected ArrayList <Reply> replies;
	
	protected ReplyList replies;
	
	/** The author. */
	protected String author;
	
	/** The upvote count. */
	protected int upvote;
	
	/** The has picture indicator. */
	protected boolean hasPicture;
	
	/** The image. */
	protected Picture image;
	
	/** The date. */
	protected Date date;

	/** The is favorited indicator. */
	transient protected boolean isFav;
	
	/** The is in reading list indicator. */
	transient protected boolean isInReadingList;
	
	/**  The unique ID. */
	protected UUID uniqueID;
	//protected String uniqueID;
	
	//The image path (where the image is)
	/** The image path. */
	private String imagePath;
	
	/** The encoded image. */
	private String encodedImage;
	
	/** The location. */
	protected String location;
	
	
	/**
	 * Instantiates a new generic response.
	 *
	 * @param name the name
	 * @param author the author
	 */
	public GenericResponse (String name, String author) {
		name = name.trim();
		if (name.length() == 0) {
			throw new IllegalArgumentException("Not a valid repsonse. Please enter another response.");
		}
		this.name=name;
		//this.replies= new ArrayList<Reply> ();
		this.replies= new ReplyList();
		this.author=author;
		this.upvote=0;
		this.hasPicture=false;
		this.image=null;
		this.date = new Date();
		this.imagePath=null;
		this.encodedImage=null;
		
		this.isFav = false;
		this.isInReadingList = false;
		
		this.location = "";
		
		//Set unique ID and convert to string to store in JSON form
		uniqueID = UUID.randomUUID();
//		this.uniqueID = UUID.randomUUID().toString();

	}
	
	
	
	/**
	 * Sets the author.
	 *
	 * @param author the new author
	 */
	public void setAuthor(String author) {
	    this.author=author;
	}
	
	/**
	 * Sets the ID.
	 *
	 * @param uid the new id
	 */
	
	public void setID(UUID uid) {
	    this.uniqueID = uid;
	}
	
	/**
	 * Sets the string id.
	 *
	 * @param ID the new string id
	 */
	public void setStringID(String ID) {
		this.uniqueID=UUID.fromString(ID);
	}
	

	/**
	 * Returns the unique ID.
	 *
	 * @return the unique ID
	 */
	
	public UUID getID() {
	    return this.uniqueID;
	}
	
	/**
	 * Gets the string id.
	 *
	 * @return the string id
	 */
	public String getStringID() {
	    return this.uniqueID.toString();
	}
	
	
	/**
	 * Gets the author.
	 *
	 * @return the author
	 */
	public String getAuthor() {
		return this.author;
	}
	
	/**
	 * Gets the response name.
	 *
	 * @return the response name
	 */
	public String getName() {
		return this.name;
	}
	
	
    /**
     * Gets the reply at the specified location in this array.
     *
     * @param position the position of the element to return
     * @return the reply at the specified position
     */
    public Reply getReplyAt(int position) {
        return replies.get(position);
    }
    
	/**
	 * Adds the specified reply to to the beginning of the array.
	 *
	 * @param reply the reply to add
	 */
	public void addReply(Reply reply) {
		//replies.add(0, reply);
		replies.add(reply);
	}
	
	/**
	 * Gets the list of replies.
	 *
	 * @return the replies
	 */
	//public ArrayList<Reply> getReplies() {
		//return this.replies;
		
	//}
	
	public ReplyList getReplies() {
		return this.replies;
	}
	
	/**
	 * Gets the number of replies.
	 *
	 * @return the number of replies
	 */
	public int getSizeReplies() {
		return replies.size();
	}
	
	/**
	 * Upvote response.
	 */
	public void upvoteResponse() {
		upvote+=1;
	}
	
	
	/**
	 * Sets the upvote count.
	 *
	 * @param number the new upvote count
	 */
	public void setUpvotes(int number) {
		this.upvote=number;
	}
	
	/**
	 * Gets the upvote count.
	 *
	 * @return the upvote count
	 */
	public int getUpvotes() {
		return upvote;
	}
	
	/**
	 * Checks for picture.
	 *
	 * @return true, if the response has a picture
	 */
	public boolean hasPicture() {
		return this.hasPicture;
	}
	
	/**
	 * Sets the picture.
	 *
	 * @param pic the new picture
	 */
	public void setPicture (Picture pic) {
		this.image= pic;
		this.hasPicture=true;
	}
	
	/**
	 * Gets the picture.
	 *
	 * @return the picture
	 */
	public Picture getPicture() {
		return this.image;
	}
	
	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public Date getDate() {
		return this.date;
	}
	
	/**
	 * Sets the date.
	 *
	 * @param date the new date
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	
	/**
	 * Gets the checks if the response is favorited.
	 *
	 * @return true if the response is favorited, false otherwise
	 */
	public boolean getIsFav() {
		return this.isFav;
	}
	
	/**
	 * Sets the checks about whether the response is is favorited.
	 *
	 * @param isChecked the new checks if is fav
	 */
	public void setIsFav(boolean isChecked) {
		this.isFav = isChecked;
	}

	/**
	 * Gets the checks if the response is in reading list.
	 *
	 * @return the checks if is in reading list
	 * @returntrue if the response is in the reading list, false otherwise
	 */
	public boolean getIsInReadingList() {
		return this.isInReadingList;
	}

	/**
	 * Sets the checks about whether the response is in reading list.
	 *
	 * @param isChecked the new checks if is in reading list
	 */
	public void setIsInReadingList(boolean isChecked) {
		this.isInReadingList = isChecked;
	}

	/**
	 * Sets the response name.
	 *
	 * @param newName the new name
	 */
	public void setName(String newName) {
		this.name=newName;
		
	}

	/**
	 * Sets the reply list.
	 *
	 * @param replyList the new reply list
	 */
	//public void setReplyList(ArrayList<Reply> replyList) {
		//this.replies= replyList;
		
	//}
	public void setReplyList(ReplyList replyList) {
		this.replies=replyList;
	}

	/**
	 * Sets the checks for picture.
	 *
	 * @param hasPicture the new checks for picture
	 */
	public void setHasPicture(boolean hasPicture) {
		this.hasPicture = hasPicture;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		GenericResponse response = (GenericResponse) o;
		
		if (!response.getName().equals(this.name) || !response.getAuthor().equals(this.author)) {
			return false;
		}
		else if (!DateFormat.format("MMM dd, yyyy", response.getDate()).equals(DateFormat.format("MMM dd, yyyy", this.date))) {
			return false;
		}
		else if (response.getUpvotes() != this.upvote || response.hasPicture() != this.hasPicture) {
			return false;
		}
		else if (response.getIsFav() != this.isFav || response.getIsInReadingList() != this.isInReadingList) {
			return false;
		}
		else if (!response.getReplies().equals(this.replies)) {
			return false;
		}
		return true;
	}
	
	/**
	 * Sets the image path.
	 *
	 * @param path the new image path
	 */
	public void setImagePath(String path) {
		//Using this to check if pictures >64kb
		
		//this.imagePath=path;
		
		File imageFile = new File(path);
		//Get size of the file in bytes, 1024 bytes in a kilobyte
		long size = imageFile.length();
		//Get size of file in kilobytes
		size=size/1024;		
		if (size>64) {
			throw new IllegalArgumentException("Image is over 64kb, choose a smaller image");
		}
		this.imagePath=path;

	}
	
	/**
	 * Gets the image path.
	 *
	 * @return the image path
	 */
	public String getImagePath() {
		return this.imagePath;
	}
	
	/**
	 * Sets the encoded image.
	 *
	 * @param encoded the new encoded image
	 */
	public void setEncodedImage(String encoded) {
		this.encodedImage=encoded;
	}
	
	/**
	 * Gets the encoded image.
	 *
	 * @return the encoded image
	 */
	public String getEncodedImage() {
		return this.encodedImage;
	}



	/**
	 * Gets the location.
	 *
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Sets the location.
	 *
	 * @param location the new location
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	

	
	
}