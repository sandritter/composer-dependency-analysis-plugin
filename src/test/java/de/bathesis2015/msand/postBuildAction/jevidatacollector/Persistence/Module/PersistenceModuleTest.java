package de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Module;

import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Database.Factory.DataLoaderFactory;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Database.Factory.DataStorageFactory;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Database.Interface.DataLoader;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Database.Interface.DataStorage;

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
