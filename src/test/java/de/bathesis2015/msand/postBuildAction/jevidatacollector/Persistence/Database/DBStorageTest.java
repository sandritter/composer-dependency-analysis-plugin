package de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Database;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Transfer.Interface.Transferable;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Database.Factory.DataStorageFactory;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Database.Interface.DataStorage;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Module.PersistenceModule;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Util.TestDataProvider;

public class DBStorageTest {

	private DataStorage dataStorage;
	private TestDataProvider dataProvider;

	@Before
	public void setUp() {

		// get path of database
		ClassLoader loader = getClass().getClassLoader();
		String dbPath = loader.getResource("jevi.db").getPath();

		Injector injector = Guice.createInjector(new PersistenceModule());
		DataStorageFactory dataLoaderFactory = injector.getInstance(DataStorageFactory.class);
		this.dataStorage = dataLoaderFactory.create(dbPath);
		
		this.dataProvider = new TestDataProvider();
	}

	@Test
	public void testStorage() throws Exception{
		Transferable t = dataProvider.getStorageData("b01", 1, 4);
		dataStorage.storeData(t);
	}
	
	@Test (expected=Exception.class) 
	public void testStorageThrowsException() throws Exception{
		Transferable t = dataProvider.getStorageData(null, 1, 4);
		dataStorage.storeData(t);
	}
}
