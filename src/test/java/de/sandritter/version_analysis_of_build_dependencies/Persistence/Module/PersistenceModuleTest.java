package de.sandritter.version_analysis_of_build_dependencies.Persistence.Module;

import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.sandritter.version_analysis_of_build_dependencies.DependencyConfiguration.Persistence.Database.Interface.DataLoader;
import de.sandritter.version_analysis_of_build_dependencies.DependencyConfiguration.Persistence.Database.Interface.DataStorage;
import de.sandritter.version_analysis_of_build_dependencies.DependencyConfiguration.Persistence.Database.Module.PersistenceModule;
import de.sandritter.version_analysis_of_build_dependencies.Persistence.Database.Factory.DataLoaderFactory;
import de.sandritter.version_analysis_of_build_dependencies.Persistence.Database.Factory.DataStorageFactory;

public class PersistenceModuleTest {

	@Test
	public void shouldCreatePersistenceModule()
	{
		Injector injector = Guice.createInjector(new PersistenceModule());
		
		DataStorageFactory dataStorageFactory = injector.getInstance(DataStorageFactory.class);
		@SuppressWarnings("unused")
		DataStorage dataStorage = dataStorageFactory.create("");
		
		DataLoaderFactory dataLoaderFactory = injector.getInstance(DataLoaderFactory.class);
		@SuppressWarnings("unused")
		DataLoader dataLoader = dataLoaderFactory.create("");
	}
}
