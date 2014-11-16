package ca.ualberta.cs.queueunderflow.models;

/**
 * The Class Answer.
 * An answer to a question. Contains the title, author, rating, date, any replies, etc (via inheritance from GenericResponse)
 * @author group 10
 * @version 0.5
 */
public class Answer extends GenericResponse {

	/**
	 * Instantiates a new answer.
	 *
	 * @param answerName the answer name
	 * @param author the author
	 */
	public Answer(String answerName, String author) {
		super(answerName, author);
	}

}