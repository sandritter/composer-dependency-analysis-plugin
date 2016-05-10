package de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Mapper;

import java.io.File;

import org.codehaus.jackson.map.ObjectMapper;

import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.DependencyReflection.JsonDataImage;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.DependencyReflection.DependencyReflectionCollection;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Exception.DataMappingFailedException;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Mapper.Interface.ComposerMapable;
/**
 * JsonMapper.java
 * maps data from json files to data image objects
 *
 * @author Michael Sandritter
 * 22.09.2015
 */
public class DependencyReflectionMapper implements ComposerMapable{
	
	/**
	 * {@link ObjectMapper}
	 */
	private ObjectMapper mapper;

	public DependencyReflectionMapper(){
		this.mapper = new ObjectMapper();
	}
	
	@Override
	public DependencyReflectionCollection mapFileToDependencyReflectionData(File f) throws DataMappingFailedException{
		return (DependencyReflectionCollection) mapData(f, DependencyReflectionCollection.class);
	}
	
	@Override
	public JsonDataImage mapFileToJsonDataImage(File f) throws DataMappingFailedException{
		return (JsonDataImage) mapData(f, JsonDataImage.class);
	}
	
	/**
	 * maps File to DataImage
	 * @param f {@link File}
	 * @param cls {@link Class}
	 * @return Object {@link Object}
	 * @throws DataMappingFailedException
	 */
	private Object mapData(File f, Class<?> cls) throws DataMappingFailedException{
		Object dataImage = null;
		try {
			dataImage = mapper.readValue(f, cls);
		} catch (Exception e) {
			throw new DataMappingFailedException("[ComposerMapper.java]: " + e.getMessage());
		}
		return dataImage;
	}

}
