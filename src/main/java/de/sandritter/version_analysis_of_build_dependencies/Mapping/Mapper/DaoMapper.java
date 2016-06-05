package de.sandritter.version_analysis_of_build_dependencies.Mapping.Mapper;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

import de.sandritter.version_analysis_of_build_dependencies.DependencyConfiguration.Mapping.Interface.RowDataMapper;
import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.DAO.Build;
import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.DAO.Component;
import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.DAO.Dependency;
import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.DAO.Stand;
import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.DependencyReflection.DependencyReflection;
import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.DependencyReflection.DependencyReflectionCollection;
import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.DependencyReflection.JsonDataImage;
import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.Transfer.BuildData;
import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.Transfer.Interface.Transferable;
import de.sandritter.version_analysis_of_build_dependencies.Mapping.Enum.DependencyType;
import de.sandritter.version_analysis_of_build_dependencies.Mapping.Exception.DataMappingFailedException;

/**
 * DaoMapper.java
 * This object maps all collected data to data access objects.
 * The created data access objects are the relational image of the database schema
 *
 * @author Michael Sandritter
 */
public class DaoMapper implements RowDataMapper {

	/**
	 * {@link Transferable}
	 */
	private Transferable transport;
	
	/**
	 * List<{@link Dependency}>
	 */
	private List<Dependency> dependencyList;
	
	/**
	 * List<{@link Stand}>
	 */
	private List<Stand> standList;
	
	/**
	 * List<{@link Component}>
	 */
	private List<Component> componentList;

	@Inject
	public DaoMapper(Transferable transport) {
		this.transport = transport;
		this.componentList = new ArrayList<Component>();
		this.dependencyList = new ArrayList<Dependency>();
		this.standList = new ArrayList<Stand>();
	}

	@Override
	public Transferable mapData(BuildData buildData, Transferable transport) throws DataMappingFailedException {
		
		try {
			DependencyReflectionCollection lockDataImage = (DependencyReflectionCollection) transport.getObject(DependencyReflectionCollection.class);
			JsonDataImage jsonDataImage = (JsonDataImage) transport.getObject(JsonDataImage.class);
			generateORLists(buildData, jsonDataImage, lockDataImage);
			generateORObjects(buildData, jsonDataImage, lockDataImage);
		} catch(Exception e) {
			throw new DataMappingFailedException("[DaoMapper.java]: DataMapping failed because some buildData value is null", e);
		}
		return this.transport;
	}

	/**
	 * generates list of data access objets and stores it into the transferable object {@link Transferable}
	 * 
	 * @param buildData {@link BuildData}
	 * @param jsonDataImage {@link JsonDataImage}
	 * @param lockDataImage {@link DependencyReflectionCollection}
	 * @throws Exception 
	 */
	private void generateORLists(BuildData buildData, JsonDataImage jsonDataImage, DependencyReflectionCollection lockDataImage) throws Exception 
	{
		try {
			componentList.add(new Component(jsonDataImage.getName(), buildData.getSourceType(), buildData.getSourceUrl()));
			standList.add(new Stand(buildData.getVersion(), buildData.getRevision(), jsonDataImage.getName()));
			dependencyList.add(new Dependency(buildData.getRevision(), buildData.getBuildId(), DependencyType.MAIN.toString()));
		} catch (Exception e) {
			throw new Exception("JsonDataImage", e);
		}
		
		try {
			List<DependencyReflection> packages = lockDataImage.getPackages();
			generateDependencies(packages, buildData.getBuildId(), DependencyType.HIGH_LEVEL);
			
			transport.setList(Component.class, componentList);
			transport.setList(Stand.class, standList);
			transport.setList(Dependency.class, dependencyList);
		} catch (Exception e) {
			throw new Exception("generateOrLists", e);
		}
	}

	
	/**
	 * generates list of data access objects
	 * @param lst - list of {@link DependencyReflection}
	 * @param buildId - build identifier
	 * @param type {@link DependencyType}
	 * @throws Exception 
	 */
	private void generateDependencies(List<DependencyReflection> lst, String buildId, DependencyType type) throws Exception{
		try {
			for (int i = 0; i < lst.size() -1; i++) {
				DependencyReflection pkg = lst.get(i);
				String reference = "";
				try {
					reference = pkg.getSource().getReference();
				} catch (NullPointerException e) {
					System.out.println(pkg.getName());
				}
				dependencyList.add(new Dependency(reference, buildId, type.toString()));
				componentList.add(new Component(pkg.getName(), pkg.getSource().getType(), pkg.getSource().getUrl()));
				standList.add(new Stand(pkg.getVersion(), reference, pkg.getName()));
			}
		} catch (Exception e) {
			throw new Exception("generateOrLists", e);
		}
		
	}

	/**
	 * generates DAOs and stores it into the transferable object
	 * 
	 * @param buildData
	 * @param jsonDataImage
	 * @param lockDataImage
	 * @throws Exception 
	 */
	private void generateORObjects(BuildData buildData, JsonDataImage jsonDataImage, DependencyReflectionCollection lockDataImage) throws Exception {
		try {
			transport.setObject(Build.class, new Build(buildData.getBuildId(), buildData.getTimestamp(),
					buildData.getNumber(), buildData.getJobName(), buildData.getJobUrl()));
		} catch (Exception e) {
			throw new Exception("generateOrLists", e);
		}
	}
}
