package de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Database.Interface;

import java.sql.SQLException;

import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Transfer.Interface.Transferable;

/**
 * DataStorage.java
 * interface that needs to be implemented from objects that wants to persist data
 *
 * @author Michael Sandritter
 * 03.10.2015
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
