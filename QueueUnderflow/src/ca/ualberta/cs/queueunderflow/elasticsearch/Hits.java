package ca.ualberta.cs.queueunderflow.elasticsearch;
import java.util.List;


/**
 * The Class Hits.
 *
 * @param <T> the generic type
 */
public class Hits<T> {

	/** The total. */
	private int total;
	
	/** The max_score. */
	private float max_score;
	
	/** The hits. */
	private List<SearchHit<T>> hits;
	
	/**
	 * Instantiates a new hits.
	 */
	public Hits() {}
	
	/**
	 * Gets the total.
	 *
	 * @return the total
	 */
	public int getTotal() {
		return total;
		}
	
	/**
	 * Sets the total.
	 *
	 * @param total the new total
	 */
	public void setTotal(int total) {
		this.total = total;
		}
	
	/**
	 * Gets the max_score.
	 *
	 * @return the max_score
	 */
	public float getMax_score() {
		return max_score;
		}
	
	/**
	 * Sets the max_score.
	 *
	 * @param max_score the new max_score
	 */
	public void setMax_score(float max_score) {
		this.max_score = max_score;
		}
	
	/**
	 * Gets the hits.
	 *
	 * @return the hits
	 */
	public List<SearchHit<T>> getHits() {
		return hits;
		}
	
	/**
	 * Sets the hits.
	 *
	 * @param hits the new hits
	 */
	public void setHits(List<SearchHit<T>> hits) {
		this.hits = hits;
		}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Hits [total=" + total + ", max_score=" + max_score + ", hits=" + hits + "]";
	}
	
}
