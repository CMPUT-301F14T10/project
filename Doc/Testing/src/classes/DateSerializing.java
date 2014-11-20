package classes;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class DateSerializing implements JsonSerializer<Date>{

	public JsonElement serialize(Date date, Type arg1,
			JsonSerializationContext arg2) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");  
		return new JsonPrimitive(sdf.format(date));
	}

}
