package de.sandritter.version_analysis_of_build_dependencies.Mapping.Facade.Interface;

import java.io.File;
import java.util.Map;

import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.Transfer.BuildData;
import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.Transfer.Interface.Transferable;
import de.sandritter.version_analysis_of_build_dependencies.Mapping.Enum.FileType;
import de.sandritter.version_analysis_of_build_dependencies.Mapping.Exception.DataMappingFailedException;

/**
 * interface that describes the mapping facade used to combine all mapper
 * 
 * @author Michael Sandritter
 */
public interface Mapping {
	
	/**
	 * maps row data to data-access-objects
	 *
	 * @param buildData build specific information
	 * @param files depedency reflection files
	 * @return {@link Transferable}
	 * @throws DataMappingFailedException
	 */
	public Transferable mapRowData(BuildData buildData, Map<FileType, File> files) throws DataMappingFailedException;
}
