package de.sandritter.version_analysis_of_build_dependencies.Persistence.Module;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

import de.sandritter.version_analysis_of_build_dependencies.Mapping.Mapper.SqliteDataMapper;
import de.sandritter.version_analysis_of_build_dependencies.Mapping.Mapper.Interface.DatabaseMapper;
import de.sandritter.version_analysis_of_build_dependencies.Persistence.Database.SQliteLoader;
import de.sandritter.version_analysis_of_build_dependencies.Persistence.Database.SQliteStorer;
import de.sandritter.version_analysis_of_build_dependencies.Persistence.Database.Factory.DataLoaderFactory;
import de.sandritter.version_analysis_of_build_dependencies.Persistence.Database.Factory.DataStorageFactory;
import de.sandritter.version_analysis_of_build_dependencies.Persistence.Database.Interface.DataLoader;
import de.sandritter.version_analysis_of_build_dependencies.Persistence.Database.Interface.DataStorage;

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
