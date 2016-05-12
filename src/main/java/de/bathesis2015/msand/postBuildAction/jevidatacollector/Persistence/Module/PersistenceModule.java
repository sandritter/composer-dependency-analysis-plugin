package de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Module;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

import de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Mapper.SqliteDataMapper;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Mapper.Interface.DatabaseMapper;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Database.SQliteLoader;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Database.SQliteStorer;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Database.Factory.DataLoaderFactory;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Database.Factory.DataStorageFactory;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Database.Interface.DataLoader;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Database.Interface.DataStorage;

/**
 * FileLoadModule.java 
 * dependency configuration of persistence objects
 *
 * @author Michael Sandritter
 */
public class PersistenceModule extends AbstractModule {

	@Override
	protected void configure()
	{
		install(new FactoryModuleBuilder().implement(DataLoader.class, SQliteLoader.class)
				.build(DataLoaderFactory.class));

		bind(DatabaseMapper.class).to(SqliteDataMapper.class);

		install(new FactoryModuleBuilder().implement(DataStorage.class, SQliteStorer.class)
				.build(DataStorageFactory.class));
	}
}
