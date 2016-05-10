package de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Mapper;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.DAO.Build;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.DAO.Component;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.DAO.Dependency;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.DAO.Stand;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.DependencyReflection.JsonDataImage;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.DependencyReflection.DependencyReflectionCollection;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.DependencyReflection.DependencyReflection;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Transfer.BuildData;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Transfer.Interface.Transferable;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Enum.DependencyType;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Mapper.Interface.RowDataMapper;

/**
 * 
 * DaoMapper.java
 * This object maps all collected data to data access objects.
 * The created data access objects are the relational image of the database schema
 *
 * @author Michael Sandritter
 *
 */
public class DaoMapper implements RowDataMapper {

	private Transferable transport;
	private List<Dependency> dependencyList;
	private List<Stand> standList;
	private List<Component> componentList;

	@Inject
	public DaoMapper(Transferable transport) {
		this.transport = transport;
		this.componentList = new ArrayList<Component>();
		this.dependencyList = new ArrayList<Dependency>();
		this.standList = new ArrayList<Stand>();
	}

	@Override
	public Transferable mapData(BuildData buildData, Transferable transport) {
		DependencyReflectionCollection lockDataImage = (DependencyReflectionCollection) transport.getObject(DependencyReflectionCollection.class);
		JsonDataImage jsonDataImage = (JsonDataImage) transport.getObject(JsonDataImage.class);
		generateORLists(buildData, jsonDataImage, lockDataImage);
		generateORObjects(buildData, jsonDataImage, lockDataImage);
		return this.transport;
	}

	/**
	 * generates list of data access objets and stores it into the transferable object {@link Transferable}
	 * 
	 * @param buildData {@link BuildData}
	 * @param jsonDataImage {@link JsonDataImage}
	 * @param lockDataImage {@link DependencyReflectionCollection}
	 */
	private void generateORLists(BuildData buildData, JsonDataImage jsonDataImage, DependencyReflectionCollection lockDataImage) {
		
		List<DependencyReflection> packages = lockDataImage.getPackages();
		generateDependencies(packages, buildData.getBuildId(), DependencyType.DIRECT);
	
		componentList.add(new Component(jsonDataImage.getName(), buildData.getSourceType(), buildData.getSourceUrl()));
		standList.add(new Stand(buildData.getVersion(), buildData.getRevision(), jsonDataImage.getName()));
		dependencyList.add(new Dependency(buildData.getRevision(), buildData.getBuildId(), DependencyType.MAIN.toString()));
		transport.setList(Component.class, componentList);
		transport.setList(Stand.class, standList);
		transport.setList(Dependency.class, dependencyList);
	}

	
	/**
	 * generates list of data access objects
	 * @param lst - list of {@link DependencyReflection}
	 * @param buildId - build identifier
	 * @param type {@link DependencyType}
	 */
	private void generateDependencies(List<DependencyReflection> lst, String buildId, DependencyType type){
		for (DependencyReflection pkg : lst){
			String reference = pkg.getSource().getReference().replace("/trunk/@", "");
			dependencyList.add(new Dependency(reference, buildId, type.toString()));
			componentList.add(new Component(pkg.getName(), pkg.getSource().getType(), pkg.getSource().getUrl()));
			standList.add(new Stand(pkg.getVersion(), reference, pkg.getName()));
		}
		
	}

	/**
	 * generates DAOs and stores it into the transferable object
	 * 
	 * @param buildData
	 * @param jsonDataImage
	 * @param lockDataImage
	 */
	private void generateORObjects(BuildData buildData, JsonDataImage jsonDataImage, DependencyReflectionCollection lockDataImage) {
		transport.setObject(Build.class, new Build(buildData.getBuildId(), buildData.getTimestamp(),
				buildData.getNumber(), buildData.getJobName()));
	}
}
