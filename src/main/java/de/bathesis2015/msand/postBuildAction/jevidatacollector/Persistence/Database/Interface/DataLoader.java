package de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Database.Interface;

import java.sql.SQLException;

import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Transfer.Interface.Transferable;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Enum.DependencyType;

/**
 * DataLoader.java
 * interface that defines the methods to load data from database
 * 
 * @author Michael Sandritter
 */
public interface DataLoader {
	
	/**
	 * is loading dependency information from database
	 * @param value table identifier
	 * @param type {@link DependencyType}
	 * @return {@link Transferable}
	 * @throws Exception 
	 */
	public Transferable loadDependencies(String value, DependencyType type) throws Exception;

	/**
	 * is loading build-specific information from database
	 * @param reference
	 * @return {@link Transferable}
	 * @throws Exception 
	 */
	public Transferable loadBuild(String reference) throws Exception;

	/**
	 * is loading information about the main component of a build from database
	 * @param value table identifier
	 * @return {@link Transferable}
	 * @throws SQLException
	 * @throws Exception 
	 */
	public Transferable loadMainComponent(String value) throws Exception;
	
	/**
	 * is loading information about dependent components of a builded component
	 * @param dependencyName
	 * @return {@link Transferable}
	 * @throws Exception
	 */
	public Transferable loadDependentComponents(String dependencyName) throws Exception;

}
