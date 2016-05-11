package de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Facade.Module;

import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Facade.MappingFacade;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Module.MappingModule;

public class MappingModuleTest {

	@Test
	public void shouldCreateMappingModule()
	{
		Injector injector = Guice.createInjector(new MappingModule());
		@SuppressWarnings("unused")
		MappingFacade mappingFacade = injector.getInstance(MappingFacade.class);
	}
}
