package de.sandritter.version_analysis_of_build_dependencies.Mapping.Mapper;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.Result.ResultSetBuilder;
import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.Result.Database.BuildSummary;
import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.Result.Database.ComponentSummary;
import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.Result.Database.DependentComponent;
import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.Transfer.Interface.Transferable;
import de.sandritter.version_analysis_of_build_dependencies.Mapping.Mapper.SqliteDataMapper;
import de.sandritter.version_analysis_of_build_dependencies.Persistence.Database.Enum.Field;

public class SqliteDataMapperTest {
	
	private SqliteDataMapper mapper;
	private ResultSetBuilder builder;
	
	@Before
	public void setUp(){
		mapper = new SqliteDataMapper();
		builder = new ResultSetBuilder();
	}
	
	@Test
	public void testDependentComponentMapping(){
		Transferable t = null;
		try {
			t = mapper.mapResult(builder.getMock(DependentComponent.class), DependentComponent.class);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		@SuppressWarnings("unchecked")
		Map<String, DependentComponent> map = (Map<String, DependentComponent>) t.getMap(DependentComponent.class);
		for (DependentComponent c : map.values()){			
			assertEquals(Field.BUILD_ID.toString(), c.getBuildId());
			assertEquals((long) 1, c.getTimeStamp());
			assertEquals(1, c.getBuildNumber());
			assertEquals(Field.JOB_NAME.toString(), c.getJobName());
			assertEquals(Field.COMPONENT_NAME.toString(), c.getComponentName());
			assertEquals(Field.REFERENCE.toString(), c.getReference());
			assertEquals(Field.VERSION.toString(), c.getVersion());
		}
	}
	
	@Test
	public void testBuildSummaryMapping(){
		Transferable t = null;
		try {
			t = mapper.mapResult(builder.getMock(BuildSummary.class), BuildSummary.class);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		BuildSummary build = (BuildSummary) t.getObject(BuildSummary.class);
		assertEquals(Field.BUILD_ID.toString(), build.getBuildId());
		assertEquals((long) 1234, build.getTime_stamp());
		assertEquals(1234, build.getBuildNumber());
		assertEquals(Field.JOB_NAME.toString(), build.getJobName());
	}

	
	@Test
	public void testComponentSummaryMapping(){
		ResultSetBuilder b = new ResultSetBuilder();
		Transferable t = null;
		try {
			t = mapper.mapResult(b.getMock(ComponentSummary.class), ComponentSummary.class);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ComponentSummary c = (ComponentSummary) t.getObject(ComponentSummary.class);
		assertEquals(Field.COMPONENT.toString(), c.getComponentName());
		assertEquals(Field.VC_TYPE.toString(), c.getSourceType());
		assertEquals(Field.VC_URL.toString(), c.getSourceUrl());
		assertEquals(Field.BUILD_ID.toString(), c.getBuildId());
		assertEquals(Field.REFERENCE.toString(), c.getReference());
		assertEquals(Field.VERSION.toString(), c.getVersion());
		assertEquals(Field.DEP_TYPE.toString(), c.getDependencyType());
	}
}
