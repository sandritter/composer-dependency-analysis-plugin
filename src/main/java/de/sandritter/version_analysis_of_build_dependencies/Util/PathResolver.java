package de.sandritter.version_analysis_of_build_dependencies.Util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import de.sandritter.version_analysis_of_build_dependencies.Mapping.Enum.FileType;
import de.sandritter.version_analysis_of_build_dependencies.Util.Interface.Resolver;
import hudson.FilePath;
import hudson.model.AbstractBuild;

/**
 * PathResolver.java
 * resolves the absolute paths of files by given file and build
 * 
 * @author Michael Sandritter
 */
public class PathResolver implements Resolver {
	
	/**
	 */
	public PathResolver(){}

	/**
	 * 
	 * @param fileType
	 * @param relativePath
	 * @return
	 */
	public String resolveAbsolutePath(FileType fileType, String absolutePath, String relativePath)
	{
		for (FileType type : FileType.values()) {
			if (type == fileType && relativePath.equals("default")) {
				return getAbsolute("source/" + fileType.toString(), absolutePath);
			}
		}
		return getAbsolute(relativePath, absolutePath);
	}

	/**
	 * concatenates a file name and directory and returns a absolute path of
	 * that file
	 * 
	 * @param value relative path
	 * @param workspace workspace path
	 * @return absolute path
	 */
	private String getAbsolute(String value, String workspace)
	{
		File f = new File(value);
		File workspacePath = new File(workspace);
		File absolutePath = new File(workspacePath, value);
		if (f.isFile() && f.isAbsolute()) {
			return f.getAbsolutePath();
		}
		return absolutePath.getAbsolutePath();
	}

}
