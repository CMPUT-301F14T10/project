package ca.ualberta.cs.queueunderflow.elasticsearch;

//This class is entirely taken directly from 301 AndroidElasticSearch Lab : https://github.com/dfserrano/AndroidElasticSearch
/**
 * The Class SimpleSearchCommand.
 * @author group 10
 * @version 1.0
 */
public class SimpleSearchCommand {

	/** The query. */
	private String query;
	
	/** The fields. */
	private String[] fields;

	/**
	 * Instantiates a new simple search command.
	 *
	 * @param query the query
	 */
	public SimpleSearchCommand(String query) {
		this(query, null);
	}

	/**
	 * Instantiates a new simple search command.
	 *
	 * @param query the query
	 * @param fields the fields
	 */
	public SimpleSearchCommand(String query, String[] fields) {
		this.query = query;
		this.fields = fields;
	}

	/**
	 * Gets the json command.
	 *
	 * @return the json command
	 */
	public String getJsonCommand() {
		StringBuffer command = new StringBuffer(
				"{\"query\" : {\"query_string\" : {\"query\" : \"" + query
						+ "\"");

		if (fields != null) {
			command.append(", \"fields\":  [");

			for (int i = 0; i < fields.length; i++) {
				command.append("\"" + fields[i] + "\", ");
			}
			command.delete(command.length() - 2, command.length());

			command.append("]");
		}

		command.append("}}}");

		return command.toString();
	}
}