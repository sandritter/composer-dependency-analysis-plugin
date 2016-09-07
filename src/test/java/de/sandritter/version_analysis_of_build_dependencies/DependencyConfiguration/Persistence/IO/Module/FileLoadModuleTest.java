package de.sandritter.version_analysis_of_build_dependencies.DependencyConfiguration.Persistence.IO.Module;

import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.sandritter.version_analysis_of_build_dependencies.DependencyConfiguration.Persistence.IO.Interface.IOAccess;
import de.sandritter.version_analysis_of_build_dependencies.DependencyConfiguration.Persistence.IO.Module.FileLoadModule;

public class FileLoadModuleTest {

	@Test
	public void shouldCreateFileLoadModule()
	{
		Injector injector = Guice.createInjector(new FileLoadModule());
		@SuppressWarnings("unused")
		IOAccess fileLoader = injector.getInstance(IOAccess.class);
	}
}
