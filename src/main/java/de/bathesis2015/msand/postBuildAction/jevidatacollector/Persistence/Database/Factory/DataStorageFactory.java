package de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Database.Factory;

import de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Database.Interface.DataStorage;

/**
 * 
 * DataStorageFactory.java
 * A Factory that is used to create a DataLoader via dependency injection
 * with assisted injection
 * 
 * @author Michael Sandritter
 *
 */
public interface DataStorageFactory {
	
	/**
	 * is creating an instance of a DataLoader
	 * @param dbPath
	 * @return {@link DataStorage}
	 */
	public DataStorage create(String dbPath);
}
