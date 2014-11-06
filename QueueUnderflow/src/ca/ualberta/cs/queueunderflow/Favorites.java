package ca.ualberta.cs.queueunderflow;

import java.util.ArrayList;

import ca.ualberta.cs.queueunderflow.models.Question;

// TODO: Auto-generated Javadoc
/**
 * The Class Favorites.
 */
public class Favorites {
	
	/** The favorites. */
	protected ArrayList <Question> favorites;
	
	/**
	 * Instantiates a new favorites.
	 */
	public Favorites() {
		favorites= new ArrayList<Question>();
	}
	
	/**
	 * Adds the.
	 *
	 * @param favorite the favorite
	 */
	public void add (Question favorite) {
		favorites.add(favorite);
	}
	
	/**
	 * Size.
	 *
	 * @return the int
	 */
	public int size() {
		return favorites.size();
	}
}
