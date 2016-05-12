package de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Database.Factory;

import de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Database.Interface.DataLoader;

/**
 * DataLoaderFactory.java
 * A factory that is used to create a DataLoader via dependency injection
 * with assisted injection
 *
 * @author Michael Sandritter
 */
public interface DataLoaderFactory {
	
	/**
	 * is creating an instance of a DataLoader
	 * @param dbPath
	 * @return {@link DataLoader}
	 */
	public DataLoader create(String dbPath);
}
