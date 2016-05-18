package de.sandritter.version_analysis_of_build_dependencies.Mapping.Exception;

/**
 * thrown when the mapping from File to Image failed 
 * 
 * @author Michael Sandritter
 */
public class DataMappingFailedException extends Exception{

	private static final long serialVersionUID = 2978951825503089876L;

	public DataMappingFailedException(String message)
	{
		super(message);
	}
}
