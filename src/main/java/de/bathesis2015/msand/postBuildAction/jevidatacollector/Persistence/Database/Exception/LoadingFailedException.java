package de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Database.Exception;

/**
 * 
 * LoadingFailedException.java
 * thrown when loading data from the database failed
 *
 * @author Michael Sandritter
 *
 */
public class LoadingFailedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6277424992738841127L;

	public LoadingFailedException(String message) {
		super(message);
	}
}
