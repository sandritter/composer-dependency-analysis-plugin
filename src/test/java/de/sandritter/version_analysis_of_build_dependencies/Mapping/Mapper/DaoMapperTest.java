package de.sandritter.version_analysis_of_build_dependencies.Mapping.Mapper;

import static org.junit.Assert.*;

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
	public void shouldMapData(){
		Transferable tranport = mapper.mapData(buildData, transport);
		assertNotNull(tranport);
	}
	
	@Test
	public void shouldMapDependency(){
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
	public void shouldMapStand(){
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
	public void shouldMapBuild(){
		Transferable tranport = mapper.mapData(buildData, transport);
		Build build = (Build) tranport.getObject(Build.class);
		assertEquals(buildData.getBuildId(), build.getBuildId());
		assertEquals(buildData.getNumber(), build.getNumber());
		assertEquals(buildData.getJobName(), build.getJobName());
		assertEquals(buildData.getTimestamp(), build.getTimestamp());
	}
	
	@Test
	public void shouldMapComponent(){
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
	
}
