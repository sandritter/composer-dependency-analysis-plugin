package de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.IO;

import java.io.File;
import java.io.IOException;

/**
 * ComposerFileRepository.java
 * is responsible to load files by given path
 *
 * @author Michael Sandritter
 * 10.09.2015
 */
public class FileLoader implements IOAccess{
	
	@Override
	public File load(String path) throws IOException{
		
		File f = new File(path);
		if (f.exists()){			
			return f;
		} else {
			throw new IOException("The requested file: " + path + " does not exist");
		}
	}
}
