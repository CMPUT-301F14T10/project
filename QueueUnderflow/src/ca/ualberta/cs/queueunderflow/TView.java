package ca.ualberta.cs.queueunderflow;

// TODO: Auto-generated Javadoc
/**
 * The Interface TView.
 *
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
