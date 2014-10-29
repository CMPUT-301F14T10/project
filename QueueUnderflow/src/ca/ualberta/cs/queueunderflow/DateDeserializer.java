package ca.ualberta.cs.queueunderflow;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class DateDeserializer implements JsonDeserializer<Date>{

	@Override
	public Date deserialize(JsonElement element, Type arg1,
			JsonDeserializationContext arg2) throws JsonParseException
	{
		String date = element.getAsString();
		
		SimpleDateFormat formatter = new SimpleDateFormat("M/d/yy hh:mm a");
		//formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		try {
			return formatter.parse(date);
		} catch (ParseException e) {
			System.err.println("Failed to parse Date due to error:");
			return null;
		}
	}
	

}
