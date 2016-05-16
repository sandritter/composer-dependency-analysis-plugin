package de.sandritter.version_analysis_of_build_dependencies.Mapping.Facade.Module;

import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.sandritter.version_analysis_of_build_dependencies.Mapping.Facade.MappingFacade;
import de.sandritter.version_analysis_of_build_dependencies.Mapping.Module.MappingModule;

public class MappingModuleTest {

	@Test
	public void shouldCreateMappingModule()
	{
		Injector injector = Guice.createInjector(new MappingModule());
		@SuppressWarnings("unused")
		MappingFacade mappingFacade = injector.getInstance(MappingFacade.class);
	}
}
