package ca.ualberta.cs.queueunderflow;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class TModel.
 *
 * @param <V> the value type
 */
public abstract class TModel<V extends TView> {

	/** The views. */
	private ArrayList<V> views;
	
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
	 * Notify views.
	 */
	public void notifyViews() {
		for (V view : views) {
			view.update(this);
		}
	}
}
