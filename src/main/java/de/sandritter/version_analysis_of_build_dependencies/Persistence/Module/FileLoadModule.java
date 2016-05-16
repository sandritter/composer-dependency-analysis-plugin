package de.sandritter.version_analysis_of_build_dependencies.Persistence.Module;

import com.google.inject.AbstractModule;

import de.sandritter.version_analysis_of_build_dependencies.Persistence.IO.FileLoader;
import de.sandritter.version_analysis_of_build_dependencies.Persistence.IO.Interface.IOAccess;

/**
 * FileLoadModule.java 
 * dependency configuration of the IOAccess
 *
 * @author Michael Sandritter
 */
public class FileLoadModule extends AbstractModule {

	@Override
	protected void configure()
	{
		bind(IOAccess.class).to(FileLoader.class);
	}

}
