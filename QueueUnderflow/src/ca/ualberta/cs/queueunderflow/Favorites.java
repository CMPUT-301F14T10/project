package ca.ualberta.cs.queueunderflow;

import java.util.ArrayList;

import ca.ualberta.cs.queueunderflow.models.Question;

public class Favorites {
	protected ArrayList <Question> favorites;
	
	public Favorites() {
		favorites= new ArrayList<Question>();
	}
	
	public void add (Question favorite) {
		favorites.add(favorite);
	}
	
	public int size() {
		return favorites.size();
	}
}
