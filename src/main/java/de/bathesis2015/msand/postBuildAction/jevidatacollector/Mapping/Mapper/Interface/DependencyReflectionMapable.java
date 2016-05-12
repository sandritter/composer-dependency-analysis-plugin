package de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Mapper.Interface;

import java.io.File;

import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.DependencyReflection.JsonDataImage;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.DependencyReflection.DependencyReflectionCollection;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Exception.DataMappingFailedException;

/**
 * ComposerMapable.java
 * needs to be implemented from classes which wants to map composer files to DataImages
 *
 * @author Michael Sandritter
 */
public interface DependencyReflectionMapable{
	
	/**
	 * maps a composer.lock file to a LockDataImage
	 * 
	 * @param f {@link File}
	 * @return {@link DependencyReflectionCollection}
	 * @throws DataMappingFailedException
	 */
	public DependencyReflectionCollection mapFileToDependencyReflectionData(File f) throws DataMappingFailedException;
	
	/**
	 * maps a composer.json file to a JsonDataImage
	 * 
	 * @param f {@link File}
	 * @return {@link JsonDataImage}
	 * @throws DataMappingFailedException
	 */
	public JsonDataImage mapFileToJsonDataImage(File f) throws DataMappingFailedException;
	
}
