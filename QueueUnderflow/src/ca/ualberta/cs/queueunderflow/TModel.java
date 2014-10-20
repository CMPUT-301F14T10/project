package ca.ualberta.cs.queueunderflow;

import java.util.ArrayList;

public abstract class TModel<V extends TView> {

	private ArrayList<V> views;
	
	public TModel() {
		views = new ArrayList<V>();
	}
	
	public void addView(V view) {
		if (!views.contains(view)) {
			views.add(view);
		}
	}
	
	public void deleteView(V view) {
		views.remove(view);
	}
	
	public void notifyViews() {
		for (V view : views) {
			view.update(this);
		}
	}
}
