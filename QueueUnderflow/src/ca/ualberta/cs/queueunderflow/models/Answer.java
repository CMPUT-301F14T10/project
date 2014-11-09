package ca.ualberta.cs.queueunderflow.models;

import java.util.ArrayList;
import java.util.Date;

import android.text.format.DateFormat;

import ca.ualberta.cs.queueunderflow.Picture;

// TODO: Auto-generated Javadoc
/* This is the class to set Answers.
 It grabs the user and the list of answers from a question. It also is what is
 used for grabbing upvotes for certain questions.
 */
/**
 * The Class Answer.
 */
public class Answer extends GenericResponse {

	public Answer(String answerName, String author) {
		super(answerName, author);
	}

}