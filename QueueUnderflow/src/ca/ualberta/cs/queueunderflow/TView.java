package ca.ualberta.cs.queueunderflow;


/**
 * The Interface TView.
 *@author group 10
 * @version 1.0
 * @param <M> the generic type
 */
public interface TView<M> {

	/**
	 * Calls all listeners to update themselves.
	 *
	 * @param model the model
	 */
	public void update(M model);
	
}
