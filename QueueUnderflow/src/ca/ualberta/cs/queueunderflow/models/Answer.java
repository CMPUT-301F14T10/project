package ca.ualberta.cs.queueunderflow.models;

// TODO: Auto-generated Javadoc
/* This is the class to set Answers.
 It grabs the user and the list of answers from a question. It also is what is
 used for grabbing upvotes for certain questions.
 */
/**
 * The Class Answer.
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