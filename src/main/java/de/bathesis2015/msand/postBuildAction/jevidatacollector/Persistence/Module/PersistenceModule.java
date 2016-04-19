package de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Module;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

import de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Mapper.SqliteDataMapper;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Mapper.Interface.DatabaseMapper;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Database.DBLoader;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Database.DBStorage;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Database.Factory.DataLoaderFactory;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Database.Factory.DataStorageFactory;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Database.Interface.DataLoader;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Database.Interface.DataStorage;

/**
 * FileLoadModule.java
 * dependency configuration of persistence objects
 *
 * @author Michael Sandritter
 * 10.09.2015
 */
public class PersistenceModule extends AbstractModule{

	@Override
	protected void configure() {
		install(new FactoryModuleBuilder()
			     .implement(DataLoader.class, DBLoader.class)
			     .build(DataLoaderFactory.class));
		bind(DatabaseMapper.class).to(SqliteDataMapper.class);
		install(new FactoryModuleBuilder()
			     .implement(DataStorage.class, DBStorage.class)
			     .build(DataStorageFactory.class));
	}
}
