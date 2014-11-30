package ca.ualberta.cs.queueunderflow.models;

import java.util.Date;

/**
 * The Class Reply.
 * Models a reply with the reply content and the author.
 * @author group 10
 * @version 0.5
 */
public class Reply {
	
	/** The reply. */
	protected String reply;
	
	/** The author. */
	protected String author;
	
	/**  The location of author. */
	protected String location;
	
	/**
	 * Instantiates a new reply.
	 *
	 */
	
	//Added date to use sorting method
	protected Date date;
	
	/**
	 * Instantiates a new reply.
	 *
	 * @param reply the reply
	 * @param author the author
	 */
	public Reply (String reply, String author) {
		reply = reply.trim();
		if (reply.length() == 0) {
			throw new IllegalArgumentException("Not a valid reply. Please re-enter a valid reply.");
		}
		this.reply=reply;
		this.author=author;
		this.location="";
		this.date=new Date();
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
	 * Gets the author.
	 *
	 * @return the author
	 */
	public String getAuthor() {
		return this.author;
	}
	
	/**
	 * Sets the reply.
	 *
	 * @param reply the new reply
	 */
	public void setReply(String reply) {
		this.reply=reply;
	}
	
	/**
	 * Gets the reply.
	 *
	 * @return the reply
	 */
	public String getReply() {
		return this.reply;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		Reply reply = (Reply) o;
		
		if ((!reply.getReply().equals(this.reply)) || (!reply.getAuthor().equals(this.author))) {
			return false;
		}
		
		return true;
	}
	
	/**
	 *  Get the location of the author.
	 *
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}
	
	/**
	 *  Set the location of the author.
	 *
	 * @param location the new location
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	
	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public Date getDate() {
		return this.date;
	}
	
	
}
