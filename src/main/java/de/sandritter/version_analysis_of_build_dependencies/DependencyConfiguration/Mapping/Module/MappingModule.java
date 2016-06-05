package de.sandritter.version_analysis_of_build_dependencies.DependencyConfiguration.Mapping.Module;

import com.google.inject.AbstractModule;

import de.sandritter.version_analysis_of_build_dependencies.DependencyConfiguration.Mapping.Interface.DependencyReflectionMapable;
import de.sandritter.version_analysis_of_build_dependencies.DependencyConfiguration.Mapping.Interface.RowDataMapper;
import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.Transfer.Transport;
import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.Transfer.Interface.Transferable;
import de.sandritter.version_analysis_of_build_dependencies.Mapping.Facade.MappingFacade;
import de.sandritter.version_analysis_of_build_dependencies.Mapping.Mapper.DaoMapper;
import de.sandritter.version_analysis_of_build_dependencies.Mapping.Mapper.DependencyReflectionMapper;

/**
 * MappingModule.java
 * dependency configuration of the {@link MappingFacade}
 *
 * @author Michael Sandritter
 */
public class MappingModule extends AbstractModule{

	@Override
	protected void configure() 
	{
		bind(DependencyReflectionMapable.class).to(DependencyReflectionMapper.class);
		bind(RowDataMapper.class).to(DaoMapper.class);
		bind(Transferable.class).to(Transport.class);
	}
}
