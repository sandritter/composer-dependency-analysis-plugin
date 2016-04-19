package de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Mapper.Interface;

import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Transfer.BuildData;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Transfer.Interface.Transferable;

/**
 * 
 * RowDataMapper.java
 * This class is mapping all extracted build- and version-information 
 * to data access objects
 *
 * @author Michael Sandritter
 *
 */
public interface RowDataMapper {
	
	/**
	 * maps row data to data access objects
	 * @param buildData {@link BuildData}
	 * @return Transferable {@link Transferable}
	 */
	public Transferable mapData(BuildData buildData, Transferable transport);
}
