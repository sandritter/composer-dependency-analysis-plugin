package de.sandritter.version_analysis_of_build_dependencies.Domain.Model.DependencyReflection;

import static org.junit.Assert.*;

import org.junit.Test;

public class DependencyReflectionTest {

	@Test
	public void shouldNotHaveNameWithEmptyStringValue()
	{
		DependencyReflection reflection = new DependencyReflection();
		assertFalse(reflection.hasName());
	}
}
