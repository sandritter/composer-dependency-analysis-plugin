package de.sandritter.version_analysis_of_build_dependencies.Persistence.IO.Interface;

import java.io.File;
import java.io.IOException;

/**
 * IOAccess.java 
 * interface that can be implemented from objects that needs to
 * load files from the file system
 *
 * @author Michael Sandritter
 */
public interface IOAccess {

	/**
	 * is loading a File from the file system by given file path
	 * 
	 * @return {@link File}
	 * @throws IOException
	 */
	public File load(String path) throws IOException;
}
