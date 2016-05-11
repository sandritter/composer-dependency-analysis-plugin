package de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Facade.Interface;

import java.io.File;
import java.util.Map;

import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Transfer.BuildData;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Transfer.Interface.Transferable;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Enum.FileType;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Exception.DataMappingFailedException;

public interface Mapping {
	
	public Transferable mapRowData(BuildData buildData, Map<FileType, File> files) throws DataMappingFailedException;
}
