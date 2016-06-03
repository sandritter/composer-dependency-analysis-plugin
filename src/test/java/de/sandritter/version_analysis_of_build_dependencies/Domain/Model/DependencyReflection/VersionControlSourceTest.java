package de.sandritter.version_analysis_of_build_dependencies.Domain.Model.DependencyReflection;

import static org.junit.Assert.*;

import org.junit.Test;

public class VersionControlSourceTest {

	@Test
	public void shouldNotHaveReferenceWithEmptyString()
	{
		VersionControlSource vcs = new VersionControlSource();
		assertFalse(vcs.hasReference());
	}

}
