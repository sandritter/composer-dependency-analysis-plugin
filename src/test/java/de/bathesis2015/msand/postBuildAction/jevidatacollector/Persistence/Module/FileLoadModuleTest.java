package de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Module;

import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.IO.Interface.IOAccess;

public class FileLoadModuleTest {

	@Test
	public void shouldCreateFileLoadModule()
	{
		Injector injector = Guice.createInjector(new FileLoadModule());
		@SuppressWarnings("unused")
		IOAccess fileLoader = injector.getInstance(IOAccess.class);
	}
}
