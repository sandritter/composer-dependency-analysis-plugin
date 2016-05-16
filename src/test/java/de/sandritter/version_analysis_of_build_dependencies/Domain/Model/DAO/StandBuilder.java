package de.sandritter.version_analysis_of_build_dependencies.Domain.Model.DAO;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.DAO.Stand;

public class StandBuilder {
	
	public Stand getMock(String reference, String version, String componentName){
		Stand source = mock(Stand.class);
		when(source.getReference()).thenReturn(reference);
		when(source.getVersion()).thenReturn(version);
		when(source.getComponentName()).thenReturn(componentName);
		return source;
	}
}
