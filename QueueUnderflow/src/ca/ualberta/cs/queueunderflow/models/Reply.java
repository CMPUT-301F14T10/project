package ca.ualberta.cs.queueunderflow.models;

// TODO: Auto-generated Javadoc
/**
 * The Class Reply.
 */
public class Reply {
	
	/** The reply. */
	protected String reply;
	
	/** The author. */
	protected String author;
	
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
		
		if (reply.getReply() != this.reply || reply.getAuthor() != this.author) {
			return false;
		}
		
		return true;
	}
	
	
}
