package ca.homedepot.relevancy.converters;


/**
 * @author Weichen Zhou
 */
public interface Converter<SOURCE, TARGET>
{
	TARGET convert(SOURCE searchQueryData);
}
