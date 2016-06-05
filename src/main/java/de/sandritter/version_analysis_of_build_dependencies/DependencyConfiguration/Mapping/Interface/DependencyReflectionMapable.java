package de.sandritter.version_analysis_of_build_dependencies.DependencyConfiguration.Mapping.Interface;

import java.io.File;

import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.DependencyReflection.DependencyReflectionCollection;
import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.DependencyReflection.JsonDataImage;
import de.sandritter.version_analysis_of_build_dependencies.Mapping.Exception.DataMappingFailedException;

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
