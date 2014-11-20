/*package classes;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ReplySerializing implements JsonSerializer<Reply>{

	public JsonElement serialize(Reply reply, Type arg1,
			JsonSerializationContext arg2) {
		final JsonObject replyObject= new JsonObject();
		replyObject.addProperty("reply",reply.getReply());
		replyObject.addProperty("author",reply.getAuthor());
		
		return replyObject;
	}

}*/
