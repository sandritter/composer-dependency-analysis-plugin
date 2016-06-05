package de.sandritter.version_analysis_of_build_dependencies.Mapping.Mapper;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.DAO.Build;
import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.DAO.Component;
import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.DAO.Dependency;
import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.DAO.Stand;
import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.DependencyReflection.DependencyReflectionCollection;
import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.DependencyReflection.JsonDataImage;
import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.DependencyReflection.JsonDataImageBuilder;
import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.DependencyReflection.LockDataImageBuilder;
import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.Transfer.BuildData;
import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.Transfer.BuildDataBuilder;
import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.Transfer.Transport;
import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.Transfer.Interface.Transferable;
import de.sandritter.version_analysis_of_build_dependencies.Mapping.Exception.DataMappingFailedException;
import de.sandritter.version_analysis_of_build_dependencies.Mapping.Mapper.DaoMapper;

public class DaoMapperTest{ 
	
	private BuildData buildData;
	private JsonDataImage jsonImage;
	private DependencyReflectionCollection lockImage;
	private DaoMapper mapper;
	private Transferable transport;
	private final int amountDepenencies = 30;
	
	@Before
	public void setUp(){
		BuildDataBuilder buildDataBuilder = new BuildDataBuilder();
		JsonDataImageBuilder jsonDataImageBuilder = new JsonDataImageBuilder();
		LockDataImageBuilder lockDataImageBuilder = new LockDataImageBuilder();
		this.mapper = new DaoMapper(new Transport());
		this.buildData = buildDataBuilder.getMock(1);
		this.jsonImage = jsonDataImageBuilder.getMock();
		this.lockImage = lockDataImageBuilder.getMock(amountDepenencies);
		this.transport = new Transport();
		this.transport.setObject(JsonDataImage.class, jsonImage);
		this.transport.setObject(DependencyReflectionCollection.class, lockImage);
	}
	
	@Test
	public void shouldMapData() throws DataMappingFailedException{
		Transferable tranport = mapper.mapData(buildData, transport);
		assertNotNull(tranport);
	}
	
	@Test
	public void shouldMapDependency() throws DataMappingFailedException{
		Transferable t = mapper.mapData(buildData, transport);
		@SuppressWarnings("unchecked")
		List<Dependency> lst = (List<Dependency>) t.getList(Dependency.class);
		for (Dependency d : lst){
			assertNotNull(d.getBuildId());
			assertNotNull(d.getReference());
			assertNotNull(d.getType());
		}
		assertEquals(amountDepenencies, lst.size());
	}
	
	@Test
	public void shouldMapStand() throws DataMappingFailedException{
		Transferable t = mapper.mapData(buildData, transport);
		@SuppressWarnings("unchecked")
		List<Stand> lst = (List<Stand>) t.getList(Stand.class);
		for (Stand stand : lst){
			assertNotNull(stand.getReference());
			assertNotNull(stand.getVersion());
			assertNotNull(stand.getComponentName());
		}
		assertEquals(amountDepenencies, lst.size());
	}
	
	@Test
	public void shouldMapBuild() throws DataMappingFailedException{
		Transferable tranport = mapper.mapData(buildData, transport);
		Build build = (Build) tranport.getObject(Build.class);
		assertEquals(buildData.getBuildId(), build.getBuildId());
		assertEquals(buildData.getNumber(), build.getNumber());
		assertEquals(buildData.getJobName(), build.getJobName());
		assertEquals(buildData.getTimestamp(), build.getTimestamp());
		assertEquals(buildData.getJobUrl(), build.getJobUrl());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldWork() throws DataMappingFailedException
	{
		ClassLoader classLoader = getClass().getClassLoader();
		File composerJson = new File(classLoader.getResource("composer.json").getFile());
		File composerLockExtended = new File(classLoader.getResource("composer/composer.lock").getFile());
		
		DependencyReflectionMapper reflectMapper = new DependencyReflectionMapper();
		
		DependencyReflectionCollection col = (DependencyReflectionCollection) reflectMapper.mapData(composerLockExtended, DependencyReflectionCollection.class);
		JsonDataImage json = (JsonDataImage) reflectMapper.mapData(composerJson, JsonDataImage.class);
		
		Transferable transport = new Transport();
		transport.setObject(DependencyReflectionCollection.class, col);
		transport.setObject(JsonDataImage.class, json);
		
		BuildDataBuilder buildDataBuilder = new BuildDataBuilder();
		BuildData buildData = buildDataBuilder.getMock(1);
		
		Transferable t = mapper.mapData(buildData, transport);
		List<Dependency> depList = (List<Dependency>) t.getList(Dependency.class);
		List<Stand> standList = (List<Stand>) t.getList(Stand.class);
		List<Component> componentList = (List<Component>) t.getList(Component.class);
		assertEquals(33, depList.size());
		assertEquals(33, standList.size());
		assertEquals(33, componentList.size());
	}
	
	@Test
	public void shouldMapComponent() throws DataMappingFailedException{
		Transferable t = mapper.mapData(buildData, transport);
		@SuppressWarnings("unchecked")
		List<Component> lst = (List<Component>) t.getList(Component.class);
		for (Component c : lst){
			assertNotNull(c.getName());
			assertNotNull(c.getSourceType());
			assertNotNull(c.getSourceUrl());
		}
		assertEquals(amountDepenencies, lst.size());
	}
	
	@Test(expected=DataMappingFailedException.class) 
	public void shoudThrowDataMappingFailedException() throws DataMappingFailedException{
		BuildData buildData = new BuildData();
		Transferable transport = new Transport();
		@SuppressWarnings("unused")
		Transferable t = mapper.mapData(buildData, transport);
	}
	
}
