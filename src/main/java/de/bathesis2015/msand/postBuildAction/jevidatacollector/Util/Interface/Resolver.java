package de.bathesis2015.msand.postBuildAction.jevidatacollector.Util.Interface;

import de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Enum.FileType;

/**
 * 
 * @author Michael Sandritter
 *
 */
public interface Resolver {

	/**
	 * 
	 * @param fileType
	 * @param relativePath
	 * @return
	 */
	public String getAbsolutePath(FileType fileType, String relativePath);
}
