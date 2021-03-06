package ca.ualberta.cs.queueunderflow;

import java.util.ArrayList;


/**
 * The Class TModel.
 *
 * @author group 10
 * @version 1.0
 * @param <V> the value type
 */
public abstract class TModel<V extends TView> {

	/** The views. */
	transient private ArrayList<V> views;
	
	/**
	 * Instantiates a new t model.
	 */
	public TModel() {
		views = new ArrayList<V>();
	}
	
	/**
	 * Adds the view.
	 *
	 * @param view the view
	 */
	public void addView(V view) {
		if (!views.contains(view)) {
			views.add(view);
		}
	}
	
	/**
	 * Delete view.
	 *
	 * @param view the view
	 */
	public void deleteView(V view) {
		views.remove(view);
	}
	
	/**
	 * Notify views to update.
	 */
	public void notifyViews() {
		for (V view : views) {
			view.update(this);
		}
	}
	
	/**
	 * Clears all views.
	 */
	public void clearViews() {
		views.clear();
	}
}
