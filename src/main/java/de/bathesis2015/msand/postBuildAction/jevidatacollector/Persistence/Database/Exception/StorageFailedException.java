package de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Database.Exception;

/**
 * trown when the database access failed
 * 
 * @author Michael Sandritter
 */
public class StorageFailedException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1742861625068857060L;

	public StorageFailedException(String message) 
	{
		super(message);
	}
}