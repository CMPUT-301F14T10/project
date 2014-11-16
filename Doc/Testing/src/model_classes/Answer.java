package model_classes;

// TODO: Auto-generated Javadoc
/* This is the class to set Answers.
 It grabs the user and the list of answers from a question. It also is what is
 used for grabbing upvotes for certain questions.
 */
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