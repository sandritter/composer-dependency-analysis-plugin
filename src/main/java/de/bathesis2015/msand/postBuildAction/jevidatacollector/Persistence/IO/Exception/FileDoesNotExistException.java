package de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.IO.Exception;

/**
 * thrown when the requested file does not exist
 * @author Michael Sandritter
 *
 */
public class FileDoesNotExistException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4350954087824900739L;

	public FileDoesNotExistException(String message){
		super(message);
	}
}
