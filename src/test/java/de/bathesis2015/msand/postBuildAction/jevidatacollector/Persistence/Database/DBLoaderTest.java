package de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Database;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Result.Database.BuildSummary;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Result.Database.ComponentSummary;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Result.Database.DependentComponent;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Transfer.Interface.Transferable;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Enum.DependencyType;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Database.Factory.DataLoaderFactory;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Database.Interface.DataLoader;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Module.PersistenceModule;

public class DBLoaderTest {

	private DataLoader dataLoader;
	
	@Before
	public void setUp() throws IOException, InterruptedException {
		
		// get path of database placed in src/test/resources
		ClassLoader loader = getClass().getClassLoader();
		String dbPath = loader.getResource("jevi.db").getPath(); 
		
		Injector injector = Guice.createInjector(new PersistenceModule());
		DataLoaderFactory dataLoaderFactory = injector.getInstance(DataLoaderFactory.class);
		this.dataLoader = dataLoaderFactory.create(dbPath);
	}
	
	
	@Test
	public void shouldLoadBuild() throws Exception{
		Transferable t = dataLoader.loadBuild("ref-k3-01");
		BuildSummary build = (BuildSummary) t.getObject(BuildSummary.class);
		assertNotNull(build);
		assertEquals("b-k3-01", build.getBuildId());
		assertEquals((long)144936, build.getTime_stamp());
		assertEquals(1, build.getBuildNumber());
		assertEquals("Job3", build.getJobName());
	}
	
	@Test
	public void shouldLoadMainComponent() throws Exception{
		Transferable t = dataLoader.loadMainComponent("b-k3-01");
		ComponentSummary component = (ComponentSummary) t.getObject(ComponentSummary.class);
		assertNotNull(component);
		assertEquals("b-k3-01", component.getBuildId());
		assertEquals(-1, component.getTimestamp());
		assertEquals("git", component.getSourceType());
		assertEquals("https://github.com/Sandritter/k3.git", component.getSourceUrl());
		assertEquals("Warenkorb", component.getComponentName());
		assertEquals("main", component.getDependencyType());
		assertEquals("ref-k3-01", component.getReference());
		assertEquals("3.0", component.getVersion());
	}
	
	@Test
	public void shouldLoadAllDependencies() throws Exception{
		Transferable t = dataLoader.loadDependencies("b-k3-01", DependencyType.ALL);
		@SuppressWarnings("unchecked")
		Map<String, ComponentSummary> totalSet = (Map<String, ComponentSummary>) t.getMap(ComponentSummary.class);
		assertEquals(3, totalSet.size());
	}
	
	@Test
	public void shouldLoadDependentComponents() throws Exception{
		Transferable t = dataLoader.loadDependentComponents("Kunde");
		@SuppressWarnings("unchecked")
		Map<String, DependentComponent> totalSet = (Map<String, DependentComponent>) t.getMap(DependentComponent.class);
		assertEquals(4, totalSet.size());
	}
	
	@Test
	public void shouldLoadHighLevelDependencies() throws Exception{
		Transferable t = dataLoader.loadDependencies("b-k3-01", DependencyType.HIGH_LEVEL);
		@SuppressWarnings("unchecked")
		Map<String, ComponentSummary> subSet = (Map<String, ComponentSummary>) t.getMap(ComponentSummary.class);
		assertEquals(2, subSet.size());
	}
}
