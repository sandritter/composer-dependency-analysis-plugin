package de.sandritter.version_analysis_of_build_dependencies.Persistence.Database.Interface;

import java.sql.SQLException;

import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.Transfer.Interface.Transferable;

/**
 * DataStorage.java
 * interface that needs to be implemented from objects that wants to persist data
 *
 * @author Michael Sandritter
 */
public interface DataStorage {
	
	/**
	 * is storing data to database
	 * @param transport {@link Transferable}
	 * @throws SQLException 
	 * @throws Exception 
	 */
	public void storeData(Transferable transport) throws Exception;

}
