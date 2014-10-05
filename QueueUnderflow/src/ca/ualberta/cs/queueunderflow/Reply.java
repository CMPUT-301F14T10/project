package ca.ualberta.cs.queueunderflow;

public class Reply {
	
	protected String reply;
	protected String author;
	
	public Reply (String reply, String author) {
		this.reply=reply;
		this.author=author;
	}
	
	public void setAuthor(String author) {
		this.author=author;
	}
	
	public String getAuthor() {
		return this.author;
	}
	
	public void setReply(String reply) {
		this.reply=reply;
	}
	
	public String getReply() {
		return this.reply;
	}
}
