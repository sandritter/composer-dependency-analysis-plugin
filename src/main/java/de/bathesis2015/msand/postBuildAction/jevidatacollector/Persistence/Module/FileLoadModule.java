package de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Module;

import com.google.inject.AbstractModule;

import de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.IO.FileLoader;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.IO.IOAccess;

/**
 * FileLoadModule.java
 * dependency configuration of the IOAccess
 *
 * @author Michael Sandritter
 * 10.09.2015
 */
public class FileLoadModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(IOAccess.class).to(FileLoader.class);
	}

}
