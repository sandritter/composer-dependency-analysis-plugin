package de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Module;

import com.google.inject.AbstractModule;

import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Transfer.Transport;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Transfer.Interface.Transferable;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Facade.MappingFacade;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Mapper.DependencyReflectionMapper;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Mapper.DaoMapper;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Mapper.Interface.DependencyReflectionMapable;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Mapper.Interface.RowDataMapper;

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
