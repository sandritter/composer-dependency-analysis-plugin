package de.sandritter.version_analysis_of_build_dependencies.Exception;

public class BuildException extends Exception {

	private static final long serialVersionUID = 1790937912794194441L;

	/**
	 * @param message
	 */
	public BuildException(String message)
	{
		super(message);
	}
	
	/**
	 * @param message
	 * @param e {@link Exception}
	 */
	public BuildException(String message, Exception e)
	{
		super(message, e);
	}

}
