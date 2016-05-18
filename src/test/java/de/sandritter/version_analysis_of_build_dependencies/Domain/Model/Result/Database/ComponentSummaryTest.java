package de.sandritter.version_analysis_of_build_dependencies.Domain.Model.Result.Database;

import static org.junit.Assert.*;

import org.junit.Test;

public class ComponentSummaryTest {

	@Test
	public void shouldGetLightReference()
	{
		ComponentSummary component = new ComponentSummary();
		component.setReference("0512bc3c6cbd15e2c7d2a76547b3af21c");
		assertEquals("0512bc3***", component.getLightReference());
	}
}
