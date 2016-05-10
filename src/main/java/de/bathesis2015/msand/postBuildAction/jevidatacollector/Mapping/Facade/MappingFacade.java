package de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Facade;

import java.io.File;
import java.util.Map;

import com.google.inject.Inject;

import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.DependencyReflection.JsonDataImage;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.DependencyReflection.DependencyReflectionCollection;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Transfer.BuildData;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Transfer.Interface.Transferable;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Enum.FileType;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Exception.DataMappingFailedException;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Mapper.Interface.ComposerMapable;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Mapper.Interface.RowDataMapper;

/**
 * 
 * MappingFacade.java
 * This object is used as facade for the mapping operations to create the data access objects
 *
 * @author Michael Sandritter
 *
 */
public class MappingFacade {
	
	private ComposerMapable composerMapper;
	private RowDataMapper rowDataMapper;
	private Transferable transport;
	
	/**
	 * constructor
	 * @param mapper {@link ComposerMapable}
	 * @param orMapper {@link RowDataMapper}
	 * @param transport {@link Transferable}
	 */
	@Inject
	MappingFacade(ComposerMapable mapper, RowDataMapper orMapper, Transferable transport){
		this.composerMapper = mapper;
		this.rowDataMapper = orMapper;
		this.transport = transport;
	}
	
	/**
	 * is mapping row data to data access objects
	 * @param buildData {@link BuildData}
	 * @param files - list of {@link File}
	 * @return {@link Transferable}
	 * @throws DataMappingFailedException
	 */
	public Transferable mapRowData(BuildData buildData, Map<FileType, File> files) throws DataMappingFailedException{
		JsonDataImage jsonDataImage = composerMapper.mapFileToJsonDataImage(files.get(FileType.COMPOSER_JSON));
		DependencyReflectionCollection lockDataImage = composerMapper.mapFileToDependencyReflectionData(files.get(FileType.COMPOSER_LOCK));
		transport.setObject(JsonDataImage.class, jsonDataImage);
		transport.setObject(DependencyReflectionCollection.class, lockDataImage);
		return rowDataMapper.mapData(buildData, transport);
	}
}
