package de.sandritter.version_analysis_of_build_dependencies.DependencyConfiguration.Persistence.IO.Module;

import com.google.inject.AbstractModule;

import de.sandritter.version_analysis_of_build_dependencies.DependencyConfiguration.Persistence.IO.Interface.IOAccess;
import de.sandritter.version_analysis_of_build_dependencies.Persistence.IO.FileLoader;

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
