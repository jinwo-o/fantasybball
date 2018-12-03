package ca.homedepot.relevancy.model.entries;

import java.util.HashMap;
import java.util.Map;

public class DefaultIEntry implements IEntry
{
	
	protected Map<String, String> fieldValues = new HashMap<>();
	
	@Override
	public String get(String fieldName) {
		return fieldValues.get(fieldName);
	}
	
	@Override
	public void put(String fieldName, String fieldValue) {
		fieldValues.put(fieldName, fieldValue);
	}
	
}