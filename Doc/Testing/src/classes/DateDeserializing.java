package classes;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;


public class DateDeserializing implements JsonDeserializer<Date>{

	/*
	public Date deserialize(JsonElement json, Type arg1,
			JsonDeserializationContext arg2) throws JsonParseException {
	    SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
	    Date date = null;
	    try {
	    	String string=json.getAsString();
	    	//date = sdf.parse(json.getAsJsonPrimitive().getAsString());
	    	date=sdf.parse(string);
		  return date;
		  
	    } catch (ParseException e) {
			e.printStackTrace();
	    }
	    return date;
	}
	*/
	
	
	public Date deserialize(JsonElement element, Type arg1,
			JsonDeserializationContext arg2) throws JsonParseException
	{
		String date = element.getAsString();
		
		//SimpleDateFormat formatter = new SimpleDateFormat("M/d/yy hh:mm a");
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		try {
			return formatter.parse(date);
		} catch (ParseException e) {
			System.err.println("Failed to parse Date due to error:");
			return null;
		}
	}
	

}
