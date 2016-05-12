package de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Facade.Interface;

import java.io.File;
import java.util.Map;

import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Transfer.BuildData;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Transfer.Interface.Transferable;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Enum.FileType;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Exception.DataMappingFailedException;

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
