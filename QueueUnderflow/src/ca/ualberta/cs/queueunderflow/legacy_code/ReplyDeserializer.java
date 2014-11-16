package ca.ualberta.cs.queueunderflow.legacy_code;

import java.lang.reflect.Type;

import ca.ualberta.cs.queueunderflow.models.Reply;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 * The Class ReplyDeserializer.
 * @author group 10
 * @version 0.5
 */
public class ReplyDeserializer implements JsonDeserializer<Reply> {

	/* (non-Javadoc)
	 * @see com.google.gson.JsonDeserializer#deserialize(com.google.gson.JsonElement, java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)
	 */
	@Override
	public Reply deserialize(JsonElement jsonReply, Type arg1,
			JsonDeserializationContext arg2) throws JsonParseException {
		final JsonObject reply= jsonReply.getAsJsonObject();
		
	    final JsonElement jsonContent = reply.get("reply");
	    final String content = jsonContent.getAsString();

	    final String author = reply.get("author").getAsString();

	    final Reply deserialized_reply= new Reply(content,author);

	    return deserialized_reply;
	}

}
