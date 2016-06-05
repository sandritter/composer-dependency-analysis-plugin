package de.sandritter.version_analysis_of_build_dependencies.Persistence.Database.Factory;

import de.sandritter.version_analysis_of_build_dependencies.DependencyConfiguration.Persistence.Database.Interface.DataStorage;

/**
 * DataStorageFactory.java
 * A factory that is used to create a DataLoader via dependency injection
 * with assisted injection
 * 
 * @author Michael Sandritter
 */
public interface DataStorageFactory {
	
	/**
	 * is creating an instance of a DataLoader
	 * @param dbPath
	 * @return {@link DataStorage}
	 */
	public DataStorage create(String dbPath);
}
