package de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Mapper;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import DataBuilder.Composer.JsonDataImageBuilder;
import DataBuilder.Composer.LockDataImageBuilder;
import DataBuilder.Transfer.BuildDataBuilder;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.DAO.Build;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.DAO.Component;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.DAO.Dependency;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.DAO.Stand;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.DependencyReflection.JsonDataImage;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.DependencyReflection.DependencyReflectionCollection;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Transfer.BuildData;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Transfer.Transport;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Transfer.Interface.Transferable;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Mapper.DaoMapper;

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
	public void testTransferableIsNotNull(){
		Transferable tranport = mapper.mapData(buildData, transport);
		assertNotNull(tranport);
	}
	
	@Test
	public void testDependencyMapping(){
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
	public void testStandMapping(){
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
	public void testBuildMapping(){
		Transferable tranport = mapper.mapData(buildData, transport);
		Build build = (Build) tranport.getObject(Build.class);
		assertEquals(buildData.getBuildId(), build.getBuildId());
		assertEquals(buildData.getNumber(), build.getNumber());
		assertEquals(buildData.getJobName(), build.getJobName());
		assertEquals(buildData.getTimestamp(), build.getTimestamp());
	}
	
	@Test
	public void testComponentMapping(){
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
