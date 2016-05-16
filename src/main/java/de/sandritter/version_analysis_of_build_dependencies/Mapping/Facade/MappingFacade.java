package de.sandritter.version_analysis_of_build_dependencies.Mapping.Facade;

import java.io.File;
import java.util.Map;

import com.google.inject.Inject;

import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.DependencyReflection.DependencyReflectionCollection;
import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.DependencyReflection.JsonDataImage;
import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.Transfer.BuildData;
import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.Transfer.Interface.Transferable;
import de.sandritter.version_analysis_of_build_dependencies.Mapping.Enum.FileType;
import de.sandritter.version_analysis_of_build_dependencies.Mapping.Exception.DataMappingFailedException;
import de.sandritter.version_analysis_of_build_dependencies.Mapping.Facade.Interface.Mapping;
import de.sandritter.version_analysis_of_build_dependencies.Mapping.Mapper.Interface.DependencyReflectionMapable;
import de.sandritter.version_analysis_of_build_dependencies.Mapping.Mapper.Interface.RowDataMapper;

/**
 * 
 * MappingFacade.java
 * This object is used as facade for the mapping operations to create the data access objects
 *
 * @author Michael Sandritter
 *
 */
public class MappingFacade implements Mapping{
	
	private DependencyReflectionMapable composerMapper;
	private RowDataMapper rowDataMapper;
	private Transferable transport;
	
	/**
	 * constructor
	 * @param mapper {@link DependencyReflectionMapable}
	 * @param orMapper {@link RowDataMapper}
	 * @param transport {@link Transferable}
	 */
	@Inject
	MappingFacade(DependencyReflectionMapable mapper, RowDataMapper orMapper, Transferable transport){
		this.composerMapper = mapper;
		this.rowDataMapper = orMapper;
		this.transport = transport;
	}
	
	/**
	 * is mapping row data to data access objects
	 * 
	 * @param buildData {@link BuildData}
	 * @param files - map of dependency reflection files
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
