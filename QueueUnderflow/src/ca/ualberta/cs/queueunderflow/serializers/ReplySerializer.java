package ca.ualberta.cs.queueunderflow.serializers;

import java.lang.reflect.Type;

import ca.ualberta.cs.queueunderflow.models.Reply;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;


/**
 * The Class ReplySerializer.
 * @author group 10
 * @version 0.5
 */
public class ReplySerializer implements JsonSerializer<Reply>{
	//This will create a jsonObject for the reply class that other serializers (like question and answer) will 
	//may end up using 
	
	//Source code link for serializing and deserializing: http://java-creed-examples.googlecode.com/svn/gson/Gson%20Serialiser%20Example/src/main/java/com/javacreed/examples/gson/part1/
	// Link for serializing:http://www.javacreed.com/gson-serialiser-example/
	
	/* (non-Javadoc)
	 * @see com.google.gson.JsonSerializer#serialize(java.lang.Object, java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
	 */
	@Override
	public JsonElement serialize(Reply reply,Type arg1,
			JsonSerializationContext arg2){
		final JsonObject replyObject= new JsonObject();
		replyObject.addProperty("reply",reply.getReply());
		replyObject.addProperty("author",reply.getAuthor());
		
		return replyObject;
	}

}
