package ca.ualberta.cs.queueunderflow.elasticsearch;


/**
 * The Class SearchResponse.
 *
 * @param <T> the generic type
 */
public class SearchResponse<T> {

	/** The took. */
	private int took;
	
	/** The timed_out. */
	private boolean timed_out;
	
	/** The _shards. */
	private Shard _shards;
	
	/** The hits. */
	private Hits<T> hits;
	
	/**
	 * Instantiates a new search response.
	 */
	public SearchResponse() {}

	/**
	 * Gets the took.
	 *
	 * @return the took
	 */
	public int getTook() {
		return took;
	}

	/**
	 * Sets the took.
	 *
	 * @param took the new took
	 */
	public void setTook(int took) {
		this.took = took;
	}

	/**
	 * Checks if is timed_out.
	 *
	 * @return true, if is timed_out
	 */
	public boolean isTimed_out() {
		return timed_out;
	}

	/**
	 * Sets the timed_out.
	 *
	 * @param timed_out the new timed_out
	 */
	public void setTimed_out(boolean timed_out) {
		this.timed_out = timed_out;
	}

	/**
	 * Gets the _shards.
	 *
	 * @return the _shards
	 */
	public Shard get_shards() {
		return _shards;
	}

	/**
	 * Sets the _shards.
	 *
	 * @param _shards the new _shards
	 */
	public void set_shards(Shard _shards) {
		this._shards = _shards;
	}

	/**
	 * Gets the hits.
	 *
	 * @return the hits
	 */
	public Hits<T> getHits() {
		return hits;
	}

	/**
	 * Sets the hits.
	 *
	 * @param hits the new hits
	 */
	public void setHits(Hits<T> hits) {
		this.hits = hits;
	}	   
}

	

class Shard {
	private int total;
	private int successful;
	private int failed;
	
	public Shard() {}
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getSuccessful() {
		return successful;
	}
	public void setSuccessful(int successful) {
		this.successful = successful;
	}
	public int getFailed() {
		return failed;
	}
	public void setFailed(int failed) {
		this.failed = failed;
	}
}