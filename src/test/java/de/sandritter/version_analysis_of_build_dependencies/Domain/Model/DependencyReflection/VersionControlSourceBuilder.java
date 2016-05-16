package de.sandritter.version_analysis_of_build_dependencies.Domain.Model.DependencyReflection;

import static org.mockito.Mockito.*;

import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.DependencyReflection.VersionControlSource;

public class VersionControlSourceBuilder {

	public VersionControlSource getMock(String reference, String sourceUrl, String sourceType){
		VersionControlSource source = mock(VersionControlSource.class);
		when(source.getReference()).thenReturn(reference);
		when(source.getType()).thenReturn(sourceType);
		when(source.getUrl()).thenReturn(sourceUrl);
		return source;
	}
}
