package de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Mapper.Interface;

import java.io.File;

import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Composer.JsonDataImage;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Composer.LockDataImage;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Exception.DataMappingFailedException;

/**
 * needs to be implemented from classes which wants to map composer files to DataImages
 * Mapper.java
 *
 * @author Michael Sandritter
 * 22.09.2015
 */
public interface ComposerMapable {
	
	/**
	 * maps a composer.lock file to a LockDataImage
	 * @param f {@link File}
	 * @return {@link LockDataImage}
	 * @throws DataMappingFailedException
	 */
	public LockDataImage mapFileToLockDataImage(File f) throws DataMappingFailedException;
	
	/**
	 * maps a composer.json file to a JsonDataImage
	 * @param f {@link File}
	 * @return {@link JsonDataImage}
	 * @throws DataMappingFailedException
	 */
	public JsonDataImage mapFileToJsonDataImage(File f) throws DataMappingFailedException;
	
}
