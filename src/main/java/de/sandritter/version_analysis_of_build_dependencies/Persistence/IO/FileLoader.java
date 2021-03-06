package de.sandritter.version_analysis_of_build_dependencies.Persistence.IO;

import java.io.File;
import java.io.IOException;

import de.sandritter.version_analysis_of_build_dependencies.DependencyConfiguration.Persistence.IO.Interface.IOAccess;

/**
 * ComposerFileRepository.java 
 * is responsible to load files by given path
 *
 * @author Michael Sandritter
 */
public class FileLoader implements IOAccess {

	@Override
	public File load(String path) throws IOException
	{
		File f = new File(path);
		if (f.exists()) {
			return f;
		} else {
			throw new IOException("The requested file: " + path + " does not exist");
		}
	}
}
