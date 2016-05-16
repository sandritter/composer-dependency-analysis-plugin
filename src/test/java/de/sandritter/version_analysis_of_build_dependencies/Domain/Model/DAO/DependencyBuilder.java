package de.sandritter.version_analysis_of_build_dependencies.Domain.Model.DAO;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.DAO.Dependency;

public class DependencyBuilder {
	
	public Dependency getMock(String buildId, String reference, String type){
		Dependency source = mock(Dependency.class);
		when(source.getBuildId()).thenReturn(buildId);
		when(source.getReference()).thenReturn(reference);
		when(source.getType()).thenReturn(type);
		return source;
	}

}
