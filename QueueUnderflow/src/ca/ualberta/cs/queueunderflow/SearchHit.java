package ca.ualberta.cs.queueunderflow;

//From 301 elasticsearch lab


/**
 * The Class SearchHit.
 *
 * @param <T> the generic type
 */
public class SearchHit<T> {
	
	/** The _index. */
	private String _index;
	
	/** The _type. */
	private String _type;
	
	/** The _id. */
	private String _id;
	
	/** The _version. */
	private String _version;
	
	/** The found. */
	private boolean found;
	
	/** The _source. */
	private T _source;
	
	/**
	 * Instantiates a new search hit.
	 */
	public SearchHit() {
		
	}
	
	/**
	 * Gets the _index.
	 *
	 * @return the _index
	 */
	public String get_index() {
		return _index;
	}
	
	/**
	 * Sets the _index.
	 *
	 * @param _index the new _index
	 */
	public void set_index(String _index) {
		this._index = _index;
	}
	
	/**
	 * Gets the _type.
	 *
	 * @return the _type
	 */
	public String get_type() {
		return _type;
	}
	
	/**
	 * Sets the _type.
	 *
	 * @param _type the new _type
	 */
	public void set_type(String _type) {
		this._type = _type;
	}
	
	/**
	 * Gets the _id.
	 *
	 * @return the _id
	 */
	public String get_id() {
		return _id;
	}
	
	/**
	 * Sets the _id.
	 *
	 * @param _id the new _id
	 */
	public void set_id(String _id) {
		this._id = _id;
	}
	
	/**
	 * Gets the _version.
	 *
	 * @return the _version
	 */
	public String get_version() {
		return _version;
	}
	
	/**
	 * Sets the _version.
	 *
	 * @param _version the new _version
	 */
	public void set_version(String _version) {
		this._version = _version;
	}
	
	/**
	 * Checks if is found.
	 *
	 * @return true, if is found
	 */
	public boolean isFound() {
		return found;
	}
	
	/**
	 * Sets the found.
	 *
	 * @param found the new found
	 */
	public void setFound(boolean found) {
		this.found = found;
	}
	
	/**
	 * Gets the source.
	 *
	 * @return the source
	 */
	public T getSource() {
		return _source;
	}
	
	/**
	 * Sets the source.
	 *
	 * @param source the new source
	 */
	public void setSource(T source) {
		this._source = source;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SimpleElasticSearchResponse [_index=" + _index + ", _type="
				+ _type + ", _id=" + _id + ", _version=" + _version
				+ ", found=" + found + ", _source=" + _source + "]";
	}
}
