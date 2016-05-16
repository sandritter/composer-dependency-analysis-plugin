package de.sandritter.version_analysis_of_build_dependencies.Util.Interface;

import de.sandritter.version_analysis_of_build_dependencies.Mapping.Enum.FileType;

/**
 * interface that defines methods which create absolute paths of given files
 * 
 * @author Michael Sandritter
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
